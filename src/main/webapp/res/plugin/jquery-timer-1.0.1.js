/**
 * JQuery 时间插件
 * @param $
 * 
 * @author xuan
 * @email 1194941255@qq.com
 * @date 2014年12月6日 下午2:03:40
 * @version 1.0.1
 * 
 */
;(function($){
	/** 添加对象的方法 */
	$.fn.extend({
		timeRun : function(){
			/** 创建Date */
			var d = new Date();
			var result = new Array();
			/** 获取年 */
			result.push(d.getFullYear() + "年");
			/** 获取月 0-11 */
			result.push($.calc(d.getMonth() + 1) + "月");
			/** 获取日 */
			result.push($.calc(d.getDate()) + "日");
			/** 星期几 0-6 */
			result.push("&nbsp;" + $.weeks[d.getDay()] + "&nbsp;");
			/** 获取时 */
			result.push($.calc(d.getHours()) + ":");
			/** 获取分 */
			result.push($.calc(d.getMinutes()) + ":");
			/** 获取秒 */
			result.push($.calc(d.getSeconds()));
			/** 输出到页面 */
			this.html(result.join(""));
			var t = this;
			/** 给时间加上定时器 每秒回调一次timeRun() */
			window.setTimeout(function(){
				t.timeRun();
			}, 1000);
		}
	});
	/** 添加静态的方法 */
	$.extend({
		weeks : ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
		calc : function(param){
			return param > 9 ? param : "0" + param;
		}
	});
	
})(jQuery);