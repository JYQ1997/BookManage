<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<meta charset="utf-8">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link rel="shortcut icon" href="favicon.ico">
    <link rel="stylesheet" href="<%=basePath%>static/css/bootstrap.min.css" />
    <link rel="stylesheet" href="<%=basePath%>static/css/animate.css" />
    <link rel="stylesheet" href="<%=basePath%>static/css/font-awesome.css" />
    <link rel="stylesheet" href="<%=basePath%>static/css/style.css" />
    <link rel="stylesheet" href="<%=basePath%>static/css/plugins/iCheck/custom.css" />
    <link rel="stylesheet" href="<%=basePath%>static/css/plugins/cropper/cropper.css" />
    <link rel="stylesheet" href="<%=basePath%>static/js/plugins/layer/laydate/skins/default/laydate.css" />
    <link rel="stylesheet" href="<%=basePath%>static/css/gg-bootdo.css" />
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12">
            <div class="ibox">
                <div class="ibox-title" style="padding-bottom: 0;">
                    <h3 class="text-center">个人资料中心</h3>
                    <div class="gg-nav">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#basic_info" data-toggle="tab">基本资料</a></li>
                            <li><a href="#photo_info" data-toggle="tab">头像修改</a></li>
                            <li><a href="#pwd_info" data-toggle="tab">修改密码</a></li>
                        </ul>
                    </div>
                </div>
                <div class="tab-content">
                    <!--basic info-->
                    <div class="ibox-content tab-pane fade in active"  id="basic_info">
                        <form class="gg-form" role="form" id="basicInfoForm">
                            <input name="userId" type="hidden" value="${user.userId}"/>
                            <div class="gg-formGroup">
                                <div class="gg-formTitle">
                                    <em class="gg-star">*</em>
                                    <span>姓名:</span>
                                </div>
                                <div class="gg-formDetail">
                                    <input type="text" class="form-control" id="userName" name="username"
                                           value="${user.name}"  placeholder="请输入姓名" />
                                </div>
                            </div>
                            <div class="gg-formGroup">
                                <div class="gg-formTitle">
                                    <em class="gg-star">*</em>
                                    <span>性别:</span>
                                </div>
                                <div class="gg-formDetail">
                                    <div class="radio i-checks">
                                        <label class="radio-inline">
                                            <input type="radio" name="sex" value="1" th:text="男" />
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="sex" value="2" th:text="女"/>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="gg-formGroup">
                                <div class="gg-formTitle">
                                    <em class="gg-star">*</em>
                                    <span>出生年月:</span>
                                </div>
                                <div class="gg-formDetail">
                                    <input type="text" class="laydate-icon layer-date form-control" id="birth"
                                           name="birth" <c:if test="${user.birth!=null}">${user.gmtCreate}</c:if>
                                           placeholder="请选择出生年月"
                                           onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" style="background-color: #fff;" readonly="readonly"/>
                                </div>
                            </div>
                            <div class="gg-formGroup">
                                <div class="gg-formTitle">
                                    <em class="gg-star">*</em>
                                    <span>手机:</span>
                                </div>
                                <div class="gg-formDetail">
                                    <input type="text" class="form-control" id="phone" name="mobile"  value="${user.mobile}" placeholder="请输入手机号" />
                                </div>
                            </div>
                            <div class="gg-formGroup">
                                <div class="gg-formTitle">
                                    <em class="gg-star">*</em>
                                    <span>邮箱:</span>
                                </div>
                                <div class="gg-formDetail">
                                    <input type="text" class="form-control" id="email" name="email" value="${user.email}" placeholder="请输入邮箱" />
                                </div>
                            </div>
                            <div class="gg-formGroup">
                                <div class="gg-formTitle">
                                    <em class="gg-star">*</em>
                                    <span>居住地:</span>
                                </div>
                                <div class="gg-formDetail gg-font0" data-toggle="distpicker"  attr="data-province=${user.province},data-city=${user.city},data-district=${user.district}">
                                    <div class="gg-area">
                                        <select class="form-control" id="province" name="province">
                                        </select>
                                    </div>
                                    <div class="gg-area">
                                        <select class="form-control" id="city" name="city">
                                        </select>
                                    </div>
                                    <div class="gg-area">
                                        <select class="form-control" id="district" name="district">
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="gg-formGroup">
                                <div class="gg-formTitle">
                                    <em class="gg-star">*</em>
                                    <span>联系地址:</span>
                                </div>
                                <div class="gg-formDetail">
                                    <input type="text" class="form-control" id="address" name="liveAddress" tvalue="${user.liveAddress}" placeholder="请输入居住地址" />
                                </div>
                            </div>
                        </form>
                        <div class="gg-btnGroup">
                            <button type="button" class="btn btn-sm btn-primary" id="base_save">保存</button>
                        </div>
                    </div>
                    <!--photo_info-->
                    <div class="ibox-content tab-pane fade gg" id="photo_info">
                        <div class="ggcontainer" id="crop-avatar">
                            <form class="avatar-form" action="/sys/user/uploadImg" enctype="multipart/form-data" method="post">
                                <div class="avatar-body">
                                    <div class="avatar-upload">
                                        <input class="avatar-src" name="avatar_src" type="hidden">
                                        <input class="avatar-data" name="avatar_data" type="hidden">
                                        <label for="avatarInput">选取文件</label>
                                        <input class="avatar-input" id="avatarInput" name="avatar_file" type="file">
                                    </div>
                                    <!-- Crop and preview -->
                                    <div class="row">
                                        <div class="col-md-9">
                                            <div class="avatar-wrapper"></div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="avatar-preview preview-lg"></div>
                                            <div class="avatar-preview preview-md"></div>
                                            <div class="avatar-preview preview-sm"></div>
                                        </div>
                                    </div>

                                    <div class="row avatar-btns">
                                        <div class="col-md-9">
                                            <div class="btn-group">
                                                <button class="btn btn-primary" data-method="rotate" data-option="-90" type="button" title="Rotate -90 degrees">左旋转</button>
                                                <button class="btn btn-primary" data-method="rotate" data-option="-15" type="button">-15°</button>
                                                <button class="btn btn-primary" data-method="rotate" data-option="-30" type="button">-30°</button>
                                                <button class="btn btn-primary" data-method="rotate" data-option="-45" type="button">-45°</button>
                                            </div>
                                            <div class="btn-group">
                                                <button class="btn btn-primary" data-method="rotate" data-option="90" type="button" title="Rotate 90 degrees">右旋转</button>
                                                <button class="btn btn-primary" data-method="rotate" data-option="15" type="button">15°</button>
                                                <button class="btn btn-primary" data-method="rotate" data-option="30" type="button">30°</button>
                                                <button class="btn btn-primary" data-method="rotate" data-option="45" type="button">45°</button>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <button class="btn btn-primary btn-block avatar-save" type="submit">完成裁剪</button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <!-- Loading state -->
                            <div class="loading" aria-label="Loading" role="img" tabindex="-1"></div>
                        </div>
                    </div>
                    <!--pwd_info-->
                    <div class="ibox-content tab-pane fade" id="pwd_info">
                        <form class="gg-form" role="form" id="modifyPwd">
                            <a id="logout" class="hidden" href="/logout"></a>
                            <input type="hidden" name ="userDO.userId" th:value="${user.userId}"/>
                            <div class="gg-formGroup">
                                <div class="gg-formTitle">
                                    <em class="gg-star">*</em>
                                    <span>旧密码:</span>
                                </div>
                                <div class="gg-formDetail gg-dashed">
                                    <input type="password" class="form-control gg-border0" id="pwdOld" name="pwdOld" placeholder="请输入旧密码" />
                                    <span class="fa fa-eye gg-faeye" title="鼠标移入显示内容"><span>
                                </div>
                            </div>
                            <div class="gg-formGroup">
                                <div class="gg-formTitle">
                                    <em class="gg-star">*</em>
                                    <span>新密码:</span>
                                </div>
                                <div class="gg-formDetail gg-dashed">
                                    <input type="password" class="form-control gg-border0" id="pwdNew" name="pwdNew" placeholder="请输入新密码" />
                                    <span class="fa fa-eye gg-faeye" title="鼠标移入显示内容"></span>
                                </div>
                            </div>
                            <div class="gg-formGroup">
                                <div class="gg-formTitle">
                                    <em class="gg-star">*</em>
                                    <span>确认密码:</span>
                                </div>
                                <div class="gg-formDetail gg-dashed">
                                    <input type="password" class="form-control gg-border0" id="confirm_password" name="confirm_password" placeholder="请确认密码" />
                                    <span class="fa fa-eye gg-faeye" title="鼠标移入显示内容"></span>
                                </div>
                            </div>
                        </form>
                        <div class="gg-btnGroup">
                            <button type="button" class="btn btn-sm btn-primary" id="pwd_save">保存</button>
                        </div>
                    </div>

                </div>

            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<%=basePath%>static/js/jquery.min.js" ></script>
