/*按钮样式切换*/
$(function(){
	$(".buttonArea .newButton").mouseover(function(){
		$(this).removeClass("newButton");
		$(this).addClass("newButton_hover");
	})
	$(".buttonArea .newButton").mouseout(function(){
		$(this).removeClass("newButton_hover");
		$(this).addClass("newButton");
	})
})
/**
 * 字符串或object数组转成json串
 * 2011-07-07 shenlj
 */
function arrayToJson(o) {
  if(o == undefined || o == null){
    return "";
  }else{
    var r = [];
    if (typeof o == "string")
      return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n")
              .replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
    if (typeof o == "object") {
      if (!o.sort) {
        for (var i in o)
          r.push(i + ":" + arrayToJson(o[i]));
        if (!!document.all
            && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/
                .test(o.toString)) {
          r.push("toString:" + o.toString.toString());
        }
        r = "{" + r.join() + "}";
      } else {
        for (var i = 0; i < o.length; i++) {
          r.push(arrayToJson(o[i]));
        }
        r = "[" + r.join() + "]";
      }
      return r;
    }
    return o.toString();
  }
}
