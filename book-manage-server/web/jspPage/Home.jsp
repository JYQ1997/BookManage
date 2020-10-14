<%--
  Created by IntelliJ IDEA.
  User: YTO
  Date: 2020/10/12
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>马不停蹄图书管理系统</title>
    <meta name="keywords" content="马不停蹄图书管理系统">
    <meta name="description" content="马不停蹄图书管理系统">
    <link href="<%=basePath%>static/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath%>static/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="<%=basePath%>static/css/animate.css" rel="stylesheet">
    <link href="<%=basePath%>static/css/style.css" rel="stylesheet">
    <link href="<%=basePath%>static/css/login.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/>
    <![endif]-->
    <script>
        if (window.top !== window.self) {
            window.top.location = window.location;
        }
    </script>

</head>

<body class="signin">
<div class="signinpanel">
    <div class="row">
        <div class="col-sm-7">
            <div class="signin-info">
                <div class="logopanel m-b">
                    <h1>BootDo</h1>
                </div>
                <div class="m-b"></div>
                <h3>
                    欢迎使用 <strong>马不停蹄图书管理系统</strong>
                </h3>
                <ul class="m-b">
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i>java</li>
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i>jsp</li>
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i>jdbc</li>
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i>mysql</li>
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i>bootstrap</li>
                </ul>
            </div>
        </div>
        <div class="col-sm-5">
            <form id="signupForm" action="user/login">
                <h3 class="text-center">用户登录</h3>
                <p class="m-t-md text-center">欢迎登录马不停蹄图书管理系统</p>
                <input type="text" name="username" class="form-control uname"
                       value="${username}"/>
                <input type="password" name="password"
                       class="form-control pword m-b" value="${password}"/>
                <a id="login" class="btn btn-login btn-block">登录</a>
                <%
                if (session.getAttribute("error")!=null){
                    %>

                <i class='fa fa-times-circle'>${error}</i>
                <%
                }
                %>
                <div class="row">
                    <div class="col-xs-6 pull_left">
                        <div class="form-group">
                            <input class="form-control" type="tel" name="verify" id="verify" placeholder="请输入验证码" maxlength="4">
                        </div>
                    </div>
                    <div class="col-xs-6 pull_left">
                        <a href="javascript:void(0);" rel="external nofollow" title="点击更换验证码">
                            <img style="margin-top: 12px;" id="imgVerify" src="" alt="更换验证码" height="36" width="100%" onclick="getVerify(this);">
                        </a>
                    </div>
                </div>
                <!--按钮模块-->
                <div class="outside-login">
                    <div class="outside-login-tit">
                        <span>代码链接</span>
                    </div>
                    <div class="outside-login-cot">
                        <a class="outside-login-btn wxoa actived oschina J-btnSwitchLoginType" target="_Blank"
                           href="https://www.oschina.net/p/bootdo">
                            <em><i class="fa fa-home"></i></em>
                            <span>oschina主页</span>
                        </a>
                        <a class="outside-login-btn wxoa actived my J-btnSwitchLoginType" target="_Blank"
                           href="https://gitee.com/lcg0124/bootdo">
                            <em><i class="fa fa-git-square"></i></em>
                            <span>码云仓库</span>
                        </a>
                        <a class="outside-login-btn wxoa actived git J-btnSwitchLoginType" target="_Blank"
                           href="https://github.com/lcg0124/bootdo">
                            <em><i class="fa fa-github"></i></em>
                            <span>GitHub仓库</span>
                        </a>
                    </div>
                </div>

            </form>
        </div>
    </div>
    <div class="signup-footer">
        <div class="pull-left">&copy; 2017 All Rights Reserved. BootDo
        </div>
    </div>
</div>
<%--<script th:inline="javascript"> var ctx = [[@{
    /}]] ; </script>--%>
<!-- 全局js -->
<script src="<%=basePath%>static/js/jquery.min.js?v=2.1.4"></script>
<script src="<%=basePath%>static/js/bootstrap.min.js?v=3.3.6"></script>

<!-- 自定义js -->
<script src="<%=basePath%>static/js/content.js?v=1.0.0"></script>

<!-- jQuery Validation plugin javascript-->
<script src="<%=basePath%>static/js/ajax-util.js"></script>
<script src="<%=basePath%>static/js/plugins/validate/jquery.validate.min.js"></script>
<script src="<%=basePath%>static/js/plugins/validate/messages_zh.min.js"></script>
<script src="<%=basePath%>static/js/plugins/layer/layer.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#login").on('click', function () {
            $("#signupForm").submit();
        });
        validateRule();
        $("body").keydown(keyDownLogon);
        // getVerify($("#imgVerify"));
        $("#imgVerify").click()
    });

    $.validator.setDefaults({
        submitHandler: function () {
            login();
        }
    });

    function login() {
        $.ajax({
            type: "POST",
            url: "user/login",
            data: $('#signupForm').serialize(),
            success: function (r) {
                console.log(r);
                console.log(r.data);
                console.log(r.code);
                if (r.code == 200) {
                    var index = layer.load(1, {
                        shade: [0.1, '#fff'] //0.1透明度的白色背景
                    });
                    parent.location.href = 'index';
                } else {
                    layer.msg(r.msg);
                }
            },
            error: function (s) {
                console.log(s);
            }
        });
    }

    function keyDownLogon() {
        if (event.keyCode == "13") {
            $("#login").trigger('click');
        }
    }


    function validateRule() {
        var icon = "<i class='fa fa-times-circle'></i> ";
        $("#signupForm").validate({
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                }
            },
            messages: {
                username: {
                    required: icon + "请输入您的用户名",
                },
                password: {
                    required: icon + "请输入您的密码",
                }
            }
        })
    }

    //获取验证码
    function getVerify(obj) {
        obj.src = "user/getCode?" + Math.random();
        console.log(obj)
    }
</script>
</body>
</html>

