/**
 * [共享图书处理部分JS]
 * @param  {[type]} require [description]
 * @param  {[type]} exports [description]
 * @param  {[type]} module) {               module.exports [description]
 * @return {[type]}         [description]
 */

define(function(require, exports, module) {

   var $ = require('jquery'),
       popupMsg = require('/js/common/popup-msg/popup-msg.js');



   var shareObj = {

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
    			alert('共享成功');
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
    		设置图书信息
    		@method setBookInfo
    	*/
    	setBookInfo:function( bookInfo ){

    		this.$bookInfoRootEl.find('.J-book-pic').attr('src', bookInfo.image  );
    		this.$bookInfoRootEl.find('.J-book-title').html(bookInfo.title + '(' + bookInfo.subtitle + ')'  );
    		this.$bookInfoRootEl.find('.J-author').html( bookInfo.author.join('&nbsp;|&nbsp;') );
    		this.$bookInfoRootEl.find('.J-publisher').html( bookInfo.publisher );
    		this.$bookInfoRootEl.find('.J-pubdate').html( bookInfo.pubdate );
    		this.$bookInfoRootEl.find('.J-price').html( bookInfo.price );
    		this.$bookInfoRootEl.find('.J-binding').html( bookInfo.binding );
    		this.$bookInfoRootEl.find('.J-pubdate').html( bookInfo.pubdate );
    		this.$bookInfoRootEl.find('.J-isbn').html( bookInfo.isbn13 || bookInfo.isbn10  );
    		this.$bookInfoRootEl.find('.J-author_intro').html( bookInfo.author_intro );
    		this.$bookInfoRootEl.find('.J-summary').html( bookInfo.summary  );

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

    module.exports = shareObj;
});