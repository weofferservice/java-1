package org.zcorp.java1.storage.serializer;

import org.zcorp.java1.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    private void writeTextSection(Section s, DataOutputStream dos) throws IOException {
        TextSection section = (TextSection) s;
        dos.writeUTF(section.getContent());
    }

    private TextSection readTextSection(DataInputStream dis) throws IOException {
        return new TextSection(dis.readUTF());
    }

    private void writeListSection(Section s, DataOutputStream dos) throws IOException {
        ListSection section = (ListSection) s;
        List<String> items = section.getItems();
        dos.writeInt(items.size());
        for (String item : items) {
            dos.writeUTF(item);
        }
    }

    private ListSection readListSection(DataInputStream dis) throws IOException {
        List<String> items = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            items.add(dis.readUTF());
        }
        return new ListSection(items);
    }

    private void writeOrganizationSection(Section s, DataOutputStream dos) throws IOException {
        OrganizationSection section = (OrganizationSection) s;
        List<Organization> orgs = section.getOrganizations();
        dos.writeInt(orgs.size());
        for (Organization org : orgs) {
            writeHomePage(org.getHomePage(), dos);
            writePositions(org.getPositions(), dos);
        }
    }

    private OrganizationSection readOrganizationSection(DataInputStream dis) throws IOException {
        List<Organization> orgs = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            Link homePage = readHomePage(dis);
            List<Organization.Position> positions = readPositions(dis);
            orgs.add(new Organization(homePage, positions));
        }
        return new OrganizationSection(orgs);
    }

    private void writeMaybeNullString(String maybeNullString, DataOutputStream dos) throws IOException {
        boolean isNotNull = maybeNullString != null;
        dos.writeBoolean(isNotNull);
        if (isNotNull) {
            dos.writeUTF(maybeNullString);
        }
    }

    private String readMaybeNullString(DataInputStream dis) throws IOException {
        if (dis.readBoolean()) {
            return dis.readUTF();
        }
        return null;
    }

    private void writeHomePage(Link homePage, DataOutputStream dos) throws IOException {
        dos.writeUTF(homePage.getName());
        writeMaybeNullString(homePage.getUrl(), dos);
    }

    private Link readHomePage(DataInputStream dis) throws IOException {
        String name = dis.readUTF();
        String url = readMaybeNullString(dis);
        return new Link(name, url);
    }

    private void writePositions(List<Organization.Position> positions, DataOutputStream dos) throws IOException {
        dos.writeInt(positions.size());
        for (Organization.Position position : positions) {
            dos.writeUTF(position.getTitle());
            dos.writeUTF(position.getStartDate().toString());
            dos.writeUTF(position.getEndDate().toString());
            writeMaybeNullString(position.getDescription(), dos);
        }
    }

    private List<Organization.Position> readPositions(DataInputStream dis) throws IOException {
        List<Organization.Position> positions = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            String title = dis.readUTF();
            LocalDate startDate = LocalDate.parse(dis.readUTF());
            LocalDate endDate = LocalDate.parse(dis.readUTF());
            String description = readMaybeNullString(dis);
            positions.add(new Organization.Position(startDate, endDate, title, description));
        }
        return positions;
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            for (Map.Entry<SectionType, Section> entry : r.getSections().entrySet()) {
                Section section = entry.getValue();
                Class<? extends Section> clazz = section.getClass();
                dos.writeUTF(clazz.getName());
                dos.writeUTF(entry.getKey().name());
                if (TextSection.class == clazz) {
                    writeTextSection(section, dos);
                } else if (ListSection.class == clazz) {
                    writeListSection(section, dos);
                } else if (OrganizationSection.class == clazz) {
                    writeOrganizationSection(section, dos);
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            while (dis.available() > 0) {
                String sectionClass = dis.readUTF();
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                Section section = null;
                if (TextSection.class.getName().equals(sectionClass)) {
                    section = readTextSection(dis);
                } else if (ListSection.class.getName().equals(sectionClass)) {
                    section = readListSection(dis);
                } else if (OrganizationSection.class.getName().equals(sectionClass)) {
                    section = readOrganizationSection(dis);
                }
                resume.addSection(sectionType, section);
            }

            return resume;
        }
    }
}