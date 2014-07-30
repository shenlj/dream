<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="fmt" uri="/WEB-INF/fmt.tld"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
response.setHeader("Pragma", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
StringBuffer localStringBuffer = new StringBuffer();
int i = request.getServerPort();
if (i < 0)
  i = 80;
String str = request.getScheme();
localStringBuffer.append(str);
localStringBuffer.append("://");
localStringBuffer.append(request.getServerName());
if (((str.equals("http")) && (i != 80)) || ((str.equals("https")) && (i != 443)))
{
  localStringBuffer.append(':');
  localStringBuffer.append(i);
}
localStringBuffer.append(request.getContextPath());
String allURL = localStringBuffer.toString();
%>
<c:set var="ctx" scope="request" value="${pageContext.request.contextPath}" />
<c:set var="ctxFull" scope="request" value="<%=allURL%>"/>
