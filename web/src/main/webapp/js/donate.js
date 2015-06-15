/**
 * [捐赠图书处理部分JS]
 * @param  {[type]} require [description]
 * @param  {[type]} exports [description]
 * @param  {[type]} module) {               module.exports [description]
 * @return {[type]}         [description]
 */

define(function(require, exports, module) {

   var $ = require('jquery'),
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
	    		this.initEvents();
               
    		}
    	},

        /**
            初始化dialog对象
            @method initEvents
        */
        initDialog:function(){

            popupMsg.runtimeConfirm('请确认选择？',$.proxy(function( isConfirm ){
                if( isConfirm === true ){
                    alert('yes');
                }else{
                     alert('no');
                }

            },this));
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

    		this.$btnShareBookEl.on('click',$.proxy(function( ev ){
    			 popupMsg.runtimeConfirm('确认共享此图书？',$.proxy(function( isConfirm ){
                    if( isConfirm === true ){
                        this.saveShareBook();
                    }

                },this));
    		},this));



    	},
    	/**
    		通过isbn 获得图书信息
    		@method getBookInfo
    	*/
    	getBookInfo:function(){
    		var isbnStr = this.$inputIsbnEl.val(),
    			getBookUrl = '/mobile/book/isbn/' + isbnStr;

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
            通过isbn 共享图书
            @method saveShareBook
        */
        saveShareBook:function(){
            var bookId = this.$inputIsbnEl.attr('data-id'),
                getBookUrl = '/book/share/' + bookId;

            $.ajax({
                url: getBookUrl,
                type:'get',
                dataType: 'json',
                data:{
                    loc:'1'
                },
                cache: false,
                success: $.proxy(function( data ){
                    if( data && data.code == 200 ){
                        popupMsg.miniTipsAlert('享书成功:' +isbnStr );
                    }else{
                        
                         popupMsg.miniTipsAlert('享书失败:' +isbnStr );
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
    		this.$bookInfoRootEl.find('.J-book-title').html(bookInfo.title;
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