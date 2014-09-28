<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.dayang.suyou.constants.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>邮件详情</title>

<link href="${ctx}/static/kindeditor/themes/default/default.css" rel="stylesheet" />
<link href="${ctx}/static/kindeditor/editor-themes.css" rel="stylesheet" />
<link href="${ctx}/static/styles/jquery.jdownload.css" rel="stylesheet" />

<script src="${ctx}/static/kindeditor/kindeditor-min.js" charset="utf-8"></script>
<script src="${ctx}/static/jquery/jquery-1.9.1.min.js" charset="utf-8"></script>
<script src="${ctx}/static/js/jquery.fileDownload.js" charset="utf-8"></script>
<script src="${ctx}/static/js/math.uuid.js" type="text/javascript"></script>
<style type="text/css">

.spantext01{
font-size: 14px;
color: #555;
border-radius: 4px;
font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
font-weight: normal;
cursor: auto;
margin-top: 5px;
margin-left: 0px;
padding: 4px;
}

</style>
</head>
<body>

	<div class="frame-main">
		<div class="mail-header">
			<div class="toolbaritem">
				<button type="button" class="btn btn-default">
					<i class="icon-trash mr5"></i> 删除
				</button>
			</div>
			<div class="toolbaritem">
				<button type="button" class="btn btn-default" onclick="parent.openNewTab('fw'+${mailDetail.id},'转发:${mailDetail.title}','${ctx}/mail/newmail/${mailDetail.id}/${fwMailType}/Math.uuid()');">
				<i class="icon-share mr5"></i>转发
				</button>
			</div>
		</div>
		
		<div class="mailtable">
	    <div class="wlMain" style="right: 80px!important">
	      <div class="wlContent" style="width: 100%">

	          <div class="wlHeader">
	            <div class="wlcol">
	              <label class="wllabel" style="left: 0">发件人：</label>
	              <div>
	                <span class="spantext01">${mailDetail.sendername}</span>
	              </div>
	              <div class="wlinfo clearfix"></div>
	            </div>
	          
	            <div class="wlcol">
	              <label class="wllabel" style="left: 0">收件人：</label>
	              <div>
	                <span class="spantext01">${mailDetail.recipients}</span>
	              </div>
	              <div class="wlinfo clearfix"></div>
	            </div>
	            
	            <div class="wlcol">
	              <label class="wllabel" style="left: 0">主&nbsp;&nbsp; 题：</label>
	              <div class="clearfix">
	                <span class="spantext01">${mailDetail.title}</span>
	              </div>
	            </div>
	            
	            <div class="clearfix">
	             <c:if test="${mailDetail.attaCounts != 0}"> 
					<div style="padding: 5px 0 5px 0">
						<i class="iconfj pull-left"></i>${mailDetail.attaCounts}个附件 <em class="c999 f12">${mailDetail.attaSizeTotal}</em>
					</div>
			     </c:if>
	            </div>
	          </div>
	          
	          <div class="col-md-12 ">
				<table id="fileInfo" class="table table-striped table-hover text-left" style="margin-top: 10px;" >
					<thead>
						<tr>
							<th class="span4">文件名</th>
							<th class="span2">大小</th>
							<th class="span6">操作</th>
						</tr>
					</thead>
					<tbody id="fsUploadProgress">
					<c:forEach items="${mailDetail.attachmentDetail}" var="attaDetail">	
					 <tr class="progressContainer" style="height: 5px">
					  <td class="progressName">
						<img src="${attaDetail.imagedata}">
						<p class="fjname">${attaDetail.resname}</p>
					  </td>
					  <td class="progressFileSize">${attaDetail.shortSize}</td>
					  <td><a href="javascript:void(0);" onclick="attaFileDownload('${mailDetail.id}','${attaDetail.id}','${attaDetail.resname}','${mailDetail.attaSizeTotalByte}')">下载</a></td>
					 </tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
	          
	          <div class="wltextarea">
	            <textarea style="width:100%;height:250px;visibility:hidden;" id="mailContent" name="mailContent" class="input-medium required">${mailDetail.content}</textarea>
	          </div>
	    
	      </div>
	    </div>
	  </div>

	</div>

<script type="text/javascript">
$(document).ready(function(){
	 //1.加载文本编辑器
	  KindEditor.ready(function(K) {
	  	editor = K.create('textarea[name="mailContent"]', {
	  		items: [],
	  		urlType:'domain',
	  		afterBlur: function(){this.sync();}
	  	});
	  });
});


function attaFileDownload(mailId,attaId,filename,userflow){
	 var url='${ctx}/mail/attachment/download/getlink';
	 $.ajax({
		 url : url,
		 type : "post",
		 dataType : "json",
		 data: {mailid:mailId,resid:attaId},
		 success : function(result) {
     		 var jsonObj = eval(result);
			 if(jsonObj.Code==200){
				 alert("获取url失败!");
			 }else if(jsonObj.Code==202){
				 alert("请稍后下载!");
			 }else if(jsonObj.Code==203){
				 alert("下载流量不足无法下载,请充值!");
			 }else if(jsonObj.Code==201){
				 if(jsonObj.Url !=""){
					 if(jsonObj.Blockorfile !=1){
						 alert("此附件上传还未完成,使用网页版无法下载,请使用客户端软件进行下载!");
					 }else{
						 updateUserFlow(userflow);//下载之前向服务端报告下载流量
					 	 doFileDownload(jsonObj.Url);
					 }
				 }
			 }
		 }
	});
}

function doFileDownload(url)
{
	$.fileDownload(url);
}

function updateUserFlow(userflow){
	 var url='${ctx}/mail/attachment/download/updateuseflow';
	 $.ajax({
		 url : url,
		 type : "post",
		 dataType : "json",
		 data: {userflow:userflow},
		 success : function(result) {
		 }
	});
}

</script>

</body>
</html>