/*
 * 扩展验证规则fileType,使用方法
 * $('#id').validatebox({
 * 		validType : 'fileType["xls","xlsx"]'
 * })
 */
$.extend($.fn.validatebox.defaults.rules, {
	fileType : {
		validator : function(value, param) {
			var type = value.substring(value.lastIndexOf(".") + 1)
					.toLowerCase();
			var flag = false;
			$.each(param, function(index, val) {
				if (type == val) {
					flag = true;
					return false;
				}
			});
			return flag;
		},
		message : '文件格式错误!'
	},
	fileSize : {
		validator : function(value, param) {
			var f = $(param[0])[0].files[0];
			var size = f.size || f.fileSize;
			return size < (param[1] * 1024 * 1024);
		},
		message : '文件不能超过{1}M!'
	}
});

/**
 * 获取选中行的主键 JavaScript判断时是false的值：0, "" null, undefined, NaN
 * 
 * @param id
 *            可选参数,默认为id
 * @param tableId
 *            可选参数,默认为main-table
 * @returns {Array}
 */
function getSelectionsIds(id, tableId) {
	id = arguments[0] ? arguments[0] : 'id';
	tableId = arguments[1] ? arguments[1] : 'main-table';
	tableId = '#' + tableId;
	var ids = [];
	var rows = $(tableId).datagrid('getSelections');
	for (var i = 0; i < rows.length; i++) {
		ids.push(rows[i][id]);
	}
	return ids;
}

/**
 * 查看框
 */
function showMainBase() {
	var rows = $('#main-table').datagrid('getSelections');
	if (rows.length != 1) {
		$.messager.show({
			title : '提示',
			msg : '请选择一条记录!'
		});
		return;
	}
	$('#show').dialog('open').dialog('setTitle', '查看');
	$('#show-fm').form('load', rows[0]);
}

/**
 * 添加框
 */
function addMainBase() {
	$('#add').dialog('open').dialog('setTitle', '添加');
	$('#add-fm').form('clear');
	$('#add-enabled').attr("value", true);
	// 处理主键字段
	$('#add-id').attr('readonly', true);
}

/**
 * 添加结果保存
 * 
 * @param actionUrl
 */
function addAuxiliaryBase(actionUrl) {
	if ($('#add-fm').form('validate')) {
		$.ajax({
			url : actionUrl,
			type : 'post',
			timeout : 10000,
			data : $('#add-fm').serialize(),
			error : function() {
				$.messager.show({
					title : '错误',
					msg : '网络错误!'
				});
			},
			success : function(data) {
				$('#add').dialog('close'); // 关闭弹出框
				$('#main-table').datagrid('reload'); // 重新加载数据
			}
		});
	}
}

/**
 * 编辑框
 */
function editMainBase() {
	var row = $('#main-table').datagrid('getSelections');
	if (row.length == 0) {
		$.messager.show({
			title : '提示',
			msg : '请选择记录!'
		});
		return;
	}
	$('#edit').dialog('open').dialog('setTitle', '修改');
	if (row.length == 1) {
		$('#edit-fm').form('load', row[0]);
	} else {
		$('#edit-fm').form('clear');
	}
}

/**
 * 编辑结果保存
 * 
 * @param actionUrl1
 * @param actionUrl2
 */
function editAuxiliaryBase(actionUrl1, actionUrl2) {
	var row = $('#main-table').datagrid('getSelections');
	// 下面是单个修改
	if (row.length == 1) {
		if ($('#edit-fm').form('validate')) {
			$.ajax({
				url : actionUrl1,
				type : 'post',
				timeout : 10000,
				data : $('#edit-fm').serialize(),
				error : function() {
					$.messager.show({
						title : '错误',
						msg : '网络错误!'
					});
				},
				success : function(data) {
					if (data != null && data != "" && data == "repeat") {
						$.messager.show({
							title : '错误',
							msg : '用户名或者邮箱重复!'
						});
					}
					$('#edit').dialog('close'); // 关闭弹出框
					$('#main-table').datagrid('reload'); // 重新加载数据
				}
			});
		}
	}
	// 下面是批量修改
	else if (row.length > 1) {
		var ids = getSelectionsIds();

		$.ajax({
			url : actionUrl2 + '?idCommaString=' + ids.join(','),
			type : 'post',
			timeout : 10000,
			data : $('#edit-fm').serialize(),
			error : function() {
				$.messager.show({
					title : '错误',
					msg : '网络错误!'
				});
			},
			success : function(data) {
				$('#edit').dialog('close'); // 关闭弹出框
				$('#main-table').datagrid('reload'); // 重新加载数据
			}
		});

	}
}

/**
 * 删除框
 * 
 * @param actionUrl
 */
