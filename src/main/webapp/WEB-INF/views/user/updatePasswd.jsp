<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>快传系统-修改密码</title>
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
	<div class="frame-main">

		<div class="loginMain" id="setPasswordDiv">
			<div class="foundMain clearfix">
				<div class="foundleft">
					<div class="foundhd">
						<h5>设置新密码</h5>
					</div>
					<form class="form-horizontal" id="updatePwdForm" method="post">
						<div class="control-group">
							<label class="control-label">原始密码：</label>
							<div class="controls">
								<input type="password" id="oldPwd" name="oldPwd"
									placeholder="请输入6-12位数字、字母组合的密码" class="required">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">新密码：</label>
							<div class="controls">
								<input type="password" id="newPwd" name="newPwd"
									placeholder="请输入6-12位数字、字母组合的密码" class="required">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">确定新密码：</label>
							<div class="controls">
								<input type="password" id="reNewPwd" name="reNewPwd"
									placeholder="请再次输入新密码" class="required">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"></label>
							<div class="controls">
								<button type="button" class="btn btn-primary" id="btnUpdatePwd">确定</button>
							</div>
						</div>
					</form>
				</div>
				<div class="foundrt"></div>
			</div>
		</div>

		<div class="frame-main" style="display: none;" id="updateSucc">
			<div class="sccontent clearfix">
				<img src="${ctx}/static/images/icn-success.gif">
				<div class="sctxt">
					<h4>您的密码已经修改成功</h4>
					<p class="txt01">此邮件发送成功。</p>
					<p class="txt02">
						<a href="#">请打开此邮件</a> <a href="">查看修改状态 </a>
					</p>
					<p>
						<button type="button" class="btn btn-primary"
							onclick="parent.openNewTab('11','收件柜','${ctx}/mail/inbox/')">返回邮箱首页</button>
					</p>
				</div>
			</div>
		</div>

	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#oldPwd").focus();
			var validator = $("#updatePwdForm").validate({
				rules : {
					newPwd : {
						required : true,
						minlength : 6,
						rangelength : [6, 12 ]
					},
					reNewPwd : {
						required : true,
						minlength : 6,
						equalTo : "#newPwd"
					}
				}
			});

			//绑定修改密码事件
			$("#btnUpdatePwd").click(function(e) {
				if (validator.form()) {
					doUpdatePwd();
				}
			});

		});

		function doUpdatePwd() {
			var oldPwd = $("#oldPwd").val();
			var newPwd = $("#newPwd").val();
			var reNewPwd = $("#reNewPwd").val();

			var url = '${ctx}/user/updatepwd';
			$.ajax({
				url : url,
				type : "post",
				dataType : "json",
				data : {oldPwd:oldPwd,newPwd:newPwd,reNewPwd:reNewPwd},
				success : function(result) {
					var jsonObj = eval(result);
					if (jsonObj.code == 100) {
						$("#oldPwd").after("<span for=\"oldPwd\" class=\"error\">"+jsonObj.message+"</span>");
					} else {
						$("#setPasswordDiv").hide();
						$("#updateSucc").show();
					}
				}
			});
		}
	</script>
</body>
</html>