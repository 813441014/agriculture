$(function () {
    $.ajax({
        type: "POST",
        url: '/validateLogin',
        dataType: "json",
        success: function (data) {
            if(data){
                if (data=='-1'||data=='-2'){
                    window.location='/logout';
                }
            }
            else{
                window.location='/logout';
            }
        },
        error: function () {
            window.location='/logout';
        }
    });
})