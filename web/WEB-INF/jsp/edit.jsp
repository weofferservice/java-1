<%@ page import="org.zcorp.java1.model.ContactType" %>
<%@ page import="org.zcorp.java1.model.SectionType" %>
<%@ page import="org.zcorp.java1.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <c:choose>
        <c:when test="${not empty resume}">
            <jsp:useBean id="resume" type="org.zcorp.java1.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
        </c:when>
        <c:otherwise>
    <title>Создание резюме</title>
        </c:otherwise>
    </c:choose>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" required></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="${SectionType.values()}">
            <jsp:useBean id="type" type="org.zcorp.java1.model.SectionType"/>
            <h4>${type.title}</h4>
            <c:choose>
                <c:when test="${type == SectionType.PERSONAL || type == SectionType.OBJECTIVE}">
                <input type="text" name="${type.name()}" value="${HtmlUtil.toTextSectionValue(resume, type)}">
                </c:when>
                <c:when test="${type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATIONS}">
                <div id="${type.name()}">
                </div>
                <img src="img/add.png" onclick="createNewListElementToParent(this.previousElementSibling, '')">
                </c:when>
                <c:when test="${type == SectionType.EXPERIENCE || type == SectionType.EDUCATION}">
                <div id="${type.name()}">
                </div>
                <img src="img/add.png" onclick="createNewOrganizationElementToParent(this.previousElementSibling, '', '')">
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>

        <%-- https://stackoverflow.com/questions/25673477/window-history-back-not-working --%>
        <%--<button onclick="window.history.back()">Отменить</button>--%>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
<script type="text/javascript">
    function deletePosition(firstPositionRow) {
        var secondPositionRow = firstPositionRow.nextElementSibling;
        var tbody = firstPositionRow.parentNode;
        tbody.removeChild(firstPositionRow);
        tbody.removeChild(secondPositionRow);

        var table = tbody.parentNode;
        var positionCounterElem = table.parentNode.querySelector("input[name='" + table.id + "-position-counter']");
        positionCounterElem.value--;
    }

    function deleteElement(elem) {
        elem.parentNode.removeChild(elem);
    }

    function createNewPositionElementToTable(table, positionStartDateValue, positionEndDateValue, positionTitleValue, positionDescriptionValue) {
        var tr1 = document.createElement("tr");

        var td1 = document.createElement("td");
        td1.rowSpan = 2;
        var inputMonthStartDate = document.createElement("input");
        inputMonthStartDate.type = "month";
        inputMonthStartDate.name = table.parentNode.parentNode.id + "-position-startdate";
        inputMonthStartDate.value = positionStartDateValue;
        inputMonthStartDate.required = true;
        td1.appendChild(inputMonthStartDate);

        var td2 = document.createElement("td");
        td2.rowSpan = 2;
        var inputMonthEndDate = document.createElement("input");
        inputMonthEndDate.type = "month";
        inputMonthEndDate.name = table.parentNode.parentNode.id + "-position-enddate";
        inputMonthEndDate.value = positionEndDateValue;
        td2.appendChild(inputMonthEndDate);

        var td3 = document.createElement("td");
        var inputTitle = document.createElement("input");
        inputTitle.type = "text";
        inputTitle.name = table.parentNode.parentNode.id + "-position-title";
        inputTitle.value = positionTitleValue;
        inputTitle.required = true;
        td3.appendChild(inputTitle);

        var td4 = document.createElement("td");
        td4.rowSpan = 2;
        var imgDeletePosition = document.createElement("img");
        imgDeletePosition.src = "img/delete.png";
        imgDeletePosition.onclick = function () {
            deletePosition(tr1);
        };
        td4.appendChild(imgDeletePosition);

        tr1.appendChild(td1);
        tr1.appendChild(td2);
        tr1.appendChild(td3);
        tr1.appendChild(td4);

        var tr2 = document.createElement("tr");
        var td5 = document.createElement("td");
        var inputDescription = document.createElement("input");
        inputDescription.type = "text";
        inputDescription.name = table.parentNode.parentNode.id + "-position-description";
        inputDescription.value = positionDescriptionValue;
        td5.appendChild(inputDescription);
        tr2.appendChild(td5);

        var tbody = table.querySelector("tbody");
        tbody.appendChild(tr1);
        tbody.appendChild(tr2);

        var positionCounterElem = table.parentNode.querySelector("input[name='" + table.parentNode.parentNode.id + "-position-counter']");
        positionCounterElem.value++;
    }

    function createNewOrganizationElementToParent(parent, homePageNameValue, homePageUrlValue) {
        var div = document.createElement("div");

        var inputName = document.createElement("input");
        inputName.type = "text";
        inputName.name = parent.id + "-name";
        inputName.value = homePageNameValue;
        inputName.placeholder = "Наименование";
        inputName.required = true;

        var inputUrl = document.createElement("input");
        inputUrl.type = "text";
        inputUrl.name = parent.id + "-url";
        inputUrl.value = homePageUrlValue;
        inputUrl.placeholder = "Адрес сайта";

        var imgDeleteOrg = document.createElement("img");
        imgDeleteOrg.src = "img/delete.png";
        imgDeleteOrg.onclick = function () {
            deleteElement(div);
        };

        div.appendChild(inputName);
        div.appendChild(document.createTextNode(" "));
        div.appendChild(inputUrl);
        div.appendChild(document.createTextNode(" "));
        div.appendChild(imgDeleteOrg);

        var table = document.createElement("table");
        table.id = parent.id;
        table.border = "1";
        table.cellPadding = "5";
        table.cellSpacing = "0";
        var thead = document.createElement("thead");
        var tr = document.createElement("tr");
        var th1 = document.createElement("th");
        th1.innerText = "Дата начала";
        var th2 = document.createElement("th");
        th2.innerText = "Дата окончания";
        var th3 = document.createElement("th");
        th3.innerText = "Должность/Описание";
        var th4 = document.createElement("th");
        tr.appendChild(th1);
        tr.appendChild(th2);
        tr.appendChild(th3);
        tr.appendChild(th4);
        thead.appendChild(tr);
        table.appendChild(thead);
        table.appendChild(document.createElement("tbody"));

        var imgAddPosition = document.createElement("img");
        imgAddPosition.src = "img/add.png";
        imgAddPosition.style.display = "block";
        imgAddPosition.style.margin = "auto";
        imgAddPosition.onclick = function () {
            createNewPositionElementToTable(table, "", "", "", "");
        };

        var positionCounter = document.createElement("input");
        positionCounter.type = "hidden";
        positionCounter.name = parent.id + "-position-counter";
        positionCounter.value = "0";

        div.appendChild(table);
        div.appendChild(imgAddPosition);
        div.appendChild(positionCounter);

        div.appendChild(document.createElement("hr"));

        parent.appendChild(div);

        return table;
    }

    function createNewListElementToParent(parent, inputValue) {
        var div = document.createElement("div");

        var input = document.createElement("input");
        input.type = "text";
        input.name = parent.id;
        input.value = inputValue;

        var img = document.createElement("img");
        img.src = "img/delete.png";
        img.onclick = function () {
            deleteElement(div);
        };

        div.appendChild(input);
        div.appendChild(document.createTextNode(" "));
        div.appendChild(img);

        parent.appendChild(div);
    }

