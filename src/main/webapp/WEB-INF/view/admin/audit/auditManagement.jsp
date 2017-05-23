<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 -->
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../backStatic.jsp"%>
<style type="text/css">
.fitem label {
	width: 100px;
}

.addItem {
	border-top: 1px solid #000000;
	padding-top: 10px;
}
</style>
<title>电子文档安全管理系统</title>
</head>
<body>
	<table id="main-table" title="文档列表"></table>
	<div id="toolbar" style="padding: 5px; height: auto;">
		<a href="javascript:void(0)" onclick="queryMain();"
			class="easyui-linkbutton" iconCls="icon-query" plain="true">查询</a> <a
			class="easyui-splitbutton" menu="#auditMenu" iconCls="icon-audit">审核操作</a>
	</div>

	<div id="auditMenu" style="width: 150px;">
		<div iconCls="icon-pass" onclick="pass();">审核通过</div>
		<div iconCls="icon-notPass" onclick="notPass();">审核不通过</div>
	</div>

	<div id="mainQuery" class="easyui-dialog" title="查询" closed="true"
		modal="true" style="width: 460px; height: 570px; padd ing: 20px"
		buttons="#mainQuery-buttons">
		<div class="ftitle">文档信息</div>
		<form id="mainQuery-fm" method="post" novalidate>
			<div class="fitem">
				<label>文档编号:</label> <input id="number" class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>文档名称:</label> <input id="name" class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>文档路径:</label> <input id="path" class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>上传时间:</label> <input id="uploadTime"
					class="easyui-datetimebox" />
			</div>
			<div class="fitem">
				<label>文档大小:</label> <input id="size" class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>文档类型:</label> <input id="type" class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>加密方法:</label> <input id="encryptMethod"
					class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>加密秘钥:</label> <input id="encryptCode" class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>浏览次数:</label> <input id="viewTimes" class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>下载次数:</label> <input id="downloadTimes"
					class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>摘要:</label> <input id="abstracts" class="easyui-textbox" />
			</div>
		</form>
	</div>
	<div id="mainQuery-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-search" onclick="queryAuxiliary()">查询</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-reload" onclick="queryAllAuxiliary()">查询全部</a>
	</div>


	<div id="loading" class="easyui-dialog" title="请稍后" closed="true"
		closable="false" modal="true"
		style="width: 200px; height: 100px; position: relative">
		<div
			style="position: absolute; left: 0; right: 0; top: 0; bottom: 0; margin: auto; font-size: 12px; height: 30px; line-height: 30px; width: 100px; text-align: center;">申请中,请稍后...</div>
	</div>

</body>
<script type="text/javascript" src="./js/audit/auditManagement.js"></script>
<script type="text/javascript" src="./js/auto.type.combobox.js"></script>
</html>