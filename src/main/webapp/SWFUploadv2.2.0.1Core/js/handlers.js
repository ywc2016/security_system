/* Demo Note:  This demo uses a FileProgress class that handles the UI for displaying the file name and percent complete.
The FileProgress class is not part of SWFUpload.
 */

/* **********************
 Event Handlers
 These are my custom event handlers to make my
 web application behave the way I went when SWFUpload
 completes different tasks.  These aren't part of the SWFUpload
 package.  They are part of my application.  Without these none
 of the actions SWFUpload makes will show up in my application.
 ********************** */

function swfUploadPreLoad() {
	var self = this;
	var loading = function() {
		// document.getElementById("divSWFUploadUI").style.display = "none";
		document.getElementById("divLoadingContent").style.display = "";

		var longLoad = function() {
			document.getElementById("divLoadingContent").style.display = "none";
			document.getElementById("divLongLoading").style.display = "";
		};
		this.customSettings.loadingTimeout = setTimeout(function() {
			longLoad.call(self)
		}, 15 * 1000);
	};

	this.customSettings.loadingTimeout = setTimeout(function() {
		loading.call(self);
	}, 1 * 1000);
}
function swfUploadLoaded() {
	var self = this;
	clearTimeout(this.customSettings.loadingTimeout);
	// document.getElementById("divSWFUploadUI").style.visibility = "visible";
	// document.getElementById("divSWFUploadUI").style.display = "block";
	document.getElementById("divLoadingContent").style.display = "none";
	document.getElementById("divLongLoading").style.display = "none";
	document.getElementById("divAlternateContent").style.display = "none";

	// document.getElementById("btnBrowse").onclick = function () {
	// self.selectFiles(); };
	document.getElementById("btnCancel").onclick = function() {
		self.cancelQueue();
	};
}

function swfUploadLoadFailed() {
	clearTimeout(this.customSettings.loadingTimeout);
	// document.getElementById("divSWFUploadUI").style.display = "none";
	document.getElementById("divLoadingContent").style.display = "none";
	document.getElementById("divLongLoading").style.display = "none";
	document.getElementById("divAlternateContent").style.display = "";
}

function fileQueued(file) {
	try {
		var progress = new FileProgress(file,
				this.customSettings.progressTarget);
		progress.setStatus("等待上传...");
		progress.toggleCancel(true, this);

	} catch (ex) {
		this.debug(ex);
	}

}

