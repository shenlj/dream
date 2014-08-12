<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1.0;text/html; charset=utf-8" />
<%@include file="/common/includes.jsp" %>
<%@include file="/common/includes-list.jsp" %>
<script type="text/javascript" src="${ctx}/app/demo/demo.js" ></script>
</head>

<body class="body">
<div class="container">
	<table id="usersTable" class="table table-striped table-bordered">
    	<thead>
        	<tr>
            	<th>主键</th>
         		<th>选择</th>
         		<th>用户名</th>
         		<th>性别</th>
         		<th>操作</th>
       		</tr>
     	</thead>
     	<tbody></tbody>
	</table>
</div>
</body>
</html>
