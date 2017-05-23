$(function() {
	$('#stock-table').datagrid({
		type : 'post',
		columns : [ [ {
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
			title : '配件编号',
			width : 150,
			sortable : true,
		}, {
			field : 'name',
			title : '配件名称',
			width : 150,
			align : 'center',
			sortable : true,
		}, {
			field : 'inventory',
			title : '库存数量',
			width : 150,
			align : 'center',
			sortable : true
		}, {
			field : 'situation',
			title : '库存状况',
			width : 150,
			align : 'center',
			sortable : true,
			formatter : function(val, row) {
				if (val === '紧急缺货') {
					return '<span style="color:red;">' + val
							+ '</span>';
				} else if (val === '充足') {
					return '<span style="color:green;">' + val
							+ '</span>';
				} else if (val === '不足') {
					return '<span style="color:grey;">' + val
							+ '</span>';
				} else {
					return val;
				}
			}
		} ] ],
		sortName : 'id',
		sortOrder : 'asc',
		toolbar : '#toolbar',
		onLoadError : function(data) {
			$.messager.show({
				title : '错误',
				msg : '操作失败.'
			});
		},
	});
});


/**
 * 编辑框
 */
function editMain() {		
	var row = $('#stock-table').datagrid('getSelections');
	if (row.length != 1) {
		$.messager.show({
			title : '提示',
			msg : '请选择一条记录!'
		});
		return;
	}
	$('#edit').dialog('open').dialog('setTitle', '修改');
	$('#edit-fm').form('load', row[0]);

}


function editAuxiliary() {	
	$('#edit-fm').form('submit', {
		url : './admin/stock/stockUpdate?'
			+ _csrf.toString(),
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			$('#edit').dialog('close'); // 关闭弹出框
			$('#stock-table').datagrid('reload'); // 重新加载数据
		}
	});
}

/**
 * 查询框
 */
function queryMain() {
	$('#query').dialog('open').dialog('setTitle', '查询');
	$('#query-fm').form('clear');
}

/**
 * 查询全部
 */
function queryAllAuxiliary() {
	$('#stock-table').datagrid('options').queryParams = null;
	$('#query').dialog('close');// 关闭弹出框
	$('#stock-table').datagrid('reload');// 重新加载数据
}

/**
 * 查询具体
 */
function queryAuxiliary() {

	$('#stock-table').datagrid('options').queryParams = {};
	var query = $('#stock-table').datagrid('options').queryParams;
	if ($('#query-number').textbox('getValue') != '')
		query.number = $('#query-number').textbox('getValue');
	if ($('#query-name').textbox('getValue') != '')
		query.name = $('#query-name').textbox('getValue');
	if ($('#query-inventory').textbox('getValue') != '')
		query.inventory = $('#query-inventory').textbox('getValue');	
	if ($('#query-situation').textbox('getValue') != '')
		query.situation = $('#query-situation').textbox('getValue');
	$('#stock-table').datagrid('reload'); // 刷新页面
	$('#query-fm').form('clear');
	$('#query').dialog('close');
}


function addMain() {
	addMainBase();
}


function addAuxiliary() {
	if ($('#add-fm').form('validate')) {
		$.ajax({
			url : './admin/stock/stockSaveOrUpdate',
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
				$('#stock-table').datagrid('reload'); // 重新加载数据
			}
		});
	}
}


/**
 * 删除记录
 */
function deleteMain() {
	var ids = new Array();
	var rows = $('#stock-table').datagrid('getSelections');
	if (rows.length === 0) {
		$.messager.show({
			title : '提示',
			msg : '请至少选择一条记录.'
		});
		return;
	}
	for (var i = 0; i < rows.length; i++) {
		ids.push(rows[i]['id']);
	}

	$.messager.confirm('确认', "确认删除么？", function(r) {
		if (r) {
			$.ajax({
				url : '/admin/stock/stockDelete',
				type : 'post',
				data : {
					'idCommaString' : ids.join(',')
				},
				success : function(data) {
					$.messager.show({
						title : '成功',
						msg : '删除成功.'
					});
					$('#stock-table').datagrid('reload');// 重新加载数据
				},
				error : function() {
					$.messager.show({
						title : '错误',
						msg : '操作失败.'
					});
				}
			});
		} else {
			return 0;
		}
	});

}
