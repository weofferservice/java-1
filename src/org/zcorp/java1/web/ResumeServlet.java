package org.zcorp.java1.web;

import org.zcorp.java1.Config;
import org.zcorp.java1.model.*;
import org.zcorp.java1.storage.Storage;
import org.zcorp.java1.util.DateUtil;
import org.zcorp.java1.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String uuid = request.getParameter("uuid");
        if (uuid == null) {
            response.sendRedirect("resume");
            return;
        }

        String fullName = request.getParameter("fullName");
        fullName = fullName.trim();
        if (fullName.length() == 0) {
            if (uuid.length() > 0) {
                storage.delete(uuid);
            }
            response.sendRedirect("resume");
            return;
        }

        Resume r;
        if (uuid.length() == 0) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            value = value.trim();
            if (value.length() > 0) {
                r.addContact(type, value);
            } else {
                r.removeContact(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case PERSONAL:
                case OBJECTIVE: {
                    String value = request.getParameter(type.name());
                    value = value.trim();
                    if (value.length() > 0) {
                        r.addSection(type, new TextSection(value));
                    } else {
                        r.removeSection(type);
                    }
                    break;
                }
                case ACHIEVEMENT:
                case QUALIFICATIONS: {
                    String[] values = request.getParameterValues(type.name());
                    if (values == null) {
                        r.removeSection(type);
                        break;
                    }
                    List<String> items = new ArrayList<>(values.length);
                    for (String value : values) {
                        value = value.trim();
                        if (value.length() > 0) {
                            items.add(value);
                        }
                    }
                    if (!items.isEmpty()) {
                        r.addSection(type, new ListSection(items));
                    } else {
                        r.removeSection(type);
                    }
                    break;
                }
                case EXPERIENCE:
                case EDUCATION: {
                    String[] names = request.getParameterValues(type.name() + "-name");
                    if (names == null) {
                        r.removeSection(type);
                        break;
                    }

                    String[] urls = request.getParameterValues(type.name() + "-url");

                    String[] positionCounters = request.getParameterValues(type.name() + "-position-counter");
                    List<Integer> positionShifts = new ArrayList<>(positionCounters.length + 1);
                    int currentPositionShift = 0;
                    positionShifts.add(currentPositionShift);
                    for (String positionCounter : positionCounters) {
                        currentPositionShift += Integer.parseInt(positionCounter);
                        positionShifts.add(currentPositionShift);
                    }

                    String[] positionStartdates = request.getParameterValues(type.name() + "-position-startdate");
                    String[] positionEnddates = request.getParameterValues(type.name() + "-position-enddate");
                    String[] positionTitles = request.getParameterValues(type.name() + "-position-title");
                    String[] positionDescriptions = request.getParameterValues(type.name() + "-position-description");

                    OrganizationSection orgSection = new OrganizationSection();
                    for (int i = 0; i < names.length; i++) { // начало цикла по организациям
                        String name = names[i];
                        name = name.trim();
                        if (name.length() == 0) {
                            continue;
                        }
                        String url = urls[i];
                        url = url.trim();

                        Organization org = new Organization(name, url);
                        orgSection.addOrganization(org);

                        int positionCounter = Integer.parseInt(positionCounters[i]);
                        int positionShift = positionShifts.get(i);
                        for (int j = 0; j < positionCounter; j++) { // начало цикла по позициям в организации
                            String positionTitle = positionTitles[positionShift + j];
                            positionTitle = positionTitle.trim();
                            if (positionTitle.length() == 0) {
                                continue;
                            }

                            String positionStartdateStr = positionStartdates[positionShift + j];
                            if (positionStartdateStr.length() == 0) {
                                continue;
                            }
                            LocalDate positionStartdate = HtmlUtil.ofYearMonth(positionStartdateStr);

                            String positionEnddateStr = positionEnddates[positionShift + j];
                            LocalDate positionEnddate =
                                    positionEnddateStr.length() == 0 ? DateUtil.NOW : HtmlUtil.ofYearMonth(positionEnddateStr);

                            String positionDescription = positionDescriptions[positionShift + j];
                            positionDescription = positionDescription.trim();

                            Organization.Position position =
                                    new Organization.Position(positionStartdate, positionEnddate, positionTitle, positionDescription);
                            org.addPosition(position);
                        } // конец цикла по позициям в организации
                    } // конец цикла по организациям

                    if (orgSection.isEmpty()) {
                        r.removeSection(type);
                    } else {
                        r.addSection(type, orgSection);
                    }
                    break;
                }
            }
        }

        if (uuid.length() == 0) {
            storage.save(r);
            uuid = r.getUuid();
        } else {
            storage.update(r);
        }

        response.sendRedirect("resume?uuid=" + uuid + "&action=view");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        String uuid = request.getParameter("uuid");
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            case "add":
                r = null;
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

}