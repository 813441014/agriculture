<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title></title>
    <link href="http://web.jialubao.com.cn/sys/sysImage/xycr_icon.png" rel="SHORTCUT ICON">

    <link rel="stylesheet" href="${re.contextPath}/plugin/plugins/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/plugins/font-awesome/css/font-awesome.min.css" media="all"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/build/css/app.css" media="all"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/build/css/themes/default.css" media="all" id="skin" kit-skin/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/others/toastr.css">
    <style>
        <#--前端无聊美化ing-->
        .layui-footer {
            background-color: #2F4056;
        }

        .layui-side-scroll {
            border-right: 3px solid #009688;
        }
    </style>
</head>

<body class="kit-theme">
<div class="layui-layout layui-layout-admin kit-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">管理平台</div>
        <div class="layui-logo kit-logo-mobile"></div>
        <ul class="layui-nav layui-layout-right kit-nav">
            <li class="layui-nav-item">
                <a href="javascript:;">
        <#assign currentUser = Session["curentUser"]>
                <#--<img src="${currentUser.photo}" class="layui-nav-img">-->${currentUser.username}
                </a>
                <dl class="layui-nav-child">
               <#-- <dd><a href="javascript:;" kit-target data-options="{url:'basic.html',icon:'&#xe658;',title:'基本资料',id:'966'}"><span>基本资料</span></a></dd>-->
                    <dd><a href="javascript:;" onclick="rePwd('修改密码','/user/goRePass?id=${currentUser.id}',500,350)">修改密码</a>
                    </dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="/logout"><i class="fa fa-sign-out" aria-hidden="true"></i> 注销</a></li>
        </ul>
    </div>

<#macro tree data start end>
    <#if (start=="start")>
  <div class="layui-side layui-nav-tree layui-bg-black kit-side">
  <div class="layui-side-scroll">
    <div class="kit-side-fold"><i class="fa fa-navicon" aria-hidden="true"></i></div>
  <ul class="layui-nav layui-nav-tree" lay-filter="kitNavbar" kit-navbar>
    </#if>
    <#list data as child>
        <#if child.children?size gt 0>
      <li class="layui-nav-item">
          <a class="" href="javascript:;"><i aria-hidden="true"
                                             class="layui-icon">${child.icon}</i><span> ${child.name}</span></a>
          <dl class="layui-nav-child">
          <@tree data=child.children start="" end=""/>
          </dl>
      </li>
        <#else>
      <dd>
          <a href="javascript:;" kit-target
             data-options="{url:'${child.url}',icon:'${child.icon}',title:'${child.name}',id:'${child.num?c}'}">
              <i class="layui-icon">${child.icon}</i><span> ${child.name}</span></a>
      </dd>
        </#if>
    </#list>
    <#if (end=="end")>
  </ul>
  </div>
  </div>
    </#if>
</#macro>
<@tree data=menu start="start" end="end"/>
    <div class="layui-body" id="container">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;"><i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63e;</i>
            请稍等...
        </div>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->

    </div>
</div>
<script src="${re.contextPath}/plugin/jquery/jquery-1.10.2.min.js"></script>
<script src="${re.contextPath}/plugin/plugins/layui/layui.js"></script>
<script src="${re.contextPath}/plugin/tools/main.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/others/toastr.min.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/app/nmp.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/tools/loginValidate.js"></script>
<script>
    $(function () {
        var v = loadUserAgent();
        if (v) {
            warning('浏览器可能会影响使用效果,推荐使用谷歌/火狐浏览器');
        }
    })

    function rePwd(title, url, w, h) {
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
            id: 'user-rePwd',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: true,
            shade: 0.4,
            title: title,
            content: url,
        });
    }
</script>
</body>

</html>
