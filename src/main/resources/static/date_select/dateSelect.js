/**
 * 遇到datetimepicker控件中多次（包括第二次点击）点击input框时日期选择器不显示，
 * 经过查阅和多次测试，需要使用鼠标监听触发
 */
$(".dateSelect").each(function() {
    //需要name 或 id
    
    addEventListener(this);
});
$(".timeSelect").each(function() {
    //需要name 或 id
    
    addEventListener(this);
});
$(".dateSelect-pro").each(function() {
    //需要name 或 id
    
    addEventListener(this);
});
function addEventListener(obj) {
    var eleId=obj.id;
    var eleName = $(obj).attr("name");
    if(eleId){
        document.getElementById(eleId).addEventListener('click',myfunc);
    }else{
        $.each(document.getElementsByName(eleName),function (i,o) {
            o.addEventListener('click',myfunc);
        })
    }
}
function myfunc(e){
    e.currentTarget.blur();
    var id=e.currentTarget.id;
    $("#"+id).focus();
}