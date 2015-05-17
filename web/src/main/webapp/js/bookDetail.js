/**
 * [description] book detail
 * @param  {[type]} require [description]
 * @param  {[type]} exports [description]
 * @param  {[type]} module) {               module.exports [description]
 * @return {[type]}         [description]
 */

define(function(require, exports, module) {

    var $ = require('jquery');

    function BookDetail(config) {

    };

    BookDetail.prototype.start = function() {
        var self = this;
        debugger;
        self.bindEvents();
    };

    BookDetail.prototype.bindEvents = function() {
        var self = this,
            doc = document;

        $(doc).delegate('[data-role=detail-share]', 'click', function(event) {
            console.log('xx');
            $('#disShare-modal').modal('show');
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

    module.exports = BookDetail;
});