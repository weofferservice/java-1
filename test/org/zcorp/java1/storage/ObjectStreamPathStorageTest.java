package org.zcorp.java1.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamStorage<>(new PathDirectoryStrategy(STORAGE_DIR)));
    }

}