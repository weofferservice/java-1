package org.zcorp.java1.storage;

import org.zcorp.java1.exception.StorageException;
import org.zcorp.java1.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    protected abstract void doWrite(Resume r, File file) throws IOException;
    protected abstract Resume doRead(File file) throws IOException;

    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] files = getFiles("IO error: cannot clear file storage");
        for (File file : files) {
            if (!file.delete()) {
                throw new StorageException("IO error: cannot delete file " + file.getName() + " from file storage", file.getName());
            }
        }
    }

    @Override
    public int size() {
        File[] files = getFiles("IO error: cannot calculate size of file storage");
        return files.length;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error: cannot update file " + r.getUuid() + " in file storage", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error: cannot save file " + r.getUuid() + " to file storage", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error: cannot read file " + file.getName() + " from file storage", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("IO error: cannot delete file " + file.getName() + " from file storage",  file.getName());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        File[] files = getFiles("IO error: cannot read files from file storage");
        List<Resume> resumes = new ArrayList<>();
        for (File file : files) {
            try {
                resumes.add(doRead(file));
            } catch (IOException e) {
                throw new StorageException("IO error: cannot read file " + file.getName() + " from file storage", file.getName(), e);
            }
        }
        return resumes;
    }

    private File[] getFiles(String errorMessage) {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException(errorMessage);
        }
        return files;
    }
}