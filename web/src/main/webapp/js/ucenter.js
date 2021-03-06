/**
 * [description]
 * @param  {[type]} require [description]
 * @param  {[type]} exports [description]
 * @param  {[type]} module) {               module.exports [description]
 * @return {[type]}         [description]
 */

define(function (require, exports, module) {

    var $ = require('jquery'),
        Handlebars = require('handelbars'),
        UcenterTpl = require('./tpl/ucenter.tpl');

    function Ucenter(config) {

    };

    Ucenter.prototype.render = function () {
        var self = this;

        self.bindEvents();

        self.renderPanel();
    };

    Ucenter.prototype.bindEvents = function () {
        var self = this,
            $doc = $(document);

        $doc.delegate('.donate-book', 'click', function (event) {
            $('#donate-modal').modal('show');
        });
        $doc.delegate('.borrow-book', 'click', function (event) {
            $('#borrow-modal').modal('show');
        });
        $doc.delegate('.share-book', 'click', function (event) {
            $('#share-modal').modal('show');
        });
        $doc.delegate('.comment-book', 'click', function (event) {
            $('#comment-modal').modal('show');
        });

        $doc.delegate('#donate-book-btn', 'click', function (event) {

        });
        $doc.delegate('#borrow-book-btn', 'click', function (event) {

        });
        $doc.delegate('#share-book-btn', 'click', function (event) {

        });
        $doc.delegate('#comment-book-btn', 'click', function (event) {

        });
    };

    Ucenter.prototype.renderPanel = function () {
        var self = this;

        $.get('/book/share/records', {}, function (data) {
            if (data.code == 200 && data.data.length > 0) {
                var element = Handlebars.compile(UcenterTpl)(data);
                $('#share').html(element);
            }else{
                $('#share').html('<h3>暂无分享记录</h3>');
            }
        }, 'json');
        $.get('/book/donate/records', {}, function (data) {
            if (data.code == 200) {
                var element = Handlebars.compile(UcenterTpl)(data);
                $('#donate').html(element);
            }else{
                $('#donate').html('<h3>暂无捐书记录</h3>');
            }
        }, 'json');
        $.get('/book/borrow/records', {}, function (data) {
            if (data.code == 200) {
                var element = Handlebars.compile(UcenterTpl)(data);
                $('#borrow').html(element);
            }else{
                $('#borrow').html('<h3>暂无借书记录</h3>');
            }
        }, 'json');
        $.get('/book/return/records', {}, function (data) {
            if (data.code == 200) {
                var element = Handlebars.compile(UcenterTpl)(data);
                $('#return').html(element);
            }else{
                $('#return').html('<h3>暂无还书记录</h3>');
            }
        }, 'json');
    };

    module.exports = Ucenter;
});