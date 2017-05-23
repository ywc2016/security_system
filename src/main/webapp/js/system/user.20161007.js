var editFlag = 0;// 标记是否处于编辑状态，是则为1，否则为0
var userId = 0;// 用来存放要关联的用户ID
var staffId = 0;
var checkType;// 审核权限类型

function showMain() {
	showMainBase();
}

function addMain() {
	addMainBase();
}

function addAuxiliary(userType) {
	var param = $("#add-name").val();
	var email = $('#add-email').val();
	if (param != null && param !== '' && email != null && email !== '') {
		$.ajax({
					url : './admin/system/add',
					type : 'post',
					data : $('#add-fm').serialize(),
					timeout : 5000,
					async : false,
					error : function() {
						$.messager.show({
									title : '提示',
									msg : '网络错误.'
								});
						$('#add').dialog('close'); // 关闭弹出框
						$('#main-table').datagrid('reload'); // 重新加载数据
					},
					success : function(data) {
						$.messager.show({
									title : '提示',
									msg : '添加成功.'
								});
						$('#add').dialog('close'); // 关闭弹出框
						$('#main-table').datagrid('reload'); // 重新加载数据
					}
				});
	}
}

function editMain() {
	editMainBase();
	var row = $('#main-table').datagrid('getSelections');
	// 对于单选情况name是不可修改的
	if (row.length == 1) {
		// 处理由于多项修改造成的style="display:none"属性，使name能正常显示
		$('#edit-name-auxiliary').attr('style', 'display:block');
		$('#edit-email-auxiliary').attr('style', 'display:block');
	}
	// 对于多选情况name是不可修改的
	else {
		$('#edit-name-auxiliary').attr('style', 'display:none');
		$('#edit-email-auxiliary').attr('style', 'display:none');
	}
}

function editAuxiliary() {
	editAuxiliaryBase('./admin/system/update', './admin/system/updateByKeys');
}

function disableUser() {
	var ids = getSelectionsIds();
	if (ids.length == 0) {
		$.messager.show({
					title : '提示',
					msg : '请选择记录.'
				});
		return;
	}
	$.messager.confirm('确认', "确认禁用" + ids.length + "条记录吗?", function(r) {
				if (r) {
					$.post('./admin/system/disabled', {
								idCommaString : ids.join(',')
							}, function(result) {
								$('#main-table').datagrid('reload'); // 重新加载数据
							});
				}
			});
}

function unRelateStaff() {
	var ids = getSelectionsIds();
	if (ids.length == 0) {
		$.messager.show({
					title : '提示',
					msg : '请选择记录.'
				});
		return;
	}
	$.messager.confirm('确认', "确认解除" + ids.length + "条记录的用户关联吗?", function(r) {
				if (r) {
					$.post('./admin/system/user/unRelateStaff', {
								idCommaString : ids.join(',')
							}, function(result) {
								$('#main-table').datagrid('reload'); // 重新加载数据
							});
				}
			});

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
	if ($('#query-name').length > 0 && $('#query-name').val() !== '') {
		queryParameter.name = $('#query-name').val();
	}
	if ($('#query-checkStatus').length > 0
			&& $('#query-checkStatus').combobox('getValue') !== '') {
		queryParameter.checkStatus = $('#query-checkStatus')
				.combobox('getValue');
	}
	if ($('#query-realName').length > 0 && $('#query-realName').val() !== '') {
		queryParameter.realName = $('#query-realName').val();
	}
	if ($('#query-email').length > 0 && $('#query-email').val() !== '') {
		queryParameter.email = $('#query-email').val();
	}
	if ($('#query-mobile').length > 0 && $('#query-mobile').val() !== '') {
		queryParameter.mobile = $('#query-mobile').val();
	}
	if ($('#query-workPhone').length > 0 && $('#query-workPhone').val() !== '') {
		queryParameter.workPhone = $('#query-workPhone').val();
	}
	if ($('#query-idCard').length > 0 && $('#query-idCard').val() !== '') {
		queryParameter.idCard = $('#query-idCard').val();
	}
	if ($('#query-fieldFirst').length > 0
			&& $('#query-fieldFirst').val() !== '') {
		queryParameter.fieldFirst = $('#query-fieldFirst').val();
	}
	if ($('#query-fieldSecond').length > 0
			&& $('#query-fieldSecond').val() !== '') {
		queryParameter.fieldSecond = $('#query-fieldSecond').val();
	}
	if ($('#query-fieldThird').length > 0
			&& $('#query-fieldThird').val() !== '') {
		queryParameter.fieldThird = $('#query-fieldThird').val();
	}
	if ($('#query-currentStatus').length > 0
			&& $('#query-currentStatus').combobox('getValue') !== '') {
		queryParameter.currentStatus = $('#query-currentStatus')
				.combobox('getValue');
	}
	if ($('#query-title').length > 0 && $('#query-title').val() !== '') {
		queryParameter.title = $('#query-title').val();
	}
	if ($('#query-description').length > 0
			&& $('#query-description').val() !== '') {
		queryParameter.description = $('#query-description').val();
	}
	// 注意combobox取值
	if ($('#query-enabled').length > 0
			&& $('#query-enabled').combobox('getValue') !== '') {
		queryParameter.enabled = $('#query-enabled').combobox('getValue');
	}
	$('#query').dialog('close');// 关闭弹出框
	$('#main-table').datagrid('reload', queryParameter);
}

