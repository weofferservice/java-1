package org.zcorp.java1.model;

import org.zcorp.java1.util.HtmlUtil;

public enum ContactType {
    PHONE("Тел."),
    MOBILE("Мобильный"),
    HOME_PHONE("Домашний тел."),
    SKYPE("Skype") {
        @Override
        protected String toHtml0(String value) {
            return getTitle() + ": " + HtmlUtil.toLink("skype:" + value, value);
        }
    },
    MAIL("Почта") {
        @Override
        protected String toHtml0(String value) {
            return getTitle() + ": " + HtmlUtil.toLink("mailto:" + value, value);
        }
    },
    LINKEDIN("Профиль LinkedIn") {
        @Override
        protected String toHtml0(String value) {
            return toLink(value);
        }
    },
    GITHUB("Профиль GitHub") {
        @Override
        protected String toHtml0(String value) {
            return toLink(value);
        }
    },
    STACKOVERFLOW("Профиль Stackoverflow") {
        @Override
        protected String toHtml0(String value) {
            return toLink(value);
        }
    },
    HOME_PAGE("Домашняя страница") {
        @Override
        protected String toHtml0(String value) {
            return toLink(value);
        }
    };

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    protected String toLink(String href) {
        return HtmlUtil.toLink(href, title);
    }
}