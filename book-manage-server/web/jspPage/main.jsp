<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>欢迎页</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="<%=basePath%>staticcss/bootstrap.min.css">
</head>
<body>
<div class="panel panel-default">
    <div class="panel-heading">了解BootDo</div>
    <div style="padding: 10px 0 20px 10px;">
        <h3>&nbsp;&nbsp;&nbsp;项目介绍</h3>
        <ul>
            <li>毕设项目，图书管理系统</li>
            <li>使用jsp+mysql技术进行开发</li>
            <li>实现功能包括，用用户管理，图书管理，用户添加编辑和删除图书等等</li>
            <li>前端借用bootdo样式模板</li>
        </ul>
    </div>
</div>
</body>
<script src="<%=basePath%>staticjs/jquery.min.js"></script>
<script src="<%=basePath%>staticjs/openTab.js"></script>
</html>