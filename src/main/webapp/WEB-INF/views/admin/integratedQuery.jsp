<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.dayang.suyou.constants.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>首页</title>
<link href="${ctx}/static/bootstrap/2.3.2/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<script src="${ctx}/static/bootstrap/2.3.2/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/2.3.2/js/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
</head>
<body>

<div class="frame-main">
  <form class="form-inline ml20 mt10">
	  <label>开始时间：</label>
	  <div class="input-append date form_datetime">            
	    <input size="16" type="text" readonly placeholder="请输入开始时间" id="startTime" name="search_startTime" value="${param.search_startTime}">
	    <span class="add-on"><i class="icon-th"></i></span>
	  </div>
	  <label>结束时间：</label>
	  <div class="input-append date form_datetime">            
	    <input size="16" type="text" readonly placeholder="请输入结束时间" id="endTime" name="search_endTime" value="${param.search_endTime}">
	    <span class="add-on"><i class="icon-th"></i></span>
	  </div>
	  <button type="submit" class="btn btn-primary btn-sm">查询</button>
   </form>

  <div class="mailtable" style="top:50px">
  <div><span>注册人数：${UserTotal} &nbsp;</span><span>活跃人数：${ActiveUser} &nbsp;</span> <span>下载总量：${DownloadTotal} &nbsp;</span></div>
    <table class="table">
      <thead>
        <tr>
          <th width="22"><input type="checkbox"/></th>
          <th>用户</th>
          <th>邮件数</th>
          <th>附件数</th>
          <th>使用空间</th>
          <th>上传总流量</th>
          <th>下载总流量</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach items="${integrateQueryList.content}" var="integrateQuery">
        <tr>
          <td width="22"><input type="checkbox"/></td>
          <td>${integrateQuery.name}</td>
          <td>${integrateQuery.mailsCount}</td>
          <td>${integrateQuery.attaCount}</td>
          <td>${integrateQuery.spaceusedStr}</td>
          <td>${integrateQuery.mailsTotalStr}</td>
          <td>${integrateQuery.attaTotalStr}</td>
        </tr>
	  </c:forEach>
      </tbody>
    </table>
    
    <div class="pagination">
		 <tags:pagination page="${integrateQueryList}" paginationSize="10"/>
    </div>
    
  </div>
</div>
<script type="text/javascript">
//加载日期控件
$('.form_datetime').datetimepicker({
    language:  'CN',
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	forceParse: 0,
	hourStep: 1,
    minuteStep: 1,//间隔分钟
    //secondStep: 30,
	showMeridian: false
});

</script>
</body>
</html>