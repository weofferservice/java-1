package org.zcorp.java1.storage;

import org.zcorp.java1.exception.StorageException;
import org.zcorp.java1.model.Resume;

import java.io.*;

public class ObjectStreamStorage<T> extends AbstractDirectoryStorage<T> {
    public ObjectStreamStorage(DirectoryStrategy<T> strategy) {
        super(strategy);
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}