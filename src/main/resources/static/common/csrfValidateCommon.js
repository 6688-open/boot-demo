/**
 * 获取Cookie中 csrfToken
 * @returns {string}
 */
function getToken() {
    debugger;
    var strCookie=document.cookie;
    //将多cookie切割为多个名/值对
    var arrCookie=strCookie.split("; ");
    for (var i = 0; i < arrCookie.length; i++) {
        var tmp = arrCookie[i].split('=');
        if (tmp[0] == 'csrfToken') {
            return tmp[1];
        }
    }
}