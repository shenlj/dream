/**  
	* Filename:    commonValidate.js
	* Description: ckeditor配置文件
	* Company:     ITC
	* @author:     SGY
	* @version:    1.0
	* Create at:   2011-02-18
	*  
	* Modification History:
	* Date         Author      Version     Description
	* ------------------------------------------------------------------
	* Copyright (c) 2010-2011 .All rights reserved.
*/
CKEDITOR.editorConfig = function( config ){
	//config.language = 'zh-cn';        // 配置语言
	//config.uiColor = '#FFF';         // 背景颜色  
	//config.width = '900px';         // 宽度  
	//config.height = '180px';       // 高度
	//config.skin = 'office2003';   //界面v2,kama,office2003  为提高速度,只保留了office2003
	//配置操作界面上的工具按钮
	config.toolbar =
		[
			['Preview'],
			['Print'],
			['Cut','Copy','Paste','PasteText','PasteFromWord'],
			['Undo','Redo','Replace','-','SelectAll','RemoveFormat'],
			['Table','HorizontalRule','SpecialChar','PageBreak'],
			'/',
			['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
			['NumberedList','BulletedList','-','Outdent','Indent'],
			['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
			['Link','Unlink'],
			'/',
			['Styles','Format','Font','FontSize'],
			['TextColor','BGColor'],
			['Image']
		];
		//增加中文字体
		config.font_names = '宋体/宋体;黑体/黑体;仿宋/仿宋 _GB2312;楷体/楷体_GB2312;隶书/隶书;幼圆/幼圆;'+ config.font_names ;
		config.filebrowserUploadUrl =jQuery.ctx+'/taskmgr/ckeditorUpLoad!upLoadImg.action';
	};
	//配置图片上传去掉高级等一些无用选项
	CKEDITOR.on('dialogDefinition', function(ev) {
		var dialogName = ev.data.name;
		if (dialogName == 'image') {
			var dialog = ev.data.definition;
			dialog.removeContents('Link');
			dialog.removeContents('advanced');
			dialog.getContents('info').elements[2].hidden = true;
			dialog.minHeight = 70;
		}
	});

