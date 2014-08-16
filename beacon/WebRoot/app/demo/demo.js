var oTable = null;

$(document).ready(function(){
  
  oTable = $("#usersTable").dataTable({
       "bServerSide": true,
       "sAjaxSource": jQuery.ctx + "/demo/user!getUserList.action", //mvc后台ajax调用接口  
       "bPaginate": true, // 是否分页。
       "bProcessing": false, // 当datatable获取数据时候是否显示正在处理提示信息。
       "bFilter": false,  //是否使用内置的过滤功能。
       "bLengthChange": true, //是否允许用户自定义每页显示条数。
       "sPaginationType": "full_numbers",      //分页样式
       "sScrollY": "100%",//分页样式
       "sScrollX": "100%",
       "sDom": "rtilfp",
       "bAutoWidth" : false,
       "bRetrieve": true,
       "bAjaxDataGet": false,
       "bSort": false,
       "aoColumns": [
           {"sName": "id", "mDataProp":"id","bVisible":false},
           {"sName" : "id1","mDataProp" : "id1","sWidth" : "50px",
           "fnRender" : function(obj) {
				return "<input name='multiSel' type='checkbox' value='"+ obj.aData['id'] + "' />";
		     	}
		   },
           {"sName": "username", "mDataProp":"username","sClass": "center","sWidth": "100px"},
           {"sName": "sex", "mDataProp":"sex","sWidth": "60px",
           	"fnRender": function (obj) {
               if( obj.aData['sex'] == '1'){return "男";}else{return "女";}
             }
           },
           {"sName": "operateType", "mDataProp":"operateType","sClass": "center","sWidth": "100px",
             "fnRender": function (obj){
        	   return "刪除";
             }
           }
       ]
  });
});
