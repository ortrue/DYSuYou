<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.dayang.suyou.constants.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>首页</title>
<script src="${ctx}/static/tab-plugin/mainTab.js" type="text/javascript"></script>
<script src="${ctx}/static/js/math.uuid.js" type="text/javascript"></script>
<script type="text/javascript">

function doNewMail(){
	//1.生成new增发件ID
	var newMailUUID=Math.uuid();
	parent.openNewTab(newMailUUID,'发件','${ctx}/mail/newmail/0/1/'+newMailUUID);
}

</script>

</head>
<body>

<div class="frame-main">
  <div class="sccontent clearfix">
  <img src="${ctx}/static/images/icn-success.gif">
   <div class="sctxt">
    <h4>您的邮件已发送</h4>
    <p class="txt01">此邮件发送成功，并已保存到“已发送”文件夹。</p>
     <p class="txt02"><a href="javascript:parent.openNewTab('11','收件柜','${ctx}/mail/inbox')">查看此邮件</a></p> 
     <p>
      <button type="button" class="btn btn-primary" onclick="parent.openNewTab('11','收件柜','${ctx}/mail/inbox');parent.closeTab('18');">返回邮箱首页</button>
      <button type="button" class="btn btn-default" onclick="doNewMail();parent.closeTab('18');">再写一封</button>
     </p>
   </div>
  </div>
</div>

</body>
</html>