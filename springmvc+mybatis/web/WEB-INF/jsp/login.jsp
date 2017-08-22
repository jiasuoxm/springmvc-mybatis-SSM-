<%--
  Created by IntelliJ IDEA.
  User: wangergou
  Date: 2017/8/20
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录页面</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/user/login.action" method="post">
    用户名：<input type="text" name="username">
    密码：<input type="password" name="password">
    <input type="submit" value="登录">
</form>
</body>
</html>
