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

<script type="text/javascript">
	$(document).ready(function() {
		$("#username").focus();
		$("#findPwdForm").validate(); 
	}); 
</script>

</head>
<body>

	<div class="head">
		<div class="top">
			<a href="index.html" class="logo">找回密码</a>
		</div>
	</div>
	
	<div class="loginWrap">
		<div class="loginMain" id="setMailDiv">
			<div class="alert mt20">
				<strong>您正在通过邮箱找回密码。</strong><br/>
			</div>
			<div class="foundMain clearfix">
				<div class="foundleft">
					<div class="foundhd">
						<h5>通过邮箱地址找回密码</h5>
					</div>
					<form class="form-horizontal" id="findPwdForm" action="${ctx}/user/findpwd" method="post">
					
						<div class="control-group">
							<label class="control-label">用户名：</label>
							<div class="controls">
								<input type="text" id="username" name ="username" placeholder="请输入用户名" class="required">
							</div>
						</div>
					
						<div class="control-group">
							<label class="control-label">邮箱地址：</label>
							<div class="controls">
								<input type="text" id="email" name="email" placeholder="请输入格式正确带@的邮箱地址" class="required email"> 
							</div>
						</div>

						<div class="control-group">
							<label class="control-label"></label>
							<div class="controls">
								<button type="submit" class="btn btn-primary">确定</button>
							</div>
						</div>
					</form>
				</div>
				<div class="foundrt"></div>
			</div>
		</div>

	</div>

	<div class="clearfix" style="height:65px;"></div>
	
	<div class="footer">copyright@suyou</div>
	
</body>
</html>