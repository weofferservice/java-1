package org.zcorp.java1.storage;

import org.zcorp.java1.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    /**
     * @param uuid
     * @return индекс резюме в массиве, если оно найдено в нем;
     *         иначе, <tt>(-(<i>insertion point</i>) - 1)</tt>.
     *         <i>insertion point</i> определяется как индекс, по которому резюме
     *         может быть добавлено в массив:
     *         1) если резюме имеет <tt>uuid</tt>, расположенный по алфавиту раньше uuid-ов
     *            остальных резюме в массиве, то <i>insertion point</i> будет равно 0;
     *         2) если резюме имеет <tt>uuid</tt>, расположенный по алфавиту после uuid-ов
     *            остальных резюме в массиве, то <i>insertion point</i> будет равно size.
     *         Обратите внимание!
     *         Такой подход гарантирует, что возвращаемое значение будет &gt;= 0 если и
     *         только если резюме будет найдено в массиве.
     */
    protected abstract int getIndex(String uuid);

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            System.out.println("Resume " + r.getUuid() + " not exist");
        } else {
            storage[index] = r;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            System.out.println("Resume " + r.getUuid() + " already exist");
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else {
            int insertionPoint = -(index + 1);
            prepareInsertionPoint(insertionPoint);
            storage[insertionPoint] = r;
            size++;
        }
    }

    private void prepareInsertionPoint(int insertionPoint) {
        for (int i = size; i > insertionPoint; i--) {
            storage[i] = storage[i - 1];
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
        } else {
            pullout(index);
            storage[size - 1] = null;
            size--;
        }
    }

    protected abstract void pullout(int index);
}