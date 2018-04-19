package org.zcorp.java1.storage;

import org.zcorp.java1.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected void setElement(Resume r, Integer index) {
        storage.set(index, r);
    }

    @Override
    protected boolean isStorageFull() {
        return false;
    }

    @Override
    protected void insertElement(Resume r, Integer index) {
        storage.add(r);
    }

    @Override
    protected void deleteElement(Resume.Entry re) {
        storage.remove(re.getIndex().intValue());
    }

    @Override
    protected Resume.Entry getResumeEntry(String uuid) {
        int index = storage.indexOf(new Resume(uuid));
        if (index == -1) {
            return new Resume.Entry(null, null);
        }
        return new Resume.Entry(index, storage.get(index));
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
