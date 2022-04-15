/**
 * 详情页数据脱敏【点击查看】方法
 *
 * 入参  form/div域限定 url 调用地址
 * **/
function maskSensitiveMulti(commonDivId, id,  item_url) {
    debugger;
    $(commonDivId+" [sensitive]").each(function (index,element) {
        $(this).addClass("ele-sensitive");
        $(this).on("click",function(){
            var sensitiveObj = $(this);
            // 防止显示敏感字段后，继续点击调用接口
            if(!sensitiveObj.hasClass("ele-sensitive"))
                return;
            var sensitiveParam = $(this).attr("sensitive");//字段名
            //var requestId = sensitiveParam.split("_")[0];
            var requestFieldNames = [];
            if($(this).hasClass("sensitiveAddress")) {
                $(".sensitiveAddress").each(function(index,element){
                    requestFieldNames.push($(this).attr("sensitive"));
                });
            } else {
                requestFieldNames.push(sensitiveParam);
            }
            /* 获取 敏感的真实数据 */
            jQuery.ajax({
                "type": "post",
                "url": item_url,
                dataType: "json",
                "data": {
                    "id": id,
                    "fieldNames":requestFieldNames.join(",")
                },
                success: function (data) {
                    var map =  data.data;
                    if(requestFieldNames.length > 1) {
                        $(".sensitiveAddress").each(function(index,element){
                            if($(this).is("input")) {
                                $(this).val(map[$(this).attr("sensitive")]);
                            } else {
                                $(this).text(map[$(this).attr("sensitive")]);
                            }
                            $(this).attr("style",'color: black');
                            $(this).removeClass("ele-sensitive");
                            $(this).unbind();
                        });
                    } else if(requestFieldNames.length == 1) {
                        var realData = map[sensitiveParam];
                        if(sensitiveObj.is("input")) {
                            sensitiveObj.val(realData);
                        } else {
                            sensitiveObj.text(realData);
                        }
                        sensitiveObj.attr("style",'color: black');
                        sensitiveObj.removeClass("ele-sensitive");
                        sensitiveObj.unbind();
                    } else {
                        sensitiveObj.removeClass("ele-sensitive");
                        sensitiveObj.unbind();
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    layer.msg("系统异常", {time: 2000, icon: 5, shift: 6});
                }
            });
        })
    })
}