<%@page import="java.util.Map"%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
	response.setContentType("text/plain;charset=UTF-8");

  Map<String, Object> errors = org.apache.struts2.ServletActionContext.getContext().getConversionErrors();
  StringBuilder sb = new StringBuilder("[");
  for (Map.Entry<String, Object> entry : errors.entrySet()) {
    if (sb.length() > 1) {
      sb.append("，");
    } 
    
    sb.append("{").append(entry.getKey()).append(":");
    
    if (entry.getValue() instanceof Object[]) {
    	Object[] arr = (Object[])entry.getValue();
      for (int i = 0; i < arr.length; i++) {
    	  if (i > 0) {
    		  sb.append(","); 
    	  }
    	  sb.append(arr[i]);
      }
    } else {
    	sb.append(entry.getValue() != null ? entry.getValue().toString() : "");
    }
    
    sb.append("}");
  }
  sb.append("]");
  
  
	// 如果是ajax请求
	if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
		out.print("{\"success\":false,\"msg\":\"");
		out.print(sb.toString());
		out.print("\"}");
		return;
	}
%>

<!--ERROR_MSG_TAG-->
<html>
	<head>
		<title>Error Page</title>
	</head>
	<body>
		<font size="5" color="red">struts拦截器执行过程中出错.</font>
		<br />
		<table border="1">
			<tr>
				<td>
					错误消息：
				</td>
				<td><%=sb.toString() %></td>
			</tr>
		</table>
	</body>
</html>
