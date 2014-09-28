<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.dayang.suyou.constants.Constants"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>发件</title>

<link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/zTreeStyle/myztree.css" type="text/css" rel="stylesheet" />

<link href="${ctx}/static/kindeditor/themes/default/default.css" rel="stylesheet" />
<link href="${ctx}/static/qiniu/main.css" rel="stylesheet" />
<link href="${ctx}/static/qiniu/highlight.css" rel="stylesheet" />

<script src="${ctx}/static/kindeditor/kindeditor-min.js" charset="utf-8"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
<script src="${ctx}/static/zTreeStyle/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>

<script src="${ctx}/static/js/jsMap.js" type="text/javascript"></script>
<script src="${ctx}/static/js/jsArray.js" type="text/javascript"></script>
<script src="${ctx}/static/qiniu/plupload.full.min.js" type="text/javascript"></script>
<script src="${ctx}/static/qiniu/zh_CN.js" type="text/javascript"></script>
<script src="${ctx}/static/qiniu/ui.js" type="text/javascript"></script>
<script src="${ctx}/static/qiniu/qiniu.js" type="text/javascript"></script>

</head>
<body>

	<input type="hidden" id="mailUUID" value="${mailUUID}">
	<input type="hidden" id="mailSID">
	<input type="hidden" id="uploadStatus" value="0">
	<div class="frame-main">
		<div class="mail-header">
			<button type="button" class="btn btn-primary mr5" onclick="sendMail();" id="sendMailBtnTop"><i class="icon-send"></i>发送</button>
			<button type="button" class="btn btn-default mr5" onclick="saveDraftMail();" id="saveDraftBtnTop"><i class="icon-save"></i>存待发区</button>
			<button type="button" class="btn btn-default" onclick="cancelUpload(0);">取消</button>
		</div>
		
		<div class="mailtable">
			<div class="wlMain">
				<div class="wlContent">
					<form class="form-horizontal">
						<div class="wlHeader clearfix">
							<div class="wlcol">
								<label class="wllabel">收件人：</label>
								<div class="wliptxt"><input type="text" class="wlinput" id="mailTo" name="mailTo" value="${mailInfo.recipients}"/></div>
							</div>
							<div class="wlcol">
								<label class="wllabel">主&nbsp;&nbsp; 题：</label>
								<div class="wliptxt">
									<input type="text" class="wlinput" id="mailTitle" name="mailTitle" value="${mailInfo.title}"/>
								</div>
							</div>
							
							<div class="wlinfo clearfix" id="container">
								<a class="wla" id="pickfiles" href="#"> 
								<span><i class="iconfj"></i>添加附件</span>
								</a>
							</div>
							
							<div>
								<div style="display: none" id="success" class="col-md-12">
									<div class="alert-success">队列全部文件处理完毕</div>
								</div>
								
								<div class="col-md-12 ">
									<table class="table table-striped table-hover text-left"
										style="margin-top: -10px; display: none" id="fileInfo">
										<thead>
											<tr>
												<th class="span4">文件名</th>
												<th class="span2">大小</th>
												<th class="span6">进度</th>
												<th class="span6">操作</th>
											</tr>
										</thead>
										<tbody id="fsUploadProgress">
										</tbody>
									</table>
								</div>
						    </div>
						</div>
						
						<div class="wltextarea">
							<textarea style="width:100%;height:350px;visibility:hidden;" id="mailContent" name="mailContent" class="input-medium required">${mailInfo.content}</textarea>
						</div>

<!-- 						
						<div class="wlfooter clearfix">
							<div class="wlbtn">
								<button type="button" class="btn btn-primary mr5 mt20" onclick="sendMail();" id="sendMailBtnBottom">发送</button>
								<button type="button" class="btn btn-default mr5 mt20" onclick="saveDraftMail();" id="saveDraftBtnBottom">存入草稿</button>
								<button type="button" class="btn btn-default mt20" onclick="parent.closeTab(curWinId());">取消</button>
							</div>
						</div> 
-->
					</form>
				</div>
			</div>
			<div class="columns-lr-view">
				<div class="wlcontact clearfix">
					<div class="ctheader">
						<h3>联系人</h3>
