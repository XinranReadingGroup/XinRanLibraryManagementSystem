/**
   @desc [弹出mini消息组件]
   @author dasen
 */
define(function(require, exports, module) {
	var $ = require('jquery'),
		Dialog = require('/js/common/artdialog/dist/dialog-min.js');
		
	var DELAY_TIME = 3000,//默认3秒消失
		OFFSET_Y = 20;

	var popupMsg = {
			/**
			  初始化开关，保证只初始化一次
			 */
			_isInit:false,
			/**
			  初始化
			 */
			_init:function(){
				if( this._isInit == false ){
	
					this._isInit = true;

					this._initDialogs();
				}
			},
			/**
			  二次确认框回调
			 */
			_confirmCallBack:function( isConfirm ){

			},

			/**
			  初始化二次确认框
			 */
			_initDialogs:function(){

				 this.alertDialog = dialog({
	                title: '温馨提醒',
	                content: '',
	                cancel: false,
	                ok: function () {}
	             });



	             this.confirmDialog =  dialog({
	                title: '请选择确认',
	                content: '',
	                okValue: '确定',
	                ok: $.proxy(function () {
	                    this._confirmCallBack( true );
	                },this),
	                cancelValue: '取消',
	                cancel: $.proxy(function () {
	                    this._confirmCallBack( false);
	                },this)
	            });

			},
	
			/**
			  错误提示
			  @method miniTipsError
			  @param {String} msg 错误提示文案
			  @param {Integer} [time] 提示消失延迟时间 毫秒;可选参数
			  @param {Integer} [offsetY] 提示相对顶部的便宜距离；可选参数
			 */
			miniTipsAlert: function( msg, time, offsetY ){
				this.confirmDialog.width(320);
				this.alertDialog.content( msg);
				this.alertDialog.show();
			},

			/**
			  runtime模块全局 Confirm 函数
			  @Function runtimeConfirm
			  @param {String} msg 消息字符串
			  @param {Function} okCallBack 点击yes后的回调
			  @public
			 */
			runtimeConfirm:function( msg, okCallBack ){
				this.confirmDialog.width(320);
				this.confirmDialog.content( msg );
				this._confirmCallBack = okCallBack || function( isConfirm ){};
				this.confirmDialog.show();
			}
		
		
	};
	
	popupMsg._init();
	
	module.exports = popupMsg;
	
	
});