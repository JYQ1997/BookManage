<%@ page import="com.bookmanage.dto.UserDto" %>
<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 2020-10-18
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    UserDto user= (UserDto) session.getAttribute("user");
    String bid=request.getParameter("bid");
%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>添加图书</title>
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
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>   </h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="signupForm">
                        <input id="bid" name="bid" type="hidden" value="<%=bid%>">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">书名：</label>
                            <div class="col-sm-8">
                                <input id="bookName" readonly name="bookName" class="form-control"
                                       type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">作者：</label>
                            <div class="col-sm-8">
                                <input id="author" name="author" readonly class="form-control"
                                       type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">类型：</label>
                            <div class="col-sm-8">
                                <select id="type" name="type" data-placeholder="选择类别" class="form-control chosen-select"
                                        tabindex="2" style="width: 100%">
                                    <option value="1">教育教学</option>
                                    <option value="2">休闲读物</option>
                                    <option value="3">经济管理</option>
                                    <option value="4">工业技术</option>
                                    <option value="5">外语教育</option>
                                    <option value="6">文学读物</option>
                                    <option value="7">艺术设计</option>
                                    <option value="8">社会科学</option>
                                    <option value="9">计算机</option>
                                    <option value="10">政治图书</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">描述：</label>
                            <div class="col-sm-8">
                                <input id="desc" name="desc" class="form-control"
                                       type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否允许下载：</label>
                            <div class="col-sm-8">
                                <select id="isDownload" name="isDownload" class="form-control">
                                    <option value="1">是</option>
                                    <option value="2">否</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">下载金币：</label>
                            <div class="col-sm-8">
                                <input id="downloadPay" name="downloadPay" class="form-control"
                                       type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button type="submit" class="btn btn-primary">提交</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div>
    <script src="<%=basePath%>static/js/jquery.min.js?v=2.1.4"></script>
    <script src="<%=basePath%>static/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="<%=basePath%>static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="<%=basePath%>static/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
    <script
            src="<%=basePath%>static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="<%=basePath%>static/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="<%=basePath%>static/js/plugins/validate/messages_zh.min.js"></script>
    <script src="<%=basePath%>static/js/plugins/jsTree/jstree.min.js"></script>
    <script src="<%=basePath%>static/js/plugins/jqTreeGrid/jquery.treegrid.min.js"></script>
    <script src="<%=basePath%>static/js/plugins/jqTreeGrid/jquery.treegrid.extension.js"></script>
    <script src="<%=basePath%>static/js/plugins/jqTreeGrid/jquery.treegrid.bootstrap3.js"></script>
    <script src="<%=basePath%>static/js/plugins/chosen/chosen.jquery.js"></script>
    <script src="<%=basePath%>static/js/plugins/layer/layer.js"></script>
    <script src="<%=basePath%>static/js/content.js?v=1.0.0"></script>
    <!--summernote-->
    <script src="<%=basePath%>static/js/plugins/summernote/summernote.js"></script>
    <script src="<%=basePath%>static/js/plugins/summernote/summernote-zh-CN.min.js"></script>
    <script src="<%=basePath%>static/js/ajax-util.js"></script>
</div>
<script type="text/javascript">
    //var menuTree;

    $(function() {
        load()
        validateRule();
    });
    $.validator.setDefaults({
        submitHandler : function() {
            save();
        }
    });

    function load() {
        $.ajax({
            cache : true,
            type : "POST",
            url : "<%=basePath%>book/getBookById",
            data : {
                "bid":"<%=bid%>"
            },
            dataType : "json",
            async : false,
            error : function(request) {
                alert("Connection error");
            },
            success : function(data) {
                if (data.code == 200){
                    $("#bookName").val(data.data.book.title);
                    $("#author").val(data.data.book.author);
                    $("#desc").val(data.data.book.desc);
                    console.log($("#isDownload").val);
                    $("#isDownload").val(data.data.book.isDownload);
                    console.log($("#isDownload").val);
                    $("#downloadPay").val(data.data.book.downloadPay);
                    $("#type").val(data.data.book.typeCode);

                } else {
                    parent.layer.msg(data.msg);
                }
            }
        });
    }


    function save() {
        var from = $('#signupForm').serialize();
        $.ajax({
            cache : true,
            type : "POST",
            url : "<%=basePath%>book/editBook",
            data : from,
            async : false,
            error : function(request) {
                alert("Connection error");
            },
            success : function(data) {
                if (data.code == 200) {
                    parent.layer.msg("操作成功");
                    parent.reLoad();
                    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引

                    parent.layer.close(index);

                } else {
                    parent.layer.msg(data.msg);
                }
            }
        });
    }

    function validateRule() {
        var icon = "<i class='fa fa-times-circle'></i> ";
        $("#signupForm").validate({
            rules : {
                bookName : {
                    required : true
                }
            },
            messages : {
                bookName : {
                    required : icon + "请输入书名"
                }
            }
        });
    }
</script>
</body>

</html>