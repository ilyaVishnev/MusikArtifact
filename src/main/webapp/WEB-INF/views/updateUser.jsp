<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 27.07.2018
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script>
        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                url: '/musicTypes',
                dataType: 'json',
                success: function (data) {
                    var types = data.typesArray;
                    var content;
                    var id;
                    var arraysTypes = [
                        <c:forEach var="item" items="${arrayOftypes}" varStatus="loop">
                        "${item}"
                        <c:if test="${!loop.last}">, </c:if>
                        </c:forEach>
                    ]
                    var chose = false;
                    for (var i = 0; i < types.length; i++) {
                        chose = false;
                        for (var j = 0; j < arraysTypes.length; j++) {
                            if (types[i].id == arraysTypes[j]) {
                                content += '<option selected value="' + types[i].id + '">' + types[i].type + '</option>';
                                chose = true;
                            }
                        }
                        if (!chose) {
                            content += '<option value="' + types[i].id + '">' + types[i].type + '</option>';
                        }

                    }
                    $('#types').empty().html(content);
                }
            })
            var adminRole = '${myuser}';
            var userRole = '${role}';
            if (adminRole == 'user') {
                $('#role').append('<option value="1">user</option>');
            }
            else if (userRole == 'user') {
                $('#role').append('<option selected value="1">user</option><option value="0">admin</option>');
            } else {
                $('#role').append('<option selected value="0">admin</option><option value="1">user</option>');
            }
        })

        function checkEmptyPlaces() {
            var data = {
                empty: false
            };
            var elem = $("#updateForm").find('input, select').each(
                function () {
                    if ($(this).val() == '' || $(this).val() == null) {
                        data.empty = true;
                    }
                }
            );
            if (data.empty) {
                alert("some of the fields are emty");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<form action="edit" method="post" name="updateForm">
    <p>Введите имя пользователя : <input type="text" name="name" value="${user.getName()}" class="form-control"></p>
    <p>Введите роль пользователя : <select name="role" id="role" class="form-control">
    </select></p>
    <p>Введите логин пользователя : <input type="text" name="login" value="${user.getLogin()}" class="form-control"></p>
    <p>Введите пароль пользователя : <input type="text" name="password" value="${user.getPassword()}"
                                            class="form-control"></p>
    <p>Введите адрес пользователя : <input type="text" name="adress" value="${user.getAddress().getAddress()}"
                                           class="form-control"></p>
    <p>Введите музыкальные стили пользователя : <select multiple name="types" id="types" class="form-control">
    </select></p>
    <input type="hidden" name="id" value="${user.getId()}"/>
    <input type="submit" value="Сохранить" name="edit" onclick="return checkEmptyPlaces();" class="btn btn-default">
</form>
</body>
</html>
