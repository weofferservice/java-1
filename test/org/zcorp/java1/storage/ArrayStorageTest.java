package org.zcorp.java1.storage;

import org.junit.BeforeClass;

public class ArrayStorageTest extends AbstractArrayStorageTest {

    @BeforeClass
    public static void setUpClass() {
        storage = new ArrayStorage();
    }

}