<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.wholetech.commons.MRSConstants"%>
<%@ include file="/common/taglibs.jsp"%>
<html style="overflow:hidden">
	<head>
		<%@ include file="/common/includes.jsp"%>

		<script>
	jQuery(function() {
		var dg = frameElement.lhgDG;
		dg.removeBtn();

		dg.addBtn("cancle", "关闭", dg.cancel);
		
		dg.reDialogSize(320, 220);
		dg.iPos(dg.dg, "center", "center");

		if (dg.topWin.actionFailure) {
			dg.topWin.actionFailure();
		}
	});
</script>
	</head>
	<body class="popup-page" style="overflow:hidden">
		<div>
			<div style=" width: 50px; height: 150px; float: left">
			<div style="width: 45px; height: 45px;margin-top:30px;background: url(${ctx}/images/error.gif)" ></div>
			
			</div>
			<div style="width: auto; height: 120px; overflow-x: hidden; overflow-y: auto">
			<div style="font-size: 14px;margin-left:10px;padding-right:10px;margin-top:35px">
			<%
				String message = (String)request.getAttribute(MRSConstants.MESSAGE_KEY);
					if (message == null) {
						message = "成功保存信息";
					}
					
					out.print(message);
			%>
			</div>
				
			</div>
		</div>

	</body>

</html>