<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 29.06.2018
  Time: 8:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Input</title>
</head>
<body>
<c:if test="${error!=''}">
    <div style="background-color: red">
        <c:out value="${error}"/>
    </div>
</c:if>
<form action="${pageContext.servletContext.contextPath}/signin" method="post">
    Login : <input type="text" name="login"></br>
    Password : <input type="password" name="password"></br>
    <input type="submit" value="Вход">
</form>
</body>
</html>
