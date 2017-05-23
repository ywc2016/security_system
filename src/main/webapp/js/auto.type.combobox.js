// 险种下拉框
/**
 * 初始化险种combobox
 * 
 * @param domId
 *            combobox的id
 */
function initInsuranceTypeCombobox(domId) {
	$.ajax({
		url : './admin/category/findAllCategoryForInsuranceDocumentlevel3',
		type : 'POST',
		timeout : 5000,
		async : false,
		error : function() {
			$.messager.show({
				title : '错误',
				msg : '网络错误.'
			});
		},
		success : function(data) {
			var json = eval(data);
			var handleJson = json.categoryList;
			// 标签combobox
			var jqId = "#" + domId;
			$(jqId).combotree({
				data : handleJson,
				valueField : 'id',
				textField : 'text',
				panelHeight : '200'
			});
		}
	});
}

/**
 * 初始化险种与标的属性的动态绑定
 * 
 * @param domId
 */
function initInsuranceTypeComboboxForInsurance(domId) {
	var insuranceNum = (!$("#create-number").val() == null && !$(
			"#create-number").val() == '') ? $("#create-number").val() : $(
			"#create-number-hidden").val();
	$
			.ajax({
				url : './admin/category/findAllCategoryForInsuranceDocumentlevel3',
				type : 'POST',
				timeout : 5000,
				async : false,
				error : function() {
					$.messager.show({
						title : '错误',
						msg : '网络错误.'
					});
				},
				success : function(data) {
					var json = eval(data);
					var handleJson = json.categoryList;
					// 标签combobox
					var jqId = "#" + domId;
					$(jqId)
							.combotree(
									{
										data : handleJson,
										valueField : 'id',
										textField : 'text',
										panelHeight : '200',
										required : true,
										onlyLeafCheck : true,
										onSelect : function(record) {
											// 返回树对象
											var tree = $(this).tree;
											// 选中的节点是否为叶子节点,如果不是叶子节点,清除选中
											var isLeaf = tree('isLeaf',
													record.target);
											if (!isLeaf) {
												// 清除选中
												$('#jqId').combotree('clear');
												alert("请选择末级险种.");
												return;
											}

											$
													.ajax({
														url : "./admin/category/findSubject",
														type : 'post',
														data : {
															typeId : record.id,
															insNum : insuranceNum
														},
														timeout : 10000,
														error : function() {
															$.messager.show({
																title : '错误',
																msg : '网络错误.'
															});
														},
														success : function(data) {
															if (data != null) {
																var json = eval(data);
																var handleJson = json.typeSubject;
																var s = "<div class='my-row'>";
																for ( var i in handleJson) {

																	var h = handleJson[i].attributeValue == undefined ? ""
																			: "value='"
																					+ handleJson[i].attributeValue
																					+ "'";
																	s = s
																			+ "<div class='my-col' style='margin-bottom:15px'><div class='fitem full-double'><label>"
																			+ handleJson[i].attributeName
																			+ "</label> <input class='easyui-textbox subject-attribute' style='height: 80px'"
																			+ " name='subject-attribute"
																			+ handleJson[i].id
																			+ " ' "
																			+ h
																			+ "data-options=\"multiline:true, prompt:'最多输入"
																			+ handleJson[i].maxLength
																			+ "字符',validType:'length[0,"
																			+ handleJson[i].maxLength
																			+ "]'\"";
																	if (handleJson[i].mustOrNot == "是") {
																		s = s
																				+ "required = 'true'/><label  class='must'>*</label></div></div>";
																	} else {

																		s = s
																				+ "/></div></div>";
																	}
																}
																s = s
																		+ "<div class='clear'></div></div>";
																$("#subject")
																		.html(s);
																$(
																		".subject-attribute")
																		.textbox();
															}
														}
													});
										}
									});
				}
			});
	// 修改保单时自动加载已填写标的信息
	var typeId = $("#" + domId).val();
	if (typeId != null && typeId != "") {
		$
				.ajax({
					url : "./admin/category/findSubject",
					type : 'post',
					data : {
						typeId : typeId,
						insNum : insuranceNum
					},
					timeout : 10000,
					error : function() {
						$.messager.show({
							title : '错误',
							msg : '网络错误.'
						});
					},
					success : function(data) {
						if (data != null) {
							var json = eval(data);
							var handleJson = json.typeSubject;
							var s = "<div class='my-row'>";
							for ( var i in handleJson) {

								var h = handleJson[i].attributeValue == undefined ? ""
										: "value='"
												+ handleJson[i].attributeValue
												+ "'";
								s = s
										+ "<div class='my-col' style='margin-bottom:15px'><div class='fitem full-double'><label>"
										+ handleJson[i].attributeName
										+ "</label> <input class='easyui-textbox subject-attribute' style='height: 80px'"
										+ " name='subject-attribute"
										+ handleJson[i].id
										+ " ' "
										+ h
										+ "data-options=\"multiline:true, prompt:'最多输入"
										+ handleJson[i].maxLength
										+ "字符',validType:'length[0,"
										+ handleJson[i].maxLength + "]'\"";
								if (handleJson[i].mustOrNot == "是") {
									s = s
											+ "required = 'true'/><label  class='must'>*</label></div></div>";
								} else {

									s = s + "/></div></div>";
								}
							}
							s = s + "<div class='clear'></div></div>";
							$("#subject").html(s);
							$(".subject-attribute").textbox();
						}
					}
				});

	}
}

