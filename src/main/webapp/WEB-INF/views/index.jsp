<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.dayang.suyou.constants.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>快传系统-欢迎登陆</title>
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
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"type="text/javascript"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#username").focus();
		$("#loginForm").validate();
		
		$("#username").val($.cookie("username")); 
		$("#password").val($.cookie("password"));
		$("#remember").prop("checked",$.cookie("rmbUser"));
	});
	
	function checkRemember()
	{
		var isRemem=$("#remember").is(':checked');
		if(isRemem){
			var username = $("#username").val(); 
			var password = $("#password").val(); 
			$.cookie("rmbUser", "true", { expires: 7 }); //存储一个带7天期限的cookie 
			$.cookie("username", username, { expires: 7 }); 
			$.cookie("password", password, { expires: 7 }); 
		}
		else{
			$.cookie("rmbUser", "false", { expire: -1 }); 
			$.cookie("username", "", { expires: -1 }); 
			$.cookie("password", "", { expires: -1 }); 
		}
	}

    if(top != self){
        if(top.location != self.location){
        	top.location=self.location; 
        }
    }
    
    //if (window != top) top.location.href = location.href; 
	
</script>

</head>
<body>

	<div class="head">
		<div class="top">
			<%-- <a href="${ctx}/user/index" class="logo"><img src="${ctx}/static/images/dayang_logo.gif" alt="快传系统logo"/></a> --%>
		</div>
	</div>
	
	<div class="loginWrap">
		<div class="loginMain">

			<div class="logLt">
				<img src="${ctx}/static/images/loginbg.jpg" width="550px"
					height="360px">
			</div>
			<form class="form-login" id="loginForm" action="${ctx}/user/login" method="post" onsubmit="return checkRemember();">
				<div class="logContent">
					<h4>欢迎登录快传系统</h4>
					
					<%
					String error = (String) request.getAttribute(Constants.LOGIN_FAILURE_ERROR_KEY);
					if(error != null){
					%>
						<div class="alert alert-error" style="margin-top: -20px">
							<button class="close" data-dismiss="alert">×</button>登录失败，请重试.
						</div>
					<%
					}
					%>
					
					<div class="group clearfix" style="margin-top: 20px">
						<label class="logLabel">用户名：</label> <input type="text"
							id="username" name="username" placeholder="用户名"
							class="required" />
					</div>

					<div class="group clearfix">
						<label class="logLabel">密码：</label> <input type="password"
							id="password" name="password" placeholder="密码"
							class="required" />
					</div>

					<div class="logGroup">
						<label class="floatleft"><input type="checkbox" id="remember" name="remember" />记住密码</label> 
						<a href="${ctx}/user/findpwd" target="_blank">找回密码？</a>
					</div>

					<div class="logBtn clearfix">
						<button type="submit" class="btn btn-primary">登录</button>
						<button type="button" class="btn btn-default" onclick="window.location.href='${ctx}/user/register'">注册</button>
					</div>

				</div>
			</form>
		</div>
	</div>

	<div class="clearfix otdownmain">
		<div class="otdown">
			<a href="#" class="downpic"><span class="iconwd"></span>Windows</a> 
			<a href="#" class="downpic"> <span class="iconad"></span>Android</a> 
			<a href="#" class="downpic"> <span class="iconin"></span>iPhone</a> 
			<a href="#" class="downpic"> <span class="iconid"></span>iPad</a> 
			<a href="#" class="downpic"> <span class="iconwp"></span>WP</a> 
			<a href="#" class="downpic"> <span class="icontb"></span>同步盘</a>
		</div>
	</div>
	<div class="footer">copyright@suyou</div>

</body>
</html>