function importMain() {
	importMainBase();
}

function importAuxiliary() {
	importAuxiliaryBase('./admin/system/user/importFromExcel');
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
	if (queryParameters.password !== undefined) {
		queryParametersString += 'password=' + queryParameters.password + '&';
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
	exportMainBase('./admin/system/user/exportToExcel', queryParametersString);
}
/*
 * 关联角色框
 */
function relevanceRoleMain(num) {
	var row = $('#main-table').datagrid('getRows')[num];
	var title = "设置角色" + "（" + row.name + "）";
	userId = row.id;
	relevanceMainBase('user-role', 'main-table-user-role',
			'tb-user-role-buttons', title,
			'./admin/system/findUserRoleByUserIdForPagination?userId=', userId);
}
function editAllRelevanceAuxiliary() {
	editAllRelevanceAuxiliaryBase('main-table-user-role');
}

function cancelAllRelevanceAuxiliary() {
	cancelAllRelevanceAuxiliaryBase('main-table-user-role');
}

function saveAllRelevanceAuxiliary() {
	var saveOrUpdateData = {
		sysUserId : userId,
		sysRoleId : 0,
		enabled : '是'
	};
	saveAllRelevanceAuxiliaryBase('main-table-user-role', saveOrUpdateData,
			'sysRoleId', './admin/system/addUserRole',
			'./admin/system/deleteUserRole');
}

/*
 * 审核权限
 */
function relevanceCheckMain(type) {
	var staffname = $('#find-check-name').val();
	var departmentName = $('#find-check-infDepartmentNumber')
			.combotree('getText');
	$('#find-check-name').textbox('setValue', '');
	$('#find-check-infDepartmentNumber').combobox('clear');
	if (type != null) {
		checkType = type;
	}
	var row = $('#main-table').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.show({
					title : '提示',
					msg : '请选择一个用户'
				});
		return;
	}
	var title = "设置权限" + "（" + row[0].name + "）";
	userId = row[0].id;
	$.ajax({
		url : "./admin/system/role/checkRoleJudge",
		type : 'POST',
		data : {
			userId : userId,
			type : checkType
		},
		success : function(result) {
			if (result == "SUCCESS") {
				relevanceMainBase(
						'user-check',
						'main-table-user-check',
						'tb-user-check-buttons',
						title,
						'./admin/system/checkAuthority/findCheckAuthorityByUserIdForPagination?userId=',
						userId + "&&type=" + checkType + "&&name=" + staffname
								+ "&&departmentName=" + departmentName);
			} else if (result == "ERROR") {
				$.messager.show({
							title : '错误',
							msg : '内部错误. '
						});
			} else {
				var s;
				switch (checkType) {
					case "insurance" :
						s = "本用户暂无保单审核员角色.";
						break;// 审核保单
					case "l-insurance" :
						s = "本用户暂无查看保单权限.";
						break;// 审核保单
					case "endorsement" :
						s = "本用户暂无批单审核员角色.";
						break;// 审核批单
					case "surrender" :
						s = "本用户暂无退保审核员角色.";
						break;// 审核退保
					case "contract" :
						s = "本用户暂无合同审核员角色.";
						break;// 审核合同
					case "l-contract" :
						s = "本用户暂无查看合同权限.";
						break;// 审核合同
					case "invoice" :
						s = "本用户暂无发票管理权限.";
						break;// 审核合同
					default :
						s = "本用户暂无审核员角色.";
				}
				alert(s);
				return;
			}
		},
		error : function() {
			$.messager.show({
						title : '错误',
						msg : '内部错误. '
					});
		}
	});
}
function editAllRelevanceAuxiliaryCheck() {
	editAllRelevanceAuxiliaryBase('main-table-user-check');
}