<!-- 						
						<div class="input-append">
							<input class="span2" id="" type="text">
							<button class="btn  btn-default btn-sm" type="button">搜索</button>
						</div> 
-->
					</div>
					<div class="ctList">
						<div class="ctgroup">
							<ul id="treeDemo" class="ztree"></ul>  
						</div>
					</div>
					<div class="ctfooter">
						<a href="#myGroupModal" role="" class="" data-toggle="modal">新建组</a>
					</div>
				</div>
			</div>
		</div>
		
	</div>

	<!-- Modal -->
	<div id="myGroupModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabelGroup" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabelGroup">新建组</h3>
		</div>
		<form id="userGroup" name="userGroup" class="form-horizontal">
		<div class="modal-body">
			<div class="control-group">
				<label class="control-label">联系人组名：</label>
				<div class="controls">
					<input type="text" id="userGroupName" name="userGroupName" placeholder="请输入组名" class="input-medium required">
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
			<button class="btn btn-primary" type="button" id="btnGroupAdd">确定</button>
		</div>
		</form>
	</div>

	<!-- Modal -->
	<div id="myContactModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabelContact" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabelContact">新建通讯录</h3>
		</div>
		<form id="contactForm" name="contactForm" class="form-horizontal">
		<div class="modal-body">
			<div class="control-group">
				<label class="control-label">联系人：</label>
				<div class="controls">
				<input type="hidden" id="groupTreeId" name="groupTreeId"/>
					<input type="text" id="groupContact" name="groupContact" placeholder="请输入联系人" class="input-medium required">
				</div>
			</div>
			
		    <div class="control-group">
				<label class="control-label">邮箱：</label>
				<div class="controls">
					<input type="text" id="groupEmail" name="groupEmail" placeholder="请输入格式正确带@的邮箱地址" class="input-medium required email">
				</div>
			</div>
			
		    <div class="control-group">
				<label class="control-label">手机：</label>
				<div class="controls">
					<input type="text" id="groupMobile" name="groupMobile" placeholder="请输入手机" class="input-medium required">
				</div>
			</div>

		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
			<button class="btn btn-primary" type="button" id="btnContactAdd">确定</button>
		</div>
		</form>
	</div>
	
	
	<!-- Modal -->
	<div id="senderValidModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="senderValidLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="senderValidLabel">系统提示</h3>
		</div>
		
		<form id="senderValid" name="senderValid" class="form-horizontal">
		<div class="modal-body">
			<div id="senderText"></div>
		</div>
		
		<div class="modal-footer">
			<button class="btn btn-primary" type="button" id="btnReturnEditAdd">返回编辑</button>
		</div>
		</form>
		
	</div>
	

<script type="text/javascript">
var attaFile = new Array();
var qiniuTokenMap = new Map();
var uploadedMap = new Map();

var setting = {
	view: {
		showLine: false,
		showIcon: false,
		selectedMulti: false,
		dblClickExpand: false,
		addDiyDom: addDiyDom
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		onClick: nodeOnClick
	}
};


function addDiyDom(treeId, treeNode) {
	var spaceWidth = 5;
	var switchObj = $("#" + treeNode.tId + "_switch");
	var icoObj = $("#" + treeNode.tId + "_ico");
	switchObj.remove();
	icoObj.before(switchObj);

	if (treeNode.level > 1) {
		var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
		switchObj.before(spaceStr);
	}

	if(treeNode.isParent){
		var parentObj = $("#"+ treeNode.tId + "_span");
		parentObj.after("<div class=\"i_add ml30\" onclick=\"contactModalShow("+treeNode.id+");\" data-toggle=\"modal\"></div>");
	}
}

