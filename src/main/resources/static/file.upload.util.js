function getUUID() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);
    s[8] = s[13] = s[18] = s[23] = "-";

    var uuid = s.join("");
    return uuid;
}

function getUtc() {
    var d = new Date();
    return Date.UTC(d.getFullYear()
        , d.getMonth()
        , d.getDate()
        , d.getHours()
        , d.getMinutes()
        , d.getSeconds()
        , d.getMilliseconds());
}

function checkUploadFileNum() {
    var image = $("#image_list span");
    var file = $("#file_list li");
    var num = image.length + file.length;
    if (num == "") {
        num = 0;
    }
    return num;
}


function deleteFile(url, id) {
    var param = {
        url: url
    };
    var delId = "#" + id;
            $("li").remove(delId)
            $("input").remove(delId)
            $("span").remove(delId)
    $.ajax({
        url: "/file/delete",
        type: "GET",
        data: param,
        dataType: "json",
        success: function (result) {
            // qm.alert({
            //     title: "提示",
            //     type: "alert",
            //     content: "删除成功！"
            // });

            return;
        },
        error: function () {
            // qm.alert({
            //     title: "错误",
            //     type: "error",
            //     content: result.msg
            // });

            return;
        }
    });

}