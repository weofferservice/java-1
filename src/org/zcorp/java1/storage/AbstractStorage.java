package org.zcorp.java1.storage;

import org.zcorp.java1.exception.ExistStorageException;
import org.zcorp.java1.exception.NotExistStorageException;
import org.zcorp.java1.model.Resume;

import java.util.*;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(String uuid);

    protected abstract void doUpdate(Resume r, Object searchKey);

    protected abstract boolean isExist(Object searchKey);

    protected abstract void doSave(Resume r, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);

    @Override
    public void update(Resume r) {
        Object searchKey = getExistedSearchKey(r.getUuid());
        doUpdate(r, searchKey);
    }

    @Override
    public void save(Resume r) {
        Object searchKey = getNotExistedSearchKey(r.getUuid());
        doSave(r, searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        return doGet(searchKey);
    }

    private Object getExistedSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistedSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected static List<Resume> sortResumes(Collection<Resume> collection) {
        List<Resume> sorted = new ArrayList<>(collection);
        Collections.sort(sorted);
        return sorted;
    }

    protected static List<Resume> sortResumes(Resume[] array, int from, int to) {
        List<Resume> list = Arrays.asList(Arrays.copyOfRange(array, from, to));
        return sortResumes(list);
    }

}