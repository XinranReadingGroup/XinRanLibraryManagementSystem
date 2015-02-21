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
        function del(id) {
            $.get("/user/delUser?id=" + id, function (data) {
                if ("success" == data.result) {
                    alert("删除成功!");
                    window.location.reload();
                } else {
                    alert("删除失败!")
                }
                window.location.reload();

            });
        }

        function search() {

            var txt = document.getElementById("search").value;
            $.get("/search?req=" + txt, function (data) {
                if ("success" == data.result) {
                    alert("查询成功!");
                    window.location.reload();
                } else {
                    alert("查询失败!")
                }
                window.location.reload();

            });
        }

    </script>
</head>
<body>
<br/>
<br/>
<a href="/user/addUser.jsp"> 添加用户戳这里</a>
<br/>
<br/>

<input id="search" type="text" value="输入查询条件">
<button onclick="search()" name="search1"> xxxxx</button>

<br/>
<br/>


<table border="1">
    <tbody>
    <tr>
        <th>姓名</th>
        <th>年龄</th>
        <th>备注</th>
        <th>编辑</th>
    </tr>
    <c:if test="${!empty user }">
        <c:forEach items="${user }" var="u">
            <tr>
                <td>${u.userName }</td>
                <td>${u.age }</td>
                <td>${u.memo }</td>

                <td>
                    <a href="/user/getUser?id=${u.id }">编辑</a>
                    <a href="javascript:del('${u.id }')">删除</a>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
</body>
</html>