function deleteMainBase(actionUrl) {
	var ids = getSelectionsIds();
	if (ids.length == 0) {
		$.messager.show({
			title : '提示',
			msg : '请选择记录!'
		});
		return;
	}
	$.messager.confirm('确认', "确认删除" + ids.length + "条记录吗?", function(r) {
		if (r) {
			$.post(actionUrl, {
				idCommaString : ids.join(',')
			}, function(result) {
				$('#main-table').datagrid('reload'); // 重新加载数据
			});
		}
	});
}

/**
 * 查询框
 */
function queryMainBase() {
	$('#query').dialog('open').dialog('setTitle', '查询');
	$('#query-fm').form('clear');
}

/**
 * 查询全部
 */
function queryAllAuxiliaryBase() {
	$('#main-table').datagrid('options').queryParams = null;
	$('#query').dialog('close');// 关闭弹出框
	$('#main-table').datagrid('reload');// 重新加载数据
}

/**
 * 导入框
 */
function importMainBase() {
	$('#import').dialog('open').dialog('setTitle', '导入');
}

/**
 * 导入上传
 * 
 * @param actionUrl
 */
function importAuxiliaryBase(actionUrl) {
	$('#import-fm').form('submit', {
		url : actionUrl,
		success : function(data) {
			$.messager.alert('信息', '导入完毕!', 'info');
			$('#import').dialog('close');// 关闭弹出框
			$('#main-table').datagrid('reload'); // 重新加载数据
		}
	});
}

/**
 * 导出
 * 
 * @param actionUrl
 * @param queryParametersString
 */
function exportMainBase(actionUrl, queryParametersString) {
	$.messager.confirm('确认', '确定将全部数据导出吗?', function(r) {
		if (r) {
			window.open(actionUrl + '?' + encodeURI(queryParametersString));
		}
	});
}

/**
 * 关联框
 * 
 * @param dialogId
 * @param tableId
 * @param toolbarId
 * @param title
 * @param actionAndParameterUrl
 * @param parameterValue
 */
function relevanceMainBase(dialogId, tableId, toolbarId, title,
		actionAndParameterUrl, parameterValue) {
	$('#' + tableId).datagrid({
		url : actionAndParameterUrl + parameterValue,
		sortName : 'name',
		toolbar : '#' + toolbarId,
		onBeforeEdit : function(index, row) {
			row.editing = true;
			$('#' + tableId).datagrid('refreshRow', index);
		},
		onAfterEdit : function(index, row) {
			row.editing = false;
			$('#' + tableId).datagrid('refreshRow', index);
		},
		onCancelEdit : function(index, row) {
			row.editing = false;
			$('#' + tableId).datagrid('refreshRow', index);
		}
	});
	$('#' + dialogId).dialog('open').dialog('setTitle', title);
}

function relevanceMainBaseForSingle(dialogId, tableId, toolbarId, title,
		actionAndParameterUrl, parameterValue) {
	$('#' + tableId).datagrid({
		url : actionAndParameterUrl + parameterValue,
		sortName : 'name',
		singleSelect:true,
		checkOnSelect:true,
		toolbar : '#' + toolbarId,
		onBeforeEdit : function(index, row) {
			row.editing = true;
			$('#' + tableId).datagrid('refreshRow', index);
		},
		onAfterEdit : function(index, row) {
			row.editing = false;
			$('#' + tableId).datagrid('refreshRow', index);
		},
		onCancelEdit : function(index, row) {
			row.editing = false;
			$('#' + tableId).datagrid('refreshRow', index);
		}
	});
	$('#' + dialogId).dialog('open').dialog('setTitle', title);
}

/**
 * 进入关联编辑状态
 * 
 * @param tableId
 */
function editAllRelevanceAuxiliaryBase(tableId) {
	editFlag = 1;
	var rows = $('#' + tableId).datagrid("getRows"); // 获取当前页的所有行
	for (var i = 0; i < rows.length; i++) {
		$('#' + tableId).datagrid('beginEdit', i);
	}
}

/**
 * 取消关联编辑状态
 * 
 * @param tableId
 */
function cancelAllRelevanceAuxiliaryBase(tableId) {
	editFlag = 0;
	var rows = $('#' + tableId).datagrid("getRows"); // 获取当前页的所有行
	for (var i = 0; i < rows.length; i++) {
		$('#' + tableId).datagrid('cancelEdit', i);
	}
}

/**
 * 关联保存
 * 
 * @param tableId
 * @param saveOrUpdateData
 * @param saveOrUpdateDataDynamicAttributeName
 * @param actionUrl1
 * @param actionUrl2
 */
