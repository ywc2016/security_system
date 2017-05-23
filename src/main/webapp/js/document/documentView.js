/**
 * 页面加载之前完成
 */
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
/**
 * 打开主查询框
 */
function queryMain() {
	$('#mainQuery').dialog('open').dialog('setTitle', '查询');
	$('#mainQuery-fm').form('clear');
}

/**
 * 查询详细信息
 */
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
/**
 * 查询全部
 */
function queryAllAuxiliary() {
	$('#main-table').datagrid('options').queryParams = null;
	$('#mainQuery').dialog('close');// 关闭弹出框
	$('#main-table').datagrid('reload');// 重新加载数据
}
/**
 * 上传文件
 */
function upload() {

	$('#dialog-uploadFile').dialog('open');
}

/**
 * 附件格式显示
 * 
 * @param {}
 *            value
 * @param {}
 *            row
 * @param {}
 *            index
 * @return {}
 */
function formatPreview(value, row, index) {
	if (value != null && value != "") {
		var s = '<a style="color:green;" href = ".' + value
				+ '" target="_blank">' + value + '</a>';
		return s;
	} else {
		return "无附件";
	}
}

/**
 * 上传之前验证
 * 
 * @return {}
 */
function beforeSubmit() {
	// ----------------------下面是封装附件信息-------------------------
	var urlArray = new Array();
	var nameArray = new Array();
	var abstractsArray = new Array();
	var url, name, abstracts;
	var attachLength = $('.upload_file_name').length;
	for (var i = 0; i < attachLength; i++) {
		url = $.trim($('.upload_file_path').eq(i).val());
		name = $('.upload_file_name').eq(i).val();
		abstracts = $('.upload_file_abstracts').eq(i).val();
		if (name == null || name == "") {
			name = " ";
		}
		if (abstracts == null || abstracts == "") {
			abstracts = " ";
		}
		urlArray.push(encodeURIComponent(url));
		nameArray.push(encodeURIComponent(name));
		abstractsArray.push(encodeURIComponent(abstracts));
	}
	$('#upload_file_name_commaString').val(nameArray.join(","));
	$('#upload_file_path_commaString').val(urlArray.join(","));
	$('#upload_file_abstracts_commaString').val(abstractsArray.join(","));
	// ----------------------下面是验证表单的有效性-------------------------
	return $('#upload_fm').form('enableValidation').form('validate');
}

/**
 * 保存文件
 * 
 * 
 */
function save() {
	window.parent.$('#loading').dialog('open');
	if (!beforeSubmit()) {
		window.parent.$('#loading').dialog('close');
		return;
	}
	$('#upload-fm').form({
				url : './admin/document/saveDocument?' + _csrf.toString(),
				onSubmit : function() {
					return beforeSubmit();
				},
				success : function(data) {
					window.parent.$('#loading').dialog('close');
					// var json = eval('(' + data + ')');
					var json = JSON.parse(data);
					if (json.isSuccessful) {
						$('#main-table').datagrid('reload');
						$("#divStatus").text(0 + " 个文件已经上传");
						window.location.reload();
					} else {
						if (json.msg == "操作失败.") {
							$.messager.show({// 提示用户
								title : '错误',
								msg : json.msg
							});
						} else {
							$.messager.show({// 提示用户
								title : '提示',
								msg : json.msg
							});
						}
					}
				}
			});
	$('#upload-fm').submit();
}

/**
 * 下载文件
 */
function downloadFile() {
	var rows = $('#main-table').datagrid("getSelections");
	if (rows.length != 1) {
		$.messager.show({// 提示用户
			msg : "请选取一个文件下载.",
			title : "提示"
		});
		return;
	}

	if (rows[0].shareStatus == '未共享') {
		$.messager.show({// 提示用户
			title : '提示',
			msg : "文件未共享."
		});
		return;
	}
	if (rows[0].auditStatus != '通过') {
		$.messager.show({// 提示用户
			title : '提示',
			msg : "文件未通过审核."
		});
		return;
	}
	var id = rows[0].id;
	window.open("./admin/document/download?id=" + id);
}

/**
 * 删除文档
 */
function deleteDocument() {
	var rows = $('#main-table').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.show({// 提示用户
			title : '提示',
			msg : "请选择要删除的文件"
		});
		return;
	}

	$.messager.confirm('操作提示', '删除后不可恢复，确认删除吗?', function(data) {
				if (data) {
					$.ajax({
								url : './admin/document/deleteDocument',
								type : 'post',
								data : {
									id : rows[0].id,
									filePath : encodeURIComponent(rows[0].path)
								},
								success : function(data) {
									$.messager.show({// 提示用户
										title : '提示',
										msg : "删除成功!"
									});
									$('#main-table').datagrid('reload');
								},
								error : function(data) {
									$.messager.show({// 提示用户
										title : '提示',
										msg : "删除失败!"
									});
								}

							});
				} else {
					return;
				}
			});
}

/**
 * 分享文档
 */
function shareDoc() {
	var rows = $('#main-table').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.show({// 提示用户
			title : '提示',
			msg : "请选择要共享的文件"
		});
		return;
	}
	$.ajax({
				url : './admin/document/shareDocument',
				type : 'post',
				data : {
					id : rows[0].id
				},
				success : function(data) {
					if (data == 'no authority') {
						$.messager.show({// 提示用户
							title : '提示',
							msg : "没有权限!"
						});
					} else {
						$.messager.show({// 提示用户
							title : '提示',
							msg : "共享成功!"
						});
						$('#main-table').datagrid('reload');
					}
				},
				error : function(data) {
					$.messager.show({// 提示用户
						title : '提示',
						msg : "共享失败!"
					});
					$('#main-table').datagrid('reload');
				}
			});
}

/**
 * 取消分享
 */
function cancelShareDoc() {
	var rows = $('#main-table').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.show({// 提示用户
			title : '提示',
			msg : "请选择要取消共享的文件"
		});
		return;
	}
	$.ajax({
				url : './admin/document/cancelShareDocument',
				type : 'post',
				data : {
					id : rows[0].id
				},
				success : function(data) {
					if (data == 'no authority') {
						$.messager.show({// 提示用户
							title : '提示',
							msg : "没有权限!"
						});
					} else {
						$.messager.show({// 提示用户
							title : '提示',
							msg : "取消共享成功!"
						});
						$('#main-table').datagrid('reload');
					}
				},
				error : function(data) {
					$.messager.show({// 提示用户
						title : '提示',
						msg : "取消共享失败!"
					});
					$('#main-table').datagrid('reload');
				}
			});
}