function cancelAllRelevanceAuxiliaryCheck() {
	cancelAllRelevanceAuxiliaryBase('main-table-user-check');
}

function saveAllRelevanceAuxiliaryCheck() {
	var saveOrUpdateData = {
		sysUserId : userId,
		businessStaffNumber : 0,
		type : checkType
	};
	saveAllRelevanceAuxiliaryBase('main-table-user-check', saveOrUpdateData,
			'businessStaffNumber',
			'./admin/system/checkAuthority/saveOrUpdate',
			'./admin/system/checkAuthority/deleteByKeys');
}

/*
 * 关联用户框
 */
function relevanceStaffMain(num) {

	var row = $('#main-table').datagrid('getRows')[num];
	if (row.staffRelate != 0) {
		$.messager.show({
					title : '错误',
					msg : '请先解除原员工关联. '
				});
		return;

	} else {
		var title = "关联员工" + "（" + row.name + "）";
		userId = row.id;
		staffId = row.staffRelate;
		relevanceMainBaseForSingle('user-staff', 'main-table-user-staff',
				'tb-user-staff-buttons', title,
				'./admin/staff/findByParamsForPaginationForSysUser?userId=',
				userId);
	}
}
function queryAllRelevanceAuxiliaryStaff() {
	$('#query-department-staff').combotree({
				url : './admin/department/findDepartmentTree',
				type : 'POST',
				timeout : 5000,
				success : function(data) {
					var treeJson = eval(data);
				},
				valueField : 'id',
				textField : 'text',
				editable : true,
				width : 236,
				panelHeight : 'auto'
				,
			});
	$('#query-staff').dialog('open').dialog('setTitle', '查询');
	$('#query-fm-staff').form('clear');

}
function querySectionAuxiliaryStaff() {
	$('#main-table-user-staff').datagrid('options').queryParams = {};
	var query = $('#main-table-user-staff').datagrid('options').queryParams;
	if ($('#query-number').val() != '') {
		query.number = $('#query-number-staff').val();
	}
	if ($('#query-name').val() != '') {
		query.name = $('#query-name-staff').val();
	}
	if ($('#query-departmentName-staff').combotree('uery-department-staff') != '') {
		query.departmentName = $('#uery-department-staff')
				.combotree('getValue');
	}
	$('#main-table-user-staff').datagrid('reload'); // 刷新页面
	$('#query-fm-staff').form('clear');
	$('#query-staff').dialog('close');
}
/**
 * 查询全部
 */
function queryAllAuxiliaryStaff() {

	$('#main-table-user-staff').datagrid('options').queryParams = null;
	$('#query-staff').dialog('close');// 关闭弹出框
	$('#main-table-user-staff').datagrid('reload');// 重新加载数据
}

/*
 * function editAllRelevanceAuxiliaryStaff() {
 * editAllRelevanceAuxiliaryBase('main-table-user-staff'); }
 * 
 * function cancelAllRelevanceAuxiliaryStaff() {
 * cancelAllRelevanceAuxiliaryBase('main-table-user-staff'); }
 */
