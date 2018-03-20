import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    {
        clear();
    }

    void clear() {
        storage[0] = null;
    }

    void save(Resume r) {
        if (r == null) {
            return;
        }

        int size = size();
        if (size < storage.length) {
            storage[size] = r;
            if (size + 1 < storage.length) {
                storage[size + 1] = null;
            }
        }
    }

    Resume get(String uuid) {
        if (uuid == null) {
            return null;
        }

        for (int i = 0; i < size(); i++) {
            Resume resume = storage[i];
            if (uuid.equals(resume.toString())) {
                return resume;
            }
        }

        return null;
    }

    void delete(String uuid) {
        if (uuid == null) {
            return;
        }

        int size = size();
        for (int i = 0; i < size; i++) {
            Resume resume = storage[i];
            if (uuid.equals(resume.toString())) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                return;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size());
    }

    int size() {
        int i = 0;
        while (storage[i] != null) {
            i++;
            if (i == storage.length) {
                break;
            }
        }
        return i;
    }
}
