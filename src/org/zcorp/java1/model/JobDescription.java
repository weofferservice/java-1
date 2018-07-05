package org.zcorp.java1.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class JobDescription {
    private Calendar dateStart;
    private Calendar dateFinish;
    private String title;
    private String description;

    public JobDescription(String title, Calendar dateStart) {
        Objects.requireNonNull(title, "'title' must not be null");
        Objects.requireNonNull(dateStart, "'dateStart' must not be null");
        this.dateStart = dateStart;
        this.title = title;
    }

    public JobDescription(String title, Calendar dateStart, Calendar dateFinish) {
        this(title, dateStart);
        this.dateFinish = dateFinish;
    }

    public JobDescription(String title, Calendar dateStart, String description) {
        this(title, dateStart);
        this.description = description;
    }

    public JobDescription(String title, Calendar dateStart, Calendar dateFinish, String description) {
        this(title, dateStart);
        this.dateFinish = dateFinish;
        this.description = description;
    }

    public Calendar getDateStart() {
        return dateStart;
    }

    public void setDateStart(Calendar dateStart) {
        this.dateStart = dateStart;
    }

    public Calendar getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(Calendar dateFinish) {
        this.dateFinish = dateFinish;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobDescription that = (JobDescription) o;

        if (!dateStart.equals(that.dateStart)) return false;
        if (dateFinish != null ? !dateFinish.equals(that.dateFinish) : that.dateFinish != null) return false;
        if (!title.equals(that.title)) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = dateStart.hashCode();
        result = 31 * result + (dateFinish != null ? dateFinish.hashCode() : 0);
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
        sb.append(dateFormat.format(dateStart.getTime()));
        sb.append(" - ");
        sb.append(dateFinish == null ? "Сейчас" : dateFormat.format(dateFinish.getTime()));
        sb.append(System.lineSeparator());

        sb.append(title);
        sb.append(System.lineSeparator());
        if (description != null) {
            sb.append(description);
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
}
