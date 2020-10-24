<%@ page import="com.bookmanage.dto.UserDto" %><%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 2020-10-18
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    UserDto user= (UserDto) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>书籍管理</title>
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
<div class="wrapper wrapper-content ">
    <div class="col-sm-12">
        <div class="ibox">
            <div class="ibox-body">
                <div class="fixed-table-toolbar">
                    <div class="columns pull-right">
                        <button class="btn btn-success" onclick="reLoad()">查询</button>
                    </div>
                    <div class="columns pull-right col-md-2 nopadding">
                        <select id="type" name="type" data-placeholder="选择类别" class="form-control chosen-select"
                                tabindex="2" style="width: 100%">
                            <option value="0">选择类别</option>
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
                <table id="exampleTable" data-mobile-responsive="true">
                </table>
            </div>
        </div>
    </div>
</div>
<!--shiro控制bootstraptable行内按钮看见性 来自bootdo的创新方案 -->
<div>
    <script type="text/javascript">
        var s_edit_h = 'hidden';
        var s_remove_h = 'hidden';
        var s_resetPwd_h = 'hidden';
    </script>
</div>
<div>
    <script type="text/javascript">
        s_edit_h = '';
    </script>
</div>
<div>
    <script type="text/javascript">
        var s_remove_h = '';
    </script>
