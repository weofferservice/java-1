package org.zcorp.java1.storage;

import org.zcorp.java1.model.Resume;

import java.util.List;
import java.util.Objects;

public abstract class AbstractDirectoryStorage<T> extends AbstractStorage<T> implements ResumeWriter, ResumeReader {
    private DirectoryStrategy<T> strategy;

    protected AbstractDirectoryStorage(DirectoryStrategy<T> strategy) {
        Objects.requireNonNull(strategy, "strategy must not be null");
        this.strategy = strategy;
    }

    @Override
    protected T getSearchKey(String uuid) {
        return strategy.getSearchKey(uuid);
    }

    @Override
    protected void doUpdate(Resume r, T searchKey) {
        strategy.doUpdate(r, searchKey, this);
    }

    @Override
    protected boolean isExist(T searchKey) {
        return strategy.isExist(searchKey);
    }

    @Override
    protected void doSave(Resume r, T searchKey) {
        strategy.doSave(r, searchKey, this);
    }

    @Override
    protected Resume doGet(T searchKey) {
        return strategy.doGet(searchKey, this);
    }

    @Override
    protected void doDelete(T searchKey) {
        strategy.doDelete(searchKey);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return strategy.doCopyAll(this);
    }

    @Override
    public void clear() {
        strategy.clear();
    }

    @Override
    public int size() {
        return strategy.size();
    }
}
