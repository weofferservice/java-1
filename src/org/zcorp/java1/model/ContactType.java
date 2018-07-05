package org.zcorp.java1.model;

public enum ContactType {
    PHONE("Тел.:", null),
    SKYPE("Skype:", "../img/skype.png"),
    EMAIL("Почта:", "../img/email.png"),
    LINKEDIN(null, "../img/lin.png"),
    GITHUB(null, "../img/gh.png"),
    STACKOVERFLOW(null, "../img/so.png"),
    SITE(null, null);

    private String preText;
    private String iconUrl;

    ContactType(String preText, String iconUrl) {
        this.preText = preText;
        this.iconUrl = iconUrl;
    }

    public String getPreText() {
        return preText;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
