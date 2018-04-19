package org.zcorp.java1.storage;

import org.zcorp.java1.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    public static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected void setElement(Resume r, Integer index) {
        storage[index] = r;
    }

    @Override
    protected boolean isStorageFull() {
        return size == STORAGE_LIMIT;
    }

    @Override
    protected void insertElement(Resume r, Integer index) {
        insertElementByIndex(r, index);
        size++;
    }

    protected abstract void insertElementByIndex(Resume r, int index);

    @Override
    protected void deleteElement(Resume.Entry re) {
        fillDeletedElement(re.getIndex());
        storage[size - 1] = null;
        size--;
    }

    protected abstract void fillDeletedElement(int index);

    @Override
    protected Resume.Entry getResumeEntry(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            return new Resume.Entry(index, null);
        }
        return new Resume.Entry(index, storage[index]);
    }

    protected abstract int getIndex(String uuid);
}