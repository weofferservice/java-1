package org.zcorp.java1.util;

import org.zcorp.java1.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Collections;
import java.util.List;

import static org.zcorp.java1.util.DateUtil.NOW;

public class HtmlUtil {
    private static final DateTimeFormatter DTF_FOR_VIEW_JSP = DateTimeFormatter.ofPattern("MM/yyyy");
    private static final DateTimeFormatter DTF_FOR_EDIT_JSP = DateTimeFormatter.ofPattern("yyyy-MM");

    public static String toLink(String href, String title) {
        if (href == null || href.equals("")) {
            return title;
        }
        return "<a href='" + href + "'>" + title + "</a>";
    }

    public static String toYearMonth(LocalDate localDate) {
        if (localDate.equals(NOW)) {
            return "";
        }
        return DTF_FOR_EDIT_JSP.format(localDate);
    }

    public static LocalDate ofYearMonth(String yearMonth) {
        TemporalAccessor ta = DTF_FOR_EDIT_JSP.parse(yearMonth);
        int year = ta.get(ChronoField.YEAR);
        int month = ta.get(ChronoField.MONTH_OF_YEAR);
        return LocalDate.of(year, month, 1);
    }

    public static String toPeriod(LocalDate startDate, LocalDate endDate) {
        return DTF_FOR_VIEW_JSP.format(startDate) + " - " + (endDate.equals(NOW) ? "Сейчас" : DTF_FOR_VIEW_JSP.format(endDate));
    }

    public static Object toContent(Section section) {
        if (section instanceof TextSection) {
            return toTextSectionValue(section);
        } else if (section instanceof ListSection) {
            return toListSectionValue(section);
        } else if (section instanceof OrganizationSection) {
            return toOrganizationSectionValue(section);
        } else {
            throw new IllegalArgumentException("Section type is not recognized");
        }
    }

    public static String toTextSectionValue(Resume resume, SectionType type) {
        if (resume == null) {
            return "";
        }
        return toTextSectionValue(resume.getSection(type));
    }

    private static String toTextSectionValue(Section section) {
        TextSection textSection = (TextSection) section;
        return textSection == null ? "" : textSection.getContent();
    }

    public static List<String> toListSectionValue(Resume resume, SectionType type) {
        if (resume == null) {
            return Collections.emptyList();
        }
        return toListSectionValue(resume.getSection(type));
    }

    private static List<String> toListSectionValue(Section section) {
        ListSection listSection = (ListSection) section;
        return listSection == null ? Collections.emptyList() : listSection.getItems();
    }

    public static List<Organization> toOrganizationSectionValue(Resume resume, SectionType type) {
        if (resume == null) {
            return Collections.emptyList();
        }
        return toOrganizationSectionValue(resume.getSection(type));
    }

    private static List<Organization> toOrganizationSectionValue(Section section) {
        OrganizationSection organizationSection = (OrganizationSection) section;
        return organizationSection == null ? Collections.emptyList() : organizationSection.getOrganizations();
    }

    public static String toEmailLink(Resume resume) {
        return ContactType.MAIL.toHtml(resume.getContact(ContactType.MAIL));
    }
}
