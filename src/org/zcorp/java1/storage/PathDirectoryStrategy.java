package org.zcorp.java1.storage;

import org.zcorp.java1.exception.StorageException;
import org.zcorp.java1.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathDirectoryStrategy implements DirectoryStrategy<Path> {
    private Path directory;

    public PathDirectoryStrategy(String dir) {
        Objects.requireNonNull(dir, "dir must not be null");
        directory = Paths.get(dir);
        if (!Files.isDirectory(directory) ||
                !Files.isWritable(directory) ||
                    !Files.isReadable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not readable/writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }

    @Override
    public Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    public void doUpdate(Resume r, Path path, ResumeWriter writer) {
        try {
            writer.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    public boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    public void doSave(Resume r, Path path, ResumeWriter writer) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create path " + path.toAbsolutePath().toString(), r.getUuid(), e);
        }
        doUpdate(r, path, writer);
    }

    @Override
    public Resume doGet(Path path, ResumeReader reader) {
        try {
            return reader.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.getFileName().toString(), e);
        }
    }

    @Override
    public void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.getFileName().toString());
        }
    }

    @Override
    public List<Resume> doCopyAll(ResumeReader reader) {
        try {
            return Files.list(directory).map(path -> doGet(path, reader)).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }
}