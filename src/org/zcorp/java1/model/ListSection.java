package org.zcorp.java1.model;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ListSection extends Section {
    private List<String> list;

    public ListSection(SectionType type, String... lines) {
        super(type);
        Objects.requireNonNull(lines, "'lines' must not be null");
        this.list = new ArrayList<>(Arrays.asList(lines));
    }

    public List<String> getList() {
        return list;
    }

    public void add(String line) {
        list.add(line);
    }

    public void remove(String line) {
        list.remove(line);
    }

    public void remove(int index) {
        list.remove(index);
    }

    public void set(int index, String line) {
        list.set(index, line);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.getTitle());
        sb.append(System.lineSeparator());
        int i = 1;
        for (String string : list) {
            sb.append(i++);
            sb.append(") ");
            sb.append(string);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
