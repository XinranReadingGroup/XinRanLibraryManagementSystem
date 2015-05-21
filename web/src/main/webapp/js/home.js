/**
 * [首页部分]
 * @param  {[type]} require [description]
 * @param  {[type]} exports [description]
 * @param  {[type]} module) {               module.exports [description]
 * @return {[type]}         [description]
 */

define(function(require, exports, module) {

   var $ = require('jquery');



   var homeObj = {

   		hasInit:false,

    	/**
    		共享图书页面初始化
    		@method init
    	*/
    	init:function(){

    		if( this.hasInit === false ){
    			this.hasInit = true;

    		    $('[data-toggle="tooltip"]').tooltip();
    		}
    	}
    	

    };

    module.exports = homeObj;
});