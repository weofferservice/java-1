package org.zcorp.java1.util;

import org.zcorp.java1.model.*;

import java.util.Collections;

public class HtmlUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String toLink(String href, String title) {
        return isEmpty(href) ? title : "<a href='" + href + "'>" + title + "</a>";
    }

    public static String toEmailLink(Resume resume) {
        return ContactType.MAIL.toHtml(resume.getContact(ContactType.MAIL));
    }

    public static String getStringFromListSection(Section section) {
        if (section == null) {
            return "";
        } else if (section instanceof ListSection) {
            ListSection listSection = (ListSection) section;
            return String.join("\n", listSection.getItems());
        } else {
            throw new IllegalArgumentException("Section is not ListSection");
        }
    }

    /**
     * Для вызова из edit.jsp (для создания или редактирования резюме)
     */
    public static Object toContent(Resume resume, SectionType type) {
        if (resume == null || // новое резюме
                resume.getSection(type) == null) { // существующее резюме, но в нем нет данной секции
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    return "";
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                case EXPERIENCE:
                case EDUCATION:
                    return Collections.emptyList();
                default:
                    throw new IllegalArgumentException("Section type is not recognized");
            }
        }
        return toContent(resume.getSection(type));
    }

    /**
     * Для вызова из view.jsp (для просмотра резюме)
     */
    public static Object toContent(Section section) {
        if (section instanceof TextSection) {
            return ((TextSection) section).getContent();
        } else if (section instanceof ListSection) {
            return ((ListSection) section).getItems();
        } else if (section instanceof OrganizationSection) {
            return ((OrganizationSection) section).getOrganizations();
        } else {
            throw new IllegalArgumentException("Section type is not recognized");
        }
    }
}