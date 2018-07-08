package org.zcorp.java1.storage;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new ObjectStreamStorage<>(new FileDirectoryStrategy(STORAGE_DIR)));
    }

}