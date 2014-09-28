<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>快传系统-用户注册</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />
<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon" />
<link href="${ctx}/static/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctx}/static/styles/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/styles/ucloud.css" rel="stylesheet">
<link href="${ctx}/static/styles/register.css" rel="stylesheet">
<link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />

<script src="${ctx}/static/jquery/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery/jquery.cookie.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#username").focus();
		$("#loginForm").validate();
	});
</script>

</head>
<body>
	<div class="reg-top">
		<div class="reg-top-main">
			<div class="pull-left">
			<span class="reg-header-text">快传系统</span>
			<span class="v-separator"></span> 
			<span class="reg-header-text">注册</span>
			</div>
			<div class="pull-right">
				<a href="${ctx}/user/index">登录</a>
			</div>
		</div>
	</div>

	<div id="view-container">
		<div>
			<div class="reg-main">
				<div id="reg-timeline-container">
					<div>
						<ul class="reg-timeline nav nav-tabs">
							<li>
								<div class="hr active" id="navStep1"></div> 
								<img src="${ctx}/static/images/register_step_1.png" id="imgStep1"><br>
								<span>账号信息</span>
							</li>
							<li>
								<div class="hr" id="navStep2"></div> 
								<img src="${ctx}/static/images/register_step_2.png" id="imgStep2"><br>
								<span>套餐选择</span>
							</li>
							<li>
								<div class="hr" id="navStep3"></div> 
								<img src="${ctx}/static/images/register_step_3.png" id="imgStep3"><br>
								<span>激活账号</span>
							</li>
							<li>
								<div class="hr" id="navStep4"></div> 
								<img src="${ctx}/static/images/register_step_4.png" id="imgStep4"><br>
								<span>完成</span>
							</li>
						</ul>
					</div>
				</div>
				
				<form  id="registerForm" method="post">
					<div class="reg-main-body" style="display: block;" id="step1">
						<table align="center">
							<tbody>
							
								<tr>
									<td class="text-right"><label for="user_name">用户名<span class="asterisk">*</span></label></td>
									<td><input type="text" name="username" id="username" placeholder="请输入用户名" class="required"></td>
								</tr>
								
								<tr>
									<td class="text-right"><label for="user_email">邮箱<span class="asterisk">*</span></label></td>
									<td><input type="text" name="useremail" id="useremail" placeholder="请输入格式正确带@的邮箱地址" class="required email"></td>
								</tr>
								
								<tr>
									<td class="text-right"><label for="user_pwd">设定密码<span class="asterisk">*</span></label></td>
									<td>
									<input type="password" name="userpasswd" id="userpasswd" placeholder="请输入6-12位数字、字母组合的密码" class="required">
									</td>
								</tr>
								<tr>
									<td class="text-right"><label for="user_repasswd">确认密码<span class="asterisk">*</span></label></td>
									<td class="pb25"><input type="password"  name="userrepasswd" id="userrepasswd" placeholder="请再次输入新密码" class="required"></td>
								</tr>

								<tr>
									<td></td>
									<td>
									<button class="btn btn-primary" type="button" id="btnAgreeReg">同意注册协议并提交</button>
									<a href="/account/cas/useragreement" target="_blank"class="reg-protocol">《快传用户注册协议》</a>
									</td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="reg-main-body reg-main-activate-body"  style="display: none;" id="step2">
							<div class="combo-content">
							<div class="combo-title clearfix">
								<span><em>请选择你要购买的容量套餐:</em></span>
							</div>
							<ul class="combo-list clearfix">
								<li class="list-item selected">
									<div class="combo-volume">
										<span class="pay-discount"></span><span class="combo-many" style="height: 80px;">每月10G空间，5G传输流量免费用</span>
									</div>
									<div class="combo-type">套餐1：个人标准用户</div>
									<div class="combo-pay-choice clearfix">
										<p class="clearfix no-discount">
											<input style="margin-top: 2px;" type="radio" name="pay" class="products" value="1"> 
											<label for="" hidefocus="true">
											<span class="paym">免费</span>
											</label>
										</p>
									</div>
								</li>

								<li class="list-item">
									<div class="combo-volume">
										<span class="pay-discount"></span><span class="combo-many" style="height: 80px;">10G的传输流量，赠送额外20G空间</span>
									</div>
									<div class="combo-type">套餐2：个人用户流量包</div>
									<div class="combo-pay-choice clearfix">
										<p class="clearfix no-discount">
											<input style="margin-top: 2px;" type="radio" name="pay" class="products" value="2"> 
											<label for="" hidefocus="true">
											<span class="paym"><em class="moneym">30</em>&nbsp;元/月&nbsp;</span>
											</label>
										</p>
									</div>
								</li>

								<li class="list-item">
									<div class="combo-volume">
										<span class="pay-discount"></span><span class="combo-many" style="height: 80px;">每月2T空间，300G传输流量，免费试用一个月</span>
									</div>
									<div class="combo-type">套餐3：企业客户A</div>
									<div class="combo-pay-choice clearfix">
										<p class="clearfix no-discount">
											<input style="margin-top: 2px;" type="radio" name="pay" class="products" value="3"> 
											<label for="" hidefocus="true">
											<span class="paym"><em class="moneym">2500</em>&nbsp;元/月&nbsp;</span>
											</label>
										</p>
									</div>
								</li>


								<li class="list-item" style="margin-right: 0">
									<div class="combo-volume">
										<span class="pay-discount"></span><span class="combo-many" style="height: 80px;">每月5T空间，800G传输流量，免费试用一个月</span>
									</div>
									<div class="combo-type">套餐4：企业用户B</div>
									<div class="combo-pay-choice clearfix">
										<p class="clearfix no-discount">
											<input style="margin-top: 2px;" type="radio" name="pay" class="products" value="4"> 
											<label for="" hidefocus="true">
											<span class="paym"><em class="moneym">4000</em>&nbsp;元/月&nbsp;</span>
											</label>
										</p>
									</div>
								</li>
							</ul>
