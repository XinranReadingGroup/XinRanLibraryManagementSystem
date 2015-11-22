/**
 * @author  leiling.cp
 * @version 1.0.0
 * @desc    共享图书
 */

define(function (require, exports, module) {
    var $ = require('jquery');

    var Share = function(){

    };

    Share.prototype.render = function(){

        this.bindEvents();
    };

    Share.prototype.bindEvents = function(){
        var self = this,
            $doc = $(document);

        $doc.delegate('.j-get-book', 'click', function(event){
            self.getBookInfo();
        });
        $doc.delegate('.j-share-btn', 'click', function(event){
            self.shareBook();
        });
    };

    Share.prototype.setBookInfo = function(bookInfo){
        var $bookInfo = $('#book-info');
        $bookInfo.find('.j-book-pic').attr('src', bookInfo.imgUrl);
        $bookInfo.find('.j-title').text(bookInfo.title);
        $bookInfo.find('.j-author').text(bookInfo.author || '');
        $bookInfo.find('.j-publisher').text(bookInfo.publisher);
        $bookInfo.find('.j-summary').text(bookInfo.summary);
        $('.j-book-id').val(bookInfo.id);
    };

    Share.prototype.getBookInfo = function(){
        var self = this,
            isbn = $('.j-input-isbn').val(),
            getBookUrl = '/book/isbn/' + isbn;

        $.ajax({
            url: getBookUrl,
            type: 'get',
            dataType: 'json',
            cache: false,
            success: function(data) {
                if (data && data.code == 200) {
                    self.setBookInfo(data.data);
                    $('#book-info').modal('show');
                } else {
                    window.alert('无法找到这本书');
                }
            }
        });
    };

    Share.prototype.shareBook = function(){
        var bookId = $('.j-book-id').val(),
            getBookUrl = '/book/share/' + bookId;

        $.ajax({
            url: getBookUrl,
            type: 'get',
            dataType: 'json',
            data: {
                locationId: '1'
            },
            cache: false,
            success: function (data) {
                if (data && data.code == 200) {
                    window.alert('享书成功');
                } else {
                    window.alert('享书失败');
                }
            }
        });
    };

    module.exports = Share;
});