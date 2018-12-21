<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>安卓版本信息</title>
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
    <div class="col-sm-4 text-primary">
        <h2 style="float: left">安卓版本信息</h2>
        <h4>
            <i id="helpIcon" class="fa fa-question-circle" style="float: right;margin-top: 22px;margin-right: 210px" data-toggle="popover" title="如何操作?"></i>
        </h4>
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
            <select id="appMarket" class="form-control">
                <option value="">应用市场名称</option>
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
<script type="text/javascript" src="${re.contextPath}/plugin/app/version/versionAndroid.js"></script>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel"><strong>新增版本信息</strong></h4>
            </div>
            <div class="modal-body" style="height: 850px;">
                <div class="container-fluid">
                    <span class="col-md-3" style="margin-top: 5px;font-size: 15px">版本名称*:</span>
                    <span class="col-md-9">
                        <div class="form-group">
                            <input type="text" id="versionName" placeholder="例:1.1.0，最多20字" class="form-control" value="" maxlength="20"/>
                        </div>
                    </span>
                </div>
                <div class="container-fluid">
                    <span class="col-md-3" style="margin-top: 5px;font-size: 15px">链接地址*:</span>
                    <span class="col-md-9">
                        <div class="form-group">
                             <input type="text" id="downloadURL" placeholder="例:http://www.xxx.com" class="form-control" maxlength="100"/>
                        </div>
                    </span>
                </div>
                <div class="container-fluid">
                    <span class="col-md-3" style="margin-top: 5px;font-size: 15px">是否显示套壳:</span>
                    <span class="col-md-9">
                        <div class="form-group" id="appMarketContainer"></div>
                    </span>
                </div>
                <div class="container-fluid">
                    <span class="col-md-3" style="margin-top: 5px;font-size: 15px">提示信息*:</span>
                    <span class="col-md-9">
                        <div class="form-group">
                            <textarea id="description" class="form-control" placeholder="最多100个字" maxlength="35" rows="7" cols="5"></textarea>
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

<div class="modal fade" id="helpModal" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="gridSystemModalLabel">如何操作?</h4>
            </div>
            <div class="modal-body">
                1.只能存在一个套壳信息,否则发布失败<br><br>
                2.发布新版本时"版本名称"格式为"x.x.x",否则发布失败<br><br>
                3.套壳信息与真实版本信息一致,应用商店审核通过后，需要第一时间删除套壳信息<br><br>
                4.“是否显示套壳信息”，是:发布套壳版本；否:发布正式版本；取消:取消发布到应用商店<br><br>
                5.提交新版本时，仔细核对版本信息。已发布的版本无法修改，无法删除<br><br>
                6.如有疑问及时与技术人员沟通<br><br>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">我知道了</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>