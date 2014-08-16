<%@page import="java.sql.SQLException"%><%@page	import="java.io.IOException;"%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
	response.setContentType("text/plain;charset=UTF-8");

	//获取异常消息体
	java.util.Map attr = (java.util.Map) com.opensymphony.xwork2.ActionContext.getContext().get("attr");
	Exception e = (Exception) attr.get("exception");

	// 如果是ajax请求
	if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
		out.print("{\"success\":false,\"msg\":\"提交数据出错！\"}");
		return;
	}

	//获取错误代码
	String errorCode = "UNKNOW_ERROR";
	if (e instanceof RuntimeException) {
		errorCode = "ERROR_RUNTIME";
	} else if (e instanceof IOException) {
		errorCode = "ERROR_IO";
	} else if (e instanceof SecurityException) {
		errorCode = "ERROR_SECURITY";
	} else if (e instanceof SQLException) {
		errorCode = "ERROR_SQL";
	} else {
		errorCode = "ERROR_UNKNOWN";
	}
%>

<!--ERROR_MSG_TAG-->
<html>
	<head>
		<title>Error Page</title>
	</head>
	<body>
		<font size="5" color="red">We are sorry but some error
			occurred. Please try to find the reason using the infomation below.
			You can also contact the administrator.</font>
		<br />
		<table border="1">
			<tr>
				<td width="30%">
					Error Code
				</td>
				<td><%=errorCode%></td>
			</tr>
			<tr>
				<td>
					Message
				</td>
				<td><%=e.getMessage()%></td>
			</tr>
			<tr>
				<td>
					Details
				</td>
				<td>
					<%
						e.printStackTrace();
					%>
				</td>
			</tr>
		</table>
	</body>
</html>
