<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="accounts" type="java.util.List<generated.Account>"--%>

<html>
<head>
    <title>Queue</title>
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">

    <script src="<c:url value="/resources/js/jquery-1.9.0.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>

</head>
<body>
<div class="container" style="min-width: 1100px; min-height: 500px;">
    <div class="navbar-inner">
        <legend>Items in queue</legend>
        <table class="table table-bordered">
            <tr>
                <th>id</th>
                <th>iban</th>
                <th>businessIdentifierCode</th>
            </tr>
            <tr>
                <td colspan="3">NOT IMPLEMENTED PROPERLY</td>
            </tr>
            <c:if test="${not empty accountsInQueue}">
                <c:forEach var="acc" items="${accountsInQueue}">
                    <tr>
                        <td>${acc.id}</td>
                        <td>${acc.iban}</td>
                        <td>${acc.businessIdentifierCode}</td>
                    </tr>
                </c:forEach>
            </c:if>
        </table>

        <c:if test="${recentlyAdded!=null}">
            <legend>Recently added</legend>
            <div class="container">
                <table class="table table-bordered">
                    <tr>
                        <td>${recentlyAdded.id}</td>
                        <td>${recentlyAdded.iban}</td>
                        <td>${recentlyAdded.businessIdentifierCode}</td>
                    </tr>
                </table>

            </div>
        </c:if>

        <div class="container">
            <form commandName="account" class="form-horizontal"
                  action="${pageContext.request.contextPath}/queue/receiveOne" method="post">

                <input type="submit" class="btn" value="Receive from jms"/>
            </form>
        </div>
    </div>
</div>

</body>
</html>


