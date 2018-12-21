<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>关于我们</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
  <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
  <link rel="stylesheet" href="${re.contextPath}/plugin/ztree/css/metroStyle/metroStyle.css">
  <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
  <#--<script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>-->
</head>
<body>
<div class="x-body">
    <div style="width:100%;height:400px;overflow: auto;">
        <div class="layui-form-item">
            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                <legend style="font-size:16px;">基础配置</legend>
            </fieldset>
        </div>
        <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
            <div class="layui-tab-content" style="height: 100px;">
                    <input type="hidden" id="pkid" name="pkid" value="${we.pkid}"/>
                        <div class="layui-form-item">
                            <label>
                                <span>公司简介&nbsp;&nbsp;&nbsp;&nbsp;</span>${we.aboutUs}
                            </label>

                        </div>
                        <div class="layui-form-item">
                            <label>
                                <span>商务合作&nbsp;&nbsp;&nbsp;&nbsp;</span>${we.businessCooperation}
                            </label>

                        </div>
                        <div class="layui-form-item">
                            <label>
                                <span>公众号图片&nbsp;&nbsp;&nbsp;&nbsp;</span><img style="width: 200px;height: 200px;" src="${we.wechatUrl}">
                            </label>
                        </div>
                        <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
  position: fixed;bottom: 1px;margin-left:-20px;">
                            <div class="layui-form-item" style="margin-right: 50px;margin-top: 8px">
                                <button  class="layui-btn layui-btn-normal" onclick="update()" style="margin-left: 50px">
                                    修改
                                </button>
                            </div>
                        </div>
            </div>

        </div>
    </div>
    <script>
        function update() {
            var id = $("#pkid").val();window.location.href='/aboutWe/getAboutWeById?id='+id;
        }
    </script>
</body>

</html>
