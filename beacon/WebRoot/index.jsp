<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<%@include file="/common/includes.jsp" %>
<link rel="stylesheet" type="text/css" href="${ctx}/css/login.css">
<script type="text/javascript" src="${ctx}/js/login.js" ></script>
<script type="text/javascript"  >
 function d(){
 document.forms[0].submit();
 }
</script>
</head>

<body onkeydown="BindEnter(event)">
<!-- 登陆页面头部 -->
<div id="logHeader_bg">
  <div id="logHeader"></div>
</div>
<form action="${ctx}/sysmgr/znshSysmgr!listPkrwWithXnxq.action" method="post"></form>

<!-- 登陆页面登陆框 -->
<div id="logBox">
<div id="logLog"><span><font id="log" color="red">&nbsp;</font></span></div>
  <div id="regBox">
  	
    <div id="r_user">
      <input type="text" class="r_user_input" id="username" />
    </div>
    <div id="r_passwd">
      <input type="password" class="r_passwd_input" id="passwd"/>
    </div>
    <div class="login_btn_wrap">
      <input type="button" class="login" id="login" onclick="d()"/>
    </div>
    <div class="login_btn_wrap">
      <input type="button" class="reset" id="reset"/>
    </div>
  </div>
</div>
<!-- 登陆页面底部版权声明 -->
<div id="copyright">
  <div class="copyrightLogo"></div>
  copyright @ 2011 All Rights Reserved  联系方式：010-12345678 地址：北京西城区
</div>
</body>
</html>