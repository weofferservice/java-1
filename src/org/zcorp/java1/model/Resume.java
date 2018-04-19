package org.zcorp.java1.model;

import java.util.UUID;

/**
 * org.zcorp.java1.model.Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    public Resume() {
        this(UUID.randomUUID().toString());
    }

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }

    public static class Entry {
        private Integer index;
        private Resume resume;

        public Entry(Integer index, Resume resume) {
            this.index = index;
            this.resume = resume;
        }

        public Integer getIndex() {
            return index;
        }

        public Resume getResume() {
            return resume;
        }
    }

}