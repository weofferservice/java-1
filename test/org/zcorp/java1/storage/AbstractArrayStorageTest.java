package org.zcorp.java1.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zcorp.java1.exception.ExistStorageException;
import org.zcorp.java1.exception.NotExistStorageException;
import org.zcorp.java1.exception.StorageException;
import org.zcorp.java1.model.Resume;

public abstract class AbstractArrayStorageTest {
    protected static AbstractArrayStorage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID = "uuid";

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        storage.update(new Resume(UUID_2));
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume(UUID_4));
    }

    @Test
    public void getAll() throws Exception {
        int actualSize = storage.getAll().length;
        Assert.assertEquals(3, actualSize);
    }

    @Test
    public void save() throws Exception {
        storage.save(new Resume(UUID_4));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_2));
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        for (int i = 3; i <= storage.STORAGE_LIMIT; i++) {
            storage.save(new Resume(UUID + (i + 1)));
        }
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void get() throws Exception {
        Resume resume = storage.get(UUID_1);
        Assert.assertEquals(UUID_1, resume.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}