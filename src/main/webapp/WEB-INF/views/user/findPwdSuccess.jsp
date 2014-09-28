<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>快传系统-重置密码</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />
<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon" />
<link href="${ctx}/static/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctx}/static/styles/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />

<script src="${ctx}/static/jquery/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery/jquery.cookie.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>

</head>
<body>

	<div class="head">
		<div class="top">
			<a href="index.html" class="logo">找回密码</a>
		</div>
	</div>

	<div class="loginWrap">
		<div class="frame-main" id="sendSuccDiv">
			<div class="sccontent clearfix">
				<img src="${ctx}/static/images/icn-success.gif">
				<div class="sctxt">
					<h4>密码重置成功</h4>
					<p class="txt01">您的密码已经发送到您注册时的邮箱请注意查收！</p>
					<p>
						<button type="button" class="btn btn-primary" onclick="window.location.href='${ctx}/user/index'">返回登录</button>
					</p>
				</div>
			</div>
		</div>

	</div>

	<div class="clearfix" style="height:65px;"></div>
	
	<div class="footer">copyright&copy;suyou</div>
	
</body>
</html>