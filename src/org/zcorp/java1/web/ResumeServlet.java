package org.zcorp.java1.web;

import org.zcorp.java1.Config;
import org.zcorp.java1.model.*;
import org.zcorp.java1.storage.Storage;
import org.zcorp.java1.util.DateUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("MM/yyyy");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        if (uuid != null) {
            writeResume(uuid, response.getWriter());
        } else {
            writeResumes(response.getWriter());
        }
    }

    private void writeResumes(PrintWriter writer) {
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta charset=\"UTF-8\">");
        writer.println("<title>Resumes</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<table border=\"1\">");
        writer.println("<thead>");
        writer.println("<tr>");
        writer.println("<th>UUID</th>");
        writer.println("<th>FULL NAME</th>");
        writer.println("<th>CONTACT TYPE</th>");
        writer.println("<th>CONTACT</th>");
        writer.println("<th>SECTION TYPE</th>");
        writer.println("<th>SECTION</th>");
        writer.println("</tr>");
        writer.println("</thead>");
        writer.println("<tbody>");

        Storage storage = Config.get().getStorage();
        List<Resume> resumes = storage.getAllSorted();
        for (Resume resume : resumes) {
            Map<ContactType, String> contacts = resume.getContacts();
            Map<SectionType, Section> sections = resume.getSections();
            Iterator<Map.Entry<ContactType, String>> contactsIter = contacts.entrySet().iterator();
            Iterator<Map.Entry<SectionType, Section>> sectionsIter = sections.entrySet().iterator();
            int contactsRowsCount = contacts.size();
            int sectionsRowsCount = sections.size();
            int rowsCount = contactsRowsCount > sectionsRowsCount ? contactsRowsCount : sectionsRowsCount;
            if (rowsCount == 0) {
                rowsCount = 1;
            }
            for (int i = 0; i < rowsCount; i++) {
                writer.println("<tr>");
                if (i == 0) {
                    writer.println("<td rowspan=\"" + rowsCount + "\">");
                    writer.println(resume.getUuid());
                    writer.println("</td>");
                    writer.println("<td rowspan=\"" + rowsCount + "\">");
                    writer.println(resume.getFullName());
                    writer.println("</td>");
                }
                if (i < contactsRowsCount) {
                    Map.Entry<ContactType, String> contact = contactsIter.next();
                    writer.println("<td>");
                    writer.println(contact.getKey().getTitle());
                    writer.println("</td>");
                    writer.println("<td>");
                    writer.println(contact.getValue());
                    writer.println("</td>");
                } else {
                    writer.println("<td></td>");
                    writer.println("<td></td>");
                }
                if (i < sectionsRowsCount) {
                    Map.Entry<SectionType, Section> section = sectionsIter.next();
                    writer.println("<td>");
                    writer.println(section.getKey().getTitle());
                    writer.println("</td>");
                    writer.println("<td>");
                    writer.println(section.getValue());
                    writer.println("</td>");
                } else {
                    writer.println("<td></td>");
                    writer.println("<td></td>");
                }
                writer.println("</tr>");
            }
        }

        writer.println("</tbody>");
        writer.println("</table>");
        writer.println("</body>");
        writer.println("</html> ");
    }

    private void writeResume(String uuid, PrintWriter writer) {
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta charset=\"UTF-8\">");
        writer.println("<title>Resume</title>");
        writer.println("</head>");
        writer.println("<body>");

        Storage storage = Config.get().getStorage();
        Resume resume = storage.get(uuid);
        writer.println("<h1>" + resume.getFullName() + "</h1>");

        writer.println("<ul>");
        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
            writer.println("<li>");
            writer.println(entry.getKey().getTitle() + ": " + entry.getValue());
            writer.println("</li>");
        }
        writer.println("</ul>");

        for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
            SectionType type = entry.getKey();
            writer.println("<h2>" + type.getTitle() + "</h2>");
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    TextSection textSection = (TextSection) entry.getValue();
                    writer.println("<p>" + textSection.getContent() + "</p>");
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    ListSection listSection = (ListSection) entry.getValue();
                    writer.println("<ul>");
                    for (String item : listSection.getItems()) {
                        writer.println("<li>");
                        writer.println(item);
                        writer.println("</li>");
                    }
                    writer.println("</ul>");
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    OrganizationSection orgSection = (OrganizationSection) entry.getValue();
                    writer.println("<ol>");
                    for (Organization org : orgSection.getOrganizations()) {
                        writer.println("<li>");
                        String url = org.getHomePage().getUrl();
                        String name = org.getHomePage().getName();
                        writer.println("<h3><a" + (!url.equals("") ? " href=\"" + url + "\">" : ">") + name + "</a></h3>");
                        writer.println("<ul>");
                        for (Organization.Position position : org.getPositions()) {
                            writer.println("<li>");
                            writer.print(DTF.format(position.getStartDate()));
                            writer.print(" - ");
                            LocalDate endDate = position.getEndDate();
                            if (endDate.equals(DateUtil.NOW)) {
                                writer.println("Сейчас");
                            } else {
                                writer.println(DTF.format(endDate));
                            }
                            writer.println("<h4>" + position.getTitle() + "</h4>");
                            String description = position.getDescription();
                            if (!description.equals("")) {
                                writer.println(description);
                            }
                            writer.println("</li>");
                        }
                        writer.println("</ul>");
                        writer.println("</li>");
                    }
                    writer.println("</ol>");
                    break;
            }
        }

        writer.println("</body>");
        writer.println("</html> ");
    }
}