function unCheckRows() {
	$('#main-table-user-staff').datagrid('uncheckAll');
}
function saveAllRelevanceAuxiliaryStaff() {
	/* 这里是保存的方法 */
	var ids = getSelectionsIds('id', 'main-table-user-staff'); // 获取当前页的所有行
	if (ids[0] == staffId) {
		$.messager.show({
					title : '提示',
					msg : '已关联.'
				});
		return;
	}
	$.ajax({
				url : "./admin/system/user/relateToStaff",
				type : 'POST',
				data : {
					id : userId,
					staffId : ids[0]
				},
				// dataType : 'json',
				success : function(result) {
					if (result == "SUCCESS") {
						$('#main-table-user-staff').datagrid('reload');
						$('#main-table').datagrid('reload');// 上面的ajax使用同步方式，全部执行完后刷新数据，否则数据不一致
						$.messager.show({
									title : '提示',
									msg : '关联成功. '
								});
						$('#user-staff').dialog('close');
					} else if (result == "EXISTUSER") {
						$('#main-table-user-staff').datagrid('reload');// 上面的ajax使用同步方式，全部执行完后刷新数据，否则数据不一致
						$.messager.show({
									title : '错误',
									msg : '该员工已关联其他用户. '
								});
					} else if (result == "UNRELATE") {
						$('#main-table-user-staff').datagrid('reload');// 上面的ajax使用同步方式，全部执行完后刷新数据，否则数据不一致
						$.messager.show({
									title : '提示',
									msg : '已取消关联. '
								});
					}
				},
				error : function() {
					$.messager.show({
								title : '错误',
								msg : '关联失败. '
							});
				}
			});

}

function checkedMain() {
	var ids = getSelectionsIds();
	if (ids.length == 0) {
		$.messager.show({
					title : '提示',
					msg : '请选择记录.'
				});
		return;
	}
	$('#verify').dialog('open').dialog('setTitle', '审核');
	$('#verify-fm').form('clear');
}

// 实名用户审核
function checkedRealUserAuxiliary() {
	var ids = getSelectionsIds();
	var checkedStatus = $('#verify-checkStatus').combobox('getValue');
	// 删除用户原有角色
	$.ajax({
		type : 'POST',
		url : './admin/system/userRole/deleteBySysUserId',
		data : {
			idCommaString : ids.join(',')
		},
		dataType : 'json',
		async : false
			// 同步方式
		});
	var sysRoleId = 10; // 不通过，关联普通用户角色
	if (checkedStatus == '通过') {
		sysRoleId = 18; // 通过，则关联实名用户角色
	}
	// 关联用户角色
	for (var i = 0; i < ids.length; i++) {
		$.ajax({
			type : 'POST',
			url : './admin/system/userRole/saveOrUpdate',
			data : {
				sysUserId : ids[i],
				sysRoleId : sysRoleId,
				enabled : '是'
			},
			dataType : 'json',
			async : false
				// 同步方式
			});
	}
	// 发送审核提醒
	$.ajax({
		type : 'POST',
		url : './admin/system/user/remindRealNameUser',
		data : {
			idCommaString : ids.join(','),
			text : $('#verify-text').val(),
			checkedStatus : checkedStatus
			,
		}
		,
			// 同步方式
		});
	checkedBase(checkedStatus); // 修改审核状态
}

/*
 * 实名用户审核通过
 */
function checkedPassMain() {
	var ids = getSelectionsIds();
	if (ids.length == 0) {
		$.messager.show({
					title : '提示',
					msg : '请选择记录.'
				});
		return;
	}
	checkedBase('通过'); // 修改审核状态
	// 删除用户原有角色
	$.ajax({
		type : 'POST',
		url : './admin/system/userRole/deleteBySysUserId',
		data : {
			idCommaString : ids.join(',')
		},
		dataType : 'json',
		async : false
		,
			// 同步方式
		});
	// 关联实名用户角色
	for (var i = 0; i < ids.length; i++) {
		$.ajax({
			type : 'POST',
			url : './admin/system/userRole/saveOrUpdate',
			data : {
				sysUserId : ids[i],
				sysRoleId : 18,
				enabled : '是'
			},
			dataType : 'json',
			async : false
				// 同步方式
			});
	}
}

function checkedUnpassMain() {
	var ids = getSelectionsIds();
	if (ids.length == 0) {
		$.messager.show({
					title : '提示',
					msg : '请选择记录.'
				});
		return;
	}
	checkedBase('不通过');

	// 删除用户原有角色
	$.ajax({
		type : 'POST',
		url : './admin/system/userRole/deleteBySysUserId',
		data : {
			idCommaString : ids.join(',')
		},
		dataType : 'json',
		async : false
			// 同步方式
		});

	// 关联普通用户角色
	for (var i = 0; i < ids.length; i++) {
		$.ajax({
			type : 'POST',
			url : './admin/system/userRole/saveOrUpdate',
			data : {
				sysUserId : ids[i],
				sysRoleId : 10,
				enabled : '是'
			},
			dataType : 'json',
			async : false
				// 同步方式
			});
	}
}

