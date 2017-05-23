var editFlag = 0;// 标记是否处于编辑状态，是则为1，否则为0
var roleId = 0;// 用来存放要关联的用户ID

function showMain() {
	showMainBase();
}

function addMain() {
	addMainBase();
}

function addAuxiliary() {
	addAuxiliaryBase('./admin/system/role/save');
}

function editMain() {
	editMainBase();
	var row = $('#main-table').datagrid('getSelections');
	// 对于单选情况name是可修改的
	if (row.length == 1) {
		// 处理由于多项修改造成的style="display:none"属性，使name能正常显示
		$('#edit-name-auxiliary').attr('style', 'display:block');
	}
	// 对于多选情况name是不可修改的
	else {
		$('#edit-name-auxiliary').attr('style', 'display:none');
	}

}

function editAuxiliary() {
	editAuxiliaryBase('./admin/system/role/update',
			'./admin/system/role/updateByKeys');
}

function deleteMain() {
	deleteMainBase('./admin/system/role/deleteByKeys');
}

function queryMain() {
	queryMainBase();
}

function queryAllAuxiliary() {
	queryAllAuxiliaryBase();
}

function querySectionAuxiliary() {
	var queryParameter = {};
	// 注意传入查询参数而不赋值，后台当""处理，不传入查询参数，后台当null处理。所以有如下的拼参数方式
	if ($('#query-name').val() !== undefined && $('#query-name').val() !== "") {
		queryParameter.name = $('#query-name').val();
	}
	if ($('#query-description').val() !== undefined
			&& $('#query-description').val() !== "") {
		queryParameter.description = $('#query-description').val();
	}
	// 注意combobox取值
	if ($('#query-enabled').combobox('getValue') !== undefined
			&& $('#query-enabled').combobox('getValue') !== "") {
		queryParameter.enabled = $('#query-enabled').combobox('getValue');
	}
	$('#query').dialog('close');// 关闭弹出框
	$('#main-table').datagrid('reload', queryParameter);
}

function importMain() {
	importMainBase();
}

function importAuxiliary() {
	importAuxiliaryBase('./admin/system/role/importFromExcel');
}
/*
 * 导出
 */
function exportMain() {
	var queryParameters = $('#main-table').datagrid('options').queryParams;
	var queryParametersString = '';
	if (queryParameters.name !== undefined) {
		queryParametersString += 'name=' + queryParameters.name + '&';
	}
	if (queryParameters.description !== undefined) {
		queryParametersString += 'description=' + queryParameters.description
				+ '&';
	}
	if (queryParameters.enabled !== undefined) {
		queryParametersString += 'enabled=' + queryParameters.enabled + '&';
	}

	if (queryParametersString !== '') {
		queryParametersString = queryParametersString.substring(0,
				queryParametersString.length - 1);
	}
	exportMainBase('./admin/system/role/exportToExcel', queryParametersString);
}

/*
 * 关联用户框
 */
function relevanceUserMain(num) {
	var row = $('#main-table').datagrid('getRows')[num];
	var title = "设置用户" + "（" + row.name + "）";
	roleId = row.id;
	relevanceMainBase('user-role', 'main-table-user-role',
			'tb-user-role-buttons', title,
			'./admin/system/role/findUserRoleByRoleIdForPagination?roleId=',
			roleId);
}

/*
 * 关联权限框
 */
function relevanceAuthorityMain(num) {
	var row = $('#main-table').datagrid('getRows')[num];
	var title = "设置权限" + "（" + row.name + "）";
	roleId = row.id;
	relevanceMainBase(
			'role-authority',
			'main-table-role-authority',
			'tb-role-authority-buttons',
			title,
			'./admin/system/authority/findRoleAuthorityByRoleIdForPagination?roleId=',
			roleId);
}

/*
 * 进入关联用户编辑状态
 */
function editAllRelevanceUserAuxiliary() {
	editAllRelevanceAuxiliaryBase('main-table-user-role');
}

/*
 * 进入关联权限编辑状态
 */
function editAllRelevanceAuthorityAuxiliary() {
	editAllRelevanceAuxiliaryBase('main-table-role-authority');
}

/*
 * 取消关联用户编辑状态
 */
function cancelAllRelevanceUserAuxiliary() {
	cancelAllRelevanceAuxiliaryBase('main-table-user-role');
}

/*
 * 取消关联权限编辑状态
 */
function cancelAllRelevanceAuthorityAuxiliary() {
	cancelAllRelevanceAuxiliaryBase('main-table-role-authority');
}
/*
 * 关联权限保存
 */
function saveAllRelevanceUserAuxiliary() {
	var saveOrUpdateData = {
		sysUserId : 0,
		sysRoleId : roleId,
		enabled : '是'
	};
	saveAllRelevanceAuxiliaryBase('main-table-user-role', saveOrUpdateData,
			'sysUserId', './admin/system/role/addRoleUserRelation',
			'./admin/system/role/deleteRoleUserRelation');
}

/*
 * 关联用户保存
 */
function saveAllRelevanceAuthorityAuxiliary() {
	var saveOrUpdateData = {
		sysAuthorityId : 0,
		sysRoleId : roleId,
		enabled : '是'
	};
	saveAllRelevanceAuxiliaryBase('main-table-role-authority',
			saveOrUpdateData, 'sysAuthorityId',
			'./admin/system/role/addRoleAuthorityRelation',
			'./admin/system/role/deleteRoleAuthorityRelation');
}

/*
 * 格式化设置角色链接
 */
function formatClick(value, row, index) {
	var s = '<a href="javascript:void(0);" class="link" onclick="relevanceUserMain('
			+ index + ')">设置用户</a>';
	var t = '&nbsp;&nbsp;<a href="javascript:void(0);" class="link" onclick="relevanceAuthorityMain('
			+ index + ')">设置权限</a>&nbsp;&nbsp;';
	var c = '<a href="javascript:void(0);" class="link" onclick="getAuthority('
			+ index + ')">已配置权限</a>';
	return s + t + c;
}

function getAuthority(num) {
	var row = $('#main-table').datagrid('getRows')[num];
	var title = "已配置权限" + "（" + row.name + "）";
	var roleId = row.id;
	$('#role-authority-all-table').datagrid({
				url : './admin/system/authority/findAllByRoleId?roleId='
						+ roleId,
				remoteSort : false,
				pagination : false
			});
	$('#role-authority-all').dialog('open').dialog('setTitle', title);
}

function queryAllRelevanceMain() {
	queryAllRelevanceMainBase('role-authority-name',
			'main-table-role-authority');
}

function queryAllRelevanceAll() {
	queryAllRelevanceAllBase('main-table-role-authority');
}