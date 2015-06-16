/**
 * @author leiling.cp
 * @version 1.0.0
 * @description seajs配置文件
 */
seajs.config({
    // Sea.js 的基础路径
    base: '/js/common/',

    // 别名配置
    alias: {
        'jquery': '/js/common/jquery.js',
        'events': '/js/common/events/events.js',
        'handelbars': '/js/common/handelbars.js'
    },

    // 文件编码
    charset: 'utf-8',

    // 变量配置
    vars: {
        locale: 'zh_CN'
    },

    // 预加载项
    preload: ['/js/common/jquery.js']
});