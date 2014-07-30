/* -----------------------

   slideWindows.js 
   edit by qiany
   date 2011-5-2
   
   ----------------------- */
   
   
/* 
 *	function: 初始化slideWindows
 *	author:   qiany
 *	date:     2011-5-2
 */
 
function initSlideWindows() {
	
	var windowNum = arguments.length;
	
	for (var i = 0 ; i < windowNum ; i = i + 1) {
		
		var options = $.extend({
			id: '',				// 按钮id
			title: '标题',		// 窗口标题
			url: '',			// 窗口内页面的url
			width: 800,			// 窗口宽度
			height: 400,		// 窗口高度
			load: 'later'		// 窗口内页面加载方式：'former'加载主页面时同时加载，'later'第一次点击按钮显示时加载
		}, arguments[i]);
		
		var $sideTab = $('#' + options.id);
		if ($sideTab.length === 0) continue; 
		
		// 缓存当前窗口的参数，不使用$.data方法是因为使用$.load方法后会丢失缓存数据
		$sideTab.attr('options', $.toJSON(options));
		
		// 给$sideTab添加hover样式
		$sideTab.css('cursor', 'pointer').hover(
			function() {
				var className = $(this).attr('class');
				if (className.indexOf('top') > -1) {
					$(this).addClass('tab-top-hover');
				} else {
					$(this).addClass('tab-hover');
				}	
			},
			function() {
				var className = $(this).attr('class');
				if (className.indexOf('top') > -1) {
					$(this).removeClass('tab-top-hover');
				} else {
					$(this).removeClass('tab-hover');
				}		
			}
		);
		
		// 给$sideTab绑定弹出窗口和收起窗口事件
		$sideTab.toggle(
		
			// 弹出窗口
			function() {
				var $this = $(this);
				var options = $.parseJSON($this.attr('options'));
				
				// 收起已打开的窗口 
				$('.tab-top-chosen, .tab-chosen').click();
			
				// 添加chosen类
				if ($this.attr('class').indexOf('top') > -1) {
					$this.addClass('tab-top-chosen');
				} else {
					$this.addClass('tab-chosen');
				}	
				
				// 弹出当前窗口
				var direction = getDirection($this);
				var top = 0, startPoz = 0, terminalPoz = 0;
				
				if ($.browser.msie && $.browser.version <= 6.0) {
					top = 2;
				}
				
				if (direction == 'left') {
					startPoz = - options.width;
					terminalPoz = 28;
				} else {
					startPoz = $(window).width();
					terminalPoz = $(window).width() - options.width - 28;
				}	
				
				var dg = new J.dialog({
					id: options.id + 'DG',
					title: options.title, 
					page: options.url, 
					width: options.width, 
					height: options.height, 
					fixed: true,
					left: startPoz,
					top: top,
					SetTopWindow: window,
					cover: false,
					btns: false
				});
				dg.ShowDialog();
				
				$('#' + dg.opt.id).animate({ left : terminalPoz }, 'slow');
				
				// 给关闭按钮改变点击事件
				
				$('#' + dg.opt.id).find('#lhgdig_xbtn').click(function() {
					$('.tab-top-chosen, .tab-chosen').click();
					dg.cancel();
				});
				
				// 缓存dg的id
				$this.attr('dgID', dg.opt.id);
				
			},
			
			// 收起窗口
			function() {
				var $this = $(this);
				
				// 移除chosen类
				if ($this.attr('class').indexOf('top') > -1) {
					$this.removeClass('tab-top-chosen');
				} else {
					$this.removeClass('tab-chosen');
				}	
				// 关闭窗口
				$('#' + $this.attr('dgID')).find('#lhgdig_xbtn').click();
				
			}
		)
		
	}	
	
}




/* 
 *	function: 获得按钮的方位'left' or 'right'
 *	author:   qiany
 *	date:     2011-5-2
 */
 
function getDirection($sideTab) {
	
	if ($sideTab.attr('class').indexOf('left') > -1) {
		return 'left';	
	} else {
		return 'right';	
	}
	
}




/* 
 *	function: 将一个对象转变为JSON格式
 *	author:   qiany
 *	date:     2011-5-24
 */
jQuery.extend({
	
	toJSON: function(obj) {
	
		var json = '';
		
		for (var property in obj) {
			json += '\"' + property + '\":\"' + obj[property] + '\",';  	
		}
		json = '{' + json.slice(0, -1) + '}';
		
		return json;
	}
	
});
