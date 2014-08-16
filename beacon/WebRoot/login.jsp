<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登陆页面</title>
<meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1.0;text/html; charset=utf-8" />
<%@include file="/common/includes.jsp" %>
<script type="text/javascript" src="${ctx}/js/login.js" ></script>
</head>
<body onkeydown="BindEnter(event)">
	<div class="container">
		<section class="loginBox row-fluid">
			<section class="span7 left">
				<h2>商户登录</h2>
	            <p><label>用户名:</label><input type="text" id="username" name="username" placeholder="用户名"/></p>
	            <p><label>密码：</label><input type="password" id="password" name="password" placeholder="密码"/></p>
	            <section class="row-fluid">
					<section class="span1"><input type="button" id="login" value="登录 " class="btn btn-primary"></section>
	            </section>
	        </section>
	        <section class="span5 right">
	            <h2>没有帐户？</h2>
	            <section>
	                <p>介绍注册的好处和公司或者项目概况。。。</p>
	                <p><input type="button" value=" 注册 " class="btn regBtn"></p>
	            </section>
	        </section>
	    </section><!-- /loginBox -->
	</div> <!-- /container -->
</body>
</html>