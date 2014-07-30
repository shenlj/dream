<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@include file="/common/includes.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>跳转页面</title>
</head>
<body>
<div >
	<div style=" width:400px;height:50px;position: absolute;top:50%;margin-top:-25px; left:50%;margin-left:-200px;">
		<font color="red" id="msgText" style="float:left;">用户未登录，或当前用户无此操作权限！</font>
		<input type="hidden" id = "msgType" value="${param.msgType}" />
		<input style="float:left;" type="button" onclick="countDown();" class="ok_button" value="确定"/>
  </div>
	</div>
</div>
</div>
<script type="text/javascript">

$(document).ready(function(){
	var type = $("#msgType").val();
	if(type=='1'){
		$("#msgText").text('页面没有配置权限表中，转到登陆页面！');
	}else if(type=='2'){
		$("#msgText").text('SESSION为空,请重新登陆!');
	}
})

function countDown(){
     top.location.href=jQuery.ctx + '/index.jsp';
} 
</script>
</body>
</html> 