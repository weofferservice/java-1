package org.zcorp.java1.storage;

import org.zcorp.java1.Config;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(Config.get().getStorage());
    }

}