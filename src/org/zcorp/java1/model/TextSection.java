package org.zcorp.java1.model;

public class TextSection extends Section {
    private String text;

    public TextSection(SectionType type, String text) {
        super(type);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(type.getTitle());
        sb.append(System.lineSeparator());
        sb.append(text);
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}
