<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>版本信息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content=" width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/plugins/font-awesome/css/font-awesome.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/bootstrap/bootstrap-tables/bootstrap-table.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/layer/theme/default/layer.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/app/css/nmp.css">
</head>
<body class="panel-body" id="registerUsers">
<div class="row">
    <div class="col-sm-2 text-primary">
        <h2>版本信息</h2>
    </div>
</div>
<div class="row " id="registerUserTableToolbar">
    <div class="form-inline" style="height:3.5em;width: 950px">
        <div class="col-sm-7 text-left">
            <input id="startTime" type="text" class="form-control" style="width: 46%"  placeholder="起始时间">
            &nbsp;-&nbsp;
            <input id="endTime" type="text" class="form-control" style="width: 46%" placeholder="结束时间">
        </div>
        <div class="col-sm-5 text-left">
            <select id="systemName" class="form-control">
                <option value="">操作类型</option>
                <option value="2">Ios正式</option>
                <option value="3">Ios测试</option>
            </select>
            <button class="btn btn-info fa fa-search" style="margin-left: 2px" id="searchBtn">搜索</button>
            <button class="btn btn-info fa fa-plus" id="addBtn">新增</button>
        </div>
    </div>
</div>
<table id="versionListTable"></table>
</body>

</html>

<script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/bootstrap/bootstrap-tables/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/bootstrap/bootstrap-tables/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/bootstrap/bootstrap-tables/tableExport.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/bootstrap/bootstrap-tables/bootstrap-table-export.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/laydate/laydate.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/layer/layer.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/app/nmp.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/app/version/version.js"></script>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel"><strong>新增版本信息</strong></h4>
            </div>
            <div class="modal-body" style="height: 350px;">
                <div class="container-fluid">
                    <#--<span class="col-md-3" style="margin-top: 5px;font-size: 15px">系统类型*:</span>-->
                    <#--<span class="col-md-9">-->
                        <#--<div class="form-group">-->
                            <#--<label>-->
                                <#--<input type="radio"  name="typeName" id="android">Android-->
                            <#--</label>-->
                            <#--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->
                            <#--<label>-->
                                <#--<input type="radio"  name="typeName" id="ios">Ios-->
                            <#--</label>-->
                        <#--</div>-->
                    <#--</span>-->
                </div>
                <div class="container-fluid">
                    <span class="col-md-3" style="margin-top: 5px;font-size: 15px">套壳版本*:</span>
                    <span class="col-md-9">
                        <div class="form-group">
                            <label>
                                <input type="radio" name="isShell" id="yesShell">是
                            </label>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <label>
                                <input type="radio" name="isShell" id="noShell">否
                            </label>
                        </div>
                    </span>
                </div>
                <div class="container-fluid">
                    <span class="col-md-3" style="margin-top: 5px;font-size: 15px">链接地址:</span>
                    <span class="col-md-9">
                        <div class="form-group">
                             <input type="text" id="downloadURL" placeholder="http://www.xxx.com" class="form-control" maxlength="100"/>
                        </div>
                    </span>
                </div>
                <div class="container-fluid">
                    <span class="col-md-3" style="margin-top: 5px;font-size: 15px">版本号:</span>
                    <span class="col-md-9">
                        <div class="form-group">
                             <input type="text" id="name" placeholder="最多20字" class="form-control" value="" maxlength="20"/>
                        </div>
                    </span>
                </div>
                <div class="container-fluid">
                    <span class="col-md-3" style="margin-top: 5px;font-size: 15px">提示信息:</span>
                    <span class="col-md-9">
                        <div class="form-group">
                            <textarea id="description" class="form-control" placeholder="最多35个字" maxlength="35" rows="7" cols="5"></textarea>
                        </div>
                    </span>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="subBtn">确认</button>
            </div>
        </div>
    </div>
</div>