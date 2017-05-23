/**
 * 
 */
$(function() {
			$('#main-table').datagrid({
						url : './admin/document/backup/findByParamsForPagination',
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
									title : '备份编号',
									width : 150,
									align : 'center',
									sortable : true
								}, {
									field : 'name',
									title : '备份名称',
									width : 150,
									align : 'center',
									sortable : true
								}, {
									field : 'path',
									title : '备份路径',
									width : 150,
									align : 'center',
									sortable : true,
									hidden : true
								}, {
									field : 'size',
									title : '备份大小',
									width : 150,
									align : 'center',
									sortable : true
								}, {
									field : 'backupTime',
									title : '备份时间',
									width : 150,
									align : 'center',
									sortable : true,
									formatter : formatDateFull
								}, {
									field : 'encryptionStatus',
									title : '加密状态',
									width : 100,
									align : 'center'
								}, {
									field : 'encryptMethod',
									title : '加密方法',
									width : 100,
									align : 'center'
								}, {
									field : 'encryptCode',
									title : '加密秘钥',
									width : 100,
									align : 'center'
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
	if ($('#latestUploadTime').datetimebox('getValue') != '') {
		query.latestUploadTime = $('#latestUploadTime').datetimebox('getValue');
	}
	if ($('#type').textbox('getValue') != '') {
		query.type = $('#type').textbox('getValue');
	}
	if ($('#viewTimes').textbox('getValue') != '') {
		query.viewTimes = $('#viewTimes').textbox('getValue');
	}
	if ($('#downloadTimes').textbox('getValue') != '') {
		query.downloadTimes = $('#downloadTimes').textbox('getValue');
	}
	if ($('#existEditions').textbox('getValue') != '') {
		query.existEditions = $('#existEditions').textbox('getValue');
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

function newBackup() {
	$.messager.confirm({
				title : '提示',
				msg : '确定要备份吗?',
				fn : function(data) {
					if (data) {
						$.ajax({
									url : './admin/document/backup/newBackup',
									type : 'post',
									success : function(data) {
										$.messager.show({
													title : "提示",
													msg : "备份成功!"
												});
										$('#main-table').datagrid('reload');
									},
									error : function(data) {
										$.messager.show({
													title : "提示",
													msg : "备份失败!"
												});
										$('#main-table').datagrid('reload');
									}
								});
					}
				}

			});

}

function recoverBackup() {
	var rows = $('#main-table').datagrid('getSelections');
	if (rows.length != 1) {
		$.messager.show({
					title : '提示',
					msg : '请选择一个备份进行恢复.'
				});
		return;
	}
	$.messager.confirm({
				title : '提示',
				msg : '确定要恢复该备份吗?',
				fn : function(data) {
					if (data) {
						$.ajax({
									url : './admin/document/backup/recoverBackup',
									type : 'post',
									data : {
										backupId : rows[0].id
									},
									success : function(data) {
										$.messager.show({
													title : "提示",
													msg : "恢复备份成功!"
												});
										$('#main-table').datagrid('reload');
									},
									error : function(data) {
										$.messager.show({
													title : "提示",
													msg : "恢复备份失败!"
												});
										$('#main-table').datagrid('reload');
									}
								});
					}
				}

			});

}

function downloadBackup() {
	var sel = $('#main-table').datagrid("getSelections");
	if (sel.length != 1) {
		$.messager.show({
					msg : "请选取一个备份下载.",
					title : "提示"
				});
		return;
	}
	var id = sel[0].id;
	window.open("./admin/document/backup/download?id=" + id);
}

function deleteBackup() {
	var rows = $('#main-table').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.show({
					title : '提示',
					msg : "请选择要删除的文件"
				});
		return;
	}

	$.messager.confirm('操作提示', '删除后不可恢复，确认删除吗?', function(data) {
				if (data) {
					$.ajax({
								url : './admin/document/backup/deleteBackup',
								type : 'post',
								data : {
									id : rows[0].id
								},
								success : function(data) {
									$.messager.show({
												title : '提示',
												msg : "删除成功!"
											});
									$('#main-table').datagrid('reload');
								},
								error : function(data) {
									$.messager.show({
												title : '提示',
												msg : "删除失败!"
											});
									$('#main-table').datagrid('reload');
								}

							});
				} else {
					return;
				}
			});
}

function encrypt() {

	var rows = $('#main-table').datagrid('getSelections');
	if (rows == 0) {
		$.messager.show({
					title : "提示",
					msg : "请选择备份!"
				});
		return;
	}
	$.messager.confirm({
				title : '提示',
				msg : '确定要加密备份吗？',
				fn : function(data) {
					if (data) {
						$.ajax({
									url : './admin/document/backup/encrypt',
									type : 'post',
									data : {
										id : rows[0].id
									},
									success : function(data) {
										$.messager.show({
													title : "提示",
													msg : "加密成功!"
												});
										$('#main-table').datagrid('reload');
									},
									error : function(data) {
										$.messager.show({
													title : "提示",
													msg : "加密失败!"
												});
									}

								});
					} else {
						return;
					}
				}
			});
}

function decrypt() {
	var rows = $('#main-table').datagrid('getSelections');
	if (rows == 0) {
		$.messager.show({
					title : "提示",
					msg : "请选择备份!"
				});
		return;
	}
	$.messager.confirm({
				title : '提示',
				msg : '确定要解密备份吗？',
				fn : function(data) {
					if (data) {
						$.ajax({
									url : './admin/document/backup/decrypt',
									type : 'post',
									data : {
										id : rows[0].id
									},
									success : function(data) {
										$.messager.show({
													title : "提示",
													msg : "解密成功!"
												});
										$('#main-table').datagrid('reload');

									},
									error : function(data) {
										$.messager.show({
													title : "提示",
													msg : "解密失败!"
												});
									}

								});
					} else {
						return;
					}
				}
			});
}