/**
 * * 检测当前框内内容是否为数据库中已存储的险种
 * 
 * @param domId
 *            combobox的id
 * @returns {Boolean} 检测出异常则会返回false，否则返回true
 */
function checkInsuranceType(domId) {
	var jqId = "#" + domId;
	var s = $.trim($(jqId).combobox('getValue'));
	if (s == null || s == '') {
		return true;
	} else {
		$.ajax({// 向后台发送检验请求
			url : './admin/category/isLegal?id=' + s,
			type : 'POST',
			async : false,
			timeout : 5000,
			error : function() {
				$.messager.show({
					title : '错误',
					msg : '网络错误.',
				});
			},
			success : function(data) {
				if (data != "SUCCESS") {// 不通过检验
					return false;
				} else {
					return true;
				}
			}
		});
	}

}

// 客户下拉框
/**
 * 初始化客户combobox
 * 
 * @param domId
 *            combobox的id
 * @param targetId
 *            目标填充框的id（若无则为null）——类型为数组
 * @param type
 *            需要绑定的数据——类型为数组
 * @param kind
 *            是否为textbox——类型为数组
 */
function initCustomerCombobox(domId, targetId, type, kind) {
	$
			.ajax({
				url : './admin/customer/findAllCustomerForOtherCertificate',
				type : 'POST',
				timeout : 5000,
				error : function() {
					$.messager.show({
						title : '错误',
						msg : '网络错误.'
					});
				},
				success : function(data) {
					var json = eval(data);
					var handleJson = json.customerList;
					// 标签combobox
					var jqId = "#" + domId;
					$(jqId)
							.combobox(
									{
										data : handleJson,
										valueField : 'id',
										textField : 'name',
										multiple : false,
										required : false,
										editable : false,
										panelHeight : '150',
										onSelect : function(record) {
											if (targetId != null) {
												for (var a = 0; a < targetId.length; a++) {
													var jqTargetId = "#"
															+ targetId[a];
													if (type[a] == "number") {
														if (kind[a]) {
															$(jqTargetId)
																	.textbox(
																			'setValue',
																			record.number);
														} else {
															$(jqTargetId)
																	.val(
																			record.number);
														}
													} else if (type[a] == "address") {
														if (kind[a]) {
															$(jqTargetId)
																	.textbox(
																			'setValue',
																			record.address);
														} else {
															$(jqTargetId)
																	.val(
																			record.address);
														}
													} else {
														if (kind[a]) {
															$(jqTargetId)
																	.textbox(
																			'setValue',
																			$(
																					jqId)
																					.combobox(
																							'getValue'));
														} else {
															$(jqTargetId)
																	.val(
																			$(
																					jqId)
																					.combobox(
																							'getValue'));
														}

													}
												}
											}
										}
									});
				}
			});
}

/**
 * 初始化客户combobox
 * 
 * @param domId
 *            combobox的id
 * @param targetId
 *            目标填充框的id（若无则为null）——类型为数组
 * @param type
 *            需要绑定的数据——类型为数组
 * @param kind
 *            是否为textbox——类型为数组
 */