$(document).ready(function(){
	  //1. 发件人输入框获取焦点
	  $("#mailTo").focus();
	  
	 //2.加载文本编辑器
	  var editor;
	  KindEditor.ready(function(K) {
	  	editor = K.create('textarea[name="mailContent"]', {
	  		allowFileManager : false,
	  	    allowUpload : false, //允许上传图片
	  		items: ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
	  			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
	  			'insertunorderedlist', '|', 'emoticons','|'],
	  		urlType:'domain',
	  		afterBlur: function(){this.sync();}
	  	});
	  });
	  
	  //3、绑定添加用户组验证事件
	  var validator =$("#userGroup").validate();
	  $("#btnGroupAdd").click(function(e) {
		   if(validator.form()){
			   userGroupAdd();
		   }
	  });
	  
	  //4、绑定添加联系人验证事件
	  var validator =$("#contactForm").validate();
	  $("#btnContactAdd").click(function(e) {
		   if(validator.form()){
			   contactAdd();
		   }
	  });

	  //4、绑定验证用户返回编辑事件
	  $("#btnReturnEditAdd").click(function(e) {
		  $("#senderValidModal").modal("hide");
	  });
	  
	  //6、加载联系人
	  getAllUserGroup();
});

//加载通讯录
function getAllUserGroup()
{
	 var url='${ctx}/mail/user/group/all';
	 $.ajax({
		 url : url,
		 type : "post",
		 dataType : "json",
		 data: {},
		 success : function(result) {
		     //var zNodes = JSON.stringify(result);		 
			 var treeObj = $("#treeDemo");
			 $.fn.zTree.init(treeObj, setting, result);
			 treeObj.addClass("showIcon");
		 }
	});
}

//选择收件人
function nodeOnClick(e,treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = zTree.getSelectedNodes();
	var treeNode = nodes[0];
	var contactName=treeNode.name;
	
	if(treeNode.isParent == false){
		if(contactName!=null){
			var mailto=$("#mailTo");
			var mailtoVal=mailto.val().split(";");
			var hasName=false;
			for(var i=0;i<mailtoVal.length;i++){
				if(mailtoVal[i] == contactName){
					hasName=true;
					break;
				}
			}
			if(hasName == false){
				if(mailto.val() == ""){
					mailto.val(contactName);
				}else{
					mailto.val(mailto.val()+";"+contactName);
				}
			}
		}
	}
	
	if (treeNode.level == 0 ) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.expandNode(treeNode);
		return false;
	}
	return true;
}

var uploader = Qiniu.uploader({
    runtimes: 'html5,flash,html4',    //上传模式,依次退化
    browse_button: 'pickfiles',       //上传选择的点选按钮，**必需**
    domain: 'http://rainbow.qiniudn.com/',   //bucket 域名，下载资源时用到，**必需**
    container: 'container',           //上传区域DOM ID，默认是browser_button的父元素，
  //max_file_size: '1000mb',          //最大文件体积限制
    flash_swf_url: '${ctx}/static/qiniu/Moxie.swf',  //引入flash,相对路径
    max_retries: 1000,                //上传失败最大重试次数
    dragdrop: false,                  //开启可拖曳上传
    drop_element: 'container',        //拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
    auto_start: false,                //选择文件后自动上传，若关闭需要自己绑定事件触发上传
    save_key: true,
    init: {
        'FilesAdded': function(up, files) {
            plupload.each(files, function(file) {
                $('#fileInfo').show();
                $('#success').hide();

                var fileId=file.id;
 		 		var fileName=file.name;
		 		var fileSize=file.size;

		 		var fileInfo=new Object();
		 		fileInfo.fileId=fileId;
		 		fileInfo.filename=fileName;
		 		fileInfo.filesize=fileSize;
		 		fileInfo.file=file;
		 		attaFile.push(fileInfo);
                
                plupload.each(files, function(file) {
                    var progress = new FileProgress(up,file,'fsUploadProgress');
                    progress.setStatus("等待...");
                });
            });
        },
        'BeforeUpload': function(up, file) {
            var progress = new FileProgress(up,file, 'fsUploadProgress');
            var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
            if (up.runtime === 'html5' && chunk_size) {
                progress.setChunkProgess(chunk_size);
            }
        },
        'UploadProgress': function(up, file) {
            var progress = new FileProgress(up,file, 'fsUploadProgress');
            var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
            
            progress.setProgress(file.percent + "%", up.total.bytesPerSec, chunk_size);
        },
        'FileUploaded': function(up, file, info) {
            uploadedMap.put(file.id,file.name);
            if(uploadedMap.size() == qiniuTokenMap.size()){
            	uploaderFileStatusChanged(2);
            }
        },
        'Error': function(up, err, errTip) {
            $('table').show();
            var progress = new FileProgress(up,err.file, 'fsUploadProgress');
            progress.setError();
            progress.setStatus(errTip);
            
            uploaderFileStatusChanged(3);
            
        	//显示附件栏删除按钮
        	ShowOrHideDelLink('show');
        },
        'UploadComplete': function() {
        	 $('#success').show();
        }
    }
});

