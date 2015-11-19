/**
 * [捐赠图书处理部分JS]
 * @param  {[type]} require [description]
 * @param  {[type]} exports [description]
 * @param  {[type]} module) {               module.exports [description]
 * @return {[type]}         [description]
 */

define(function(require, exports, module) {

   var $ = require('jquery'),
       selectDialogTmp = require('./tpl/donate-address-select.tpl'),
       popupMsg = require('/js/common/popup-msg/popup-msg.js');



   var donateObj = {

   		hasInit:false,

    	/**
    		共享图书页面初始化
    		@method init
    	*/
    	init:function(){

    		if( this.hasInit === false ){
    			this.hasInit = true;
    			this.$rootEl = $('#book-share-box-root');
	    		this.$inputIsbnEl = this.$rootEl.find('.J-input-isbn');
	    		this.$inputDonorEl = this.$rootEl.find('.J-input-donor');
	    		this.$btnGetBookEl = this.$rootEl.find('.J-btn-get-book');
	    		//book info
	    		this.$bookInfoRootEl =  $('#bookinfo-box-root');
	    		this.$btnShareBookEl = this.$bookInfoRootEl.find('.J-btn-share-book');
                this.$dropDownLiTmpEl = $('<li><a class="J-item" href="#"></a></li>');
                this.initDialog();
	    		this.initEvents();
               
    		}
    	},

        /**
            初始化dialog对象
            @method initEvents
        */
        initDialog:function(){
            

            this.$addressDialogContentEl = $(selectDialogTmp);
            $(document.body).append( this.$addressDialogContentEl);
            
            this.$btnShareBookEl.attr({
                'data-toggle': 'modal',
                'data-target':'#J-address-dialog-root'
            });

            var getUrl = '/book/location/provinces';
            //初始化省份
            $.ajax({
                url: getUrl,
                type:'get',
                dataType: 'json',
                cache: false,
                success: $.proxy(function( data ){
                    if( data && data.code == 200 ){

                        var provinceList = data.data || [];
                        this.$addressDialogContentEl.find('.J-provinces .J-list').empty();
                        for( var i=0,len = provinceList.length; i < len; i++){
                            var provinceItem = this.$dropDownLiTmpEl.clone( true );
                            provinceItem.find('.J-item').html( provinceList[i].name );
                            provinceItem.find('.J-item').attr('data-id', provinceList[i].id );
                            this.$addressDialogContentEl.find('.J-provinces .J-list').append( provinceItem );
                        }

                    }else{
                        
                         popupMsg.miniTipsAlert('无法找到省份所属城市' );
                    }
                },this)

            });


        },
    	/**
    		事件初始化
    		@method initEvents
    	*/
    	initEvents:function(){

    		this.$btnGetBookEl.on('click',$.proxy(function( ev ){
    			var $el = $(ev.currentTarget);
    			this.getBookInfo();
    		},this));


            this.$addressDialogContentEl.find('.J-btn-ok').on('click',$.proxy(function( ev ){
                var $el = $(ev.currentTarget);
                
                this.$addressDialogContentEl.modal('hide');

                this.getLocationId();

            },this));

            //省份
            this.$addressDialogContentEl.find('.J-provinces').delegate('.J-item','click',$.proxy(function( ev ){
                var $el = $(ev.currentTarget);
                this.$addressDialogContentEl.find('.J-provinces .J-text').html(  $el.html() );
                this.$addressDialogContentEl.find('.J-provinces .J-text').attr( 'data-id', $el.attr('data-id') );

                this.getCity();

                this.$addressDialogContentEl.find('.J-city .J-text').html( '城市' );
                this.$addressDialogContentEl.find('.J-city .J-text').attr( 'data-id', '0' );

                this.$addressDialogContentEl.find('.J-counties .J-text').html( '区县' );
                this.$addressDialogContentEl.find('.J-counties .J-text').attr( 'data-id', '0' );

            },this));
            //城市
            this.$addressDialogContentEl.find('.J-city').delegate('.J-item','click',$.proxy(function( ev ){
                var $el = $(ev.currentTarget);
                
                this.$addressDialogContentEl.find('.J-city .J-text').html(  $el.html() );
                this.$addressDialogContentEl.find('.J-city .J-text').attr( 'data-id', $el.attr('data-id') );
                this.getCounties();

                this.$addressDialogContentEl.find('.J-counties .J-text').html( '区县' );
                this.$addressDialogContentEl.find('.J-counties .J-text').attr( 'data-id', '0' );

            },this));
            //区县
            this.$addressDialogContentEl.find('.J-counties').delegate('.J-item','click',$.proxy(function( ev ){
                var $el = $(ev.currentTarget);
                
                this.$addressDialogContentEl.find('.J-counties .J-text').html(  $el.html() );
                this.$addressDialogContentEl.find('.J-counties .J-text').attr( 'data-id', $el.attr('data-id') );


            },this));

    	},

        /**
            通过isbn 获取省份城市
            @method getCity
        */
        getCity:function(){
            var provinceIdStr = this.$addressDialogContentEl.find('.J-provinces .J-text').attr('data-id'),
                getUrl = '/book/location/provinces/' + provinceIdStr + '/cities';

            $.ajax({
                url: getUrl,
                type:'get',
                dataType: 'json',
                cache: false,
                success: $.proxy(function( data ){
                    if( data && data.code == 200 ){

                        var cityList = data.data;
                        this.$addressDialogContentEl.find('.J-city .J-list').empty();
                        for( var i=0,len = cityList.length; i < len; i++){
                            var cityItem = this.$dropDownLiTmpEl.clone( true );
                            cityItem.find('.J-item').html( cityList[i].name);
                            cityItem.find('.J-item').attr( 'data-id',cityList[i].id);
                            this.$addressDialogContentEl.find('.J-city .J-list').append( cityItem );
                        }

                    }else{
                        
                         popupMsg.miniTipsAlert('无法找到省份所属城市' );
                    }
                },this)

            });

        },
          /**
            通过isbn 获取区县
            @method getCounties
        */
        getCounties:function(){
            var cityIdStr = this.$addressDialogContentEl.find('.J-city .J-text').attr('data-id'),
                getUrl = '/book/location/cities/' + cityIdStr + '/counties';

            $.ajax({
                url: getUrl,
                type:'get',
                dataType: 'json',
                cache: false,
                success: $.proxy(function( data ){
                    if( data && data.code == 200 ){

                        var countiesList = data.data;
                        this.$addressDialogContentEl.find('.J-counties .J-list').empty();
                        for( var i=0,len = countiesList.length; i < len; i++){
                            var countiesItem = this.$dropDownLiTmpEl.clone( true );
                            countiesItem.find('.J-item').html( countiesList[i].name );
                            countiesItem.find('.J-item').attr( 'data-id',countiesList[i].id);
                            this.$addressDialogContentEl.find('.J-counties .J-list').append( countiesItem );
                        }

                    }else{
                        
                         popupMsg.miniTipsAlert('无法找到省份所属城市' );
                    }
                },this)

            });

        },


    	/**
    		通过isbn 获得图书信息
    		@method getBookInfo
    	*/
    	getBookInfo:function(){
    		var isbnStr = this.$inputIsbnEl.val(),
    			getBookUrl = '/book/isbn/' + isbnStr;

    		$.ajax({
				url: getBookUrl,
				type:'get',
				dataType: 'json',
				cache: false,
				success: $.proxy(function( data ){
					if( data && data.code == 200 ){
						this.setBookInfo( data.data );
						this.changeToBookInfoUI( true);
					}else{
                        
                         popupMsg.miniTipsAlert('无法找到对应的书籍信息：isbn' +isbnStr );
                    }
				},this)

			});

    	},

        /**
            活动位置Id
            @method getLocationId
        */
        getLocationId:function(){
            var provinceId = this.$addressDialogContentEl.find('.J-provinces .J-text').attr('data-id'),
                cityId = this.$addressDialogContentEl.find('.J-city .J-text').attr('data-id'),
                countiesId = this.$addressDialogContentEl.find('.J-counties .J-text').attr('data-id'),
                getUrl = '/book/address/add';

            $.ajax({
                url: getUrl,
                type:'get',
                dataType: 'json',
                data:{
                    province: provinceId,
                    city: cityId,
                    county: countiesId
                },
                cache: false,
                success: $.proxy(function( data ){
                    if( data && data.code == 200 ){
                        this.saveDonateBook( data.data.id );

                    }else{
                        
                         popupMsg.miniTipsAlert('享书失败:' +isbnStr );
                    }
                },this)

            });

        },

        /**
            通过isbn 共享图书
            @method saveShareBook
        */
        saveDonateBook:function( locationId ){
            var bookId = this.$inputIsbnEl.attr('data-id'),
                getBookUrl = '/book/donate/' + bookId;

            $.ajax({
                url: getBookUrl,
                type:'get',
                dataType: 'json',
                data:{
                    locationId: locationId
                },
                cache: false,
                success: $.proxy(function( data ){
                    if( data && data.code == 200 ){
                        popupMsg.miniTipsAlert('捐书成功');
                        window.location.href = '/book/detail/' + data.data.id;
                    }else{
                        
                         popupMsg.miniTipsAlert('享书失败' );
                    }
                },this)

            });

        },
		/**
    		设置图书信息
    		@method setBookInfo
    	*/
    	setBookInfo:function( bookInfo ){

    		this.$bookInfoRootEl.find('.J-book-pic').attr('src', bookInfo.imgUrl  );
    		this.$bookInfoRootEl.find('.J-book-title').html(bookInfo.title );
    		this.$bookInfoRootEl.find('.J-author').html( bookInfo.author || ''); 
    		this.$bookInfoRootEl.find('.J-publisher').html( bookInfo.publisher );
    		this.$bookInfoRootEl.find('.J-price').html( bookInfo.price );
    		this.$bookInfoRootEl.find('.J-summary').html( bookInfo.summary  );
            this.$inputIsbnEl.attr('data-id', bookInfo.id );

    	},
    	/**
    		设置图书信息
    		@method setBookInfo
    	*/
    	changeToBookInfoUI:function( isTrue ){
    		if( isTrue === true ){
    			this.$rootEl.hide();
    			this.$bookInfoRootEl.show();
    		}else{
    			this.$rootEl.show();
    			this.$bookInfoRootEl.hide();
    		}

    	}

    };

    module.exports = donateObj;
});