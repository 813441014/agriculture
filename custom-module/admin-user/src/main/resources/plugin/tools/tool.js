

//通用
function popup(title, url, w, h,id) {
  if (title == null || title == '') {
    title = false;
  }
  ;
  if (url == null || url == '') {
    url = "error/404";
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
    id: id,
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

/**
 * 父窗口弹出
 * @param url
 * @param data
 * @param tableId
 */
function postAjaxre(url,data,tableId){
  $.ajax({
    url: url,
    type: "post",
    data:data,
    dataType: "json", traditional: true,
    success: function (data) {
      if(data.flag){
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
        window.parent.layui.table.reload(tableId);
        window.top.layer.msg(data.msg,{icon:6,offset: 'rb',area:['120px','80px'],anim:2});
      }else{
        layer.msg(data.msg,{icon:5,offset: 'rb',area:['120px','80px'],anim:2});
      }
    }
  });
}