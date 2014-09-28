<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title>快传系统-<sitemesh:title/></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />
<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
<link href="${ctx}/static/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctx}/static/styles/style.css?v=20140927" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/jquery/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery/jquery-2.1.0.min.js" type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/2.3.2/js/bootstrap.min.js" type="text/javascript"></script>
<sitemesh:head/>
</head>

<body style="overflow-y:hidden;overflow-x:auto;">
	<div>
		<%@ include file="/WEB-INF/layouts/header.jsp"%>
		<sitemesh:body/>
		<%@ include file="/WEB-INF/layouts/footer.jsp"%>
	</div>
</body>
</html>