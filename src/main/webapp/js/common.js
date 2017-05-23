/**
 * 点击切换两种方法
 */
$.fn.toggleFunction = function(fn, fn2) {
	var args = arguments, guid = fn.guid || $.guid++, i = 0, toggler = function(
			event) {
		var lastToggle = ($._data(this, "lastToggle" + fn.guid) || 0) % i;
		$._data(this, "lastToggle" + fn.guid, lastToggle + 1);
		event.preventDefault();
		return args[lastToggle].apply(this, arguments) || false;
	};
	toggler.guid = guid;
	while (i < args.length) {
		args[i++].guid = guid;
	}
	return this.click(toggler);
};

$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

/**
 * 获取网页url问号传递的参数
 * 
 * @param paras
 *            参数名
 * @returns 参数值
 */
function getParameterFromURL(paras) {
	var url = location.href;
	var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
	var paraObj = {};
	for (var i = 0; i < paraString.length; i++) {
		var j = paraString[i];
		paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j
				.indexOf("=") + 1, j.length);
	}
	var returnValue = paraObj[paras.toLowerCase()];
	return typeof (returnValue) == "undefined" ? "" : returnValue;
}

/**
 * 获取系统当前时间，并转换为datebox能识别的字符串
 * 
 * @returns {String}
 */
function getCurrentDateForDatebox() {
	var curr_time = new Date();
	var year = curr_time.getFullYear();
	var month = String(curr_time.getMonth() + 1).length > 1 ? curr_time
			.getMonth() + 1 : "0" + (curr_time.getMonth() + 1);
	var day = String(curr_time.getDate()).length > 1 ? curr_time.getDate()
			: "0" + curr_time.getDate();

	var strDate = year + "-" + month + "-" + day;
	return strDate;
}

/**
 * 图片按比例缩放,参数为img标签的id节点,用document.getElementXXX()作为参数
 */
var flag = false;
function zoomImage(ImgD, height, width) {
	var image = new Image();
	var iwidth = height != null ? heigth : 260; // 定义允许图片宽度
	var iheight = width != null ? width : 180; // 定义允许图片高度
	image.src = ImgD.src;
	image.onload = function() {
		if (image.width > 0 && image.height > 0) {
			flag = true;
			if (image.width / image.height >= iwidth / iheight) {
				if (image.width > iwidth) {
					ImgD.width = iwidth;
					ImgD.height = (image.height * iwidth) / image.width;
				} else {
					ImgD.width = image.width;
					ImgD.height = image.height;
				}
				ImgD.alt = image.width + "×" + image.height;
			} else {
				if (image.height > iheight) {
					ImgD.height = iheight;
					ImgD.width = (image.width * iheight) / image.height;
				} else {
					ImgD.width = image.width;
					ImgD.height = image.height;
				}
				ImgD.alt = image.width + "×" + image.height;
			}
		}
	};
}

/**
 * 日期格式化，不带时间
 * 
 * @param value
 *            需要格式化的日期
 * @returns
 */
function formatDate(value) {
	if (value == null || value == '') {
		return '';
	}
	var dt = parseToDate(value);
	return dt.format("yyyy-MM-dd");
}

/**
 * 日期格式化，带时间
 * 
 * @param value
 *            需要格式化的日期
 * @returns
 */
function formatDateFull(value) {
	if (value == null || value == '') {
		return '';
	}
	var dt = parseToDate(value);
	return dt.format("yyyy-MM-dd hh:mm:ss");
}

/**
 * 将日期字符串转换为时间
 * 
 * @param value
 *            日期字符串
 * @returns
 */
function parseToDate(value) {
	if (value == null || value == '') {
		return undefined;
	}

	var dt;
	if (value instanceof Date) {
		dt = value;
	} else {
		if (!isNaN(value)) {
			dt = new Date(value);
		} else if (value.indexOf('/Date') > -1) {
			value = value.replace(/\/Date(−?\d+)\//, '$1');
			dt = new Date();
			dt.setTime(value);
		} else if (value.indexOf('/') > -1) {
			dt = new Date(Date.parse(value.replace(/-/g, '/')));
		} else {
			dt = new Date(value);
		}
	}
	return dt;
}

/**
 * 生成指定位数的随机数
 * 
 * @param 
 * 		number 生成随机数的位数
 * @returns {String} 返回生成的随机数
 */
function generateRandom(number) {
	var random = "";
	for (var i = 0; i < number; i++) {
		random += Math.floor(Math.random() * 10);
	}
	return random;
}


/**
 * 为Date类型拓展一个format方法，用于格式化日期
 */
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
};

/**
 * csrf功能
 */
var _csrf = {
	parameterName : $("meta[name='_csrf_parameter']").attr("content"),
	token : $("meta[name='_csrf']").attr("content"),
	toString : function() {
		return this.parameterName + '=' + this.token;
	}
};

$.extend($.fn.tree.methods, {
	getAllLeafChildren : function(jq, params) {
		var nodes = [];
		$(params).next().children().children("div.tree-node").each(function() {
			nodes.push($(jq[0]).tree('getNode', this));
		});
		return nodes;
	}
});