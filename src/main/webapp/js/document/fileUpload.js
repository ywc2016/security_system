/**
 * 
 */
$(function() {
			initSWF();
		});

/**
 * 初始化SWFUpload
 */
function initSWF() {
	// 初始化SWFUpload
	var basePath = $('base').attr('href');
	var sessionInfo = $('#sessionIdForUpload').val();
	// ----------------------下面是文件上传插件的初始化-------------------------
	var settings = {
		flash_url : basePath + 'SWFUploadv2.2.0.1Core/Flash/swfupload.swf',
		upload_url : basePath + 'admin/document/uploadFiles;jsessionid='
				+ sessionInfo,
		file_size_limit : "2000 MB",
		file_types : "*.*", // 这是全部文件都可以上传，如果要限制只有某些文件上传，则可以这么写
		file_types_description : "files",
		file_upload_limit : 0,
		file_queue_limit : 0,
		custom_settings : {
			progressTarget : "fsUploadProgress",
			uploadButtonId : "btnUpload",
			cancelButtonId : "btnCancel"
		},
		debug : false,

		// 这儿是swfupload v2新增加的功能，由于flash player
		// 10的安全性的提高所以增加了此功能。

		// 在SWFUpload
		// v2中，不能再使用html的button来触发SWFUpload，必须使用定制的Button，这其中比较要注意的是，button不能再用css控制，需要用图片来显示

		button_image_url : basePath
				+ 'SWFUploadv2.2.0.1Core/XPButtonNoText_160x22.png',
		button_placeholder_id : "spanButtonPlaceHolder",
		button_width : 160,
		button_height : 22,
		button_text : '<span class="button">&nbsp; &nbsp;选择文件 &nbsp;  &nbsp;<span class="buttonSmall">(20 MB Max)</span></span>',
		button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
		button_text_top_padding : 1,
		button_text_left_padding : 5,

		// The event handler functions are defined in
		// handlers.js
		file_queued_handler : fileQueued,
		file_queue_error_handler : fileQueueError,
		file_dialog_complete_handler : fileDialogComplete,
		upload_start_handler : fileUploadStart,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : uploadFileSuccess,
		upload_complete_handler : uploadComplete,
		queue_complete_handler : queueComplete
	};
	swfu = new SWFUpload(settings);
}

/**
 * 添加附件
 * 
 */
function addAttachment() {
	$('#dialog-addAttachment').dialog('open').dialog('setTitle', "上传附件");
}

/**
 * 重置
 * 
 * @returns
 */
function reset() {
	$('#reinsuranceAttachment_fm').form('reset');
}

/**
 * 取消
 * 
 * @returns
 */
function cancel() {
	$.messager.confirm('Confirm', '您确定要取消吗?', function(r) {
				if (r) {
					window.close();
				}
			});

}

function startUpload() {
	/*
	 * if ($('.reinsurance-attach').length == 0) { swfu.startUpload(); } else {
	 * $.messager.alert("提示", "只允许上传一个附件"); }
	 */
	swfu.startUpload();
}

