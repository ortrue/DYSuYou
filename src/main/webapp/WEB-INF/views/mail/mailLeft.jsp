<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.dayang.suyou.constants.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>首页</title>

<link href="${ctx}/static/tab-plugin/blue/main.css" rel="stylesheet" />
<link href="${ctx}/static/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/zTreeStyle/myztree.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/js/jsArray.js" type="text/javascript"></script>
<script src="${ctx}/static/tab-plugin/mainTab.js?v=20140927" type="text/javascript"></script>
<script src="${ctx}/static/zTreeStyle/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
<script src="${ctx}/static/js/math.uuid.js" type="text/javascript"></script>

</head>
<body>
	<input type="hidden" value ="<%=basePath%>" id="basePath"/>
	<div class="frame-nav">
		<div class="navtop">
			<ul class="clearfix">
				<li class="lt" onclick="openNewTab('11','收件柜','${ctx}/mail/inbox');buildMenuTotal();"><i class="icon-envelope"></i><a>收件</a></li>
				<li onclick="doNewMail();"><i class="icon-edit"></i><a>发件</a></li>
			</ul>
		</div>

		<ul class="mailLiast clearfix" id="mailMenu">
			<li class="active" onclick="openNewTab('11','收件柜','${ctx}/mail/inbox');"><div class="box"><span><i class="icon-sj"></i>收件柜</span><strong id="mailTotal"></strong></div></li>
			<li onclick="openNewTab('12','待发区','${ctx}/mail/mailmng/1');"><div class="box drafts"><span><i class="icon-cg"></i>待发区</span><strong id="draftTotal"></strong></div></li>
			<li onclick="openNewTab('13','已投递','${ctx}/mail/mailmng/2');"><div class="box sent"><span><i class="icon-yf"></i>已投递</span><strong id="sendedTotal"></strong></div></li>
<%-- 		<li onclick="openNewTab('14','已删除','${ctx}/mail/mailmng/3');"><div class="box trash"><span><i class="icon-sc"></i>已删除</span><strong id="deleteTotal"></strong></div></li> --%>
			<li><div class="ltLine"></div></li>
			<li>

		    <div class="zTreeDemoBackground left">
				<ul id="treeDemo" class="ztree"></ul>
	        </div>

			</li>
		</ul>
	</div>

    <div id="divContent" class="content"></div>
<script type="text/javascript">
$(document).ready(function(){
	  $(".cthd").click(function(e) {
	    $(this).parent().toggleClass("open");
	  });

	  $("#mailMenu > li").click(function(){
		  $("#mailMenu > li").removeClass("active");//首先移除全部的active
		  $(this).addClass("active");//选中的添加acrive
	  });
	  
	  openNewTab('11','收件柜','${ctx}/mail/inbox');
	  buildMenuTotal();
});

function openNewTab(id,itemText,url)
{
	fGoto(id,itemText,url);
}

function iframeResize(iframe) {
    var cwin=iframe; 
    var h1=0, h2=0;
    
    if(document.documentElement&&document.documentElement.scrollHeight){
        h1=document.documentElement.scrollHeight;
    }
    if(document.body){
    	h2=document.body.scrollHeight;
    }
    	
    var h=Math.max(h1, h2);
    if(document.all){
    	h += 4;
    }
    
    if(window.opera){
    	h += 1;
    }

    cwin.style.height = h +"px";
}

function buildMenuTotal(){
   $.ajax({
       url:"${ctx}/mail/total",
       data:{},
       type:'post',
       dataType:'json',
       success:function(result){
		$("#mailTotal").text("("+result.mailTotal+")");
		$("#draftTotal").text("("+result.draftTotal+")");
		$("#sendedTotal").text("("+result.sendedTotal+")");
		$("#deleteTotal").text("("+result.deleteTotal+")");
      }
    });
}

function doNewMail(){
	//1.生成新的发件ID
	var newMailUUID=Math.uuid();
	openNewTab(newMailUUID,'发件','${ctx}/mail/newmail/0/1/'+newMailUUID);
}

</script>
</body>
</html>