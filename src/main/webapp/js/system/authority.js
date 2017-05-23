var editFlag = 0;// 标记是否处于编辑状态，是则为1，否则为0
var authorityId = 0;// 用来存放要关联的权限ID

function showMain() {
	showMainBase();
}

function addMain() {
	addMainBase();
}

function addAuxiliary() {
	addAuxiliaryBase('./admin/system/authority/save');
}

function editMain() {
	editMainBase();
	var row = $('#main-table').datagrid('getSelections');
	// 对于单选情况name是可修改的
	if (row.length === 1) {
		$('#edit-name-auxiliary').attr('style', 'display:block');
	}
	// 对于多选情况name是不可修改的
	else {
		$('#edit-name-auxiliary').attr('style', 'display:none');
	}
}

function editAuxiliary() {
	editAuxiliaryBase('./admin/system/authority/update',
			'./admin/system/authority/updateByKeys');
}

function deleteMain() {
	deleteMainBase('./admin/system/authority/deleteByKeys');
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
	importAuxiliaryBase('./admin/system/authority/importFromExcel');
}
/*
 * 导出
 */
function exportMain() {
	var queryParameters = $('#main-table').datagrid('options').queryParams;
	var queryParametersString = '';
	if (queryParameters.name != undefined) {
		queryParametersString += 'name=' + queryParameters.name + '&';
	}
	if (queryParameters.description != undefined) {
		queryParametersString += 'description=' + queryParameters.description
				+ '&';
	}
	if (queryParameters.enabled != undefined) {
		queryParametersString += 'enabled=' + queryParameters.enabled + '&';
	}

	if (queryParametersString != '') {
		queryParametersString = queryParametersString.substring(0,
				queryParametersString.length - 1);
	}
	exportMainBase('./admin/system/authority/exportToExcel',
			queryParametersString);
}

/*
 * 角色关联框
 */
function relevanceRoleMain(num) {
	var row = $('#main-table').datagrid('getRows')[num];
	var title = "设置角色" + "（" + row.name + "）";
	authorityId = row.id;
	relevanceMainBase(
			'role-authority',
			'main-table-role-authority',
			'tb-role-authority-buttons',
			title,
			'./admin/system/role/findRoleAuthorityByAuthorityIdForPagination?authorityId=',
			authorityId);
}

/*
 * 资源关联框
 */
function relevanceResourceMain(num) {
	var row = $('#main-table').datagrid('getRows')[num];
	var title = "设置资源" + "（" + row.name + "）";
	authorityId = row.id;
	relevanceMainBase(
			'authority-resource',
			'main-table-authority-resource',
			'tb-authority-resource-buttons',
			title,
			'./admin/system/resource/findAuthorityResourceByAuthorityIdForPagination?authorityId=',
			authorityId);
}

/*
 * 进入角色关联编辑状态
 */
function editAllRelevanceRoleAuxiliary() {
	editAllRelevanceAuxiliaryBase('main-table-role-authority');
}
/*
 * 进入资源关联编辑状态
 */
function editAllRelevanceResourceAuxiliary() {
	editAllRelevanceAuxiliaryBase('main-table-authority-resource');
}

/*
 * 取消角色关联编辑状态
 */
function cancelAllRelevanceRoleAuxiliary() {
	cancelAllRelevanceAuxiliaryBase('main-table-role-authority');
}

/*
 * 取消资源关联编辑状态
 */
function cancelAllRelevanceResourceAuxiliary() {
	cancelAllRelevanceAuxiliaryBase('main-table-authority-resource');
}
/*
 * 角色关联保存
 */
function saveAllRelevanceRoleAuxiliary() {
	var saveOrUpdateData = {
		sysRoleId : 0,
		sysAuthorityId : authorityId,
		enabled : '是'
	};
	saveAllRelevanceAuxiliaryBase('main-table-role-authority',
			saveOrUpdateData, 'sysRoleId',
			'./admin/system/roleAuthority/addRoleAuthority',
			'./admin/system/roleAuthority/deleteRoleAuthority');
}
/*
 * 资源关联保存
 */
function saveAllRelevanceResourceAuxiliary() {
	var saveOrUpdateData = {
		sysResourceId : 0,
		sysAuthorityId : authorityId,
		enabled : '是'
	};
	saveAllRelevanceAuxiliaryBase('main-table-authority-resource',
			saveOrUpdateData, 'sysResourceId',
			'./admin/system/authorityResource/saveOrUpdate',
			'./admin/system/authorityResource/deleteByKeys');
}

function getResource(num) {
	var row = $('#main-table').datagrid('getRows')[num];
	var title = "已配置资源" + "（" + row.name + "）";
	var authorityId = row.id;
	$('#authority-resource-all-table').datagrid({
		url : './admin/system/resource/findAllByAuthorityId?authorityId='
				+ authorityId,
		remoteSort : false,
		pagination : false
	});
	$('#authority-resource-all').dialog('open').dialog('setTitle', title);
}

/*
 * 格式化设置角色和权限链接
 */
function formatClick(value, row, index) {
	var s = '<a href="javascript:void(0);" class="link" onclick="relevanceRoleMain('
			+ index + ')">设置角色</a>  ';
	// var t = ' <a href="javascript:void(0);" class="link"
	// onclick="relevanceResourceMain('
	// + index + ')">设置资源</a>&nbsp;&nbsp;';
	// var c = '<a href="javascript:void(0);" class="link"
	// onclick="getResource('
	// + index + ')">已配置资源</a>';
	return s;
}

function queryAllAuthorityResourceMain() {
	queryAllRelevanceMainBase('authority-resource-name',
			'main-table-authority-resource');
}

function queryAllAuthorityResourceAll() {
	queryAllRelevanceAllBase('main-table-authority-resource');
}

function queryAllRoleAuthorityMain() {
	queryAllRelevanceMainBase('role-authority-name',
			'main-table-role-authority');
}

function queryAllRoleAuthorityAll() {
	queryAllRelevanceAllBase('main-table-role-authority');
}