function checkedBase(checkedStatus) {
	var ids = getSelectionsIds();
	$.post('./admin/system/user/updateByKeys', {
				idCommaString : ids.join(','),
				checkStatus : checkedStatus
			}, function(result) {
				$('#verify').dialog('close'); // 关闭弹出框
				$('#main-table').datagrid('reload'); // 重新加载数据
			});
}

function lockMain() {
	var ids = getSelectionsIds();
	if (ids.length == 0) {
		$.messager.show({
					title : '提示',
					msg : '请选择记录.'
				});
		return;
	}
	var ids = getSelectionsIds();
	$.post('./admin/system/user/updateByKeys', {
				idCommaString : ids.join(','),
				enabled : '否'
			}, function(result) {
				$('#main-table').datagrid('reload'); // 重新加载数据
			});

}

function unlockMain() {
	var ids = getSelectionsIds();
	if (ids.length == 0) {
		$.messager.show({
					title : '提示',
					msg : '请选择记录.'
				});
		return;
	}
	var ids = getSelectionsIds();
	$.post('./admin/system/user/updateByKeys', {
				idCommaString : ids.join(','),
				enabled : '是'
			}, function(result) {
				$('#main-table').datagrid('reload'); // 重新加载数据
			});

}

/*
 * 密码重置框
 */
function resetPasswordMain() {
	var row = $('#main-table').datagrid('getSelections');
	if (row.length == 0) {
		$.messager.show({
					title : '提示',
					msg : '请选择用户.'
				});
		return;
	}
	// 初始密码italents_2015
	$('#reset-password').val('italents_2015');
	$('#reset').dialog('open').dialog('setTitle', '重置');
}
/*
 * 密码重置方法
 */
function resetPasswordAuxiliary() {
	var ids = getSelectionsIds();
	$('#reset-idCommaString').val(ids.join(','));

	if ($('#reset-fm').form('validate')) {
		$.ajax({
					url : './admin/system/user/resetPassword',
					type : 'post',
					timeout : 10000,
					data : $('#reset-fm').serialize(),
					error : function() {
						$.messager.show({
									title : '错误',
									msg : '网络错误.'
								});
					},
					success : function(data) {
						if (data == 'weakPwd') {
							$.messager.show({
										title : '错误',
										msg : '密码强度不符合要求，请重新输入.'
									});
						} else {
							$('#reset').dialog('close'); // 关闭弹出框
							$('#main-table').datagrid('reload'); // 重新加载数据
						}
					}
				});
	}
}

/*
 * 添加单击按钮样式与事件
 */
function formatClick(value, row, index) {
	var s = '<a href="javascript:void(0);" class="link" onclick="relevanceRoleMain('
			+ index + ')">设置角色</a>';
	return s;
}

function formatClickForSys(value, row, index) {
	var s = '<a href="javascript:void(0);" class="link" onclick="relevanceRoleMain('
			+ index + ')">设置角色</a>&nbsp;|&nbsp;';
	var h = '<a href="javascript:void(0);" class="link" onclick="relevanceStaffMain('
			+ index + ')">绑定员工</a>';
	return s + "" + h;
}
function toRealUserMain() {
	var ids = getSelectionsIds();
	if (ids.length == 0) {
		$.messager.show({
					title : '提示',
					msg : '请选择记录.'
				});
		return;
	}
	var ids = getSelectionsIds();
	$.post('./admin/system/user/toRealUser', {
				idCommaString : ids.join(',')
			}, function(result) {
				$('#main-table').datagrid('reload'); // 重新加载数据
				$.messager.show({
							title : '提示',
							msg : '设置成功，请到实名用户表查看.'
						});
			});
}

// 删除
function deleteMain() {
	var actionUrl = './admin/system/user/userCancellation';
	var ids = getSelectionsIds();
	if (ids.length == 0) {
		$.messager.show({
					title : '提示',
					msg : '请选择记录.'
				});
		return;
	}
	$.messager.confirm('确认', "确认注销" + ids.length + "条记录吗?", function(r) {
				if (r) {
					$.post(actionUrl, {
								idCommaString : ids.join(',')
							}, function(result) {
								$('#main-table').datagrid('reload'); // 重新加载数据
							});
				}
			});
}