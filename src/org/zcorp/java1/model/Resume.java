package org.zcorp.java1.model;

import java.util.*;

/**
 * org.zcorp.java1.model.Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private final String fullName;

    private Map<ContactType, Contact> contacts = new HashMap<>();
    private Map<SectionType, Section> sections = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void setContacts(Contact... contacts) {
        Objects.requireNonNull(contacts, "'contacts' must not be null");
        for (Contact contact : contacts) {
            this.contacts.put(contact.getType(), contact);
        }
    }

    public void setSections(Section... sections) {
        Objects.requireNonNull(sections, "'sections' must not be null");
        for (Section section : sections) {
            this.sections.put(section.getType(), section);
        }
    }

    public void setContact(Contact contact) {
        contacts.put(contact.getType(), contact);
    }

    public void setSection(Section section) {
        sections.put(section.getType(), section);
    }

    public Contact getContact(ContactType type) {
        return contacts.get(type);
    }

    public Section getSection(SectionType type) {
        return sections.get(type);
    }

    public List<Contact> getContacts() {
        return new ArrayList<>(contacts.values());
    }

    public List<Section> getSections() {
        return new ArrayList<>(sections.values());
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        if (!fullName.equals(resume.fullName)) return false;
        if (!contacts.equals(resume.contacts)) return false;
        return sections.equals(resume.sections);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + contacts.hashCode();
        result = 31 * result + sections.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(fullName);
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        for (Map.Entry<ContactType, Contact> contact : contacts.entrySet()) {
            sb.append(contact.getValue());
            sb.append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());
        for (Map.Entry<SectionType, Section> section : sections.entrySet()) {
            sb.append(section.getValue());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }

}