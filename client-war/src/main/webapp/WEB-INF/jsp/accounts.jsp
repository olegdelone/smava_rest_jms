<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="accounts" type="java.util.List<generated.Account>"--%>

<html>
<head>
    <title>Accounts</title>
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">

    <script src="<c:url value="/resources/js/jquery-1.9.0.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <script>
        var onRemove = function(el, id){
            $.ajax({
                url: 'http://localhost:8080/rest/rest/account/'+id,
                type: 'DELETE',
                success: function(result) {
                    window.location.reload();
                }
            });
        }
    </script>
</head>
<body>
<div class="container" style="min-width: 1100px; min-height: 500px;">
    <div class="navbar-inner">
        <legend>Items in DB</legend>
        <table class="table table-bordered">
            <tr>
                <th>id</th>
                <th>iban</th>
                <th>businessIdentifierCode</th>
                <th>remove</th>
            </tr>
            <c:if test="${not empty accounts}">
                <c:forEach var="acc" items="${accounts}">
                    <tr>
                        <td>${acc.id}</td>
                        <td>${acc.iban}</td>
                        <td>${acc.businessIdentifierCode}</td>
                        <td><button class="btn btn-danger" type="button" onclick="onRemove(this, ${acc.id})">X</button></td>
                    </tr>
                </c:forEach>
            </c:if>

        </table>

        <div class="container">
            <form:form commandName="account" class="form-horizontal"
                       action="${pageContext.request.contextPath}/accounts/add" method="post">
                <legend>Account form</legend>
                <div class="control-group">
                    <label class="control-label">iban: </label>
                    <div class="controls">
                        <form:input path="iban"/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">businessIdentifierCode: </label>
                    <div class="controls">
                        <form:input path="businessIdentifierCode"/>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <input type="submit" class="btn" value="Push to jms"/>
                    </div>
                </div>
            </form:form>
        </div>
    </div>

    <legend>Notes</legend>
    Push to jms something, then go to http://&lt;host&gt;:&lt;port&gt;/rest/queue and receive that one
    from queue. After that you may refresh this page in order to see the changes into DB. <br>
    <br>
    Wasn't able to implement logic which retrieves elements from queue, i didn't expect that it would be so hard. Some attemptions you may find at de.smava.bank.jms.JmsService<br>
    Rejected listener-style implementation for demonstrative purposes.<br>
    Removing of elements (above table) implemented through ajax directly to the REST resource,
    while retrieving elements goes through the java REST client. (demonstrative purposes)<br>
    Spent more than 10 hrs, probably because i'm not get used to working with rest wadls (especially after wsdls) - the tool cxf-wadl2java-plugin made me cry.
    Didn't accord on specification, some points clashes to each other and unclear -<br>
    <b>2. Create jsp page that displays list of bank accounts, using jstl, provided by backend. A bank account consists of two fields: iban (String) and Business Identifier Code (String)</b>
    <br>
    <b>5. Create js based submission of bank account list edited by user towards backend</b>
    <br>
    <br>
    Update not implemented at all.
</div>

</body>
</html>


