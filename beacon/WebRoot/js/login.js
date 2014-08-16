$(document).ready(function(){
	$("#login").click(function(){
		checklogin();
	});
})

function checklogin(){
	
	var user = $("#username").val();
	var passwd = $("#password").val();
	if(user==''){
		$("#log").html("请输入用户名!");
		return;
	}
	if(passwd==''){
		$("#log").html("请输入密码!");
		return;
	}
	checkUser(user,passwd);
}


function checkUser(user,passwd){
	
	var url = jQuery.ctx+"/demo/user!checkLogin.action?loginID="+user+"&passwd="+passwd;
	$.ajax({
		type:"post",
		async: true,
		url:url,
		success: function(data){
			var obj = jQuery.parseJSON(data);
     		if(obj.msg!='success'){alert();
     			$("#username").val('');
				$("#password").val('');
     		    $("#log").html(obj.msg);
				return;
     		}
     		else {
     			window.location.href =  jQuery.ctx+'/app/demo/demo.jsp';
     		}
   		}
	});
}

function BindEnter(obj){
	if(obj.keyCode == 13){
		checklogin();
	}
}