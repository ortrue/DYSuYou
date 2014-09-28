<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.dayang.suyou.constants.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>首页</title>

</head>
<body>

<div class="frame-main">
   <form class="form-inline ml20 mt10" method="get" action="${ctx}/admin/mailtrend">
    <div>
    <label>邮件标题：</label><input type="text" class="input-medium" placeholder="请输入邮件标题" id="title" name="search_title" value="${param.search_title}">
    <button type="submit" class="btn btn-primary btn-sm">查询</button>
    </div>
   </form> 

  <div class="mailtable" style="top:50px">
    <table class="table">
      <thead>
        <tr>
          <th width="22"><input type="checkbox"/></th>
          <th>标题</th>
          <th>发件人</th>
          <th>时间</th>
          <th>大小</th>
          <th>邮件动态</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach items="${mailTrendList.content}" var="mailTrend">
        <tr>
          <td width="22"><input type="checkbox"/></td>
          <td>${mailTrend.title}</td>
          <td>${mailTrend.sendUser}</td>
          <td>${mailTrend.gmtTime}</td>
          <td>${mailTrend.attachmentspaceStr}</td>
          <td>
          <a id="openDetail-${mailTrend.mailId}" onclick="openDetail('openDetail-${mailTrend.mailId}',${mailTrend.mailId})" href="#" data-toggle="popover" data-placement="left" data-html="true" title="邮件动态" data-original-title="邮件动态" data-container="body">查看动态</a>
          </td>
        </tr>
        </c:forEach>
      </tbody>
      
    </table>
    
    <div class="pagination">
		 <tags:pagination page="${mailTrendList}" paginationSize="10"/> 
    </div>
    
  </div>
</div>

<script type="text/javascript">
$(document).ready(function() {

});

function openDetail(id,mailid){
	var popoverContent="";
	popoverContent+="<table class=\"table\" style=\"border-bottom: 1px solid #c1c8d2;\">";
	$('[data-toggle="popover"]').popover("destroy");
    $.ajax({
       url:"${ctx}/admin/mailTrendDetail/"+mailid,
       data:{},
       type:'post',
       dataType:'json',
       success:function(result){
		$(result).each(function(i,val) { 
			popoverContent+="<tr>";
			popoverContent+="<td>"+(i+1)+"</td>";
			popoverContent+="<td>"+val.content+"</td>";
			popoverContent+="</tr>";
		});
		popoverContent+="</table>";
		$("#"+id).popover({'content': popoverContent}).popover('toggle');
      }
    });
}

</script>

</body>
</html>