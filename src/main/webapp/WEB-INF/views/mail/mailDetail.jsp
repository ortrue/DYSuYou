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
			<div class="fjmain">
			
			<div class="mailmsgtop">
				<div class="mailtitle">
					<h1 class="py0">主题：<span>${mailDetail.title}</span></h1>
				</div>
				<div class="mailow clearfix">
					<ul class="clearfix">
						<li>
						    <span class="spanlabel">发件人：</span>
							<div class="spancontent">
								<span class="spanmsg"><b>${mailDetail.sendername}</b>&#8250;</span>
							</div>
						</li>
						<li>
						    <span class="spanlabel">收件人：</span>
							<div class="spancontent">
								<span class="spanmsg"><b>${mailDetail.recipients}</b>&#8250;</span>
							</div>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="col-md-12 ">
				<table id="fileInfo" style="margin-top: -10px;" class="table table-striped table-hover text-left">
					<thead>
						<tr>
							<th class="span4">文件名</th>
							<th class="span2">大小</th>
							<th class="span6">进度</th>
							<th class="span6">操作</th>
						</tr>
					</thead>
					<tbody id="fsUploadProgress">
					<tr id="o_18uvnenr1m8jn9m1b8718elr969" class="progressContainer"><td class="progressName">green-3.5.0.zip</td><td class="progressFileSize">1.2 MB</td><td><div class="info"><div class="progress progress-striped"><div class="progress-bar progress-bar-info" role="progressbar" aria-valuemax="100" aria-valuenow="0" aria-valuein="0" style="width: 0%;"><span class="sr-only">1.2 MB</span></div></div><a href="#" class="progressCancel"></a><div class="status text-left">等待...</div></div></td><td><div id="opBox-o_18uvnenr1m8jn9m1b8718elr969"><span id="opDel-o_18uvnenr1m8jn9m1b8718elr969"><a>删除</a></span></div></td></tr></tbody>
				</table>
			</div>
			
			<div>
				<textarea style="width:100%;height:300px;visibility:hidden;" id="mailContent" name="mailContent" class="input-medium required">${mailDetail.content}</textarea>
			</div>
			
			 <c:if test="${mailDetail.attaCounts != 0}"> 
				<div class="fjhd">
					<i class="iconfj pull-left"></i>${mailDetail.attaCounts}个附件 <em class="c999 f12">${mailDetail.attaSizeTotal}</em>
				</div>
			 </c:if>
				<div class="fjment clearfix">
					<ul>
						<c:forEach items="${mailDetail.attachmentDetail}" var="attaDetail">	
      					<li>
							<div class="fjmsg">
								<a href="#" class="fjimg"><img src="${attaDetail.imagedata}"></a>
								<p class="fjname">${attaDetail.resname}</p>
								<p>
									<em class="c999 f12">${attaDetail.shortSize}</em>
								</p>
							</div>
							<p class="fjdown">
								<a href="javascript:void(0);" onclick="attaFileDownload('${mailDetail.id}','${attaDetail.id}','${attaDetail.resname}')">下载</a>
							</p>
						</li>
						</c:forEach>
						
					</ul>
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
	  		themeType : 'example1',
	  		urlType:'domain',
	  		afterBlur: function(){this.sync();}
	  	});
	  });
});


function attaFileDownload(mailId,attaId,filename){
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
						 //var downloadURL='${ctx}/mail/attachment/download?dlink='+encodeURIComponent(jsonObj.Url,"UTF-8")+"&filename="+filename;
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

</script>
</body>
</html>