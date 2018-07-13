package org.zcorp.java1.sql;

import org.zcorp.java1.exception.ExistStorageException;
import org.zcorp.java1.exception.StorageException;

import java.sql.SQLException;
import org.postgresql.util.PSQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertSQLException(SQLException e) {
        if (e instanceof PSQLException) {
//          https://www.postgresql.org/docs/9.3/static/errcodes-appendix.html
            if (e.getSQLState().equals("23505")) {
                return new ExistStorageException(null);
            }
        }
        return new StorageException(e);
    }
}