package org.zcorp.java1.storage;

import org.zcorp.java1.exception.NotExistStorageException;
import org.zcorp.java1.model.ContactType;
import org.zcorp.java1.model.Resume;
import org.zcorp.java1.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.util.logging.Logger;

// TODO implement Section (except OrganizationSection)
// TODO Join and split ListSection by `\n`
public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        LOG.info("clear");
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.execute("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid = ? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        LOG.warning("Resume " + uuid + " not exist");
                        throw new NotExistStorageException(uuid);
                    }

                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(rs, r);
                    } while (rs.next());

                    return r;
                });
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.<Void>transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    LOG.warning("Resume " + r.getUuid() + " not exist");
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(r);
            insertContacts(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        sqlHelper.<Void>transactionalExecute(conn -> {
                try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    ps.execute();
                }
                insertContacts(conn, r);
                return null;
            }
        );
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.<Void>execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                LOG.warning("Resume " + uuid + " not exist");
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        return sqlHelper.execute(""+
                        "SELECT * FROM resume r\n" +
                        "    LEFT JOIN contact c\n" +
                        "           ON r.uuid = c.resume_uuid\n" +
                        "     ORDER BY full_name, uuid",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    Map<String, Resume> map = new LinkedHashMap<>();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        Resume resume = map.get(uuid);
                        if (resume == null) {
                            resume = new Resume(uuid, rs.getString("full_name"));
                            map.put(uuid, resume);
                        }
                        addContact(rs, resume);
                    }
                    return new ArrayList<>(map.values());
                });
    }

    @Override
    public int size() {
        LOG.info("size");
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Resume r) {
        sqlHelper.<Void>execute("DELETE FROM contact WHERE resume_uuid = ?", ps -> {
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }
}