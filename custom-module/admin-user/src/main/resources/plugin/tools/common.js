//非空验证
function isBlank(parameter) {
    return undefined == parameter || null == parameter || "" == parameter;
}

function chk() {
    var all = document.getElementById("all");
    var mychk = document.getElementsByName("mychk");
    //alert("mychk长度=="+mychk.length);
    if (all.checked == true) {
        //alert("all.checked==true全选");
        if (mychk.length) {
            for (var i = 0; i < mychk.length; i++) {
                mychk[i].checked = true;
            }
        }
        mychk.chcked = true;
    } else {
        //alert("all.checked==false全不选");
        if (mychk.length) {
            for (var i = 0; i < mychk.length; i++) {
                mychk[i].checked = false;
            }
        }
    }
}

//获取复选框的值
function show() {
    obj = document.getElementsByName("mychk");
    check_val = [];
    for (k in obj) {
        if (obj[k].checked)
            check_val.push(obj[k].value);
    }
    return check_val;
}

//获取路径中的值
function getUrlParms(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

// 时间戳(毫秒)转换成普通时间
function formatDateTime(timestamp) {
    var date = new Date(timestamp);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
}