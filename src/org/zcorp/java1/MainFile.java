package org.zcorp.java1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/org/zcorp/java1");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("------------------------");
        System.out.println("- Домашнее задание 8.2 -");
        System.out.println("------------------------");
        try {
            recursiveDirTreePrinting(new File("."));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void recursiveDirTreePrinting(File rootDir) throws IOException {
        if (rootDir.isDirectory()) {
            File[] files = rootDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.isDirectory()) {
                        doFile(file);
                    } else {
                        recursiveDirTreePrinting(file);
                    }
                }
            }
        }
    }

    private static void doFile(File file) throws IOException {
        System.out.println(file.getCanonicalPath());
    }
}