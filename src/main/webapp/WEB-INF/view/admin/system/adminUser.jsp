<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>系统用户管理</title>
<%@ include file="../backStatic.jsp"%>

</head>
<body>
	<script type="text/javascript">
		//扩展easyui表单的验证  
		$.extend($.fn.validatebox.defaults.rules, {
			//验证强密码
			strongPwd : {
				validator : function(value) {
					return /^${adminPwdRegex}$/.test(value);
				},
				message : '密码强度不符合要求(必须含有字母、数字、以及特殊字符的不低于8位的密码)，请重新输入.'
			}
		})
	</script>
	<table id="main-table" class="easyui-datagrid" title="系统用户列表"
		data-options="
			url:'./admin/system/findAdminUserByExampleForPagination?${_csrf.parameterName}=${_csrf.token}',
			sortName:'name',
			singleSelect:false,
			fit:true,
			toolbar:'#tb'">
		<thead frozen="true">
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'id',sortable:true,hidden:true">主键</th>
				<th data-options="field:'staffRelate',hidden:true"></th>
				<th
					data-options="field:'name',sortable:true ,align:'center',width:150">用户名</th>
				<th
					data-options="field:'staff',sortable:true,align:'center',width:200,
					styler:function(value,row,index){
							if (value==null||value == '') {return 'color:#888;';} 
							else {return 'color:green;';}},
					formatter:function (value,row,index){
						if (value==null||value == '') {
					return  '未关联';
					}
					else {
					return value;
					}
	}">关联员工</th>
			</tr>
		</thead>
		<thead>
			<tr>
				<th
					data-options="field:'email',sortable:true ,align:'center',width:200">邮箱</th>
				<th
					data-options="field:'password',sortable:true ,align:'center',width:200">密码</th>
				<th
					data-options="field:'userType',sortable:true ,align:'center',width:200">用户类别</th>
				<th
					data-options="field:'description',sortable:true ,align:'center',width:200">备注</th>
				<th
					data-options="field:'enabled',sortable:true,align:'center',width:70,styler:function(value,row,index){
							if (value == '是') {return 'color:green;';} else {return 'color:#888;';}}">启用</th>
				<th
					data-options="field:'action',width:150, align:'center', formatter:formatClickForSys">操作</th>
			</tr>
		</thead>
	</table>
	<input id="okPic" type="hidden" value="." />
	<div id="tb" style="padding: 5px; height: auto;">
		<a id="tb-query" href="javascript:void(0)" onclick="queryMain();"
			class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		<div class="div-datagrid-btn-seperator"></div>
		<a id="tb-add" href="javascript:void(0)" onclick="addMain();"
			class="easyui-linkbutton" iconCls="icon-add" plain="true">添加系统用户</a>
		<a id="tb-edit" href="javascript:void(0)" onclick="editMain();"
			class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a> <a
			id="tb-delete" href="javascript:void(0)" onclick="disableUser();"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true">禁用</a>
		<div class="div-datagrid-btn-seperator"></div>
		<!-- 		<a id="tb-import" href="javascript:void(0)" onclick="importMain();" -->
		<!-- 			class="easyui-linkbutton" iconCls="icon-save" plain="true">导入系统用户</a><a -->
		<!-- 			id="tb-export" href="javascript:void(0)" onclick="exportMain();" -->
		<!-- 			class="easyui-linkbutton" iconCls="icon-save" plain="true">导出全部用户</a> -->
		<!-- 		<div class="div-datagrid-btn-seperator"></div> -->
		<a id="tb-password" href="javascript:void(0)"
			onclick="resetPasswordMain();" class="easyui-linkbutton"
			iconCls="icon-reload" plain="true">密码重置</a> <a id="tb-password"
			href="javascript:void(0)" onclick="unRelateStaff();"
			class="easyui-linkbutton" iconCls="icon-cancel" plain="true">员工解绑</a>
		<div class="div-datagrid-btn-seperator"></div>
		<!-- <a class="easyui-splitbutton"
			data-options="menu:'#lookMenu',iconCls:'icon-7'">查看权限控制</a> <a
			class="easyui-splitbutton"
			data-options="menu:'#checkMenu',iconCls:'icon-4'">审核权限控制</a>
 -->
	</div>
	<div id="lookMenu" style="width: 150px;">
		<div data-options="iconCls:'icon-column'"
			onclick="relevanceCheckMain('l-contract');">合同</div>
		<div data-options="iconCls:'icon-1'"
			onclick="relevanceCheckMain('l-insurance');">保单</div>
		<div class="menu-sep"></div>
		<div data-options="iconCls:'icon-template'"
			onclick="relevanceCheckMain('invoice');">发票</div>
		<!-- <div data-options="iconCls:'icon-static'"
			onclick="relevanceCheckMain('l-endorsement');">批单</div>
		<div class="menu-sep"></div>
		<div data-options="iconCls:'icon-returnAndBack'"
			onclick="relevanceCheckMain('l-surrender');">退保</div> -->
	</div>
	<div id="checkMenu" style="width: 150px;">
		<div data-options="iconCls:'icon-column'"
			onclick="relevanceCheckMain('contract');">合同</div>
		<div data-options="iconCls:'icon-1'"
			onclick="relevanceCheckMain('insurance');">保单</div>
		<div data-options="iconCls:'icon-static'"
			onclick="relevanceCheckMain('endorsement');">批单</div>
		<div class="menu-sep"></div>
		<div data-options="iconCls:'icon-returnAndBack'"
			onclick="relevanceCheckMain('surrender');">退保</div>
	</div>
	<div id="query" class="easyui-dialog"
		style="width: 450px; height: 400px; padding: 10px 20px" closed="true"
		buttons="#query-buttons">
		<div class="ftitle">用户信息</div>
		<form id="query-fm" method="post" novalidate>
			<div class="fitem">
				<label>用户名:</label> <input id="query-name" name="name" type="text"
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
		<div class="ftitle">用户信息</div>
		<form id="show-fm" method="post" novalidate>
			<div class="fitem" style="display: none">
				<label>主键:</label> <input id="show-id" name="id" disabled />
			</div>
			<div class="fitem">
				<label>用户名:</label> <input id="show-name" name="name" disabled
					type="text" class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>密码:</label> <input name="show-password" disabled type="text"
					class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>备注:</label> <input name="show-description" disabled
					type="text" class="easyui-textbox" />
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
		<div class="ftitle">用户信息</div>
		<form id="add-fm" method="post">
			<div class="fitem" style="display: none">
				<label>主键:</label> <input id="add-id" name="id" />
			</div>
			<div class="fitem">
				<label>用户名:</label> <input id="add-name" name="name"
					class=" easyui-textbox easyui-validatebox" required="true" />
			</div>
			<div class="fitem">
				<label>邮箱:</label> <input id="add-email" name="email"
					class=" easyui-textbox easyui-validatebox"
					data-options="required:true,validType:'email'" />
			</div>
			<div class="fitem">
				<label>密码:</label> <input id="add-password" type="password"
					name="password" class="easyui-textbox easyui-validatebox"
					data-options="required:true,validType:'strongPwd'" />
			</div>
			<div class="fitem">
				<label>备注:</label> <input id="add-description" name="description"
					class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>启用:</label> <select id="add-enabled" name="enabled"
					class="easyui-combobox" panelHeight="auto" style="width: 236px;"
					editable="false">
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</form>
	</div>
	<div id="add-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="addAuxiliary()">保存</a>
	</div>

	<div id="edit" class="easyui-dialog"
		style="width: 450px; height: 400px; padding: 10px 20px" closed="true"
		buttons="#edit-buttons">
		<div class="ftitle">用户信息</div>
		<form id="edit-fm" method="post">
			<div class="fitem" style="display: none">
				<label>主键:</label> <input id="edit-id" name="id" /> <input
					id="edit-staffRelate" name="staffRelate" />
			</div>
			<div class="fitem" id="edit-name-auxiliary">
				<label>用户名:</label> <input id="edit-name" name="name"
					class="easyui-validatebox easyui-textbox" required="true" />
			</div>
			<div class="fitem" id="edit-email-auxiliary">
				<label>邮箱:</label> <input id="edit-email" name="email"
					class="easyui-validatebox easyui-textbox"
					data-options="required:true,validType:'email'" />
			</div>
			<div class="fitem" style="display: none">
				<label>密码:</label> <input id="edit-password" name="password" />
			</div>
			<div class="fitem" style="display: none">
				<label>用户类型:</label> <input id="edit-userType" name="userType" />
			</div>
			<div class="fitem">
				<label>备注:</label> <input id="edit-description" name="description"
					class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>启用:</label> <select id="edit-enabled" name="enabled"
					class="easyui-combobox" style="width: 135px;" panelHeight="auto"
					editable="false">
					<option value="是">是</option>
					<option value="否">否</option>
				</select>
			</div>
		</form>
	</div>
	<div id="edit-buttons">
		<a id="edit-update-save" href="javascript:void(0)"
			class="easyui-linkbutton" iconCls="icon-ok" onclick="editAuxiliary()">保存</a>
	</div>

	<div id="import" class="easyui-dialog"
		style="width: 450px; height: 250px; padding: 10px 20px" closed="true"
		buttons="#import-buttons">
		<div class="ftitle">请选择文件（支持Excel2003，Excel2007）</div>
		<form id="import-fm" method="post" enctype="multipart/form-data">
			<div class="fitem">
				<label>文件:</label><input id="import-file" type="file" name="file">
			</div>
		</form>
	</div>
	<div id="import-buttons">
		<a href="./excel/download-template/SysUser.xlsx"
			class="easyui-linkbutton" iconCls="icon-help">下载模版</a><a
			href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="importAuxiliary()">上传</a>
	</div>

	<!-- ----关联用户与角色----->
	<div id="user-role" class="easyui-dialog" modal="true"
		data-options="maximized:true,closed:true,closable:true">
		<table id="main-table-user-role" class="easyui-datagrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true,hidden:true"></th>
					<th
						data-options="field:'id',sortable:true,width:50,align:'center',hidden:true">角色编号</th>
					<th
						data-options="field:'relevance',sortable:false,width:70,align:'center',formatter:function(value, row, index){
							if (value == 1) {return '是';}else{return '否';}},editor:{type:'checkbox',options:{on:'1',off:'0'}},
							styler:function(value,row,index){if (value == 1) {return 'color:green;';} else {return 'color:#888;';}}">关联</th>
					<th data-options="field:'name',sortable:true,width:200">角色名称</th>
					<th data-options="field:'description',sortable:true,width:200">备注</th>
					<th
						data-options="field:'relevanceId',sortable:true,width:100,align:'center',hidden:true">关联记录id</th>
				</tr>
			</thead>
		</table>
		<div id="tb-user-role-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" onclick="editAllRelevanceAuxiliary();"
				plain="true">编辑</a> <a href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-save"
				onclick="saveAllRelevanceAuxiliary();" plain="true">保存</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="cancelAllRelevanceAuxiliary();"
				plain="true">取消</a>
		</div>
	</div>

	<!-- ----------- -->

	<div id="reset" class="easyui-dialog"
		style="width: 450px; height: 400px; padding: 10px 20px" closed="true"
		buttons="#reset-buttons">
		<div class="ftitle">密码重置</div>
		<form id="reset-fm" method="post">
			<div class="fitem" style="display: none">
				<label>主键:</label> <input id="reset-idCommaString"
					name="idCommaString" />
			</div>
			<div class="fitem">
				<label>密码:</label> <input id="reset-password" name="password"
					class="easyui-validatebox easyui-textbox"
					data-options="required:true,validType:'strongPwd'" />
			</div>
		</form>
	</div>
	<div id="reset-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" onclick="resetPasswordAuxiliary()">保存</a>
	</div>

	<!-- -- 关联员工 -------- -->

	<div id="user-staff" class="easyui-dialog" modal="true"
		data-options="maximized:true,closed:true,closable:true">
		<table id="main-table-user-staff" class="easyui-datagrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th
						data-options="field:'id',sortable:true,width:100,align:'center',hidden:true">Id</th>
					<th
						data-options="field:'number',sortable:true,width:100,align:'center'">员工编号</th>
					<th
						data-options="field:'relevance',sortable:false,width:70,align:'center',formatter:function(value, row, index){
							if (value == 1) {return '是';}else{return '否';}},editor:{type:'checkbox',options:{on:'1',off:'0'}},
							styler:function(value,row,index){if (value == 1) {return 'color:green;';} else {return 'color:#888;';}}">关联</th>
					<th data-options="field:'name',sortable:true,width:100">姓名</th>
					<th data-options="field:'departmentName',sortable:true,width:150">部门</th>
					<th
						data-options="field:'relevanceId',sortable:true,width:100,align:'center',hidden:true">关联记录id</th>
				</tr>
			</thead>
		</table>
		<div id="tb-user-staff-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" onclick="queryAllRelevanceAuxiliaryStaff();"
				plain="true">查询</a> <a href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-save"
				onclick="saveAllRelevanceAuxiliaryStaff();" plain="true">保存</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-returnAndBack" onclick="unCheckRows();" plain="true">取消选择</a>
		</div>
	</div>

	<div id="query-staff" class="easyui-dialog"
		style="width: 450px; height: 400px; padding: 10px 20px" closed="true"
		buttons="#query-buttons-staff">
		<div class="ftitle">员工信息</div>
		<form id="query-fm-staff" method="post" novalidate>
			<div class="fitem">
				<label>姓名:</label> <input id="query-name-staff" name="name"
					type="text" class="easyui-textbox" />
			</div>
			<div class="fitem">
				<label>部门:</label> <input id="query-department-staff" name="name" />
			</div>
			<div class="fitem">
				<label>备注:</label> <input id="query-description-staff"
					name="description" type="text" class="easyui-textbox" />
			</div>
		</form>
	</div>
	<div id="query-buttons-staff">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-search" onclick="querySectionAuxiliaryStaff()">查询部分</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-reload" onclick="queryAllAuxiliaryStaff()">查询全部</a>
	</div>


	<!-------------------->

	<!-- ----权限控制----->
	<div id="user-check" class="easyui-dialog" modal="true"
		data-options="maximized:true,closed:true,closable:true">
		<table id="main-table-user-check" class="easyui-datagrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true,hidden:true"></th>
					<th
						data-options="field:'id',sortable:true,width:50,align:'center',hidden:true">员工ID</th>
					<th
						data-options="field:'relevance',sortable:false,width:70,align:'center',formatter:function(value, row, index){
							if (value == 1) {return '是';}else{return '否';}},editor:{type:'checkbox',options:{on:'1',off:'0'}},
							styler:function(value,row,index){if (value == 1) {return 'color:green;';} else {return 'color:#888;';}}">配置</th>
					<th data-options="field:'number',sortable:true,width:200">员工编号</th>
					<th data-options="field:'name',sortable:true,width:200">业务员姓名</th>
					<th data-options="field:'departmentName',sortable:true,width:200">所属部门</th>
					<th
						data-options="field:'relevanceId',sortable:true,width:100,align:'center',hidden:true">关联记录id</th>
				</tr>
			</thead>
		</table>
		<div id="tb-user-check-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" onclick="editAllRelevanceAuxiliaryCheck();"
				plain="true">编辑</a> <a href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-save"
				onclick="saveAllRelevanceAuxiliaryCheck();" plain="true">保存</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="cancelAllRelevanceAuxiliaryCheck();"
				plain="true">取消</a>
			<div>
				<label>用户名:</label> <input id="find-check-name" name="check-name"
					class="easyui-validatebox easyui-textbox" style="width: 150px;" />
				<label>部门:</label><input class="easyui-comboTree"
					id="find-check-infDepartmentNumber"
					name="find-check-infDepartmentNumber" /> <a
					href="javascript:void(0)" onclick="relevanceCheckMain()"
					class="easyui-linkbutton" iconCls="icon-search">查找</a> <a
					href="javascript:void(0)" onclick="relevanceCheckMain()"
					class="easyui-linkbutton" iconCls="icon-reload">显示全部</a>
			</div>

		</div>
	</div>

	<!-- ----------- -->

</body>
<script type="text/javascript" src="./js/system/user.20161007.js"></script>
<script type="text/javascript" src="./js/auto.type.combobox.js"></script>
</html>