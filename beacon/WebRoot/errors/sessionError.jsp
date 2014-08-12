<%@ page contentType="text/html;charset=UTF-8"%>
<script>

<%
	String error = request.getParameter("error");
		if ("expire".equals(error)) {
%>
	alert("由于长时间为操作，为了你的使用安全请重新登录")
<%
	} else if ("logout".equals(error)) {
%>
	alert("你所使用的账户已经从系统注销，请你重新登录");
<%}%>

	top.location.href = "${pageContext.request.contextPath}/login.jsp";
</script>