
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>后台登录</title>
    <#--<meta name="renderer" content="webkit|ie-comp|ie-stand">-->
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    <link href="http://web.jialubao.com.cn/sys/sysImage/xycr_icon.png" rel="SHORTCUT ICON">
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${re.contextPath}/plugin/x-admin/css/font.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/x-admin/css/xadmin.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/others/toastr.css">


</head>
<body class="login-bg">

<div class="login">
    <div class="message">后台登录</div>
    <div id="darkbannerwrap"></div>

    <form method="post" action="/login" class="layui-form" >
        <input name="username" placeholder="用户名" autocomplete="on"  type="text" lay-verify="username" class="layui-input" value="">
        <hr class="hr15">
        <input name="password" lay-verify="password" placeholder="密码" autocomplete="off"  type="password" class="layui-input" value="">
        <hr class="hr15">
        <div  class="layui-inline">
            <label class="layui-form-label" style="width:40px;padding: 9px 0px;">验证码:</label>
               <div class="layui-input-inline">
                     <input type="text" name="code" style="width:150px;height:35px;" autocomplete="off" lay-verify="code"   class="layui-input">
              </div>
            <div class="layui-input-inline">
                <img src="" id="code">
            </div>

        </div>
        <#--<div>-->
        <#--<label class="layui-form-label" style="width:40px;padding: 9px 0px;">记住我</label>  由于不好验证 目前去掉-->
            <#--<input type="checkbox"   name="rememberMe" lay-skin="switch" lay-filter="switchTest" lay-text="ON|OFF">-->
        <#--</div>-->
        <hr class="hr15">
        <input value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit">
        <hr class="hr20" >
    </form>
</div>

<script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/others/toastr.min.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/app/nmp.js"></script>


<script>
  var layer;
  $(function  () {
      var v=loadUserAgent();
      if (v){
          warning('浏览器可能会影响使用效果,推荐使用谷歌/火狐浏览器');
      }
    layui.use(['form','layer'], function(){
      var form = layui.form;
      form.verify({
        username:function(v){
          if(v.trim()==''){
            return "用户名不能为空";
          }
        }
        ,password:function(v){
          if(v.trim()==''){
            return "密码不能为空";
          }
        },code:function(v){
              if(v.trim()==''){
                  return '验证码不能为空';
              }
          }
      });

      form.render();
    });
    layer = layui.layer;
    var msg='${message}';
    if(msg.trim()!=""){
        layer.msg(msg, {icon: 5,anim:6});
    }
      $("#code").click(function(){
          var url = "/getCode?"+new Date().getTime();
          this.src = url;
      }).click().show();
    $('#code').on('mouseover',function(){
        layer.tips('点击刷新验证码', this,{time:1000});
    });
  })

  if (window != top)
    top.location.href = location.href;
</script>


<!-- 底部结束 -->
</body>
</html>