<!-- 						
							<div class="subtotal">
								<div class="sub01">
								        购买容量：<span class="f18 c00be03">50G</b></span>
								       支付金额：<b>30</b>
								       有效期：2014-6-13 至 2015-6-13
								</div>
						    </div> 
-->
						</div>
						<div class="logBtn" align="center">
							<button type="button" class="btn btn-primary" id="btnPackage" onclick="agreePackage()">下一步</button>
						</div>
						
					</div>

					<div class="reg-main-body reg-main-activate-body" style="display: none"  id="step3">
					
					   <div>
						<p class="text-center mb5">激活邮件发送至您的邮箱：<a class="dt mail" href="http://mail.qq.com" id="mailaddr"></a>
						</p>
						<p class="text-center activ-tips">提示：尊敬的用户，激活链接已经发送到您的注册邮箱，请你查收后进行激活，然后再登录使用</p>
						<div class="text-center">
							<button type="button" class="btn btn-primary mail" onclick="activeAccount()" id="btnActive">激活帐号</button>
						</div>
						<div class="dashed-line"></div>
						<div class="activate-email-info">
							<p class="mt25 mb15">没有收到激活邮件？</p>
							<ul class="mail_info list-decimal">
								<li>1. 请检查邮件是否在垃圾邮件或广告邮件中。</li>
								<li>2. 若您在30分钟后仍未收到激活邮件，请<a href="#" id="sendActivateMail">重发激活邮件</a>。</li>
								<li>3. 如果仍未成功，请<a href="#">联系客服</a>。</li>
							</ul>
						</div>
                      </div>
					</div>

					<div class="reg-main-body reg-main-success-body mt30" style="display: none" id="step4">
						<p class="text-center">
							<img src="${ctx}/static/images/register_success.png">
						</p>
						<h3 class="text-center mt25">帐号激活成功</h3>
						<p class="text-center mt15">感谢您注册快传，我们将为您提供最专业的服务！</p>
						<p class="text-center activ-tips">
							您的登录帐号是：<span id="userAccount"></span><br> 如果您忘记密码或丢失帐号，可通过此邮箱找回。
						</p>
						<div class="text-center mb25">
							<button type="button" class="btn btn-primary" onclick="javascript:window.location.href='${ctx}/user/login'">立即登录体验</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	<div class="clearfix" style="height:65px;"></div>
	<div class="footer">copyright@suyou</div>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#registerForm").focus();
			var validator = $("#registerForm").validate({
				rules : {
					userpasswd : {
						required : true,
						minlength : 6,
						rangelength : [6, 12]
					},
					userrepasswd : {
						required : true,
						minlength : 6,
						equalTo : "#userpasswd"
					}
				}
			});

			$("#btnAgreeReg").click(function(e) {
				var checkF=true;
				if (validator.form()) {
					//判断用户名是否存在 true:不存在
					var userName=$("#username").val();
					var userEmail=$("#useremail").val();
					
					if(checkUserNameAndEmail(userName,null)){
						$("#username").after("<span for=\"username\" class=\"error\">该用户已经存在!</span>");
						checkF=false;
					}
					
					//判断email是否绑定 true:不存在
					if(checkUserNameAndEmail(null,userEmail)){
						$("#useremail").after("<span for=\"useremail\" class=\"error\">该Email已经存在!</span>");
						checkF=false;
					}
				}else{
					return ;
				}
				
				if(checkF){
					agreeRegister();
				}else{
					return ;
				}
			});

			$("#username").blur(function(e) {
				//判断用户名是否存在 true:不存在
				var userName=$("#username").val();
				if(userName != ""){
					if(checkUserNameAndEmail(userName,null)){
						$("#username").after("<span for=\"username\" class=\"error\">该用户已经存在!</span>");
					}
				}
			});
			
			$("#useremail").blur(function(e) {
				//判断Email是否存在 true:不存在
				var userEmail=$("#useremail").val();
				if(userEmail != ""){
					if(checkUserNameAndEmail(null,userEmail)){
						$("#useremail").after("<span for=\"useremail\" class=\"error\">该Email已经注册过!</span>");
						checkF=false;
					}
				}
			});
			
		});
		
		function agreeRegister(){
			$("#step1").hide();
			$("#step2").show();
			activeNav("navStep2","imgStep2","register_step_2_active.png");
		}
		
		function agreePackage(){
			$("#step2").hide();
			$("#step3").show();
			$("#mailaddr").text($("#useremail").val());
			activeNav("navStep3","imgStep3","register_step_3_active.png");
		}
		
		function activeAccount(){
			//activeNav("navStep4","imgStep4","register_step_4_active.png");
			registerAccount();
		}
		
		function activeNav(objId,imgObj,imgSrc){
			$("#"+objId).addClass("active");
			$("#"+imgObj).prop("src","${ctx}/static/images/"+imgSrc);
		}
		
		function registerAccount(){
			var username=$("#username").val();
			var useremail=$("#useremail").val();
			var userrepasswd=$("#userrepasswd").val();
			var chargetype=$('input[name="pay"]:checked').val();
			
		    $.ajax({
		       url:"${ctx}/user/register",
		       data:{username:username,useremail:useremail,userrepasswd:userrepasswd,chargetype:chargetype},
		       type:'post',
		       dataType:'json',
		       success:function(result){
					var jsonObj = eval(result);
					if (jsonObj.code == 100) {
						$("#useremail").after("<span for=\"oldPwd\" class=\"error\">"+jsonObj.message+"</span>");
					} else {
						$("#step3").hide();
						$("#step4").show();
						$("#userAccount").text(username);
					}
		      }
		    });
		}
		
		
		function checkUserNameAndEmail(username,email){
			 var isAvailableUser="";
			 var url='${ctx}/user/checkuser';
			 $.ajax({
				 url : url,
				 type : "post",
				 dataType : "json",
				 data: {username:username,email:email},
				 async:false,
				 success : function(result) {
					 var jsonObj = eval(result);
					 isAvailableUser=jsonObj.availableUser;
				 }
			});
			return isAvailableUser;
		}
		
	</script>

</body>
</html>