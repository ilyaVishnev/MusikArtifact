<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 23.07.2018
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>create</title>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                url: '/musicTypes',
                dataType: 'json',
                success: function (data) {
                    var types = data.typesArray;
                    var content = '<option disabled selected>Выберите стиль</option>';
                    for (var i = 0; i < types.length; i++) {
                        content += '<option value="' + types[i].id + '">' + types[i].type + '</option>';
                    }
                    $('#types').empty().html(content);
                }
            })
            var adminRole = '${myuser}';
            if (adminRole == 'user') {
                $('#role').append('<option value="1">user</option>');
            } else {
                $('#role').append('<option value="0">admin</option><option value="1">user</option>');
            }
        })

        function checkEmptyPlaces() {
            var data = {
                empty: false
            };
            var elem = $("#createForm").find('input, select').each(
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
<form name="createForm" action="create" method="post" id="createForm">
    <p>Введите имя пользователя : <input type="text" name="name" class="form-control"></p>
    <p>Введите роль пользователя : <select name="role" id="role" class="form-control">
    </select></p>
    <p>Введите логин пользователя : <input type="text" name="login" class="form-control"></p>
    <p>Введите пароль пользователя : <input type="text" name="password" class="form-control"></p>
    <p>Введите адрес пользователя : <input type="text" name="adress" class="form-control"></p>
    <p>Введите музыкальные стили пользователя : <select multiple name="types" id="types" class="form-control">
    </select></p>
    <input type="submit" value="Создать" name="create" class="btn btn-default">
</form>
</body>
</html>