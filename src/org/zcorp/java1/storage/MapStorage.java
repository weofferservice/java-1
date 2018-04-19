package org.zcorp.java1.storage;

import org.zcorp.java1.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void setElement(Resume r, Integer index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected boolean isStorageFull() {
        return false;
    }

    @Override
    protected void insertElement(Resume r, Integer index) {
        setElement(r, index);
    }

    @Override
    protected void deleteElement(Resume.Entry re) {
        storage.remove(re.getResume().getUuid());
    }

    @Override
    protected Resume.Entry getResumeEntry(String uuid) {
        return new Resume.Entry(null, storage.get(uuid));
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
