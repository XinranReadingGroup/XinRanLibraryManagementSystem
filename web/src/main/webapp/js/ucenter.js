/**
 * [description]
 * @param  {[type]} require [description]
 * @param  {[type]} exports [description]
 * @param  {[type]} module) {               module.exports [description]
 * @return {[type]}         [description]
 */

define(function(require, exports, module) {

    var $ = require('jquery');

    function Ucenter(config) {

    };

    Ucenter.prototype.render = function() {
        var self = this;

        self.bindEvents();

        self.renderPanel();
    };

    Ucenter.prototype.bindEvents = function() {
        var self = this,
            doc = document;

        $(doc).delegate('.donate-book', 'click', function(event) {
            $('#donate-modal').modal('show');
        });
        $(doc).delegate('.borrow-book', 'click', function(event) {
            $('#borrow-modal').modal('show');
        });
        $(doc).delegate('.share-book', 'click', function(event) {
            $('#share-modal').modal('show');
        });
        $(doc).delegate('.comment-book', 'click', function(event) {
            $('#comment-modal').modal('show');
        });

        $(doc).delegate('#donate-book-btn', 'click', function(event) {
            $.get('book/donate/3', {}, function(json) {

            }, 'json');
        });
        $(doc).delegate('#borrow-book-btn', 'click', function(event) {
            $.get('book/borrow/3', {}, function(json) {

            }, 'json');
        });
        $(doc).delegate('#share-book-btn', 'click', function(event) {
            $.get('book/share/3', {}, function(json) {

            }, 'json');
        });
        $(doc).delegate('#comment-book-btn', 'click', function(event) {
            $.get('book/comment/3', {}, function(json) {

            }, 'json');
        });
    };

    Ucenter.prototype.renderPanel = function() {
        var self = this;

        $.get('/book/donate/records', {}, function(data) {
            if (data.code == 200) {

            }

        }, 'json');
    };

    module.exports = Ucenter;
});