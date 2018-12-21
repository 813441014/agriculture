function loadInfo(url, data, successFunc) {
    try {

        $.ajax({
            type: "POST",
            url: url,
            data: data,
            dataType: "json",
            success: function (data) {
                successFunc(data)
            },
            error: function (e) {
                if (e.responseJSON&&e.responseJSON.path&&e.responseJSON.path.indexOf("{\"code\":\"-998\",\"message\":\"无权限\"}") != -1) {
                    window.location.href = "../../error/403.ftl"
                }
                else {
                    throw e;
                }
            }
        });
    }
    catch (e) {
        alert(e)
    }
}
function loadUserAgent(){
    return window.navigator.userAgent.indexOf("Trident")!=-1;
}

function warning(msg){
    tip.options = {
        "closeButton": false,
        "debug": false,
        "positionClass": "toast-top-full-width",
        "onclick": null,
        "showDuration": "100000",
        "hideDuration": "100000",
        "timeOut": "300000",
        "extendedTimeOut": "100000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }
    tip.warning(msg);
}

function timestampToTime(timestamp) {
    var ts="";
    if (timestamp!=undefined){
        timestamp=(timestamp.length==10)?timestamp*1000:timestamp;
        var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        var Y = date.getFullYear() + '-';
        var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        var D = ((date.getDate()<10)?'0'+date.getDate():date.getDate())+ ' ';
        var h = ((date.getHours()<10)?'0'+date.getHours():date.getHours()) + ':';
        var m = ((date.getMinutes()<10)?'0'+date.getMinutes():date.getMinutes()) + ':';
        var s = ((date.getSeconds()<10)?'0'+date.getSeconds():date.getSeconds());
        ts=Y+M+D+h+m+s
    }
    return ts;
}

function isChineseCode(str) {
    return window.Mtils.validation.isContainsSpecialChar(str)||window.Mtils.validation.isChinese(str);
}

