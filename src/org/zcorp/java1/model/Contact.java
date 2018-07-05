package org.zcorp.java1.model;

import java.util.Objects;

public class Contact {
    private ContactType type;
    private String text;
    private String url;

    public Contact(ContactType type, String text) {
        Objects.requireNonNull(type, "'type' must not be null");
        Objects.requireNonNull(text, "'text' must not be null");
        this.type = type;
        this.text = text;
    }

    public ContactType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return  (type.getPreText() != null ? type.getPreText() + " " : "") +
                (type.getIconUrl() != null ? type.getIconUrl() + " " : "") +
                (url != null ? "<a href=\"" + url + "\">" : "") +
                text +
                (url != null ? "</a>" : "");
    }
}
