<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>攻略捷报管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js" charset="utf-8"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layer/layer.js" charset="utf-8"></script>

    <style>
        .layui-form-pane .layui-form-label {
            width: 160px;
        }

        .layui-input {
            width: 83%;
        }
    </style>
</head>

<body>
<div class="x-body">
    <form class="layui-form layui-form-pane" style="margin-left: 20px;">
        <div style="width:100%;height:400px;overflow: auto;">

            <div class="layui-form-item" style="display: ${strategy}">
                <label for="name" class="layui-form-label">
                    <span class="x-red">*</span>标题
                </label>
                <div class="layui-input-inline">
                    <input value="${strategySuccess.pkid}" type="hidden" name="pkid">
                    <input type="text" id="name" name="name" value="${strategySuccess.name}" lay-verify="name"
                           maxlength="20"
                           autocomplete="off" class="layui-input" placeholder="字数不能超过20个" style="width: 473px;">
                </div>
            </div>

            <div class="layui-form-item" style="display: ${success}">
                <label for="des" class="layui-form-label">
                    <span class="x-red">*</span>内容
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="des" name="des" value="${strategySuccess.des}" lay-verify=""
                           autocomplete="off" class="layui-input" placeholder="字数不能超过50个" maxlength="50">
                </div>
            </div>
            <div class="layui-form-item" style="display: ${strategy}">
                <label for="url" class="layui-form-label">
                    <span class="x-red"></span>攻略链接
                </label>
                <div class="layui-input-block">
                    <input type="url" id="url" style="width: 83%" name="url" value="${strategySuccess.url}"
                           lay-verify="${url}"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item" style="display: ${strategy}">
                <label for="readNum" class="layui-form-label">
                    <span class="x-red"></span>阅读量（单位：万）
                </label>
                <div class="layui-input-block">
                    <input type="text" id="readNum" style="width: 83%" name="readNum" lay-verify="readNum"
                           value="${strategySuccess.readNum}"
                           autocomplete="off" class="layui-input" placeholder="只能填写数字">
                </div>
            </div>
            <div class="layui-form-item">
                <label for="status" class="layui-form-label">
                    <span class="x-red"></span>状态
                </label>
                <div class="layui-input-block">
                    <select id="status" name="status" onselect="${strategySuccess.status}" lay-verify="">
                        <option value="1">上线</option>
                        <option value="2">下线</option>
                    </select>
                </div>
            </div>

            <div style="height: 60px"></div>
        </div>
        <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
  position: fixed;bottom: 1px;margin-left:-20px;">
            <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">

                <button class="layui-btn layui-btn-normal" lay-filter="add" lay-submit="">
                    确定
                </button>
                <button class="layui-btn layui-btn-primary" id="close">
                    取消
                </button>
            </div>
        </div>
    </form>
</div>
<script>
    $(function () {
        $("#readNum").val($("#readNum").val().replace(',',''));
        <#--var select = 'dd[lay-value=' + ${strategySuccess.status} +']';-->
        <#--$('#status').siblings("div.layui-form-select").find('dl').find(select).click();-->
    });
    layui.use(['form', 'layer'], function () {
        $ = layui.jquery;
        var form = layui.form
                , layer = layui.layer;
        var stype = '${type}';
        //自定义验证规则
        form.verify({
            name: function (value) {
                if (stype == 2) {
                    if (value.trim() == "") {
                        return "标题不能为空";
                    }
                }
            },
            readNum: function (value) {
                if (stype == 2) {
                    var n = /^[1-9]\d*$/;
                    if (value.trim() == "") {
                        return "阅读量不能为空";
                    }
                    if (value.trim() != "") {
                        value=value.replace(',','');
                        if (!n.test(value.trim())) {
                            return "请填写整数";
                        }
                    }
                }
            },
            des: function (value) {
                if (stype == 1) {
                    if (value.trim() == "") {
                        return "内容不能为空";
                    }
                }
            }
            ,
            url: [/^((ht|f)tps?):\/\/[\w\-]+(\.[\w\-]+)+([\w\-\.,@?^=%&:\/~\+#]*[\w\-\@?^=%&\/~\+#])?$/, '请输入正确的地址链接，如：https://www.jd.com/']
        });

        $('#close').click(function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });
        //监听提交
        form.on('submit(add)', function (data) {
            $.ajax({
                url: 'updateStrategySuccess',
                type: 'post',
                data: data.field,
                async: false, traditional: true,
                success: function (msg) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    window.parent.layui.table.reload('strategyList');
                    window.parent.layui.table.reload('successList');
                    alert('修改成功');
                    // window.top.layer.msg(msg, {icon: 6, offset: 'rb', area: ['120px', '80px'], anim: 2});
                }, error: function () {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    alert('修改失败');
                     // window.top.layer.msg('请求失败', {icon: 5, offset: 'rb', area: ['120px', '80px'], anim: 2});
                }
            });
            return false;
        });
        form.render();
    });
</script>
</body>

</html>
