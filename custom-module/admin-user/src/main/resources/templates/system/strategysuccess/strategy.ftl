<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>攻略维护</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport"
        content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
  <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
  <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js"
            charset="utf-8"></script>
  <style>
    .layui-input {
      height: 30px;
      width: 120px;
    }

    .x-nav {
      padding: 0 20px;
      position: relative;
      z-index: 99;
      border-bottom: 1px solid #e5e5e5;
      height: 32px;
      overflow: hidden;
    }
    /*.layui-table-page {*/
        /*position: fixed;*/
    /*}*/
  </style>
</head>
<body>
<#--<div class="x-nav">-->
<#--</div>-->
<div class="layui-tab layui-tab-brief">
    <ul class="layui-tab-title">
        <li class="layui-this">攻略</li>
        <li>捷报</li>
    </ul>
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <div class="layui-col-md12" style="height:40px;margin-top:3px;">
                <div class="layui-btn-group">
                    <button class="layui-btn layui-btn-normal" data-type="add">
                        <i class="layui-icon">&#xe608;</i>新增
                    </button>
                </div>
            </div>
            <table id="strategyList" class="layui-hide" lay-filter="user"></table>
        </div>
        <div class="layui-tab-item">
            <div class="layui-col-md12" style="height:40px;margin-top:3px;">
                <div class="layui-btn-group">
                    <button class="layui-btn layui-btn-normal" data-type="addSuccess">
                        <i class="layui-icon">&#xe608;</i>新增
                    </button>
                </div>
            </div>
            <table id="successList" class="layui-hide" lay-filter="user"></table>
        </div>
    </div>
</div>

<!-- 攻略工具条-->
<script type="text/html" id="toolBar">
  <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>

  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<!-- 捷报工具条-->
<script type="text/html" id="toolBarSuccess">
  <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="editSuccess">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delSuccess">删除</a>

