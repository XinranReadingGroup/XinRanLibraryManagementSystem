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

        var onOffStockId = window.location.href.substr(37,1);
        $(doc).delegate('[data-role=detail-dis-share]', 'click', function(event) {
            $('#disShare-modal').modal('show');
        });

        $(doc).delegate('[data-role=detail-borrow-book]', 'click', function(event) {
            $('#borrow-modal').modal('show');
        });
        $(doc).delegate('#borrow-book-btn', 'click', function(event) {
            var target = $('[data-role=detail-borrow-book]'),
                id = target.attr('data-id');

            $.ajax({
                url: 'http://xinrandushuba.com/mobile/book/borrow/'+onOffStockId,
                dataType: 'json',
                timeout: 15000,
                success: function(json){
                    if(json && json.code == 200) {
                        alert('借阅成功');
                    } else {
                        alert('借阅失败');
                    }
                    $('#borrow-modal').modal('hide');
                },
                error: function(){
                    //console.log(arguments);
                    // do something
                    alert('借阅失败');
                    $('#borrow-modal').modal('hide');
                }
            });

        });
        $(doc).delegate('#return-book-btn', 'click', function(event) {
            var target = $('[data-role=detail-return-book]'),
                id = target.attr('data-id');

            $.ajax({
                url: 'http://xinrandushuba.com/mobile/book/return/'+onOffStockId,
                dataType: 'json',
                timeout: 15000,
                success: function(json){
                    if(json && json.code == 200) {
                        alert('还书成功');
                    } else {
                        alert('还书失败');
                    }
                    $('#return-modal').modal('hide');
                },
                error: function(){
                    //console.log(arguments);
                    // do something
                    alert('还书失败');
                    $('#return-modal').modal('hide');
                }
            });

        });


        $(doc).delegate('#detail-return-book', 'click', function(event) {
            $('#return-modal').modal('show');
        });
    };

    BookDetail.prototype.formatSummary = function() {
        var bookSummary = $('.xy-detail-des'),
            viewMoreSummary = $('[data-role=xy-detail-seek-more]');

        if(bookSummary.height() > 250) {
            viewMoreSummary.show();
            bookSummary.addClass('xy-detail-limit');
        }
        viewMoreSummary.click(function() {
            bookSummary.addClass('xy-detail-des-show-all');
            $(this).hide();
            return false;
        });
    }

    module.exports = BookDetail;
});