function uploadFileSuccess(file, serverData) {
	try {
		var progress = new FileProgress(file,
				this.customSettings.progressTarget);
		progress.setComplete();
		progress.setStatus("已上传.");
		progress.toggleCancel(false);

		var json = eval("(" + serverData + ")");
		if (!json.success) {
			$.messager.alert('提示', json.msg, 'error');
			progress.setStatus("错误.");
			progress.toggleCancel(true);
			return;
		}

		/*
		 * <div class="fitem"><label>文档编号:</label> <input
		 * class="upload_file_number" class="easyui-textbox" /></div> + '<div
		 * class="fitem"><label>上传时间:</label> <input
		 * class="upload_file_uploadTime" class="easyui-datetimebox" /></div>' + '<div
		 * class="fitem"><label>文档大小:</label> <input
		 * class="upload_file_size"class="easyui-textbox" /></div>' + '<div
		 * class="fitem"><label>文档类型:</label> <input
		 * class="upload_file_type"class="easyui-textbox" /></div>' + '<div
		 * class="fitem"><label>加密方法:</label> <input
		 * class="upload_file_encryptMethod"class="easyui-textbox" /></div>' + '<div
		 * class="fitem"><label>加密秘钥:</label> <input
		 * class="upload_file_encryptCode" class="easyui-textbox" /></div>' + '<div
		 * class="fitem"><label>浏览次数:</label> <input
		 * class="upload_file_viewTimes" class="easyui-textbox" /></div>' + '<div
		 * class="fitem"><label>下载次数:</label> <input
		 * class="upload_file_downloadTimes"class="easyui-textbox" /></div>'
		 */
		var html = '<div class="addItem">'
				+ '<div class="fitem"><label>文档名称:</label> <input class="upload_file_name" class="easyui-textbox" value= "'
				+ json.fileTitle
				+ '"/></div>'
				+ '<div class="fitem"><label>文档路径:</label> <input class="upload_file_path" class="easyui-textbox" value="'
				+ json.filePath
				+ '" /> <a href=".'
				+ json.filePath
				+ '" target="_blank">查看源文件</a></div>'
				+ '<div class="fitem"><label>摘要:</label> <input class="upload_file_abstracts" class="easyui-textbox" data-options="multiline:true,prompt:\'请输入摘要\'" style="height:80px" />'
				+ '<a href="javascript:void(0)" onclick="deleteFile(this)" >撤销上传</a>'
				+ '</div></div>';

		$('#uploadMultiFiles').before(html);
		// $("input[class='upload_file_number']").last().textbox();
		$("input[class='upload_file_name']").last().textbox();
		$("input[class='upload_file_path']").last().textbox();
		// $("input[class='upload_file_uploadTime']").last().datetimebox();
		// $("input[class='upload_file_size']").last().textbox();
		// $("input[class='upload_file_type']").last().textbox();
		// $("input[class='upload_file_encryptMethod']").last().textbox();
		// $("input[class='upload_file_encryptCode']").last().textbox();
		// $("input[class='upload_file_viewTimes']").last().textbox();
		// $("input[class='upload_file_downloadTimes']").last().textbox();
		$("input[class='upload_file_abstracts']").last().textbox();
	} catch (ex) {
		this.debug(ex);
	}
}

/**
 * 删除单个再保附件
 * 
 * @param obj
 */
function deleteFile(obj) {
	$.messager.confirm('操作提示', '删除后不可恢复，确认删除吗?', function(data) {
				if (data) {
					var url = $(obj).parent("div").parent("div")
							.children(".upload_file_path").val();
					// var id = parseInt($(obj).prev(".reinsurance-attach-id")
					// .val());
					var num = $("#divStatus").text().substring(0, 1);
					// if (isNaN(id)) {
					// id = null;
					// }
					// 删除相应的附件
					$.ajax({
								url : './admin/document/deleteDocument',
								type : 'post',
								data : {
									filePath : encodeURIComponent(url)
									// id : id
								},
								error : function() {
									$.messager.show({
												title : '错误',
												msg : '内部错误.'
											});
								},
								success : function(data) {
									if (data == "success") {
										// 同步更新上传的文件数
										if (num > 0) {
											$("#divStatus").text(num - 1
													+ " 个文件已经上传");
										} else {
											$("#divStatus")
													.text(0 + " 个文件已经上传");
										}
										// $(obj).parent("div").prev().remove();
										$(obj).parent("div").parent("div")
												.remove();
									} else {
										$.messager.show({
													title : '错误',
													msg : '内部错误.'
												});
									}
								}
							});
				}
			});
}

function fileUploadStart(file) {
	try {
		/*
		 * I don't want to do any file validation or anything, I'll just update
		 * the UI and return true to indicate that the upload should start. It's
		 * important to update the UI here because in Linux no uploadProgress
		 * events are called. The best we can do is say we are uploading.
		 */
		console.log(file);
		var progress = new FileProgress(file,
				this.customSettings.progressTarget);
		progress.setStatus("准备上传...");
		progress.toggleCancel(true, this);
		// // 判断文件是否以_enc结尾
		// var name = file.name;
		// if (name.lastIndexOf(".") < 5
		// || !name.substring(name.lastIndexOf(".") - 4, name
		// .lastIndexOf(".")) == '_enc') {
		// $.messager.show({
		// msg : '文件名格式不正确,已经取消',
		// title : '提示'
		// });
		// swfu.cancelQueue();
		// return false;
		// }
	} catch (ex) {
	}

	return true;
}
