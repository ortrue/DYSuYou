<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.dayang.suyou.constants.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>首页</title>
<link href="${ctx}/static/tab-plugin/blue/main.css" rel="stylesheet" />
<script src="${ctx}/static/tab-plugin/mainTab.js?v=20140927" type="text/javascript"></script>

<script type="text/javascript">
$(document).ready(function(){
	  $(".cthd").click(function(e) {
	    $(this).parent().toggleClass("open");
	  });
	  $(".arrowrt").click(function(e) {
	    $(this).toggleClass("open");
	  });
	  
	  $(".frame-nav > ul > li").click(function(){
		  $(".frame-nav > ul > li").removeClass("active");//首先移除全部的active
		  $(this).addClass("active");//选中的添加acrive
	  });
	  
	  openNewTab('21','用户管理','${ctx}/admin/usermanage');
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

</script>

</head>
<body>

<div class="frame-nav">
  <ul class="mailLiast clearfix">
    <li class="active" onclick="openNewTab('21','用户管理','${ctx}/admin/usermanage');">
      <div class="box"><span>用户管理</span> </div>
    </li>
    <li onclick="openNewTab('22','用户流水','${ctx}/admin/userstatement');">
      <div class="box drafts"><span>用户流水</span> </div>
    </li>
    <li onclick="openNewTab('23','邮件动态','${ctx}/admin/mailtrend');">
      <div class="box sent"><span>邮件动态</span> </div>
    </li>
    <li onclick="openNewTab('24','综合查询','${ctx}/admin/integratequery');">
      <div class="box trash"><span>综合查询</span> </div>
    </li>
  </ul>
  <div class="ltLine"></div>
<%--   
  <ul class="mailLiast clearfix">
    <li onclick="openNewTab('25','系统设置','${ctx}/mail/homepage');">
      <div class="box"><span>系统设置</span> </div>
    </li>
  </ul> 
 --%>
</div>

<div id="divContent" class="content"></div>

</body>
</html>