<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../backStatic.jsp"%>
<title>角色管理</title>
<script type="text/javascript" src="./js/system/role.js"></script>
</head>
<body>
	<table id="main-table" class="easyui-datagrid" title="角色列表"
		data-options="
			url:'./admin/system/role/findByExampleForPagination?${_csrf.parameterName}=${_csrf.token}',		
			sortName:'name',
			singleSelect:true,
			toolbar:'#tb',">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',sortable:true,hidden:true">主键</th>
				<th data-options="field:'name',sortable:true,width:400">角色名</th>
				<th data-options="field:'description',sortable:true,width:200">备注</th>
				<th
					data-options="field:'enabled',sortable:true,align:'center',width:70,styler:function(value,row,index){
							if (value == '是') {return 'color:green;';} else {return 'color:#888;';}}">启用</th>
				<th
					data-options="field:'action',width:210, align:'center' ,formatter:formatClick">操作</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding: 5px; height: auto;">
		<a id="tb-query" href="javascript:void(0)" onclick="queryMain();"
			class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		<!-- <a
			id="tb-show" href="javascript:void(0)" onclick="showMain();"
			class="easyui-linkbutton" iconCls="icon-8" plain="true">查看</a> -->
		<div class="div-datagrid-btn-seperator"></div>
		<a id="tb-add" href="javascript:void(0)" onclick="addMain();"
			class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> <a
			id="tb-edit" href="javascript:void(0)" onclick="editMain();"
			class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a> <a
			id="tb-delete" href="javascript:void(0)" onclick="deleteMain();"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		<div class="div-datagrid-btn-seperator"></div>
		<a id="tb-import" href="javascript:void(0)" onclick="importMain();"
			class="easyui-linkbutton" iconCls="icon-save" plain="true">导入</a> <a
			id="tb-export" href="javascript:void(0)" onclick="exportMain();"
			class="easyui-linkbutton" iconCls="icon-save" plain="true">导出</a>
		<div class="div-datagrid-btn-seperator"></div>
	</div>

	<div id="query" class="easyui-dialog"
		style="width: 450px; height: 250px; padding: 10px 20px" closed="true"
		buttons="#query-buttons">
		<div class="ftitle">角色信息</div>
		<form id="query-fm" method="post" novalidate>
			<div class="fitem">
				<label>角色名:</label> <input id="query-name" name="name" type="text"
					class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>备注:</label> <input id="query-description" name="description"
					type="text" class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>启用:</label> <select id="query-enabled" name="enabled"
					class="easyui-combobox" panelHeight="auto" editable="false"
					style="width: 135px;">
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</form>
	</div>

	<div id="query-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-search" onclick="querySectionAuxiliary()">查询部分</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-reload" onclick="queryAllAuxiliary()">查询全部</a>
	</div>

	<div id="show" class="easyui-dialog"
		style="width: 450px; height: 400px; padding: 10px 20px" closed="true"
		buttons="#show-buttons">
		<div class="ftitle">角色信息</div>
		<form id="show-fm" method="post" novalidate>
			<div class="fitem" style="display: none">
				<label>主键:</label> <input id="show-id" name="id" disabled />
			</div>
			<div class="fitem" id="show-name-auxiliary">
				<label>角色名:</label> <input id="show-name" name="name" disabled
					type="text" class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>备注:</label> <input id="show-description" name="description"
					disabled type="text" class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>启用:</label> <input id="show-enabled" name="enabled" disabled
					type="text" class="easyui-textbox" />
			</div>
		</form>
	</div>

	<div id="show-buttons">
		<a id="show-print" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-print" onclick="printAuxiliary()">打印</a>
	</div>

	<div id="add" class="easyui-dialog"
		style="width: 450px; height: 400px; padding: 10px 20px" closed="true"
		buttons="#add-buttons">
		<div class="ftitle">角色信息</div>
		<form id="add-fm" method="post" novalidate>
			<div class="fitem" style="display: none">
				<label>主键:</label> <input id="add-id" name="id" />
			</div>
			<div class="fitem" id="add-name-auxiliary">
				<label>角色名:</label> <input id="add-name" name="name"
					class="easyui-validatebox easyui-textbox" required="true" />
			</div>
			<div class="fitem">
				<label>备注:</label> <input id="add-description" name="description"
					type="text" class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>启用:</label> <select id="add-enabled" name="enabled"
					class="easyui-combobox" panelHeight="auto" editable="false"
					style="width: 135px;">
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</form>
	</div>

	<div id="add-buttons">
		<a id="add-save" href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="addAuxiliary()">保存</a>
	</div>

	<div id="edit" class="easyui-dialog"
		style="width: 450px; height: 400px; padding: 10px 20px" closed="true"
		buttons="#edit-buttons">
		<div class="ftitle">角色信息</div>
		<form id="edit-fm" method="post" novalidate>
			<div class="fitem" style="display: none">
				<label>主键:</label> <input id="edit-id" name="id" />
			</div>
			<div class="fitem" id="edit-name-auxiliary">
				<label>角色名:</label> <input id="edit-name" name="name"
					class="easyui-validatebox easyui-textbox" required="true" />
			</div>
			<div class="fitem">
				<label>备注:</label> <input id="edit-description" name="description"
					type="text" class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>启用:</label> <select id="edit-enabled" name="enabled"
					class="easyui-combobox" panelHeight="auto" editable="false"
					style="width: 135px;">
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</form>
	</div>

	<div id="edit-buttons">
		<a id="edit-insert-save" href="javascript:void(0)"
			class="easyui-linkbutton" iconCls="icon-ok" onclick="editAuxiliary()">保存</a>
	</div>

	<div id="import" class="easyui-dialog"
		style="width: 450px; height: 250px; padding: 10px 20px" closed="true"
		buttons="#import-buttons">
		<div class="ftitle">请选择文件（支持Excel2003，Excel2007）</div>
		<form id="import-fm" method="post" enctype="multipart/form-data">
			<div class="fitem">
				<label>文件:</label><input type="file" name="file">
			</div>
		</form>
	</div>

	<div id="import-buttons">
		<a href="./excel/download-template/SysRole.xlsx"
			class="easyui-linkbutton" iconCls="icon-help">下载模版</a><a
			href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="importAuxiliary()">上传</a>
	</div>

	<!----关联用户与角色----->
	<div id="user-role" class="easyui-dialog" modal="true"
		data-options="maximized:true,closed:true,closable:true">
		<table id="main-table-user-role" class="easyui-datagrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true,hidden:true"></th>
					<th
						data-options="field:'id',sortable:true,width:50,align:'center',hidden:true">用户编号</th>
					<th
						data-options="field:'relevance',sortable:false,width:70,align:'center',formatter:function(value, row, index){
							if (value == 1) {return '是';}else{return '否';}},editor:{type:'checkbox',options:{on:'1',off:'0'}},
							styler:function(value,row,index){if (value == 1) {return 'color:green;';} else {return 'color:#888;';}}">关联</th>
					<th data-options="field:'name',sortable:true,width:400">用户名称</th>
					<th data-options="field:'description',sortable:true,width:400">备注</th>
					<th
						data-options="field:'relevanceId',sortable:true,width:100,align:'center',hidden:true">关联记录id</th>
				</tr>
			</thead>
		</table>
		<div id="tb-user-role-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" onclick="editAllRelevanceUserAuxiliary();"
				plain="true">编辑</a> <a href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-save"
				onclick="saveAllRelevanceUserAuxiliary();" plain="true">保存</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="cancelAllRelevanceUserAuxiliary();"
				plain="true">取消</a>
		</div>
	</div>


	<!----关联角色与权限 ---->
	<div id="role-authority" class="easyui-dialog" modal="true"
		data-options="maximized:true,closed:true,closable:true">
		<table id="main-table-role-authority" class="easyui-datagrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true,hidden:true"></th>
					<th
						data-options="field:'id',sortable:true,width:50,align:'center',hidden:true">权限编号</th>
					<th
						data-options="field:'relevance',sortable:false,width:70,align:'center',formatter:function(value, row, index){
							if (value == 1) {return '是';}else{return '否';}},editor:{type:'checkbox',options:{on:'1',off:'0'}},
							styler:function(value,row,index){if (value == 1) {return 'color:green;';} else {return 'color:#888;';}}">关联</th>
					<th data-options="field:'name',sortable:true,width:400">权限名称</th>
					<th data-options="field:'description',sortable:true,width:400">备注</th>
					<th
						data-options="field:'relevanceId',sortable:true,width:100,align:'center',hidden:true">关联记录id</th>
				</tr>
			</thead>
		</table>
		<div id="tb-role-authority-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" onclick="editAllRelevanceAuthorityAuxiliary();"
				plain="true">编辑</a> <a href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-save"
				onclick="saveAllRelevanceAuthorityAuxiliary();" plain="true">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel"
				onclick="cancelAllRelevanceAuthorityAuxiliary();" plain="true">取消</a>
			<input name="name" id="role-authority-name"> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-search" onclick="queryAllRelevanceMain();"
				plain="true">查询</a> <a href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-search"
				onclick="queryAllRelevanceAll();" plain="true">显示全部</a>
		</div>
	</div>

	<div id="role-authority-all" class="easyui-dialog" modal="true"
		data-options="maximized:true,closed:true,closable:true">
		<table id="role-authority-all-table" class="easyui-datagrid">
			<thead>
				<tr>
					<th data-options="field:'name',sortable:true,width:400">权限名称</th>
					<th data-options="field:'description',sortable:true,width:400">备注</th>
				</tr>
			</thead>
		</table>
	</div>

</body>
</html>