package org.zcorp.java1.storage;

import org.zcorp.java1.Config;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getDbUrl(),
                             Config.get().getDbUser(),
                             Config.get().getDbPassword()));
    }

}