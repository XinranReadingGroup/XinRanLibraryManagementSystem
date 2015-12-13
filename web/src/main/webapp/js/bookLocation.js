/**
 * @author  
 * @version 1.0.0
 * @desc    地址管理
 */

define(function (require, exports, module) {
    var $ = require('jquery');

    var bookLocation : function(){

    };

    bookLocation.prototype = {

        /**
            @des 初始化渲染
        */
        render:function( cfg ){

        },

        /**
            @des 页面事件处理
        */
        bindEvents:function(){
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
        },

        /**
            @des 获得省份列表
        */
        getProvinces:function(){
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
        },
        /**
            @des 获得省份下的城市列表
        */
        getCitys : function(){
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
        },
        /**
            @des 获得城市下的区县列表
        */
        Counties : function(){
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
        },
        /**
            @des 添加一个地址
        */
        addLocationData : function () {
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
                   
                } else {
                    window.alert('捐书失败:获取地址失败');
                }
            }, 'json');
        }






    };


    

    module.exports = Donate;
});