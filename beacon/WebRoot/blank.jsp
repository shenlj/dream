<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/common/taglibs.jsp"></jsp:include>
<jsp:include page="/common/includes.jsp"></jsp:include>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>空白页面1</title>
    <style>
		.style{width:668px; left:50%; position:absolute; top:20%; bottom:20%; margin-left:-300px;}
    	.style.tishi{background:url(${ctx}/themes/${themes}/css/images/tishi.jpg) center center no-repeat; }
		.style.xnsz{background:url(${ctx}/themes/${themes}/css/images/xnsztishi.gif) center center no-repeat;}
		.style.xydwh{background:url(${ctx}/themes/${themes}/css/images/xydwh.gif) center center no-repeat;}
		.style.jswh{background:url(${ctx}/themes/${themes}/css/images/jstishi.gif) center center no-repeat;}
    </style>
    <script language="javascript">
		$(function(){
			//url路径
			var param=top.getLocationParam(location);
			var obj=$("#tsxx");
			if(param.tag!=null){
				$(obj).removeAttr("class");
				switch(param.tag){
					case"tishi":
						$(obj).addClass("style tishi");  //请选择班级维护
					break;
					case "xnsz":
						$(obj).addClass("style xnsz");   //请选择学年学期进行设置
					break;
					case "xydwh":
						$(obj).addClass("style xydwh");   //请选择学员队或区队进行维护
					break;
					case "jswh":
						$(obj).addClass("style jswh");   // 该功能正在建设中
					break;
				}
			}
		})
	</script>
</head>
<body>
<div id="tsxx" class="style jswh">
</div>
</body>
</html>

