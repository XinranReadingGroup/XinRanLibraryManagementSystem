/**
 * @author  张森,leiling.cp
 * @version 1.0.0
 * @desc    捐赠信息
 */

define(function (require, exports, module) {
    var $ = require('jquery');

    var Donate = function(){

    };

    Donate.prototype.render = function(){
        this.bindEvents();
        this.getProvinces();
    };

    Donate.prototype.bindEvents = function(){
        var self = this,
            $doc = $(document);

        $doc.delegate('.j-get-book', 'click', function(event){
            self.getBookInfo();
        });
        $doc.delegate('.j-donate-btn', 'click', function(event){
            self.getLocationId();
        });
        $doc.delegate('.j-provinces', 'change', function(event){
            self.getCitys();
        });
        $doc.delegate('.j-citys', 'change', function(event){
            self.Counties();
        });
    };

    Donate.prototype.setBookInfo = function(bookInfo){
        var $bookInfo = $('#book-info');
        $bookInfo.find('.j-book-pic').attr('src', bookInfo.imgUrl);
        $bookInfo.find('.j-title').text(bookInfo.title);
        $bookInfo.find('.j-author').text(bookInfo.author || '');
        $bookInfo.find('.j-publisher').text(bookInfo.publisher);
        $bookInfo.find('.j-summary').text(bookInfo.summary);
        $('.j-book-id').val(bookInfo.id);
    };

    Donate.prototype.getBookInfo = function(){
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

    Donate.prototype.donateBook = function(){
        var bookId = $('.j-book-id').val(),
            locationId =  $('.j-location-id').val();

        $('#book-info').modal('hide');
        $.get('/book/donate/' + bookId, {
            locationId: locationId
        }, function(data){
            if (data && data.code == 200) {
                window.alert('捐书成功');
                location.href = '/';
            } else {
                window.alert('捐书失败');
                location.href = '/';
            }
        }, 'json');
    };

    Donate.prototype.getProvinces = function(){
        var self = this;

        $.get('/book/location/provinces', function(data){
            if(data && data.code == 200){
                var options = [],
                    provinceList = data.data;

                for (var i = 0, len = provinceList.length; i < len; i++) {
                    options.push('<option value='+provinceList[i].id + '>'+ provinceList[i].name+'</option>');
                }
                $('.j-provinces').html(options.join(' '));
            }
        }, 'json');
    };

    Donate.prototype.getCitys = function(){
        var self = this,
            provinceId = $('.j-provinces').val();

        $.get('/book/location/provinces/' + provinceId + '/cities', function(data){
            if(data && data.code == 200){
                var options = [],
                    cityList = data.data;

                for (var i = 0, len = cityList.length; i < len; i++) {
                    options.push('<option value='+cityList[i].id + '>'+ cityList[i].name+'</option>');
                }
                $('.j-citys').html(options.join(' '));
            }
        }, 'json');
    };

    Donate.prototype.Counties = function(){
        var self = this,
            cityId = $('.j-citys').val();

        $.get('/book/location/cities/' + cityId + '/counties', function(data){
            if(data && data.code == 200){
                var options = [],
                    countiesList = data.data;

                for (var i = 0, len = countiesList.length; i < len; i++) {
                    options.push('<option value='+countiesList[i].id + '>'+ countiesList[i].name+'</option>');
                }
                $('.j-counties').html(options.join(' '));
            }
        }, 'json');
    };

    Donate.prototype.getLocationId = function () {
        var self = this;
            provinceId = $('.j-provinces').val(),
            cityId = $('.j-citys').val(),
            countiesId = $('.j-counties').val();

        $.get('/book/address/add', {
            province: provinceId,
            city: cityId,
            county: countiesId
        },function(data){
            if (data && data.code == 200) {
                self.donateBook(data.data.id);
            } else {
                window.alert('捐书失败:获取地址失败');
            }
        }, 'json');
    };

    module.exports = Donate;
});