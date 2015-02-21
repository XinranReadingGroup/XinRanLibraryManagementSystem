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
        function addUser() {
            var form = document.forms[0];
            form.action = "/user/loginin";
            form.method = "post";
            form.submit();
        }
    </script>
</head>
<body>
<h>用户登录</h>

<br/>

<br/>
<% if (null != request.getAttribute("error")) {%>
<%= request.getAttribute("error")%>
<% }%>
<br/>
<br/>

<form name="userForm" action="">
    姓名：<input type="text" name="userName">
    年龄：<input type="password" name="password">


    <input type="button" value="登录" onclick="addUser()">


</form>


</body>
</html>