//发送邮件
function sendMail(btnObj){
	var mailToVal=$("#mailTo").val();
	var mailContent=$("#mailContent").val();
	var mailTitle=$("#mailTitle").val();
	var attaJson=JSON.stringify(attaFile);//附件信息
	
	//验证用户信息
	if(validSender() == false){
		return ;
	}
	
	//隐藏附件栏删除按钮
	ShowOrHideDelLink('hide');
	
	var url='${ctx}/mail/newmail';
	$.ajax({
		 url : url,
		 type : "post",
		 dataType : "json",
		 data: {mailTo:mailToVal,mailTitle:mailTitle,mailContent:mailContent,attaInfo:attaJson},
		 success : function(result) {
			$("#mailSID").val(result.mailid);
			uploaderFileStatusChanged(1);
			if(attaFile.length >0){
				qiniuTokenMap=buildAttaTokenInfo(result);
				uploader.start();
			}else{
				uploaderFileStatusChanged(2);
			}
		 }
	});
}

function buildAttaTokenInfo(result){
	var attaTokenMap = new Map();
	var attainfos=result.attachmentupinfos;
	if(attainfos!=null){
		for(var i=0;i<attainfos.length;i++){
			var attainfoObj=attainfos[i];
			attaTokenMap.put(attainfoObj.filename,attainfoObj.token);
		}
	}
	return attaTokenMap;
}

//保存邮件到待发区
function saveDraftMail(){
	var mailToVal=$("#mailTo").val();
	var mailContent=$("#mailContent").val();
	var mailTitle=$("#mailTitle").val();
	var attaJson=JSON.stringify(attaFile);//附件信息
	
	var url='${ctx}/mail/draftmailadd';
	$.ajax({
		 url : url,
		 type : "post",
		 dataType : "json",
		 data: {mailTo:mailToVal,mailTitle:mailTitle,mailContent:mailContent,attaInfo:attaJson},
		 success : function(result) {
    	   if(result.success == "true"){
    		   parent.buildMenuTotal();
    		   alert("邮件已保存待发区");
    	   }
		 }
	});
}

//添加组
function userGroupAdd()
{
	 var userGroupName=$("#userGroupName").val();
	 var url='${ctx}/mail/user/group/add';
	 $.ajax({
		 url : url,
		 type : "post",
		 dataType : "json",
		 data: {groupName:userGroupName},
		 success : function(result) {
			 var jsonObj = eval(result);
			 if(jsonObj.success == 'true'){
				 $("#myGroupModal").modal("hide");
				 getAllUserGroup();
			 }else{
				 alert(jsonObj.msg);
				 return;
			}
		 }
	});
}

//显示添加联系人dialog
function contactModalShow(treeId){
	$("#groupTreeId").val(treeId);
	$("#myContactModal").modal("show");
	
}

//验证发件人
function validSender(){
	var isValid=true;
	var validText="以下收件地址无效，将无法成功收到邮件";
	var userArray =  new Array();
	var mailToVal=$("#mailTo").val();
	var mailToArr=mailToVal.split(";");
	for(var i=0;i<mailToArr.length;i++){
		var username=mailToArr[i];
		if(checkUserName(username) == false){
			userArray.push(username);
		}
	}
	
	if(userArray.length > 0){
		var validUser="";
		if(userArray.length == 1){
			validUser=userArray[0];
		}else{
			validUser=userArray.join(",");
		}
		$("#senderText").html(validText+"<br/><br/>"+validUser);
		$("#senderValidModal").modal("show");
		isValid=false;
	}
	
	return isValid;
}


