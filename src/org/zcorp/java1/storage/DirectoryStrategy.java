package org.zcorp.java1.storage;

import org.zcorp.java1.model.Resume;

import java.util.List;

public interface DirectoryStrategy<T> {
    void clear();

    int size();

    T getSearchKey(String uuid);

    void doUpdate(Resume r, T searchKey, ResumeWriter writer);

    boolean isExist(T searchKey);

    void doSave(Resume r, T searchKey, ResumeWriter writer);

    Resume doGet(T searchKey, ResumeReader reader);

    void doDelete(T searchKey);

    List<Resume> doCopyAll(ResumeReader reader);
}
