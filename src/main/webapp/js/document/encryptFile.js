/**
 * 
 */
var fileSystem = null;
/**
 * 加密文件,文件加密之后才能上传 加密文件格式*_encrypted.*
 */
function encryptFile() {

	var rows = $('#main-table').datagrid('getSelections');
	if (rows == 0) {
		$.messager.show({
					title : "提示",
					msg : "请选择文件!"
				});
		return;
	}
	if (rows[0].shareStatus == '未共享') {
		$.messager.show({
					title : '提示',
					msg : "文件未共享."
				});
		return;
	}
	if (rows[0].auditStatus != '通过') {
		$.messager.show({
					title : '提示',
					msg : "文件未通过审核."
				});
		return;
	}
	$.messager.confirm({
				title : '提示',
				msg : '确定要加密文件吗？',
				fn : function(data) {
					if (data) {
						$.ajax({
									url : './admin/document/encrypt',
									type : 'post',
									data : {
										id : rows[0].id
									},
									success : function(data) {
										if (data == 'success') {
											$.messager.show({
														title : "提示",
														msg : "加密成功!"
													});
										} else if (data == 'failed') {
											$.messager.show({
														title : "提示",
														msg : "加密失败!"
													});
										}
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

function doEncrypt() {
	var selectedFile = document.getElementById("files").files[0];// 获取读取的File对象
	var path = document.getElementById("files").value;
	var name = selectedFile.name;// 读取选中文件的文件名
	var size = selectedFile.size;// 读取选中文件的大小
	console.log("文件名:" + name + "大小：" + size);

	var reader = new FileReader();// 这里是核心！！！读取操作就是由它完成的。
	reader.readAsText(selectedFile);// 读取文件的内容

	var plaintext;
	reader.onload = function() {
		plaintext = reader.result;
		console.log(plaintext);// 当读取完成之后会回调这个函数，然后此时文件的内容存储到了result中。直接操作即可。
		var data = "123456";
		var key = '1234567812345678';
		var iv = '1234567812345678';
		var ciphertext = encryptAES(plaintext, getKey(key), getIv(iv));
		console.log(ciphertext);

		path = path.substring(0, path.lastIndexOf('.')) + "_enc"
				+ path.substring(path.lastIndexOf('.'));
		writeFile(ciphertext, path);
	};

}

function decryptFile() {
	var rows = $('#main-table').datagrid('getSelections');
	if (rows == 0) {
		$.messager.show({
					title : "提示",
					msg : "请选择文件!"
				});
		return;
	}
	if (rows[0].shareStatus == '未共享') {
		$.messager.show({
					title : '提示',
					msg : "文件未共享."
				});
		return;
	}
	if (rows[0].auditStatus != '通过') {
		$.messager.show({
					title : '提示',
					msg : "文件未通过审核."
				});
		return;
	}
	$.messager.confirm({
				title : '提示',
				msg : '确定要解密文件吗？',
				fn : function(data) {
					if (data) {
						$.ajax({
									url : './admin/document/decrypt',
									type : 'post',
									data : {
										id : rows[0].id
									},
									success : function(data) {
										if (data == 'success') {
											$.messager.show({
														title : "提示",
														msg : "解密成功!"
													});
										} else if (data == 'failed') {
											$.messager.show({
														title : "提示",
														msg : "解密失败!"
													});
										}
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
