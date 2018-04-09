import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int RESUME_IS_NULL = -1;
    private static final int RESUME_UUID_IS_NULL = -2;
    private static final int RESUME_NOT_FOUND = -3;

    private Resume[] storage = new Resume[10000];
    private int size = 0;

    {
        clear();
    }

    private int getIndexByUUID(String uuid) {
        if (uuid == null) {
            System.out.println("ERROR: поиск осуществить невозможно, т.к. входные данные не верны.");
            return RESUME_UUID_IS_NULL;
        }

        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return RESUME_NOT_FOUND;
    }

    private int getIndex(Resume r) {
        if (r == null) {
            System.out.println("ERROR: поиск осуществить невозможно, т.к. входные данные не верны.");
            return RESUME_IS_NULL;
        }
        return getIndexByUUID(r.getUuid());
    }

    public void update(Resume r) {
        int index = getIndex(r);
        if (index >= 0) {
            storage[index] = r;
        } else if (index == RESUME_NOT_FOUND) {
            System.out.println("ERROR: резюме " + r + " не найдено.");
        }
    }

    public void clear() {
        storage[0] = null;
        size = 0;
    }

    public void save(Resume r) {
        int index = getIndex(r);
        if (index == RESUME_NOT_FOUND) {
            if (size < storage.length) {
                storage[size] = r;
                size++;
                if (size < storage.length) {
                    storage[size] = null;
                }
            } else {
                System.out.println("ERROR: не могу сохранить резюме " + r + ". Массив переполнен.");
            }
        } else if (index >= 0) {
            System.out.println("ERROR: резюме " + r + " уже есть в массиве.");
        }
    }

    public Resume get(String uuid) {
        int index = getIndexByUUID(uuid);
        if (index >= 0) {
            return storage[index];
        } else if (index == RESUME_NOT_FOUND) {
            System.out.println("ERROR: резюме c UUID = " + uuid + " не найдено.");
        }
        return null;
    }

    public void delete(String uuid) {
        int index = getIndexByUUID(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else if (index == RESUME_NOT_FOUND) {
            System.out.println("ERROR: резюме c UUID = " + uuid + " не найдено.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }
}
