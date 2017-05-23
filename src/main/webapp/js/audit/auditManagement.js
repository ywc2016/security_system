$(function() {
			$('#main-table').datagrid({
				url : './admin/document/findByParamsForPagination',
				type : 'post',
				columns : [[{
							field : 'ck',
							title : '',
							checkbox : true
						}, {
							field : 'id',
							title : 'ID',
							width : 35,
							sortable : true,
							hidden : true
						}, {
							field : 'number',
							title : '文档编号',
							width : 150,
							align : 'center',
							sortable : true
						}, {
							field : 'name',
							title : '文档名称',
							width : 150,
							align : 'center',
							sortable : true
						}, {
							field : 'path',
							title : '文档路径',
							width : 150,
							align : 'center',
							sortable : true,
							hidden : true
						}, {
							field : 'auditStatus',
							title : '审核状态',
							width : 100,
							align : 'center',
							sortable : true,
							formatter : function(val, row) {
								if (val === '通过') {
									return '<span style="color:green;">' + '通过'
											+ '</span>';
								} else if (val === '未通过') {
									return '<span style="color:red;">' + '未通过'
											+ '</span>';
								} else if (val === '待审核') {
									return '<span >' + '待审核' + '</span>';
								}
							}
						}, {
							field : 'uploadTime',
							title : '上传时间',
							width : 150,
							align : 'center',
							sortable : true,
							formatter : formatDateFull
						}, {
							field : 'encryptionStatus',
							title : '加密状态',
							width : 50,
							align : 'center',
							sortable : true,
							formatter : function(val, row) {
								if (val === 'plain') {
									return '<span style="color:green;">' + '明文'
											+ '</span>';
								} else if (val === 'cipher') {
									return '<span style="color:red;">' + '密文'
											+ '</span>';
								}
							}
						}, {
							field : 'shareStatus',
							title : '共享状态',
							width : 150,
							align : 'center',
							sortable : true,
							formatter : function(val, row) {
								if (val === '未共享') {
									return '<span style="color:gray;">' + '未共享'
											+ '</span>';
								} else if (val === '正在共享') {
									return '<span style="color:blue;">'
											+ '正在共享' + '</span>';
								}
							}
						}, {
							field : 'size',
							title : '文档大小',
							width : 150,
							align : 'center',
							sortable : true
						}, {
							field : 'type',
							title : '文档类型',
							width : 150,
							align : 'center',
							sortable : true
						}, /*
							 * { field : 'encryptMethod', title : '加密方法', width :
							 * 100, align : 'center' }, { field : 'encryptCode',
							 * title : '加密秘钥', width : 100, align : 'center' },
							 */{
							field : 'viewTimes',
							title : '浏览次数',
							width : 100,
							align : 'center',
							hidden : true
						}, {
							field : 'downloadTimes',
							title : '下载次数',
							width : 100,
							align : 'center'
						}, {
							field : 'abstracts',
							title : '摘要',
							width : 100,
							align : 'center'
						}]],
				sortName : 'id',
				sortOrder : 'asc',
				toolbar : '#toolbar',
				onLoadError : function(data) {
					$.messager.show({
								title : '错误',
								msg : '操作失败.'
							});
				}
			});
		});

function queryMain() {
	$('#mainQuery').dialog('open').dialog('setTitle', '查询');
	$('#mainQuery-fm').form('clear');
}

function queryAuxiliary() {
	$('#main-table').datagrid('options').queryParams = {};
	var query = $('#main-table').datagrid('options').queryParams;

	if ($('#number').textbox('getValue') != '') {
		query.number = $('#number').textbox('getValue');
	}
	if ($('#name').textbox('getValue') != '') {
		query.name = $('#name').textbox('getValue');
	}
	if ($('#path').textbox('getValue') != '') {
		query.path = $('#path').textbox('getValue');
	}
	if ($('#uploadTime').datetimebox('getValue') != '') {
		query.uploadTime = $('#uploadTime').datetimebox('getValue');
	}
	if ($('#size').textbox('getValue') != '') {
		query.size = $('#size').textbox('getValue');
	}
	if ($('#type').textbox('getValue') != '') {
		query.type = $('#type').textbox('getValue');
	}
	if ($('#encryptMethod').textbox('getValue') != '') {
		query.encryptMethod = $('#encryptMethod').textbox('getValue');
	}
	if ($('#encryptCode').textbox('getValue') != '') {
		query.encryptCode = $('#encryptCode').textbox('getValue');
	}
	if ($('#viewTimes').textbox('getValue') != '') {
		query.viewTimes = $('#viewTimes').textbox('getValue');
	}
	if ($('#downloadTimes').textbox('getValue') != '') {
		query.downloadTimes = $('#downloadTimes').textbox('getValue');
	}
	$('#main-table').datagrid('reload'); // 刷新页面
	$('#mainQuery-fm').form('clear');
	$('#mainQuery').dialog('close');
}

function queryAllAuxiliary() {
	$('#main-table').datagrid('options').queryParams = null;
	$('#mainQuery').dialog('close');// 关闭弹出框
	$('#main-table').datagrid('reload');// 重新加载数据
}

function formatPreview(value, row, index) {
	if (value != null && value != "") {
		var s = '<a style="color:green;" href = ".' + value
				+ '" target="_blank">' + value + '</a>';
		return s;
	} else {
		return "无附件";
	}
}

function pass() {
	var rows = $('#main-table').datagrid('getSelections');
	if (rows == 0) {
		$.messager.show({
					title : '提示',
					msg : '请选择一个文档'
				});
		return;

	}
	if (rows[0].auditStatus != "待审核") {
		$.messager.show({
					title : '提示',
					msg : '只能通过待审核的文档'
				});
		return;

	}
	$.ajax({
				url : './admin/audit/passAudit',
				type : 'post',
				data : {
					id : rows[0].id
				},
				success : function(data) {
					$.messager.show({
								title : '提示',
								msg : '审核成功!'
							});
					$('#main-table').datagrid('reload');
				},
				error : function() {
					$.messager.show({
								title : '提示',
								msg : '审核失败'
							});
					$('#main-table').datagrid('reload');
				}
			});
}

function notPass() {
	var rows = $('#main-table').datagrid('getSelections');
	if (rows == 0) {
		$.messager.show({
					title : '提示',
					msg : '请选择一个文档'
				});
		return;

	}
	if (rows[0].auditStatus != "待审核") {
		$.messager.show({
					title : '提示',
					msg : '只能通过待审核的文档'
				});
		return;
	}
	$.ajax({
				url : './admin/audit/notPassAudit',
				type : 'post',
				data : {
					id : rows[0].id
				},
				success : function(data) {
					$.messager.show({
								title : '提示',
								msg : '审核成功!'
							});
					$('#main-table').datagrid('reload');
				},
				error : function() {
					$.messager.show({
								title : '提示',
								msg : '审核失败'
							});
					$('#main-table').datagrid('reload');
				}
			});
}