<script type="text/javascript" src="<%=basePath%>static/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="<%=basePath%>static/js/plugins/iCheck/icheck.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/plugins/cropper/cropper.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/plugins/layer/laydate/laydate.js" ></script>
<script type="text/javascript" src="<%=basePath%>static/js/plugins/distpicker/distpicker.data.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/plugins/distpicker/distpicker.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/appjs/sys/user/gg-bootdo.js"></script>
<!--校验插件-->
<script src="<%=basePath%>static/js/plugins/validate/jquery.validate.min.js"></script>
<script src="<%=basePath%>static/js/plugins/validate/jquery.validate.extend.js"></script>
<script src="<%=basePath%>static/js/plugins/validate/messages_zh.min.js"></script>
<script type="text/javascript">
    var prefix = "/sys/user"
    $(function () {
        laydate({
            elem : '#birth'
        });
    });
    /**
     * 基本信息提交
     */
    $("#base_save").click(function () {
        var hobbyStr = getHobbyStr();
        $("#hobby").val(hobbyStr);
        if($("#basicInfoForm").valid()){
            $.ajax({
                cache : true,
                type : "POST",
                url :"/sys/user/updatePeronal",
                data : $('#basicInfoForm').serialize(),
                async : false,
                error : function(request) {
                    laryer.alert("Connection error");
                },
                success : function(data) {
                    if (data.code == 0) {
                        parent.layer.msg("更新成功");
                    } else {
                        parent.layer.alert(data.msg)
                    }
                }
            });
        }

    });
    $("#pwd_save").click(function () {
        if($("#modifyPwd").valid()){
            $.ajax({
                cache : true,
                type : "POST",
                url :"/sys/user/resetPwd",
                data : $('#modifyPwd').serialize(),
                async : false,
                error : function(request) {
                    parent.laryer.alert("Connection error");
                },
                success : function(data) {
                    if (data.code == 0) {
                        parent.layer.alert("更新密码成功");
                        $("#photo_info").click();
                    } else {
                        parent.layer.alert(data.msg)
                    }
                }
            });
        }
    });
    function getHobbyStr(){
        var hobbyStr ="";
        $(".hobby").each(function () {
            if($(this).is(":checked")){
                hobbyStr+=$(this).val()+";";
            }
        });
        return hobbyStr;
    }

</script>
</body>
</html>