function fileQueueError(file, errorCode, message) {
	try {
		if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
			alert("文件过多.\n"
					+ (message === 0 ? "已达到最大限制."
							: "你已经选择 "
									+ (message > 1 ? "上传 " + message + " 个文件."
											: "个文件.")));
			return;
		}

		var progress = new FileProgress(file,
				this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);

		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			progress.setStatus("文件太大.");
			this.debug("错误: 文件太大, 文件名: " + file.name + ", 文件大小: " + file.size
					+ ", 信息: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			progress.setStatus("不能上传0 Byte 文件.");
			this.debug("错误: 0 Byte 文件, 文件名: " + file.name + ", 文件大小: "
					+ file.size + ", 信息: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			progress.setStatus("无效的文件类型.");
			this.debug("错误: 无效的文件类型, 文件名: " + file.name + ", 文件大小: "
					+ file.size + ", 信息: " + message);
			break;
		default:
			if (file !== null) {
				progress.setStatus("Unhandled Error");
			}
			this.debug("错误: " + errorCode + ", 文件名: " + file.name + ", 文件大小: "
					+ file.size + ", 信息: " + message);
			break;
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function fileDialogComplete(numFilesSelected, numFilesQueued) {
	try {
		if (numFilesSelected > 0) {
			$("#btnCancel").linkbutton({
				disabled : false
			});
		}

		/* I want auto start the upload and I can do that here */
		// this.startUpload();
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadStart(file) {
	try {
		/*
		 * I don't want to do any file validation or anything, I'll just update
		 * the UI and return true to indicate that the upload should start. It's
		 * important to update the UI here because in Linux no uploadProgress
		 * events are called. The best we can do is say we are uploading.
		 */
		var progress = new FileProgress(file,
				this.customSettings.progressTarget);
		progress.setStatus("准备上传...");
		progress.toggleCancel(true, this);
	} catch (ex) {
	}

	return true;
}

function uploadProgress(file, bytesLoaded, bytesTotal) {
	try {
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);

		var progress = new FileProgress(file,
				this.customSettings.progressTarget);
		progress.setProgress(percent);
		progress.setStatus("上传时间可能较长，请耐心等待...");
	} catch (ex) {
		this.debug(ex);
	}
}

/**
 * 图片上传成功后操作
 * 
 * @param file
 * @param serverData
 */
function uploadImagesSuccess(file, serverData) {
	try {
		var progress = new FileProgress(file,
				this.customSettings.progressTarget);
		progress.setComplete();
		progress.setStatus("已上传.");
		progress.toggleCancel(false);

		var json = eval("(" + serverData + ")");
		if (json.success) {
			var html = '<div class="div-picture">'
					+ '<a href="'
					+ json.picture.path
					+ '" target="_blank"><img class="img-picture" src="'
					+ json.picture.path
					+ '"></a>'
					+ '<br/><a href="javascript:void(0)" onclick="deletePicture(\''
					+ json.picture.path + '\',this)">删除</a>' + '</div>';
			$('#pictureArea').append(html);
		}
	} catch (ex) {
		this.debug(ex);
	}
}
function uploadContractSuccess(file, serverData) {
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

		var html = "<div class=\"my-row  contract-attach\"><div class=\"my-col\"><div class=\"fitem full-double\"><label>附件名称</label><input class=\"contract-attach-name\"  class=\"easyui-textbox\" value=\""
				+ json.fileTitle
				+ "\" /><input type=\"hidden\" value=\""
				+ json.filePath
				+ "\" class=\"contract-attach-url\" /><a href=\"."
				+ json.filePath
				+ "\"  target=\"_blank\">查看源文件 </a></div></div><div class=\"my-col\"><div class=\"fitem full-double\"><label>附件摘要</label><input class=\"contract-attach-abstracts\" value=\" \" class=\"easyui-textbox\"  data-options=\"multiline:true,prompt: \'请输入附件摘要…\'\" style=\"height: 80px\" /><input type=\"hidden\" value=\" \" class=\"contract-attach-id\"/><a href=\"javascript:void(0)\" onclick=\"deleteAttachment(this)\">删除</a></div></div><div class=\"clear\"></div></div>";
		$('#attachment-button').before(html);
		$("input[class='contract-attach-name']").last().textbox();
		$("input[class='contract-attach-abstracts']").last().textbox();
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadReinsuranceAttachmentSuccess(file, serverData) {
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

		var html = "<div class=\"fitem reinsurance-attach-name-div\"><label>附件名称</label><input  class=\"reinsurance-attach-name\" class=\"easyui-textbox\" value=\""
				+ json.fileTitle
				+ "\" /><input type=\"hidden\" value=\""
				+ json.filePath
				+ "\" class=\"reinsurance-attach-url\" /><a href=\"."
				+ json.filePath
				+ "\"  target=\"_blank\">查看源文件 </a></div><div class=\"fitem  reinsurance-attach-abstracts-div\"><label>附件摘要</label><input  class=\"reinsurance-attach-abstracts\" class=\"easyui-textbox\" value=\" \"  data-options=\"multiline:true,prompt: \'请输入附件摘要…\'\" style=\"height: 80px\" /><input type=\"hidden\" value=\" \" class=\"reinsurance-attach-id\"/><a href=\"javascript:void(0)\" onclick=\"deleteAttachment(this)\">删除</a></div>";
		// console.log(json.filePath);
		$('#attachment-url-commaString').before(html);
		$("input[class='reinsurance-attach-name']").last().textbox();
		$("input[class='reinsurance-attach-abstracts']").last().textbox();
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadSuccess(file, serverData) {
	try {
		var progress = new FileProgress(file,
				this.customSettings.progressTarget);
		progress.setComplete();
		progress.setStatus("已上传.");
		progress.toggleCancel(false);
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadError(file, errorCode, message) {
	try {
		var progress = new FileProgress(file,
				this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);

		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			progress.setStatus("上传错误: " + message);
			this
					.debug("错误: HTTP Error, 文件名: " + file.name + ", 信息: "
							+ message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
			progress.setStatus("上传失败.");
			this.debug("错误: 上传失败, 文件名: " + file.name + ", 文件大小: " + file.size
					+ ", 信息: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			progress.setStatus("服务器（IO）错误.");
			this.debug("错误: IO 错误, 文件名: " + file.name + ", 信息: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
			progress.setStatus("安全错误.");
			this.debug("错误: 安全错误, 文件名: " + file.name + ", 信息: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			progress.setStatus("上传超出限制.");
			this.debug("错误: 上传超出限制, 文件名: " + file.name + ", 文件大小: " + file.size
					+ ", 信息: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
			progress.setStatus("文件检验失败,已跳过.");
			this.debug("错误: 文件检验失败, 文件名: " + file.name + ", 文件大小: " + file.size
					+ ", 信息: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			// If there aren't any files left (they were all cancelled) disable
			// the cancel button
			if (this.getStats().files_queued === 0) {
				$("#btnCancel").linkbutton({
					disabled : true
				});
			}
			progress.setStatus("已取消.");
			progress.setCancelled();
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			progress.setStatus("已停止.");
			break;
		default:
			progress.setStatus("未知错误: " + errorCode);
			this.debug("错误: " + errorCode + ", 文件名: " + file.name + ", 文件大小: "
					+ file.size + ", 信息: " + message);
			break;
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadComplete(file) {
	if (this.getStats().files_queued === 0) {
		$("#btnCancel").linkbutton({
			disabled : true
		});
		$("#dialog-addAttachment").dialog('close');// 关闭上传文件对话框
	}
}

// This event comes from the Queue Plugin
function queueComplete(numFilesUploaded) {
	var status = document.getElementById("divStatus");
	status.innerHTML = numFilesUploaded + " 个文件已经上传";
}
