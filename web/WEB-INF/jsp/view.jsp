<%@ page import="org.zcorp.java1.model.SectionType" %>
<%@ page import="org.zcorp.java1.util.HtmlUtil" %>
<%@ page import="org.zcorp.java1.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="org.zcorp.java1.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h1>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h1>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<org.zcorp.java1.model.ContactType, java.lang.String>"/>
        ${contactEntry.key.toHtml(contactEntry.value)}
        <br>
        </c:forEach>
    </p>
    <hr>
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<org.zcorp.java1.model.SectionType, org.zcorp.java1.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
        <tr>
            <td colspan="3">
                <h2>${type.title}</h2>
            </td>
        </tr>
            <c:choose>
                <c:when test="${type == SectionType.OBJECTIVE}">
        <tr>
            <td colspan="3">
                <h4>${section}</h4>
            </td>
        </tr>
                </c:when>
                <c:when test="${type == SectionType.PERSONAL}">
        <tr>
            <td colspan="3">
                <pre>${section}</pre>
            </td>
        </tr>
                </c:when>
                <c:when test="${type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATIONS}">
        <tr>
            <td colspan="3">
                <ul>
                    <c:forEach var="item" items="${HtmlUtil.toContent(section)}">
                    <li>${item}</li>
                    </c:forEach>
                </ul>
            </td>
        </tr>
                </c:when>
                <c:when test="${type == SectionType.EXPERIENCE || type == SectionType.EDUCATION}">
                    <c:forEach var="org" items="${HtmlUtil.toContent(section)}">
        <tr>
            <td colspan="3">
                <h4>${HtmlUtil.toLink(org.homePage.url, org.homePage.name)}</h4>
            </td>
        </tr>
                        <c:forEach var="position" items="${org.positions}">
                            <jsp:useBean id="position" type="org.zcorp.java1.model.Organization.Position"/>
        <tr>
            <td rowspan="2" width="30"></td>
            <td rowspan="2" style="vertical-align: top;">
                ${DateUtil.formatDatesToView(position)}
            </td>
            <td><b>${position.title}</b></td>
        </tr>
        <tr>
            <td>${position.description}</td>
        </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <tr>
            <td colspan="3" style="text-align: center; height: 50px;">
                <%-- https://stackoverflow.com/questions/25673477/window-history-back-not-working --%>
                <button type="button" onclick="window.history.back()">ОК</button>
            </td>
        </tr>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>