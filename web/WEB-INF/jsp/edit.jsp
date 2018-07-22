<%@ page import="org.zcorp.java1.model.ContactType" %>
<%@ page import="org.zcorp.java1.model.SectionType" %>
<%@ page import="org.zcorp.java1.util.DateUtil" %>
<%@ page import="org.zcorp.java1.util.HtmlUtil" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <h1>Имя:</h1>
        <input type="text" name="fullName" size="55" value="${resume.fullName}">
        <h2>Контакты:</h2>
        <dl style="columns: 3;">
        <c:forEach var="type" items="${ContactType.values()}">
            <dt>${type.title}</dt>
            <dd>
                <input type="text" name="${type.name()}" size="30" value="${resume.getContact(type)}">
            </dd>
        </c:forEach>
        </dl>
        <hr>
        <c:forEach var="type" items="${SectionType.values()}">
            <c:set var="section" value="${resume.getSection(type)}"/>
        <h2>${type.title}</h2>
            <c:choose>
                <c:when test="${type == SectionType.OBJECTIVE}">
        <input type="text" name="${type}" size="75" value="${section}">
                </c:when>
                <c:when test="${type == SectionType.PERSONAL}">
        <textarea name="${type}" cols="69" rows="5">${section}</textarea>
                </c:when>
                <c:when test="${type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATIONS}">
        <textarea name="${type}" cols="69"
                  rows="5">${HtmlUtil.getStringFromListSection(section)}</textarea>
                </c:when>
                <c:when test="${type == SectionType.EXPERIENCE || type == SectionType.EDUCATION}">
                    <c:forEach var="org" items="${HtmlUtil.toContent(section)}" varStatus="counter">
        <dl>
            <dt>Название учреждения:</dt>
            <dd>
                <input type="text" name="${type}" size="100" value="${org.homePage.name}">
            </dd>

            <dt>Сайт учреждения:</dt>
            <dd>
                <input type="text" name="${type}url" size="100" value="${org.homePage.url}">
            </dd>
        </dl>
        <div style="margin-left: 100px;">
                        <c:forEach var="pos" items="${org.positions}">
                            <jsp:useBean id="pos" type="org.zcorp.java1.model.Organization.Position"/>
            <hr>
            <dl>
                <dt>Начальная дата:</dt>
                <dd>
                    <input type="month" name="${type}${counter.index}startDate" value="${DateUtil.formatToEdit(pos.startDate)}">
                </dd>
            </dl>
            <dl>
                <dt>Конечная дата:</dt>
                <dd>
                    <input type="month" name="${type}${counter.index}endDate" value="${DateUtil.formatToEdit(pos.endDate)}">
                </dd>
            </dl>
            <dl>
                <dt>Должность:</dt>
                <dd>
                    <input type="text" name="${type}${counter.index}title" size="75" value="${pos.title}">
                </dd>
            </dl>
            <dl>
                <dt>Описание:</dt>
                <dd>
                    <textarea name="${type}${counter.index}description" cols="69"
                              rows="5">${pos.description}</textarea>
                </dd>
            </dl>
                        </c:forEach>
        </div>
        <hr>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Сохранить</button>

        <%-- https://stackoverflow.com/questions/25673477/window-history-back-not-working --%>
        <%--<button onclick="window.history.back()">Отменить</button>--%>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>