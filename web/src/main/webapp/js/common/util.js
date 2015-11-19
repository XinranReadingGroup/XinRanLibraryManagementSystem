/**
 * @author leiling.cp
 * @version 2015.3.20
 * @description 工作台工具类
 */

define(function(require, exports, module) {
    var Util = function() {

    };

    Util.prototype = {
        getQueryString: function(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"),
                r = window.location.search.substr(1).match(reg);
            if (r != null) return window.unescape(r[2]);
            return null;
        },
        getCookie: function(name) {
            var tmp, reg = new RegExp('(?:^| )' + name + '=([^;]*)(?:;|$)', 'gi');
            return (tmp = reg.exec(document.cookie)) ? (window.unescape(tmp[1])) : '';
        },
        setCookie: function(name, value) {
            var nowDate = new Date();
            nowDate.setTime(nowDate.getTime() + 30 * 24 * 60 * 60 * 1000);
            document.cookie = name + '=' + window.escape(value) + '; expires=' + nowDate.toUTCString() + '; path=/; domain=xinrandushuba.com';
        },
        clearCookie: function(name) {
            document.cookie = name + '= ; expires=' +  new Date().toUTCString() + '; path=/; domain=xinrandushuba.com';
        }
    };

    module.exports = new Util();

});