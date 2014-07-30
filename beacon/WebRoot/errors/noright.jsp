<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div region="center">
	<div class="ftitle">
		<font color="red">用户未登录，或当前用户无此操作权限！</font>
	</div>
	<form id="fm" method="post">
		<div class="fitem">
			系统会在<span id="jumpTo" style="color:red">2</span>秒钟后，自动转到登录页面...
		</div>
	</form>
</div>
</div>
<script type="text/javascript">

	$(function(){
		countDown(3,"<%=request.getContextPath()%>/index.jsp");
	});
	function countDown(secs,surl){     
		 var jumpTo = document.getElementById('jumpTo');
		 jumpTo.innerHTML=secs;  
		 if(--secs>0){
		     setTimeout("countDown("+secs+",'"+surl+"')",1000);     
		 }
		 else{  
		     top.location.href=surl;
		 }
 } 
</script>
</body>
</html>