function saveAllRelevanceAuxiliaryBase(tableId, saveOrUpdateData,
		saveOrUpdateDataDynamicAttributeName, actionUrl1, actionUrl2) {
	if (editFlag == 0) {
		$.messager.show({
			title : '提示',
			msg : '进入编辑状态才可以进行保存!'
		});
		return;
	}
	/* 这里是保存的方法 */
	var rows = $('#' + tableId).datagrid('getRows'); // 获取当前页的所有行
	var relevanceBefore = 0;// 存储编辑之前的关联情况
	for (var i = 0; i < rows.length; i++) {
		relevanceBefore = rows[i].relevance;
		$('#' + tableId).datagrid('endEdit', i);
		var row = $('#' + tableId).datagrid('getRows')[i];
		/* 更改前后的relevance值不相同，需要对数表进行处理 */
		if (relevanceBefore != row.relevance) {
			saveOrUpdateData[saveOrUpdateDataDynamicAttributeName] = row.id;
			/* 将不关联改为关联，需要将记录插入表中 */
			if (row.relevance == 1) {
				$.ajax({
					type : 'POST',
					url : actionUrl1,
					data : saveOrUpdateData,
					dataType : 'json',
					async : false
				// 同步方式
				});
			}
			/* 将关联改为不关联，需要删除原纪录 */
			else {
				var ids ="0";
				if(row.relevanceId!=null){
					ids = row.relevanceId;
				}
				$.ajax({
					type : 'POST',
					url : actionUrl2,
					data : {
						idCommaString : ids
					},
					dataType : 'json',
					async : false
				});
			}
		}
	}

	$.messager.show({
		title : '提示',
		msg : '保存成功!'
	});
	editFlag = 0;
	$('#' + tableId).datagrid('reload');// 上面的ajax使用同步方式，全部执行完后刷新数据，否则数据不一致
}

function saveAllRelevanceAuxiliaryBaseForCheck(tableId, saveOrUpdateData,
		saveOrUpdateDataDynamicAttributeName, actionUrl1, actionUrl2) {
	if (editFlag == 0) {
		$.messager.show({
			title : '提示',
			msg : '进入编辑状态才可以进行保存!'
		});
		return;
	}
	/* 这里是保存的方法 */
	var rows = $('#' + tableId).datagrid('getRows'); // 获取当前页的所有行
	var relevanceBefore = 0;// 存储编辑之前的关联情况
	for (var i = 0; i < rows.length; i++) {
		relevanceBefore = rows[i].relevance;
		$('#' + tableId).datagrid('endEdit', i);
		var row = $('#' + tableId).datagrid('getRows')[i];
		/* 更改前后的relevance值不相同，需要对数表进行处理 */
		if (relevanceBefore != row.relevance) {
			saveOrUpdateData[saveOrUpdateDataDynamicAttributeName] = row.number;
			/* 将不关联改为关联，需要将记录插入表中 */
			if (row.relevance == 1) {
				$.ajax({
					type : 'POST',
					url : actionUrl1,
					data : saveOrUpdateData,
					dataType : 'json',
					async : false
				// 同步方式
				});
			}
			/* 将关联改为不关联，需要删除原纪录 */
			else {
				$.ajax({
					type : 'POST',
					url : actionUrl2,
					data : {
						idCommaString : row.relevanceId
					},
					dataType : 'json',
					async : false
				});
			}
		}
	}

	$.messager.show({
		title : '提示',
		msg : '保存成功!'
	});
	editFlag = 0;
	$('#' + tableId).datagrid('reload');// 上面的ajax使用同步方式，全部执行完后刷新数据，否则数据不一致
}

/**
 * 获取title
 * 
 * @returns
 */
function getTitle() {
	return $('.panel,.datagrid').find('.panel-title');
}

/**
 * 改变title
 * 
 * @param html
 */
function changeTitle(html) {
	getTitle().html(html);
}

/**
 * 查询关联记录
 * 
 * @param input
 * @param tableId
 */
function queryAllRelevanceMainBase(input, tableId) {
	var name = $('#' + input).val();
	if (name != null && name != '') {
		$('#' + tableId).datagrid('options').queryParams.name = name;
		$('#' + tableId).datagrid('reload');
	}
}

/**
 * 查询关联记录
 * 
 * @param tableId
 */
function queryAllRelevanceAllBase(tableId) {
	$('#' + tableId).datagrid('options').queryParams.name = "";
	$('#' + tableId).datagrid('reload');
}

/**
 * 扩展
 */
$.extend($.fn.datagrid.defaults.editors, {
	datebox : {
		init : function(container, options) {
			var input = $('<input type="text">').appendTo(container);
			input.datebox(options);
			return input;
		},
		destroy : function(target) {
			$(target).datebox('destroy');
		},
		getValue : function(target) {
			return $(target).datebox('getValue');
		},
		setValue : function(target, value) {
			$(target).datebox('setValue', formatDatebox(value));
		},
		resize : function(target, width) {
			$(target).datebox('resize', width);
		}
	},
	datetimebox : {
		init : function(container, options) {
			var input = $('<input type="text">').appendTo(container);
			input.datetimebox(options);
			return input;
		},
		destroy : function(target) {
			$(target).datetimebox('destroy');
		},
		getValue : function(target) {
			return $(target).datetimebox('getValue');
		},
		setValue : function(target, value) {
			$(target).datetimebox('setValue', formatDateBoxFull(value));
		},
		resize : function(target, width) {
			$(target).datetimebox('resize', width);
		}
	}
});