<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="cn.com.dayang.suyou.constants.Constants"%>
<%@ page import="cn.com.dayang.suyou.vo.SysUser"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	SysUser sysUser=(SysUser)request.getSession().getAttribute(Constants.SESSION_TOKEN);
%>

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner" style="border-bottom:2px solid #10619a;box-shadow:1px 0 3px rgba(0,0,0,0.8)">
		<div class="container-fluid">
			<a class="brand" href="#">快传</a>
			<div class="nav-collapse collapse pull-right">
				<ul class="nav">
					<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">
					<i class="iconUser"></i>
					<%
					if(sysUser!=null){
						out.print(sysUser.getUsername());
					}
					%>
					<b class="caret"></b>
					</a>
					<ul class="dropdown-menu">
						<li><a href="javascript:openNewTab('10','修改密码','${ctx}/user/updatepwd');">修改密码</a></li>
						<li class="divider"></li>
						<li><a href="${ctx}/user/logout">退出</a></li>
					</ul>
					</li>
					<% if(sysUser!=null && Constants.SYSTEM_CODE_101.equals(sysUser.getCode())){ %>
					<li>
						<span class="line">|</span>
						<a href="${ctx}/admin" target="_blank">管理中心</a>
					</li>
					<li>
						<span class="line">|</span>
						<a href="${ctx}/mail" target="_blank">首页</a>
					</li>
					<% } %>
				</ul>
			</div>
		</div>
		
		<div id="divTab" ></div> 

	</div>
</div>