function ShowOrHideDelLink(f){
	if(f == 'show'){
		for(var i=0;i<attaFile.length;i++){
			var attaObj=attaFile[i];
			var fileid=attaObj.fileId;
			$("#opDel-"+fileid).show();
		}
	}else if(f == 'hide'){
		for(var i=0;i<attaFile.length;i++){
			var attaObj=attaFile[i];
			var fileid=attaObj.fileId;
			$("#opDel-"+fileid).hide();
		}
	}
}

function checkUserName(username){
	 var isAvailableUser="";
	 var url='${ctx}/mail/availableUser';
	 $.ajax({
		 url : url,
		 type : "post",
		 dataType : "json",
		 data: {username:username},
		 async:false,
		 success : function(result) {
			 var jsonObj = eval(result);
			 isAvailableUser=jsonObj.availableUser;
		 }
	});
	return isAvailableUser;
}

//添加联系人
function contactAdd()
{
	 var groupTreeId=$("#groupTreeId").val();
	 var groupContact=$("#groupContact").val();
	 var groupEmail=$("#groupEmail").val();
	 var groupMobile=$("#groupMobile").val();
	 
	 var url='${ctx}/mail/user/contact/add';
	 $.ajax({
		 url : url,
		 type : "post",
		 dataType : "json",
		 data: {treeId:groupTreeId,contact:groupContact,email:groupEmail,mobile:groupMobile},
		 success : function(result) {
			 var jsonObj = eval(result);
			 if(jsonObj.success == 'true'){
				 $("#myContactModal").modal("hide");
				 getAllUserGroup();
			 }else{
				 alert(jsonObj.msg);
				 return;
			}
		 }
	});
}

function uploaderFileStatusChanged(status) {
	//status:0准备上传,1上传中,2上传成功,3上传失败
	$("#uploadStatus").val(status);
	if(status =="1"){
		$("#sendMailBtnTop").text("邮件发送中...");
		$("#sendMailBtnTop").prop("disabled",true);
		$("#saveDraftBtnTop").prop("disabled",true); 
	}
	if(status =="2"){
		parent.buildMenuTotal();
		parent.openNewTab('18','邮件发送成功','${ctx}/mail/success');
		parent.closeTab(curWinId());
		
		$("#sendMailBtnTop").text("发送");
		$("#sendMailBtnTop").prop("disabled",false);
		$("#saveDraftBtnTop").prop("disabled",false); 
	}
	if(status =="3"){
		alert("邮件发送失败!请重新上传文件!");
		return;
	}
}

function cancelUpload(isTab){
	var uploadStatus=$("#uploadStatus").val();
    if(isTab == 1){
		if(window.confirm('关闭页面，邮件将发送失败,确定要这样操作吗？')){
	    	if(attaFile.length > 0 && uploadStatus == 1 ){
	    		doCancelUpload();
	    	}else{
	    		parent.closeTab(curWinId());
	    	}
		}
    }else{
    	if(attaFile.length > 0 && uploadStatus == 1 ){
    		if(window.confirm('目前正在上传附件，如果取消页面会造成上传失败,确定要这样操作吗？')){
    			doCancelUpload();
    		}
    	}
    }
}

function doCancelUpload(){
	uploader.stop();//停止上传
	fileProgressCanal();//重置进度条
	var mailSID=$("#mailSID").val();
	if(mailSID != null){
		senderDeleteMail(mailSID);//调用服务端senderdeletemail接口
		$("#sendMailBtnTop").text("发送");
		$("#sendMailBtnTop").prop("disabled",false);
		$("#saveDraftBtnTop").prop("disabled",false); 
	}
}

function senderDeleteMail(mailid){
	 var url='${ctx}/mail/senderdeletemail';
	 $.ajax({
		 url : url,
		 type : "post",
		 dataType : "json",
		 data: {mailid:mailid},
		 async:false,
		 success : function(result) {
		 }
	});
}

function curWinId(){
	var curWinId=$("#mailUUID").val();
	return curWinId;
}

function fileProgressCanal(){
	if(attaFile != null){
		for(var i=0;i<attaFile.length;i++){
			var attaObj=attaFile[i];
			var progress = new FileProgress(uploader,attaObj.file, 'fsUploadProgress');
			progress.reset();
			progress.setStatus("等待...");
		}
	}	
}

</script>

</body>
</html>