function initCustomerComboboxByNumber(domId) {
	$.ajax({
		url : './admin/customer/findAllCustomerForOtherCertificate',
		type : 'POST',
		timeout : 5000,
		error : function() {
			$.messager.show({
				title : '错误',
				msg : '网络错误.'
			});
		},
		success : function(data) {
			var json = eval(data);
			var handleJson = json.customerList;
			// 标签combobox
			var jqId = "#" + domId;
			$(jqId).combobox({
				data : handleJson,
				valueField : 'number',
				textField : 'name',
				multiple : false,
				required : false,
				editable : false,
				panelHeight : '150'
			});
		}
	});
}
/**
 * * 检测当前框内内容是否为数据库中已存储的客户名称
 * 
 * @param domId
 *            combobox的id
 * @returns {Boolean} 检测出异常则会返回false，否则返回true
 */
function checkCustomer(domId) {
	var jqId = "#" + domId;
	var s = $.trim($(jqId).combobox('getValue'));
	if (s == null || isNaN(s) || s == '') {
		return false;
	} else {
		$.ajax({// 向后台发送检验请求
			url : './admin/customer/isLegal?id=' + s,
			type : 'POST',
			async : false,
			timeout : 5000,
			error : function() {
				$.messager.show({
					title : '错误',
					msg : '网络错误.',
				});
			},
			success : function(data) {
				if (data != "SUCCESS") {// 不通过检验
					return false;
				} else {
					return true;
				}
			}
		});
	}

}

// 保险公司下拉框

/**
 * 初始化保险公司combobox
 * 
 * @param domId
 *            combobox的id
 * @param targetId
 *            目标填充框的id（若无则为null）
 * @param type
 *            返回类型
 */
function initCompanyCombobox(domId, targetId, type) {
	$.ajax({
		url : './admin/company/findCompanyTree',
		type : 'POST',
		timeout : 5000,
		error : function() {
			$.messager.show({
				title : '错误',
				msg : '网络错误.'
			});
		},
		success : function(data) {
			var json = eval(data);
			var handleJson = json;
			// 标签combobox
			var jqId = "#" + domId;
			$(jqId).combotree(
					{
						data : json,
						valueField : 'id',
						textField : 'name',
						required : true,
						panelHeight : '150',
						cascadeCheck : true,
						onSelect : function(record) {
							if (targetId != null) {
								var jqTargetId = "#" + targetId;
								if (type == "number") {
									$(jqTargetId).textbox('setValue',
											record.number);
								} else {
									$(jqTargetId).textbox('setValue',
											$(jqId).combobox('getValue'));
								}
							}
						}
					});
		}
	});
}
/**
 * 绑定下拉保险公司树
 * 
 * @param domId
 * @param targetId
 * @param type
 */
function initCompanyCombotreeByNumber(domId) {
	$.ajax({
		url : './admin/company/findCompanyTreeBindNumber',
		type : 'POST',
		timeout : 5000,
		error : function() {
			$.messager.show({
				title : '错误',
				msg : '网络错误.'
			});
		},
		success : function(data) {
			var json = eval(data);
			// 标签combobox
			var jqId = "#" + domId;
			$(jqId).combotree({
				data : json,
				valueField : 'id',
				textField : 'name',
				animate : true,
				panelHeight : '150',
			});
		}
	});
}
/**
 * * 检测当前框内内容是否为数据库中已存储的保险公司名称
 * 
 * @param domId
 *            combobox的id
 * @returns {Boolean} 检测出异常则会返回false，否则返回true
 */
function checkCompany(domId) {
	var jqId = "#" + domId;
	var s = $.trim($(jqId).combobox('getValue'));
	if (s == null || isNaN(s) || s == '') {
		return false;
	} else {
		$.ajax({// 向后台发送检验请求
			url : './admin/company/isLegal?id=' + s,
			type : 'POST',
			timeout : 5000,
			error : function() {
				$.messager.show({
					title : '错误',
					msg : '网络错误.',
				});
			},
			success : function(data) {
				if (data != "SUCCESS") {// 不通过检验
					return false;
				} else {
					return true;
				}
			}
		});
	}

}

