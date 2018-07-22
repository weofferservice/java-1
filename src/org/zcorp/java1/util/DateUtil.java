package org.zcorp.java1.util;

import org.zcorp.java1.model.Organization;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateUtil {

    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    private static final DateTimeFormatter DATE_FORMATTER_FOR_VIEW = DateTimeFormatter.ofPattern("MM/yyyy");
    private static final DateTimeFormatter DATE_FORMATTER_FOR_EDIT = DateTimeFormatter.ofPattern("yyyy-MM");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    /**
     * Создаем диапазон дат, чтобы отобразить во view.jsp
     */
    public static String formatDatesToView(Organization.Position position) {
        return formatToView(position.getStartDate()) + " - " + formatToView(position.getEndDate());
    }

    /**
     * Для форматирования вывода даты во view.jsp
     */
    private static String formatToView(LocalDate date) {
        Objects.requireNonNull(date, "date must not be null");
        return date.equals(NOW) ? "Сейчас" : date.format(DATE_FORMATTER_FOR_VIEW);
    }

    /**
     * Для форматирования вывода даты в edit.jsp
     */
    public static String formatToEdit(LocalDate date) {
        Objects.requireNonNull(date, "date must not be null");
        return date.equals(NOW) ? "" : date.format(DATE_FORMATTER_FOR_EDIT);
    }

    /**
     * Для парсинга строки даты, которая приходит из формы edit.jsp
     */
    public static LocalDate parseFromEdit(String date) {
        if (HtmlUtil.isEmpty(date)) {
            return NOW;
        }
        YearMonth yearMonth = YearMonth.parse(date, DATE_FORMATTER_FOR_EDIT);
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    }

}