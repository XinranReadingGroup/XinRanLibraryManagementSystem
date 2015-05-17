/**
 * [description]
 * @param  {[type]} require [description]
 * @param  {[type]} exports [description]
 * @param  {[type]} module) {               module.exports [description]
 * @return {[type]}         [description]
 */

define(function(require, exports, module) {

    var $ = require('jquery'),
        Util = require('./common/util');

    function Login(config) {

    };

    Login.prototype.render = function() {

    };

    Login.prototype.bindEvents = function() {
        var self = this,
            doc = document;

        $(doc).delegate('#register-btn', 'click', function(event) {
            self.register();
        });
    };

    Login.prototype.login = function() {
        var self = this,
            uid = $('#account').val(),
            uname = $('#nickname').val(),
            pswd = $('#password').val();

        $.post('/user/signUp', {
            'userIdentifier': uid,
            'password': pswd,
            'nickName': uname
        }, function(json) {
            if (json.code == 200) {
                Util.setCookie('accessToken', json.data.accessToken)
            }
        }, 'json');
    };

    Login.prototype.register = function() {
        var self = this,
            uid = $('#account').val(),
            uname = $('#nickname').val(),
            pswd = $('#password').val();

        $.post('/user/signUp', {
            'userIdentifier': uid,
            'password': pswd,
            'nickName': uname
        }, function(json) {
            if (json.code == 200) {
                Util.setCookie('accessToken', json.data.accessToken);
            }
        }, 'json');
    };

    Login.prototype.logout = function() {
        var self = this,
            uid = $('#account').val(),
            uname = $('nickname').val(),
            pswd = $('#password').val();

        $.post('/user/signOut', {
            'accessToken': token
        }, function(json) {
            if (json.code == 200) {
                Util.clearCookie('accessToken');
                top.location.reload();
            }
        }, 'json');
    };

    module.exports = Login;
});