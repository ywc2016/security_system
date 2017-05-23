$(function() {
	$('#main-table').datagrid({
		url : './admin/documentVersion/findByParamsForPagination',
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
				}/*
					 * , { field : 'path', title : '文档路径', width : 150, align :
					 * 'center', sortable : true, formatter : formatPreview },
					 */, {
					field : 'latestUploadTime',
					title : '最近上传时间',
					width : 150,
					align : 'center',
					sortable : true,
					formatter : formatDateFull
				}, {
					field : 'earliestUploadTime',
					title : '初次上传时间',
					width : 150,
					align : 'center',
					sortable : true,
					formatter : formatDateFull
				}, {
					field : 'type',
					title : '文档类型',
					width : 150,
					align : 'center',
					sortable : true
				}, /*
					 * { field : 'encryptMethod', title : '加密方法', width : 100,
					 * align : 'center' }, { field : 'encryptCode', title :
					 * '加密秘钥', width : 100, align : 'center' },
					 */
				{
					field : 'viewTimes',
					title : '浏览次数',
					width : 100,
					align : 'center',
					hidden : true
				}, {
					field : 'downloadTimes',
					title : '下载次数',
					width : 100,
					align : 'center',
					hidden : true
				}, {
					field : 'existEditions',
					title : '存在的版本数',
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
		},
		view : detailview,
		detailFormatter : function(rowIndex, rowData) {
			var html = "<div style=\'height:200px;padding:2px\'><table id=\'sub-table-"
					+ rowIndex + "\'></table></div>";
			// html += "<div id = 'tool-bar-sub-table-"
			// + rowIndex
			// + "'><a href = 'javascript:void(0)' onclick = 'inStore("
			// + rowIndex
			// + ");' class = 'easyui-linkbutton' iconCls= 'icon-add' plain=
			// 'true'>原材料入库</a></div>";
			return html;
		},
		onExpandRow : function(index, row) {
			// $('#tool-bar-sub-table-' +
			// index).children('a').eq(0).linkbutton();
			$("#sub-table-" + index).datagrid({
				url : './admin/document/findByParamsForPagination',
				queryParams : {
					editionId : row.id
				},
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
						}/*
							 * , { field : 'path', title : '文档路径', width : 150,
							 * align : 'center', sortable : true, formatter :
							 * formatPreview },
							 */, {
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
				// toolbar : '#tool-bar-sub-table-' + index,
				sortName : 'id',
				sortOrder : 'asc',
				loadMsg : '载入中...',
				// pagination : false,
				striped : false,
				border : true,
				onResize : function() {
					$('#main-table').datagrid('fixDetailRowHeight', index);
				},
				onLoad : function() {
					$('#main-table').datagrid('fixDetailRowHeight', index);
					$('#main-table').datagrid('fixRowHeight', index);
				}
			});
			$('#main-table').datagrid('fixDetailRowHeight', index);
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

function upload() {
	var rows = $('#main-table').datagrid('getSelections');
	if (rows.length != 0) {
		$.messager.show({
					title : "提示",
					msg : "请勿选择文档集合!"
				});
		return;
	}
	$('#dialog-uploadFile').dialog('open');
}

function uploadNewVersion() {
	var rows = $('#main-table').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.show({
					title : "提示",
					msg : "请选择一个文档集合."
				});
		return;
	}
	$('#dialog-uploadFile').dialog('open');
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

	var rows = $('#main-table').datagrid('getSelections');
	var isNew = null;
	if (rows.length != 0) {
		$('#versionId').val(rows[0].id);
	} else {
		$('#versionId').val("");
	}
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
							$.messager.show({
										title : '错误',
										msg : json.msg
									});
						} else {
							$.messager.show({
										title : '提示',
										msg : json.msg
									});
						}
					}
				}
			});
	$('#upload-fm').submit();
}
