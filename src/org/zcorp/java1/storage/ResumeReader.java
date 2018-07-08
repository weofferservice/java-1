package org.zcorp.java1.storage;

import org.zcorp.java1.model.Resume;

import java.io.IOException;
import java.io.InputStream;

@FunctionalInterface
public interface ResumeReader {

    Resume doRead(InputStream is) throws IOException;

}
