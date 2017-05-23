<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 -->
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../backStatic.jsp"%>
<link href="./SWFUploadv2.2.0.1Core/default.css" rel="stylesheet"
	type="text/css" />
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
	<table id="main-table" title="备份列表"></table>
	<div id="toolbar" style="padding: 5px; height: auto;">
		<a href="javascript:void(0)" onclick="queryMain();"
			class="easyui-linkbutton" iconCls="icon-query" plain="true">查询</a> <a
			href="javascript:void(0)" onclick="newBackup();"
			class="easyui-linkbutton" iconCls="icon-upload" plain="true">新建备份</a><a
			href="javascript:void(0)" onclick="recoverBackup();"
			class="easyui-linkbutton" iconCls="icon-recover" plain="true">还原备份</a>
		<a href="javascript:void(0)" onclick="downloadBackup();"
			class="easyui-linkbutton" iconCls="icon-download" plain="true">下载备份</a>
		<a href="javascript:void(0)" onclick="deleteBackup();"
			class="easyui-linkbutton" iconCls="icon-delete" plain="true">删除备份</a>
		<a href="javascript:void(0)" onclick="encrypt();"
			class="easyui-linkbutton" iconCls="icon-encryption" plain="true">备份加密</a>
		<a href="javascript:void(0)" onclick="decrypt();"
			class="easyui-linkbutton" iconCls="icon-decrypt" plain="true">备份解密</a>
	</div>

	<div id="mainQuery" class="easyui-dialog" title="查询" closed="true"
		modal="true" style="width: 460px; height: 570px; padding: 20px"
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

	<input id="sessionIdForUpload" type="hidden"
		value="<%=request.getSession().getId()%>" />

	<div id="dialog-uploadFile" class="easyui-dialog" title="上传文件"
		style="width: 550px; height: 500px; padding: 20px;" closed="true"
		resizable="true" modal="true" buttons="#add-buttons">
		<div class="ftitle">上传文档信息</div>
		<form id="upload-fm" method="post" novalidate>
			<div id="uploadMultiFiles">
				<div id="content" style="margin-left: 30px; margin-top: 20px">
					<div
						style="margin-bottom: 20px; color: #666666; vertical-align: top;">
						提示：建议使用IE、Chrome、FireFox、搜狗浏览器较新版本，以获得更好地体验！</div>
					<div id="upload-button">
						<span id="spanButtonPlaceHolder"></span>
					</div>
					<div class="fieldset flash" style="width: 400px; margin-top: 20px"
						id="fsUploadProgress">
						<span class="legend">上传队列</span>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<input id="upload_file_name_commaString"
				name="upload_file_name_commaString" type="hidden" /> <input
				id="upload_file_path_commaString"
				name="upload_file_path_commaString" type="hidden" /> <input
				id="upload_file_abstracts_commaString"
				name="upload_file_abstracts_commaString" type="hidden" />
		</form>
	</div>
	<div id="add-buttons">
		<div id="divStatus"
			style="float: left; font-size: 12px; padding-left: 10px; padding-top: 5px">0
			个文件已经上传</div>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" id="btnUpload" onclick="startUpload();">开始上传</a> <a
			href="javascript:void(0)" class="easyui-linkbutton" id="btnCancel"
			iconCls="icon-cut" onclick="swfu.cancelQueue();">取消全部上传</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-save" onclick="save();">保存</a>
	</div>
	<!-- =====================加密文件对话框 ==========================-->
	<div id="encrypt" class="easyui-dialog" title="加密文件" closed="true"
		modal="true" style="width: 460px; height: 570px; padding: 20px"
		buttons="#encrypt-buttons">
		<div class="ftitle">文档信息</div>
		<form id="encrypt-fm" method="post" novalidate>
			<div class="fitem">
				<label>文档编号:</label> <input class="encrypt easyui-filebox" />
			</div>
			<div class="fitem">
				<label>选择文件:</label> <input type="file" id="files" />
			</div>

		</form>
	</div>
	<div id="encrypt-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-search" onclick="doEncrypt()">加密</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-search" onclick="cancelEncrypt()">取消</a>
	</div>



	<div id="loading" class="easyui-dialog" title="请稍后" closed="true"
		closable="false" modal="true"
		style="width: 200px; height: 100px; position: relative">
		<div
			style="position: absolute; left: 0; right: 0; top: 0; bottom: 0; margin: auto; font-size: 12px; height: 30px; line-height: 30px; width: 100px; text-align: center;">申请中,请稍后...</div>
	</div>

</body>
<script type="text/javascript" src="./js/document/backup.js"></script>
<script type="text/javascript" src="./js/auto.type.combobox.js"></script>
</html>