</script>
<script type="text/html" id="statusTemplet">
    {{#  if(d.status === 1){ }}
    上线
    {{# } else if(d.status === 2) { }}
    下线
    {{#  } }}
</script>
<script>
  layui.laytpl.toDateString = function(d, format){
    var date = new Date(d || new Date())
        ,ymd = [
      this.digit(date.getFullYear(), 4)
      ,this.digit(date.getMonth() + 1)
      ,this.digit(date.getDate())
    ]
        ,hms = [
      this.digit(date.getHours())
      ,this.digit(date.getMinutes())
      ,this.digit(date.getSeconds())
    ];
    format = format || 'yyyy-MM-dd HH:mm:ss';
    return format.replace(/yyyy/g, ymd[0])
    .replace(/MM/g, ymd[1])
    .replace(/dd/g, ymd[2])
    .replace(/HH/g, hms[0])
    .replace(/mm/g, hms[1])
    .replace(/ss/g, hms[2]);
  };

  //数字前置补零
  layui.laytpl.digit = function(num, length, end){
    var str = '';
    num = String(num);
    length = length || 2;
    for(var i = num.length; i < length; i++){
      str += '0';
    }
    return num < Math.pow(10, length) ? str + (num|0) : num;
  };

  document.onkeydown = function (e) { // 回车提交表单
    var theEvent = window.event || e;
    var code = theEvent.keyCode || theEvent.which;
    if (code == 13) {
      $(".select .select-on").click();
    }
  }
  layui.use('table', function () {
    var table = layui.table;
    //方法级渲染
    table.render({
      id: 'strategyList',
      elem: '#strategyList'
      , url: 'showStrategyList?type=2'
      , cols: [[
         {field: 'url', title: '攻略链接', width: '20%', sort: true}
        , {field: 'name', title: '标题', width: '20%', sort: true}
        , {field: 'status', title: '状态', width: '20%',templet: '#statusTemplet'}
        , {field: 'remark', title: '操作', width: '20%', toolbar: "#toolBar"}
      ]]
      , page: true
      ,  height: 'full-100'
    });

    //捷报
    table.render({
        id: 'successList',
        elem: '#successList'
        , url: 'showStrategyList?type=1'
        , cols: [[
             {field: 'des', title: '内容', width: '20%', sort: true}
            , {field: 'status', title: '状态', width: '20%',templet: '#statusTemplet'}
            , {field: 'remark', title: '操作', width: '20%', toolbar: "#toolBarSuccess"}
        ]]
        , page: true
        ,  height: 'full-100'
    });


      var $ = layui.$, active = {
      reload:function(){
        table.reload('strategyList', {
          where: {
        type: 2,
        status: 1
          }
        });
      },
      add: function () {
        add('添加攻略', 'showAddStrategySuccess?type=2', 700, 450);
      },
      addSuccess: function () {
          add('添加捷报', 'showAddStrategySuccess?type=1', 700, 450);
      }
    };

    //监听表格复选框选择
    table.on('checkbox(user)', function (obj) {
      console.log(obj)
    });
    //监听工具条
    table.on('tool(user)', function (obj) {
      var data = obj.data;
      if (obj.event === 'del') {
        layer.confirm('确定删除攻略[<label style="color: #00AA91;">' + data.name + '</label>]?', function(){
          del(data.pkid);
        });
      } else if (obj.event === 'edit') {
        update('编辑攻略', 'updateStrategySuccess?id=' + data.pkid + '&type=2', 700, 450);
      } else if (obj.event === 'delSuccess') {
          layer.confirm('确定删除捷报[<label style="color: #00AA91;">' + data.des + '</label>]?', function(){
              del(data.pkid);
          });
      } else if (obj.event === 'editSuccess') {
          update('编辑捷报', 'updateStrategySuccess?id=' + data.pkid + '&type=1', 700, 450);
      }
    });

    $('.layui-col-md12 .layui-btn').on('click', function () {
      var type = $(this).data('type');
      active[type] ? active[type].call(this) : '';
    });
    $('.select .layui-btn').on('click', function () {
      var type = $(this).data('type');
      active[type] ? active[type].call(this) : '';
    });

  });
  function del(id) {
    $.ajax({
      url: "del",
      type: "post",
      data: {id: id},
      success: function (msg) {
        layer.msg(msg,{icon:6,offset: 'rb',area:['120px','80px'],anim:2});
        layui.table.reload('strategyList');
        layui.table.reload('successList');
      }
    });
  }

  /**
   * 更新用户
   */
  function update(title, url, w, h) {
    if (title == null || title == '') {
      title = false;
    }
    if (url == null || url == '') {
      url = "404.html";
    }
    if (w == null || w == '') {
      w = ($(window).width() * 0.9);
    }
    if (h == null || h == '') {
      h = ($(window).height() - 50);
    }
    layer.open({
      id: 'user-update',
      type: 2,
      area: [w + 'px', h + 'px'],
      fix: false,
      maxmin: true,
      shadeClose: false,
      shade: 0.4,
      title: title,
      content: url
    });
  }

  /*弹出层*/
  /*
   参数解释：
   title   标题
   url     请求的url
   id      需要操作的数据id
   w       弹出层宽度（缺省调默认值）
   h       弹出层高度（缺省调默认值）
   */
  function add(title, url, w, h) {
    if (title == null || title == '') {
      title = false;
    }
    ;
    if (url == null || url == '') {
      url = "404.html";
    }
    ;
    if (w == null || w == '') {
      w = ($(window).width() * 0.9);
    }
    ;
    if (h == null || h == '') {
      h = ($(window).height() - 50);
    }
    ;
    layer.open({
      id: 'user-add',
      type: 2,
      area: [w + 'px', h + 'px'],
      fix: false,
      maxmin: true,
      shadeClose: false,
      shade: 0.4,
      title: title,
      content: url
    });
  }
</script>
</body>

</html>
