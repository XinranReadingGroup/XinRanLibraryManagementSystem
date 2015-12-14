/**
 * @author  
 * @version 1.0.0
 * @desc    地址管理
 */

define(function (require, exports, module) {
    var $ = require('jquery'),
        Handlebars = require('handelbars'),
        locationRowTmp = require('./tpl/locationrow.tpl');

    var bookLocation = function(){

    };

    bookLocation.prototype = {

        /**
            @des 初始化渲染
        */
        render:function( cfg ){

            this.$rootEl = $('#location-manager-content');
            this.$locationDataEl =$('#location-data-info');
            this.$locationListContentEl = this.$rootEl.find('.J-info-list-content');
            this.$locationDelEl = $('#location-data-del');

            this.getProvinces();
            this.refreshListInfo();

            this.bindEvents();
        },

        /**
            @des 页面事件处理
        */
        bindEvents:function(){
            var self = this,
                $doc = $(document);

            this.$rootEl.delegate('.J-provinces', 'change', function(event){
                self.getCitys();
                self.refreshListInfo();
            });
            this.$rootEl.delegate('.J-citys', 'change', function(event){
                self.Counties();
                self.refreshListInfo();
            });
            this.$rootEl.delegate('.J-add-new', 'click', function(event){
                self.$locationDataEl.find('.J-location-id').val('');
                self.$locationDataEl.find('.J-location-action').val( 'add' );
                self.$locationDataEl.modal('show');
            });
            this.$rootEl.delegate('.J-edit', 'click', function(event){
                var $el = $(evnet.targetElment),
                    dataId = $el.attr('data-id');

                
                self.$locationDataEl.find('.J-location-id').val( dataId );
                self.$locationDataEl.find('.J-location-action').val( 'edit' );
                self.$locationDataEl.find('.J-input-provinces').val( $el.attr('data-province') );
                self.$locationDataEl.find('.J-input-citys').val( $el.attr('data-city') );
                self.$locationDataEl.find('.J-input-counties').val( $el.attr('data-countie') );

                self.$locationDataEl.modal('show');
            });

            this.$rootEl.delegate('.J-del', 'click', function(event){
                var $el = $(evnet.targetElment),
                    dataId = $el.attr('data-id');

                 self.$locationDelEl.find('.J-location-id').val( dataId );
                 self.$locationDelEl.find('.J-location-action').val( 'del' );
                 self.$locationDelEl.modal('show');

            });


            this.$locationDataEl.delegate('.J-save-btn', 'click', function(event){

                 self.addLocationData();
                 self.$locationDataEl.modal('hide');
                 self.getProvinces();
            });

            this.$locationDelEl.delegate('.J-save-btn', 'click', function(event){
                var self = this,
                    dataId =  self.$locationDelEl.find('.J-location-id').val();
                $.get('/book/address/delete', {
                    id: dataId
                },function(data){
                    if (data && data.code == 200) {
                       window.alert('删除成功！');
                    } else {
                        window.alert('捐书失败:获取地址失败');
                    }
                }, 'json');

                self.$locationDelEl.modal('hide');
                 
            });
            

        },

         /**
            @des 刷新地址列表
        */
        refreshListInfo:function(){
            var self = this;

            $.get('/book/address/query', function(data){
                if(data && data.code == 200){
                    var options = [],
                        data = data.data;
                    var listEl = Handlebars.compile(locationRowTmp)(data);
                    self.$locationListContentEl.html( listEl );
                }
            }, 'json');
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
                        options.push('<option value='+provinceList[i].province + '>'+ provinceList[i].province+'</option>');
                    }
                    self.$rootEl.find('.J-provinces').html(options.join(' '));
                    self.getCitys();
                }
            }, 'json');
        },
        /**
            @des 获得省份下的城市列表
        */
        getCitys : function(){
            var self = this,
                provinceVal = self.$rootEl.find('.J-provinces').val();

            $.get('/book/location/provinces/' + provinceVal + '/cities', function(data){
                if(data && data.code == 200){
                    var options = [],
                        cityList = data.data;

                    for (var i = 0, len = cityList.length; i < len; i++) {
                        options.push('<option value='+cityList[i].city + '>'+ cityList[i].city+'</option>');
                    }
                    self.$rootEl.find('.J-citys').html(options.join(' '));
                    self.Counties();
                }
            }, 'json');
        },
        /**
            @des 获得城市下的区县列表
        */
        Counties:function(){
            var self = this,
                cityVal = this.$rootEl.find('.J-citys').val();

            $.get('/book/location/cities/' + cityVal + '/counties', function(data){
                if(data && data.code == 200){
                    var options = [],
                        countiesList = data.data;

                    for (var i = 0, len = countiesList.length; i < len; i++) {
                        options.push('<option value='+countiesList[i].county + '>'+ countiesList[i].county+'</option>');
                    }
                    self.$rootEl.find('.J-counties').html(options.join(' '));
                }
            }, 'json');
        },
        /**
            @des 添加一个地址
        */
        addLocationData : function () {
            var self = this,
                dataId =  self.$locationDataEl.find('.J-location-id').val(),
                provinceId = this.$locationDataEl.find('.J-input-provinces').val(),
                cityId = this.$locationDataEl.find('.J-input-citys').val(),
                countiesId = this.$locationDataEl.find('.J-input-counties').val(),
                action =  this.$locationDataEl.find('.J-location-action').val();
            if( action === 'add'){
                $.get('/book/address/add', {
                    province: provinceId,
                    city: cityId,
                    county: countiesId
                },function(data){
                    if (data && data.code == 200) {
                       window.alert('添加成功');
                    } else {
                        window.alert('捐书失败:获取地址失败');
                    }
                }, 'json');
            }else if( action === 'edit'){
                $.get('/book/address/update', {
                    id: dataId,
                    province: provinceId,
                    city: cityId,
                    county: countiesId
                },function(data){
                    if (data && data.code == 200) {
                       window.alert('修改成功');
                    } else {
                        window.alert('捐书失败:获取地址失败');
                    }
                }, 'json');
            }


           
        }






    };


    

    module.exports = bookLocation;
});