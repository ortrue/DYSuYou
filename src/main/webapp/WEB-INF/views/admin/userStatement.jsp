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
	<div align="left" style="float: left;">
		<div>
			<label>用户：</label>
			<input type="text" id="userName" name="search_userName" style="width: 100px;height: 20px;" autoComplete="off" placeholder="请输入用户名称" value="${param.search_userName}">
		</div>
		<div id="wordsDiv" style="position:absolute;z-index: 9999">
			<select id="words" multiple="multiple" size="10"></select>	
		</div>
	</div>
<%-- 	
	<div class="btn-group">
      <button type="button" class="btn btn-default">--请选择用户--</button>
      <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
        <span class="caret"></span>
        <span class="sr-only"></span>
      </button>
      <ul class="dropdown-menu" role="menu">
         <c:forEach items="${usersAllList}" var="usersObj">
           <li><a href="#">${usersObj.name}</a></li>
         </c:forEach>
      </ul>
    </div> 
--%>
    <label>开始时间：</label>
    <div class="input-append date form_datetime">
    <input type="text" readonly class="input-medium" placeholder="请输入开始时间" id="startTime" name="search_startTime" value="${param.search_startTime}">
    <span class="add-on"><i class="icon-th"></i></span>
    </div>
    <label>结束时间：</label>
    <div class="input-append date form_datetime">
    <input type="text" readonly class="input-medium" placeholder="请输入结束时间" id="endTime" name="search_endTime" value="${param.search_endTime}">
    <span class="add-on"><i class="icon-th"></i></span>
    </div>
    <button type="submit" class="btn btn-primary btn-sm">查询</button>
    <div style="clear: both;"></div>
   </form>

  <div class="mailtable" style="top:50px">
  <div style="margin: 0 0 0 20px;"><span>用户套餐:<b>${UserInfo.chargetypeidStr }</b></span> <span>总空间:<b>${UserInfo.spacetotalStr }</b></span> <span>已用空间:<b>${UserInfo.spaceusedStr }</b> </span> <span>总流量:<b>${UserInfo.downloadflowtotalStr }</b></span> <span>已用流量:<b>${UserInfo.downloadflowusedStr }</b></span></div>
    <table class="table">
      <thead>
        <tr>
          <th width="22"><input type="checkbox"/></th>
          <th>邮件标题</th>
          <th>时间</th>
          <th>操作</th>
          <th>流量</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach items="${UserStatementList.content}" var="UserStatement">
        <tr>
          <td width="22"><input type="checkbox"/></td>
          <td>${UserStatement.title}</td>
          <td>${UserStatement.gmtTime}</td>
          <td>${UserStatement.opType}</td>
          <td>${UserStatement.flowTotalStr}</td>
        </tr>
        </c:forEach>
      </tbody>
    </table>
    
    <div class="pagination">
		 <tags:pagination page="${UserStatementList}" paginationSize="10"/>
    </div>
    
  </div>
</div>
<script type="text/javascript">
$(document).ready(function(){

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
	
$("#wordsDiv").hide();
// 给input输入框注册keyup键盘点击事件
$("#userName").bind("keyup",
	function() {
		// 获取输入的值
		var keyVal = $(this).val();
		// 判断如果输入框为空时，清空隐藏select并返回，不再执行以下代码
		if ("" == keyVal) {
			// 清空并隐藏select框
			$("#words").empty(); // 对象链式操作，jQuery的特性
			$("#wordsDiv").hide();
			return;
		}
		// 发送ajax请求
	    $.ajax({
	        url:"${ctx}/admin/userall/",
	        data:{userName : keyVal},
	        type:'post',
	        dataType:'json',
	        success:function(result){
			// 清空
			$("#words").empty();
			var opt =null;
	 		$(result).each(function(i,val) { 
				// 得到具体的user对象
				opt += "<option>"+val.name+"</option>";
	 		});
	 		$("#words").append(opt);
	 		$("#wordsDiv").show();
	       }
	     });
	
		// 为select框注册双击事件
		$("#words").bind("dblclick", function() {
			// 为input框设值
			$("#userName").val($(this).val());
			$("#wordsDiv").hide();
		});
	
		// 为select框注册回车事件
		$("#words").bind("keyup", function(event) {
			// 按键13为回车键
			if (event.which == 13) {
				$("#userName").val($(this).val());
				$("#wordsDiv").hide();
			}
		});
	});
});
</script>
</body>
</html>