// 员工下拉框

/**
 * 初始化员工combobox
 * 
 * @param domId
 *            combobox的id
 * @param targetId
 *            目标填充框的id（若无则为null）
 * @param type
 *            返回类型
 */
function initStaffComboboxToHidden(domId, targetId, type) {
	$.ajax({
		url : './admin/staff/findAllStaffForOtherCertificate',
		type : 'POST',
		timeout : 5000,
		error : function() {
			$.messager.show({
				title : '错误',
				msg : '网络错误.'
			});
		},
		success : function(data) {
			var json = eval(data);
			var handleJson = json.staffList;
			// 标签combobox
			var jqId = "#" + domId;

			$(jqId).combobox({
				data : handleJson,
				valueField : 'id',
				textField : 'name',
				multiple : false,
				separator : '；',
				panelHeight : '150',
				onSelect : function(record) {
					if (targetId != null) {
						var jqTargetId = "#" + targetId;
						if (type == "number") {
							$(jqTargetId).val(record.number);
						} else {
							$(jqTargetId).val($(jqId).combobox('getValue'));
						}
					}
				}
			});
		}
	});
}

/**
 * 初始化员工combobox
 * 
 * @param domId
 *            combobox的id
 * @param targetId
 *            目标填充框的id（若无则为null）
 * @param type
 *            返回类型
 */
function initStaffCombobox(domId, targetId, type) {
	$.ajax({
		url : './admin/staff/findAllStaffForOtherCertificate',
		type : 'POST',
		timeout : 5000,
		error : function() {
			$.messager.show({
				title : '错误',
				msg : '网络错误.'
			});
		},
		success : function(data) {
			var json = eval(data);
			var handleJson = json.staffList;
			// 标签combobox
			var jqId = "#" + domId;

			$(jqId).combobox(
					{
						data : handleJson,
						valueField : 'id',
						textField : 'name',
						multiple : false,
						editable : false,
						panelHeight : '150',
						onSelect : function(record) {
							if (targetId != null) {
								var jqTargetId = "#" + targetId;
								if (type == "number") {
									$(jqTargetId).textbox('setValue',
											record.number);
								} else {
									$(jqTargetId).textbox('setValue',
											$(jqId).combobox('getValue'));
								}
							}
						}
					});
		}
	});
}

// 员工下拉框

/**
 * 初始化员工combobox,下拉框的值为number
 * 
 * @param domId
 *            combobox的id
 */
function initStaffBindNumber(domId) {
	$.ajax({
		url : './admin/staff/findAllStaffForOtherCertificate',
		type : 'POST',
		timeout : 5000,
		error : function() {
			$.messager.show({
				title : '错误',
				msg : '网络错误.'
			});
		},
		success : function(data) {
			var json = eval(data);
			var handleJson = json.staffList;
			// 标签combobox
			var jqId = "#" + domId;

			$(jqId).combobox({
				data : handleJson,
				valueField : 'number',
				textField : 'name',
				multiple : false,
				required : true,
				editable : false,
				panelHeight : '150',
			});
		}
	});
}

/**
 * * 检测当前框内内容是否为数据库中已存储的员工
 * 
 * @param domId
 *            combobox的id
 * @returns {Boolean} 检测出异常则会返回false，否则返回true
 */
function checkStaff(domId) {
	var jqId = "#" + domId;
	var s = $.trim($(jqId).combobox('getValue'));
	if (s == null || isNaN(s) || s == '') {
		return false;
	} else {
		$.ajax({// 向后台发送检验请求
			url : './admin/staff/isLegal?id=' + s,
			type : 'POST',
			timeout : 5000,
			error : function() {
				$.messager.show({
					title : '错误',
					msg : '网络错误.',
				});
			},
			success : function(data) {
				if (data != "SUCCESS") {// 不通过检验
					return false;
				} else {
					return true;
				}
			}
		});
	}

}

/**
 * 显示机构树面板
 * 
 * @param domId
 *            面板Id
 * @param treeId
 *            树元素Id
 */
function selectDepartment(domId, treeId) {
	var jqId = "#" + domId;
	$(jqId).dialog('open');
	initDepartmentTree(treeId);

}

