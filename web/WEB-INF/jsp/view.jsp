<%@ page import="org.zcorp.java1.model.SectionType" %>
<%@ page import="org.zcorp.java1.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<org.zcorp.java1.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<org.zcorp.java1.model.SectionType, org.zcorp.java1.model.Section>"/>
        <c:set var="type" value="${sectionEntry.key}"/>
    <h3>${type.title}</h3>
        <c:choose>
            <c:when test="${type == SectionType.PERSONAL || type == SectionType.OBJECTIVE}">
    <p>
        ${HtmlUtil.toContent(sectionEntry.value)}
    </p>
            </c:when>
            <c:when test="${type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATIONS}">
    <ul>
                <c:forEach var="item" items="${HtmlUtil.toContent(sectionEntry.value)}">
        <li>${item}</li>
                </c:forEach>
    </ul>
            </c:when>
            <c:when test="${type == SectionType.EXPERIENCE || type == SectionType.EDUCATION}">
    <ul>
                <c:forEach var="org" items="${HtmlUtil.toContent(sectionEntry.value)}">
                    <c:set var="homePage" value="${org.homePage}"/>
                    <jsp:useBean id="homePage" type="org.zcorp.java1.model.Link"/>
        <li>
            <h4>${HtmlUtil.toLink(homePage.url, homePage.name)}</h4>
            <table cellspacing="0" cellpadding="5">
                    <c:forEach var="position" items="${org.positions}">
                <tr>
                    <td ${not empty position.description ? "rowspan=\"2\"" : ""}>${HtmlUtil.toPeriod(position.startDate, position.endDate)}</td>
                    <td>${position.title}</td>
                </tr>
                        <c:if test="${not empty position.description}">
                <tr>
                    <td>${position.description}</td>
                </tr>
                        </c:if>
                    </c:forEach>
            </table>
        </li>
                </c:forEach>
    </ul>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>