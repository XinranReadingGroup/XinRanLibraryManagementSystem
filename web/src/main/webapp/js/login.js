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
        var self = this;

        self.bindEvents();
    };

    Login.prototype.bindEvents = function() {
        var self = this,
            doc = document;

        $(doc).delegate('#register-btn', 'click', function(event) {
            self.register();

            return false;
        });

        $(doc).delegate('#login-btn', 'click', function(event) {
            self.login();

            return false;
        });

        $(doc).delegate('#logout-btn', 'click', function(event) {
            self.logout();

            return false;
        });
    };

    Login.prototype.login = function() {
        var self = this,
            uid = $('#account').val(),
            pswd = $('#password').val();

        $.post('/user/signIn', {
            'userIdentifier': uid,
            'password': pswd
        }, function(json) {
            if (json.code == 200) {
                Util.setCookie('accessToken', json.data.accessToken);
                top.location.href = '/';
            }
        }, 'json');
    };

    Login.prototype.register = function() {
        var self = this,
            uid = $('#account').val(),
            nickname = $('#nickname').val(),
            pswd = $('#password').val();

        $.post('/user/signUp', {
            'userIdentifier': uid,
            'password': pswd,
            'nickName': nickname
        }, function(json) {
            if (json.code == 200) {
                Util.setCookie('accessToken', json.data.accessToken);
                top.location.href = '/';
            }
        }, 'json');
    };

    Login.prototype.logout = function() {
        var self = this;

        $.post('/user/signOut', {}, function(json) {
            if (json.code == 200) {
                Util.clearCookie('accessToken');
                top.location.href = '/';
            }
        }, 'json');
    };

    module.exports = Login;
});