package org.zcorp.java1.storage;

import org.zcorp.java1.exception.NotExistStorageException;
import org.zcorp.java1.model.*;
import org.zcorp.java1.sql.SqlHelper;

import java.sql.*;
import java.util.*;

import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> {
            DriverManager.registerDriver(new org.postgresql.Driver()); // Регистрация драйвера нужна только в Tomcat, тесты работают и без этого
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        });
    }

    @Override
    public void clear() {
        LOG.info("clear");
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    LOG.warning("Resume " + uuid + " not exist");
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }
            ContactProcessor.get().addAll(conn, resume);
            SectionProcessor.get().addAll(conn, resume);
            return resume;
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
            ContactProcessor.get().deleteAll(conn, r);
            ContactProcessor.get().insertAll(conn, r);
            SectionProcessor.get().deleteAll(conn, r);
            SectionProcessor.get().insertAll(conn, r);
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
            ContactProcessor.get().insertAll(conn, r);
            SectionProcessor.get().insertAll(conn, r);
            return null;
        });
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
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    map.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }
            if (!map.isEmpty()) {
                ContactProcessor.get().fillAllResumes(conn, map);
                SectionProcessor.get().fillAllResumes(conn, map);
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

    private static abstract class Processor {
        abstract void add(ResultSet rs, Resume r) throws SQLException;

        abstract void insertAll(Connection conn, Resume r) throws SQLException;

        abstract String getTableName();

        void addAll(Connection conn, Resume r) throws SQLException {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + getTableName() + " WHERE resume_uuid = ?")) {
                ps.setString(1, r.getUuid());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    add(rs, r);
                }
            }
        }

        void deleteAll(Connection conn, Resume r) throws SQLException {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM " + getTableName() + " WHERE resume_uuid = ?")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }
        }

        void fillAllResumes(Connection conn, Map<String, Resume> map) throws SQLException {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + getTableName())) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    Resume resume = map.get(uuid);
                    add(rs, resume);
                }
            }
        }
    }

    private static class ContactProcessor extends Processor {
        private static final ContactProcessor INSTANCE = new ContactProcessor();

        private ContactProcessor() {
        }

        private static ContactProcessor get() {
            return INSTANCE;
        }

        @Override
        void add(ResultSet rs, Resume r) throws SQLException {
            r.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
        }

        @Override
        void insertAll(Connection conn, Resume r) throws SQLException {
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

        @Override
        String getTableName() {
            return "contact";
        }
    }

    private static class SectionProcessor extends Processor {
        private static final SectionProcessor INSTANCE = new SectionProcessor();

        private SectionProcessor() {
        }

        private static SectionProcessor get() {
            return INSTANCE;
        }

        @Override
        void add(ResultSet rs, Resume r) throws SQLException {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    r.addSection(type, new TextSection(rs.getString("value")));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String value = rs.getString("value");
                    r.addSection(type, new ListSection(Arrays.asList(value.split("\n"))));
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    // TODO for OrganizationSections
                    break;
            }
        }

        @Override
        void insertAll(Connection conn, Resume r) throws SQLException {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?, ?, ?)")) {
                for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                    ps.setString(1, r.getUuid());
                    SectionType type = e.getKey();
                    ps.setString(2, type.name());
                    switch (type) {
                        case PERSONAL:
                        case OBJECTIVE:
                            TextSection textSection = (TextSection) e.getValue();
                            ps.setString(3, textSection.getContent());
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            ListSection listSection = (ListSection) e.getValue();
                            ps.setString(3, String.join("\n", listSection.getItems()));
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            // TODO for OrganizationSections
                            break;
                    }
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }

        @Override
        String getTableName() {
            return "section";
        }
    }
}