<%@ page import="musik.models.User" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 17.06.2018
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>Users</title>
</head>
<body>
<table>
    <c:forEach var="user" items="${myusers}">
        <tr>
            <td>
                <c:if test="${role.getRole().equals('admin') || myuser.getId()==user.getId()}">
                    <p>
                        <form action="${pageContext.servletContext.contextPath}/edit" method="get">
                            <input type="hidden" name="id" value="${user.getId()}" class="form-control">
                    <p><input type="submit" value="Редактировать" name="update" class="btn btn-default">
                    </p>
                    </form>
                    </p>
                    <p>
                        <form action="${pageContext.servletContext.contextPath}/users" method="post">
                            <input type="hidden" name="id" value="${user.getId()}" class="form-control">
                    <p><input type="submit" value="Удалить" name="del" class="btn btn-default">
                    </p>
                    </form>
                    </p>
                </c:if>
            </td>
            <td><c:out value="${user}"/>
            </td>
        </tr>
    </c:forEach>
</table>
<c:if test="${role.getRole().equals('admin')}">
    <form action="${pageContext.servletContext.contextPath}/create" method="get">
        <p><input type="submit" value="Создать пользователя" name="create" class="btn btn-default"></p>
    </form>
</c:if>
</br>
<form action="${pageContext.servletContext.contextPath}/signin" method="get">
    <p><input type="submit" value="выход" name="exit" class="btn btn-default"></p>
</form>
</body>
</html>
