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
            $.get("/springMVC/user/delUser?id=" + id, function (data) {
                if ("success" == data.result) {
                    alert("删除成功!");
                    window.location.reload();
                } else {
                    alert("删除失败!")
                }
            });
        }

    </script>
</head>
<body>
<br/>
<br/>
<a href="/springMVC/user/getAllUser"> 用户管理请戳这里</a>
<br/>
<br/>

</body>
</html>