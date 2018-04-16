package org.zcorp.java1.storage;

import org.junit.BeforeClass;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    @BeforeClass
    public static void setUpClass() {
        storage = new SortedArrayStorage();
    }

}