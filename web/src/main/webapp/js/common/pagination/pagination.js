/**
 * 分页组件
 * @author dasen
 *
 */
define(function(require, exports, module) {
    var $ = require('jquery'),
        Events = require('events');

    function Pagination(selector, cfg) {
        if (this instanceof Pagination) {

            this.con = $(selector);

        
            this.init( cfg );

        } else {
            return new Pagination(selector, cfg);
        }
    }



   Pagination.prototype = {

         ATTRS:{
            // 总页数
            totalPage: {
                value: 10
            },
            // 默认选中的页数
            currentPage: {
                value: 1
            },
            // 当前页的最大紧邻前置页数（不包括最前面的显示页数）
            preposePagesCount: {
                value: 2
            },
            // 当前页的最大紧邻后置页数
            postposePagesCount: {
                value: 1
            },
            // 第一个"..."前显示的页数
            firstPagesCount: {
                value: 2
            },
            // 第二个"..."后显示的页数
            lastPagesCount: {
                value: 0
            },
            render: {
                value: true
            }
        },

        get:function( key ){
            return this.ATTRS[key].value;
        },

        set:function( key, val ){
            this.ATTRS[key].value = val;
        },

        init: function( cfg ) {
            for( var key in cfg ){
                if( cfg.hasOwnProperty( key ) ){
                    this.set( key, cfg[key] );
                }
            }

            if (this.get('render')) {
                this.render();
            }
        },
        render: function() {
            this.renderUI();
            this.bindUI();
            this.syncUI();
        },
        renderUI: function() {
            this._resetPagination();
        },
        bindUI: function() {


            this.con.delegate('.pagination-spec', 'click',  $.proxy(function(ev) {
                var $itemEl = $(ev.currentTarget),
                    toPage = parseInt( $itemEl.attr('data-page') );
                this._switchToPage(toPage);

            },this));

            this.con.delegate('.pagination-prev','click', $.proxy(function(ev) {
                var toPage = this.get('currentPage') - 1;
                this._switchToPage(toPage);
            },this));

            this.con.delegate('.pagination-next', 'click',  $.proxy(function(ev) {
                var toPage = this.get('currentPage') + 1;
                this._switchToPage(toPage);
            },this));

            this.con.delegate('.pagination-btn', 'click', $.proxy(function(ev) {

                var toPage, toPageStr = this.con.find('.pagination-text').val();
				if( toPageStr === '' ){
					toPageStr = '1';
				}
				toPage = parseInt( (toPageStr > this.get('totalPage'))?this.get('totalPage'):toPageStr, 10 );
                this._switchToPage(toPage);

                return false;

            },this));
        },
        syncUI: function() {},
        /**
         * @brief 刷新分页组件
         */
        _resetPagination: function() {
            var i,
				paginationInner = '',
                totalPage = this.get('totalPage') > 0 ? this.get('totalPage') : 1,
                currPage = (this.get('currentPage') <= totalPage && this.get('currentPage')) > 0 ? this.get('currentPage') : 1,
                preposePagesCount = this.get('preposePagesCount') >= 0 ? this.get('preposePagesCount') : 2,
                postposePagesCount = this.get('postposePagesCount') >= 0 ? this.get('postposePagesCount') : 1,
                firstPagesCount = this.get('firstPagesCount') >= 0 ? this.get('firstPagesCount') : 2,
                lastPagesCount = this.get('lastPagesCount') >= 0 ? this.get('lastPagesCount') : 0,
                offset;

            // currPage前的页码展示
            paginationInner += currPage === 1 ? '<span class="pagination-start"></span>' : '<a class="pagination-prev"></a>';

            if (currPage <= firstPagesCount + preposePagesCount + 1) {
                for ( i = 1; i < currPage; i++) {
                    paginationInner += this._renderActivePage(i);
                }

            } else {
                for ( i = 1; i <= firstPagesCount; i++) {
                    paginationInner += this._renderActivePage(i);
                }
                paginationInner += '<span class="pagination-break">...</span>';
                for ( i = currPage - preposePagesCount; i <= currPage - 1; i++) {
                    paginationInner += this._renderActivePage(i);
                }
            }

            // currPage的页码展示
            paginationInner += '<span class="pagination-curr">' + currPage + '</span>';

            // currPage后的页码展示
            if (currPage >= totalPage - lastPagesCount - postposePagesCount) {
                offset = currPage + 1;
                for ( i = currPage + 1; i <= totalPage; i++) {
                    paginationInner += this._renderActivePage(i);
                }

            } else {
                for ( i = currPage + 1; i <= currPage + postposePagesCount; i++) {
                    paginationInner += this._renderActivePage(i);
                }
                paginationInner += '<span class="pagination-break">...</span>';
                for ( i = totalPage - lastPagesCount + 1; i <= totalPage; i++) {
                    paginationInner += this._renderActivePage(i);
                }
            }

            paginationInner += currPage === totalPage ? '<span class="pagination-end"></span>' : '<a class="pagination-next"></a>';
            
            paginationInner +=  '<form class="pagination-form" name="" method="get" action=""> 共' + this.get('totalPage');
			paginationInner +=  '页 去第<input type="text" name="page" class="pagination-text" size="3" value="' + currPage + '" />';
			paginationInner +=  '页<button type="submit" class="pagination-btn">确定</button>' ;
			paginationInner +=  '</form>';
			
            this.con.html(paginationInner);

        },
        /**
         * @brief 渲染可点击的页码
         * @param index {Number} 页码索引
         *
         */
        _renderActivePage: function(index) {
            return '<a class="pagination-spec" data-page="' + index + '">' + index + '</a>';
        },
        _switchToPage: function(page) {
            
            var ret =  this.trigger('beforeswitch', {
                toPage: page
            });

            if (ret === false) {
                return;
            }

            this.set('currentPage', page);
            this._resetPagination();
            this.trigger('switch', {
                toPage: this.get('currentPage')
            });
        },
        /**
         * @brief 重置分页组件页码UI
         * @param pageNum {Number} 页码索引
         *
         */
        resetPageNum: function(pageNum ,totalPage ) {

            this.set('currentPage', pageNum);
            if( typeof( totalPage ) !== 'undefined'){
                this.set('totalPage', totalPage);
            }

            this._resetPagination();

        },
        show: function() {
            this.con.show();
        },
        hide: function() {
            this.con.hide();
        }
    };

    Events.mixTo( Pagination );

    module.exports = Pagination;


});