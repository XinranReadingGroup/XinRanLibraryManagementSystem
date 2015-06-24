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

    function Common(config) {

    };

    Common.prototype.render = function() {
        var self = this;

        self.bindEvents();
    };

    Common.prototype.bindEvents = function() {
        var self = this,
            doc = document;

        $(doc).delegate('#logout-btn', 'click', function(event) {
            self.logout();

            return false;
        });
    };

    Common.prototype.logout = function() {
        var self = this;

        $.post('/user/signOut', {}, function(json) {
            if (json.code == 200) {
                Util.clearCookie('accessToken');
                top.location.href = '/';
            }
        }, 'json');
    };

    module.exports = Common;
});