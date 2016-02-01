/**
 * @author  leiling.cp
 * @version 1.0
 * @desc    详情页
 */

define(function (require, exports, module) {

    var $ = require('jquery');

    function BookDetail(config) {

    };

    BookDetail.prototype.render = function () {
        this.bindEvents();
        this.formatSummary();
    };

    BookDetail.prototype.bindEvents = function () {
        var self = this,
            $doc = $(document),
            onOffStockId = $('#main-stage').data('id');

        $doc.delegate('[data-role=detail-dis-share]', 'click', function (event) {
            $('#disShare-modal').modal('show');
        });
        $doc.delegate('[data-role=detail-borrow-book]', 'click', function (event) {
            $('#borrow-modal').modal('show');
        });
        $doc.delegate('[data-role=detail-return-book]', 'click', function (event) {
            $('#return-modal').modal('show');
        });
        $doc.delegate('#borrow-book-btn', 'click', function (event) {
            var target = $('[data-role=detail-borrow-book]'),
                id = target.attr('data-id');

            $.ajax({
                url: '/book/borrow/' + onOffStockId,
                dataType: 'json',
                timeout: 15000,
                success: function (json) {
                    top.location.reload();
                },
                error: function () {
                    top.location.reload();
                }
            });

        });
        $doc.delegate('#return-book-btn', 'click', function (event) {
            var target = $('[data-role=detail-return-book]'),
                id = target.attr('data-id');

            $.ajax({
                url: '/book/return/' + onOffStockId,
                dataType: 'json',
                timeout: 15000,
                success: function (json) {
                    top.location.reload();
                },
                error: function () {
                    top.location.reload();
                }
            });

        });
        $doc.delegate('#cancel-donate-book-btn', 'click', function (event) {
            var target = $('[data-role=detail-return-book]'),
                id = target.attr('data-id');

            $.ajax({
                url: '/book/offstock/' + onOffStockId,
                dataType: 'json',
                timeout: 15000,
                success: function (json) {
                    top.location.reload();
                },
                error: function () {
                    top.location.reload();
                }
            });

        });
    };

    BookDetail.prototype.formatSummary = function () {
        var bookSummary = $('.xy-detail-des'),
            viewMoreSummary = $('[data-role=xy-detail-seek-more]');

        if (bookSummary.height() > 250) {
            viewMoreSummary.show();
            bookSummary.addClass('xy-detail-limit');
        }
        viewMoreSummary.click(function () {
            bookSummary.addClass('xy-detail-des-show-all');
            $(this).hide();
            return false;
        });
    }

    module.exports = BookDetail;
});