<c:forEach var="type" items="<%=new SectionType[]{SectionType.ACHIEVEMENT, SectionType.QUALIFICATIONS}%>">
    var parent1 = document.getElementById("${type.name()}");
    <c:forEach var="item" items="${HtmlUtil.toListSectionValue(resume, type)}">
    createNewListElementToParent(parent1, "${item}");
    </c:forEach>
</c:forEach>

<c:forEach var="type" items="<%=new SectionType[]{SectionType.EXPERIENCE, SectionType.EDUCATION}%>">
    var parent2 = document.getElementById("${type.name()}");
    <c:forEach var="orga" items="${HtmlUtil.toOrganizationSectionValue(resume, type)}">
        <%-- Если дать имя id="org" в useBean, то ломается дальнейшее выполнение
             всех JSP EL на данной странице. При этом java-вставки с именем org,
             продолжают работать корректно. Меняем имя на orga, чтобы работало и
             то, и другое.
             P.S. Может это связано с тем, что имя уже занято системой, но зачем
             системе это имя не ясно? Может потому что корневой пакет у меня org,
             а может и нет? Устал гадать, поэтому продолжаем работать с этим workaround-ом --%>
        <jsp:useBean id="orga" type="org.zcorp.java1.model.Organization"/>

    var table = createNewOrganizationElementToParent(parent2, "${orga.homePage.name}", "${orga.homePage.url}");

        <c:forEach var="position" items="${orga.positions}">
    createNewPositionElementToTable(table, "${HtmlUtil.toYearMonth(position.startDate)}", "${HtmlUtil.toYearMonth(position.endDate)}",
                                                "${position.title}", "${position.description}");
        </c:forEach>
    </c:forEach>
</c:forEach>
</script>
</html>