/**
 * 初始化机构树
 * 
 * @param domId
 *            easyui Tree的id
 */
function initDepartmentTree(domId) {
	$.ajax({
		url : './admin/department/findDepartmentTree',
		type : 'POST',
		timeout : 5000,
		error : function() {
			$.messager.show({
				title : '错误',
				msg : '网络错误.'
			});
		},
		success : function(data) {
			var json = eval(data);
			var jqId = "#" + domId;
			$(jqId).combotree({
				data : json,
				animate : true,
				checkbox : true,
				onlyLeafCheck : true,
				cascadeCheck : false
			});
		}
	});
}

/**
 * 初始化机构树,下拉框的值为number
 * 
 * @param domId
 *            combotree的id
 */
function initDepartmentBindNumber(domId) {
	$.ajax({
		url : './admin/department/findDepartmentTreeBindNumber',
		type : 'POST',
		timeout : 5000,
		error : function() {
			$.messager.show({
				title : '错误',
				msg : '网络错误.'
			});
		},
		success : function(data) {
			var json = eval(data);
			var jqId = "#" + domId;
			$(jqId).combotree({
				data : json,
				animate : true,
				checkbox : true,
				onlyLeafCheck : true,
				cascadeCheck : false,
			});
		}
	});
}

/**
 * 
 * @param domId
 *            tree的Id
 * @param mulNodes
 *            是否允许多个，'0'只选一个，'1'可选多个
 * @param targetTextId
 *            存储选择节点名称的元素Id
 * @param targetId
 *            存储选择节点Id的元素Id
 */
function getCheckedNodes(domId, mulNodes, targetTextId, targetId) {
	var jqId = "#" + domId;
	var nodes = $(jqId).tree('getChecked');
	var names = '';
	var ids = '';
	if (mulNodes == '1' && nodes.length > 1) {
		alert('只允许选择一个机构. ');
	} else {
		for (var i = 0; i < nodes.length; i++) {
			if (names != '')
				names += ',';
			names += nodes[i].text;
			if (ids != '')
				ids += ',';
			ids += nodes[i].id;
		}
	}
	// 复制到目标元素
	if (names != '' && targetTextId != '') {
		$('#' + targetTextId).val(names);
	}
	if (ids != '' && targetId != '') {
		$('#' + targetId).val(ids);
	}
}
/**
 * 初始化合同combogrid
 * 
 * @param domId
 */
function initContractCombogrid(domId) {
	var jqId = $("#" + domId);
	jqId.combogrid({
		idField : 'number',
		textField : 'name',
		width : 200,
		url : './admin/contract/findByParamsForPaginationContractWithoutAuth?'
				+ _csrf.toString(),
		columns : [ [ {
			field : 'ck',
			title : '',
			checkbox : true,
		}, {
			field : 'id',
			title : 'ID',
			hidden : true,
		}, {
			field : 'number',
			title : '合同编号',
			width : 130,
			align : 'center'
		}, {
			field : 'name',
			title : '合同名称',
			width : 150,
			align : 'center'
		}, {
			field : 'type',
			title : '合同类型',
			width : 150,
			align : 'center'
		}, {
			field : 'firstName',
			title : '甲方',
			width : 200,
			align : 'center'
		} ] ],
		panelWidth : '560',
		panelHeight : '220',
		pagination : true,
		striped : false,
		rownumbers : false,
		multiple : true
	});
}

/**
 * * 检测当前框内内容是否为数据库中已存储的合同
 * 
 * @param domId
 *            combogrid的id
 * @returns {Boolean} 检测出异常则会返回false，否则返回true
 */
function checkContract(domId) {
	var jqId = "#" + domId;
	var contractId = $(jqId).combogrid('grid').datagrid('getSelected').id;
	var s = $.trim(contractId);
	if (s == null || isNaN(s) || s == '') {
		return false;
	} else {
		$.ajax({// 向后台发送检验请求
			url : './admin/contract/isLegal?id=' + s,
			type : 'POST',
			timeout : 5000,
			error : function() {
				$.messager.show({
					title : '错误',
					msg : '网络错误.',
				});
			},
			success : function(data) {
				if (data != "success") {// 不通过检验
					return false;
				} else {
					return true;
				}
			}
		});
	}
}