package org.zcorp.java1.model;

import java.util.Objects;

public abstract class Section {
    protected SectionType type;

    protected Section(SectionType type) {
        Objects.requireNonNull(type, "'type' must not be null");
        this.type = type;
    }

    public SectionType getType() {
        return type;
    }
}