</div>
<div>
    <script type="text/javascript">
        var s_add_h = '';
    </script>
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


    var prefix = "<%=basePath%>";
    $(function() {

        load();
    });
    /*function selectLoad() {
        var html = "";
        $.ajax({
            url :prefix + "book/type",
            success : function(data) {
                for (var i = 0; i < data.length; i++) {
                    html += '<option value="' + data[i].code + '">' + data[i].desc + '</option>'
                }
                $(".chosen-select").append(html);
                $(".chosen-select").chosen({
                    maxHeight : 200
                });
                //点击事件
                $('.chosen-select').on('change', function(e, params) {
                    console.log(params.selected);
                    var opt = {
                        query : {
                            type : params.selected,
                        }
                    }
                    $('#exampleTable').bootstrapTable('refresh', opt);
                });
            }
        });
    }*/
    function load() {
        $('#exampleTable')
            .bootstrapTable(
                {
                    method : 'get', // 服务器数据的请求方式 get or post
                    url : prefix + "user/getUserList", // 服务器数据的加载地址
                    //	showRefresh : true,
                    //	showToggle : true,
                    //	showColumns : true,
                    iconSize : 'outline',
                    toolbar : '#exampleToolbar',
                    striped : true, // 设置为true会有隔行变色效果
                    dataType : "json", // 服务器返回的数据类型
                    pagination : true, // 设置为true会在底部显示分页条
                    // queryParamsType : "limit",
                    // //设置为limit则会发送符合RESTFull格式的参数
                    singleSelect : false, // 设置为true将禁止多选
                    // contentType : "application/x-www-form-urlencoded",
                    // //发送到服务器的数据编码类型
                    pageSize : 10, // 如果设置了分页，每页数据条数
                    pageNumber : 1, // 如果设置了分布，首页页码
                    //search : true, // 是否显示搜索框
                    showColumns : true, // 是否显示内容下拉框（选择显示的列）
                    sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                    queryParams : function(params) {
                        return {
                            //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                            limit : params.limit,
                            offset : params.offset,
                            // name:$('#searchName').val(),
                            type : $('#type').val(),
                        };
                    },
                    // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                    // queryParamsType = 'limit' ,返回参数必须包含
                    // limit, offset, search, sort, order 否则, 需要包含:
                    // pageSize, pageNumber, searchText, sortName,
                    // sortOrder.
                    // 返回false将会终止请求
                    columns : [
                        {
                            checkbox : true
                        },
                        {
                            field : 'userId',
                            title : '用户ID'
                        },
                        {
                            field : 'userName',
                            title : '账号'
                        },
                        {
                            field : 'name',
                            title : '昵称'
                        },
                        {
                            field : 'sex',
                            title : '性别',
                            formatter : function(value, row, index) {
                                if (row.sex==1){
                                    var e = '男';
                                }
                                else{
                                    var e = '女';
                                }

                                return e;
                            }
                        },
                        {
                            field : 'email',
                            title : '邮箱'
                        },
                        {
                            field : 'status',
                            title : '状态',
                            formatter : function(value, row, index) {
                                if (row.status==1){
                                    //如果用户状态是启用
                                    var e = '启用';
                                }
                                else if (row.status==2) {
                                    //如果用户状态是禁用
                                    var e = '<span style="color: #42c0ff">申请恢复</span>';
                                }
                                else {
                                    //如果用户状态是禁用
                                    var e = '<span style="color: red">已禁用</span>';
                                }

                                return e;
                            }
                        },
                        {
                            field : 'gmtCreate',
                            title : '注册时间'
                        },
                        {
                            field : 'name',
                            title : '创建者'
                        },
                        {
                            field : 'province',
                            title : '省份'
                        },
                        {
                            field : 'city',
                            title : '市区'
                        },
                        {
                            title : '操作',
                            field : 'id',
                            align : 'center',
                            formatter : function(value, row, index) {

                                if (row.userId!=<%=user.getUserId()%> && row.roleId > <%=user.getRoleId()%>){
                                    //不是自己，且登陆用户权限较大，允许操作

                                    if (row.status==1){
                                        //如果用户状态是启用
                                        var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title=状态" onclick="changeSatus(\''
                                            + row.userId
                                            + '\')">禁用</a> ';
                                    }
                                    else{
                                        //如果用户状态是禁用
                                        var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title=状态" onclick="changeSatus(\''
                                            + row.userId
                                            + '\')">启用</a> ';
                                    }
                                    //如果是超级管理员
                                    if(row.roleId==2){
                                        var f = '<a class="btn btn-success btn-sm ' + s_add_h + '" href="#" title="下载"  mce_href="#" onclick="setUser(\''
                                            + row.userId
                                            + '\')">设为普通用户</a> ';
                                    }
                                    //如果是超级管理员
                                    if(row.roleId==3){
                                        var f = '<a class="btn btn-success btn-sm ' + s_add_h + '" href="#" title="下载"  mce_href="#" onclick="setAdmin(\''
                                            + row.userId
                                            + '\')">设为管理员</a> ';
                                    }
                                    return e + f;
                                }
                            }
                        } ]
                });
    }
    function reLoad() {
        var opt = {
            query : {
                type : $('.chosen-select').val(),
            }
        }
        $('#exampleTable').bootstrapTable('refresh', opt);
    }

    function changeSatus(id) {

        $.ajax({
            url : prefix + "user/changeStatus",
            type : "post",
            data : {
                'id' : id
            },
            success : function(r) {
                if (r.code == 200) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }
    function setAdmin(id) {

        $.ajax({
            url : prefix + "user/setAdmin",
            type : "post",
            data : {
                'id' : id
            },
            success : function(r) {
                if (r.code == 200) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }
    function setUser(id) {

        $.ajax({
            url : prefix + "user/setUser",
            type : "post",
            data : {
                'id' : id
            },
            success : function(r) {
                if (r.code == 200) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }

    function addD(type,description) {
        layer.open({
            type : 2,
            title : '增加',
            maxmin : true,
            shadeClose : false, // 点击遮罩关闭层
            area : [ '800px', '520px' ],
            content : prefix + '/add/'+type+'/'+description // iframe的url
        });
    }
    function batchRemove() {
        var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
        if (rows.length == 0) {
            layer.msg("请选择要删除的数据");
            return;
        }
        layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
            btn : [ '确定', '取消' ]
            // 按钮
        }, function() {
            var ids = new Array();
            // 遍历所有选择的行数据，取每条数据对应的ID
            $.each(rows, function(i, row) {
                ids[i] = row['id'];
            });
            $.ajax({
                type : 'POST',
                data : {
                    "ids" : ids
                },
                url : prefix + '/batchRemove',
                success : function(r) {
                    if (r.code == 0) {
                        layer.msg(r.msg);
                        reLoad();
                    } else {
                        layer.msg(r.msg);
                    }
                }
            });
        }, function() {});
    }
</script>
</body>
</html>