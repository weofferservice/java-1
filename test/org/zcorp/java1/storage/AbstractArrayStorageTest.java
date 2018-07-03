package org.zcorp.java1.storage;

import org.junit.Assert;
import org.junit.Test;
import org.zcorp.java1.exception.StorageException;
import org.zcorp.java1.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(DUMMY));
            }
        } catch (StorageException e) {
            Assert.fail();
        }
        storage.save(new Resume(DUMMY));
    }

}