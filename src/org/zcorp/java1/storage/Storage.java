package org.zcorp.java1.storage;

import org.zcorp.java1.model.Resume;

import java.util.List;

public interface Storage {

    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    /**
     * @return list contains only Resumes in storage (without null)
     */
    List<Resume> getAllSorted();

    int size();

}