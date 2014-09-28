<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.dayang.suyou.constants.Constants"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户管理</title>

</head>
<body>

<div class="frame-main">
<!--    
   <button type="submit" class="btn btn-primary btn-sm">启用</button>
   <button type="submit" class="btn btn-primary btn-sm">禁用</button> 
-->
   <div class="mailtable" style="top:20px">
    <div style="font-size: 14px;font-weight: bold;"><span>用户注册总数:${usersCount}人</span> <span>&nbsp;&nbsp;总空间:${spaceTotal}</span><span>&nbsp;&nbsp;已用空间 :${spaceUsedTotal}</span></div>
    <table class="table">
      <thead>
        <tr>
          <th width="22"><input type="checkbox"/></th>
          <th>用户名</th>
          <th>注册时间</th>
          <th>套餐类型</th>
          <th>总空间</th>
          <th>已用空间</th>
          <th>总流量</th>
          <th>已使用流量</th>
          <th>状态</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
       <c:forEach items="${userManageList.content}" var="userManage">
        <tr>
          <td width="22"><input type="checkbox"/></td>
          <td>${userManage.name}</td>
          <td>${userManage.createtime}</td>
          <td>${userManage.chargetypeidStr}</td>
          <td>${userManage.spacetotalStr}</td>
          <td>${userManage.spaceusedStr}</td>
          <td>${userManage.downloadflowtotalStr}</td>
          <td>${userManage.downloadflowusedStr}</td>
          <td>
          <c:if test="${userManage.status==0}"> 
	                       正常
		  </c:if>
		  <c:if test="${userManage.status==1}"> 
	                       封停
		  </c:if>
          </td>
          <td>
          <c:if test="${userManage.status==0}"> 
	          <button type="submit" class="btn btn-primary btn-sm" onclick="userOperation(${userManage.id},1)">禁用</button> 
		  </c:if>
		  <c:if test="${userManage.status==1}"> 
	          <button type="submit" class="btn btn-primary btn-sm" onclick="userOperation(${userManage.id},0)">启用</button>
		  </c:if>
          </td>
        </tr>
       </c:forEach>
      </tbody>
    </table>
    
    <div class="pagination">
		<tags:pagination page="${userManageList}" paginationSize="10"/> 
    </div>
    
  </div>
</div>

<script type="text/javascript">

function userOperation(userId,status){
	if(window.confirm('确定要这样操作吗？')){
	   $.ajax({
	       url:"${ctx}/admin/user/status",
	       data:{userId:userId,status:status},
	       type:'post',
	       dataType:'json',
	       success:function(result){
	    	   if(result.success == "true"){
	    		   window.location.reload();
	    	   }
	      }
	    });
	}
}

</script>

</body>
</html>