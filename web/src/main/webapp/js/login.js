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

        $(doc).delegate('#password', 'keydown', function(event) {
            if (event.keyCode == 13) {
                self.login();
                return false;
            }
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

        $('.form-group').removeClass('has-error');
        $.post('/user/signIn', {
            'userIdentifier': uid,
            'password': pswd
        }, function(json) {
            if (json.code == 200) {
                Util.setCookie('accessToken', json.data.accessToken);
                top.location.href = '/';
            } else{
                $('.form-group').addClass('has-error');
                $('#helpBlock').text(json.data);
            }
        }, 'json');
    };

    Login.prototype.register = function() {
        var self = this,
            agreement= $('#J-agreement')[0].checked,
            uid = $('#reg_account').val(),
            nickname = $('#reg_nickname').val(),
            pswd = $('#reg_password').val();
        var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
 
        if( uid.trim() == ""){
            $('.form-group').addClass('has-error');
            $('#helpBlock').text('邮箱不能为空！');
        }else if( filter.test( uid ) == false ){
            $('.form-group').addClass('has-error');
            $('#helpBlock').text('邮箱格式不正确！');
        }else if( nickname.trim() == "" ){
            $('.form-group').addClass('has-error');
            $('#helpBlock').text('用户名不能为空！');
        }else if( pswd.trim() == "" ){
            $('.form-group').addClass('has-error');
            $('#helpBlock').text('密码不能为空！');
        }else if( agreement == false ){
            $('.form-group').addClass('has-error');
            $('#helpBlock').text('请阅读网站协议！');
        }else{
            $.post('/user/signUp', {
                'userIdentifier': uid,
                'password': pswd,
                'nickName': nickname
            }, function(json) {
                if (json.code == 200) {
                    Util.setCookie('accessToken', json.data.accessToken);
                    top.location.href = '/';
                }else{
                    $('.form-group').addClass('has-error');
                    $('#helpBlock').text( json.data );
                }
            }, 'json');
        }
        
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