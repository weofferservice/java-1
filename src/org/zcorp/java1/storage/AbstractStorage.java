package org.zcorp.java1.storage;

import org.zcorp.java1.exception.ExistStorageException;
import org.zcorp.java1.exception.NotExistStorageException;
import org.zcorp.java1.exception.StorageException;
import org.zcorp.java1.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume newResume) {
        Resume.Entry re = getResumeEntry(newResume.getUuid());
        if (re.getResume() == null) {
            throw new NotExistStorageException(newResume.getUuid());
        }
        setElement(newResume, re.getIndex());
    }

    protected abstract void setElement(Resume r, Integer index);

    @Override
    public void save(Resume newResume) {
        Resume.Entry re = getResumeEntry(newResume.getUuid());
        if (re.getResume() != null) {
            throw new ExistStorageException(newResume.getUuid());
        }
        if (isStorageFull()) {
            throw new StorageException("Storage overflow", newResume.getUuid());
        }
        insertElement(newResume, re.getIndex());
    }

    protected abstract boolean isStorageFull();
    protected abstract void insertElement(Resume r, Integer index);

    @Override
    public void delete(String uuid) {
        Resume.Entry re = getResumeEntry(uuid);
        if (re.getResume() == null) {
            throw new NotExistStorageException(uuid);
        }
        deleteElement(re);
    }

    protected abstract void deleteElement(Resume.Entry re);

    @Override
    public Resume get(String uuid) {
        Resume.Entry re = getResumeEntry(uuid);
        if (re.getResume() == null) {
            throw new NotExistStorageException(uuid);
        }
        return re.getResume();
    }

    protected abstract Resume.Entry getResumeEntry(String uuid);

}
