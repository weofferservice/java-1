package org.zcorp.java1;

import org.zcorp.java1.storage.SqlStorage;
import org.zcorp.java1.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String PROPS = "/resumes.properties";
    private static final Config INSTANCE = new Config();

    private final File storageDir;
    private final Storage storage;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        Properties props = new Properties();
        try (InputStream is = getClass().getResourceAsStream(PROPS)) {
            props.load(is);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS, e);
        }
        storageDir = new File(props.getProperty("storage.dir"));
        storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }
}