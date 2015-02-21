<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript">

    </script>
</head>
<body>
<h>编辑用户</h>
<form name="userForm" action="/user/updateUser" method="post">
    <input type="hidden" name="id" value="${user.id }">
    姓名：<input type="text" name="userName" value="${user.userName }">
    年龄：<input type="text" name="age" value="${user.age }">
    描述：<input type="text" name="memo" value="${user.memo }">

    <input type="submit" value="编辑">


</form>


</body>
</html>