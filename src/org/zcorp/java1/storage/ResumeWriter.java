package org.zcorp.java1.storage;

import org.zcorp.java1.model.Resume;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface ResumeWriter {

    void doWrite(Resume r, OutputStream os) throws IOException;

}
