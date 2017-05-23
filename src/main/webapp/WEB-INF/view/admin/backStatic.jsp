<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spr" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<sec:csrfMetaTags />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ "/";
%>
<base href="<%=basePath%>" />
<link href="<%=basePath%>/images/logocolor.png" rel="shortcut icon">

<link href="./jquery-easyui-1.4.2/themes/default/easyui.css"
	rel="stylesheet" type="text/css" />
<link href="./jquery-easyui-1.4.2/themes/icon.css" rel="stylesheet"
	type="text/css" />
<link href="./css/admin.css" rel="stylesheet" type="text/css" />


<script src="./jquery-easyui-1.4.2/jquery.min.js" type="text/javascript"></script>
<script src="./jquery-easyui-1.4.2/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="./jquery-easyui-1.4.2/datagrid-detailview.js"
	type="text/javascript"></script>
<script src="./jquery-easyui-1.4.2/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script src="./jquery-easyui-1.4.2/jquery.cookie.js"
	type="text/javascript"></script>
<script src="./js/common.js" type="text/javascript"></script>
<script src="./js/easyui.common.20160713.js" type="text/javascript"></script>

<script>
	$(function() {
		$('#dialog-common').dialog('close');
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});
	});
</script>

<div id="dialog-common" class="easyui-dialog" title="提示消息"
	data-options="iconCls:'icon-tip'"
	style="width: 300px; height: 150px; padding: 10px;"></div>