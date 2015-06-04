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
        this.bindEvents();
        this.formatSummary();
    };

    BookDetail.prototype.bindEvents = function() {
        var self = this,
            doc = document;

        $(doc).delegate('[data-role=detail-share]', 'click', function(event) {
            $('#disShare-modal').modal('show');
        });

        $(doc).delegate('[data-role=detail-donate]', 'click', function(event) {
            $('#share-modal').modal('show');
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

    BookDetail.prototype.formatSummary = function() {
        var bookSummary = $('.xy-detail-des');
        console.log(bookSummary.height());

        if(bookSummary.height() > 300) {

        }
    }

    module.exports = BookDetail;
});