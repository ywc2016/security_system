<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="spr" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="./backStatic.jsp"%>
<title>登录</title>

<style type="text/css">
body {
	margin: 0px;
}

html, body {
	height: 100%;
	width: 100%;
}

.background-gradient {
	height: 100%;
	background-image: -webkit-linear-gradient(top, #7dc1e1, #f2f9f6);
	background-image: -moz-linear-gradient(top, #7dc1e1, #f2f9f6);
	filter: progid:DXImageTransform.Microsoft.gradient(StartColorStr="#7dc1e1",
		EndColorStr="#f2f9f6");
	background-image: -ms-linear-gradient(top, #7dc1e1, #f2f9f6);
	background-image: linear-gradient(top, #7dc1e1, #f2f9f6);
}

.logo {
	padding-top: 0px;
	height: 45px;
	width: 450px;
}

.pageContent {
	box-shadow: 1px 3px 8px rgba(0, 0, 0, 0.2);
	background: #fafafa;
	padding: 30px;
	width: 440px;
	height: 500px;
	background-color: rgba(250, 250, 250, 0.8)
}

.login-form {
	border-top: 1px solid #000000;
	padding: 20px;
}

.login-form label {
	color: #333333;
	font-size: 14px;
	font-weight: bold;
}

.login-form input[type="text"], .login-form input[type="password"] {
	border: 1px solid #CCCCCC;
	border-radius: 5px;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1) inset;
	color: #666666;
	font-size: 15px;
	height: 24px;
	width: 200px;
	font-size: 15px
}

.color-btn {
	border-radius: 4px;
	border-style: solid;
	border-width: 1px;
	border-color: #0650f8 #0650f8 #0650f8;
	cursor: pointer;
	padding-left: 16px;
	padding-right: 16px;
	padding-top: 6px;
	padding-bottom: 22px;
	background-color: #00bfff;
	box-shadow: 0 1px 0 rgba(255, 255, 255, 0.2) inset, 0 1px 1px
		rgba(0, 0, 0, 0.05);
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
	text-align: center;
	color: #fafafa;
	font-size: 14px;
	font-weight: bold;
}

.color-btn:hover, .color-btn:focus {
	color: #333333;
	text-decoration: none;
	background-color: #F89406;
	-webkit-transition: background-position 0.1s linear;
	-moz-transition: background-position 0.1s linear;
	-o-transition: background-position 0.1s linear;
	transition: background-position 0.1s linear;
}

.col-center-block {
	float: none;
	display: block;
	margin-left: auto;
	margin-right: auto;
}

.logo-checked {
	position: absolute;
	/*background-position: 0 -96px;*/
	width: 45px;
	height: 45px;
	font-size: 0;
	background-image:
		url(${pagecontext.request.getcontextpath}/images/assets/logo.png);
}
</style>
</head>
<body
	style="background: url(./images/default/theme.jpg) 50% 0 no-repeat;">
	<div align="center">
		<div class="logo"></div>
		<div class="pageContent" style="margin-left: 50%;">
			<h1>电子文档安全管理系统</h1>
			<!-- <h2>管理信息系统</h2> -->
			<form action="./admin/j_spring_security_check" method="post"
				class="login-form">
				<sec:csrfInput />
				<c:if test="${ not empty param.error and param.error == 0}">用户名或密码错误！
				</c:if>
				<c:if
					test="${ not empty param.error and param.error > 0 and param.error < 5}">登录失败，今天还能尝试<c:out
						value="${5 - param.error}" />次！
				</c:if>
				<c:if test="${ not empty param.error and param.error >= 5}">登录失败次数过多，账户已被锁定！请次日五点后再登录！
				</c:if>
				<c:if test="${ not empty param.pwdWeek}">密码格式不符合规范
				</c:if>
				<table>
					<tr>
						<td><label for="username">用户名：</label></td>
						<td><input type="text" id="username" name="j_username"
							<c:out value="${SPRING_SECURITY_LAST_USERNAME}"/> /></td>
					</tr>
					<tr>
						<td><label for="password">密码：</label></td>
						<td><input type="password" id="password" name="j_password" /></td>
					</tr>

					<tr>
						<td style="text-align: right;"><input id="submit"
							type="submit" value="登录" class="color-btn"
							onclick="return checkNull();" />
						<td><input class="color-btn" type="button" id="refresh"
							value="刷新" /></td>
					</tr>
					<tr>
						<td colspan="2" style="text-align: center;">请选中图片中的"<span
							id="tip" style="color: red">${tip}</span>"
						</td>
					</tr>
				</table>
				<div style="width: 400px; height: 200px; position: relative"
					id="png">
					<img
						src="${pageContext.request.contextPath}/images/assets/tmp/${name}"
						alt="" id="images">
				</div>

			</form>
		</div>
		<%@ include file="footer.jsp"%>
	</div>
</body>
<script type="text/javascript">
	var index = 0;
	$(function() {
		$("#png").click(function(event) {
			event = event || window.event;
			var x = event.offsetX || event.originalEvent.layerX;
			var y = event.offsetY || event.originalEvent.layerY;
			var div = $("<div></div>");
			div.addClass("logo-checked");
			div.css("left", (x - 12) + "px");
			div.css("top", (y - 12) + "px");
			div.attr("index", index++);
			div.click(function() {
				var nowindex = div.attr("index");
				$('.logo-checked[index=' + nowindex + ']').remove();
			});
			/**
			 * 阻止边框的影响
			 * 处理不完美
			 */
			if (x > 13 && y > 13 && x < 400 && y < 200) {
				//                var value=$("#location").val()+x+","+y+";"
				//                $("#location").val(value);
				$("#png").append(div);
			}

		});
		/* 	$("#submitForm").click(function() {
				var childerns = $(".logo");
				var value = "";
				for (var i = 0; i < childerns.length; i++) {
					value += $(childerns[i]).css("left") + ",";
					value += $(childerns[i]).css("top") + ";";
				}
				value = value.replace(/px/g, "");
				$("#location").val(value);
				$(".form-signin").submit();
			}); */
		$("#refresh").click(function() {
			$.ajax({
				url : "./admin/getPng",
				success : function(data) {
					var datas = data.split(",");
					$("#images").attr("src", "/images/assets/tmp/" + datas[0]);
					$("#tip").html(datas[1]);
					$(".logo-checked").each(function(i, e) {
						e.remove();
					});
				},
				error : function() {
					alert("请求失败");
				}
			});
		});
	});

	function checkNull() {
		var username = $("#username").val();
		var password = $("#password").val();
		if (username == "" || password == "") {
			$.messager.alert('信息', '用户名密码不能为空!', 'info');
			return false;
		}
		//验证图片验证码
		var childerns = $(".logo-checked");
		var value = "";
		for (var i = 0; i < childerns.length; i++) {
			value += $(childerns[i]).css("left") + ",";
			value += $(childerns[i]).css("top") + ";";
		}
		value = value.replace(/px/g, "");
		$("#location").val(value);
		var location = value;
		var flag = false;
		//发送ajax请求
		$.ajax({
			url : './admin/doLogin',
			type : "post",
			async : false,
			data : {
				location : location,
			},
			success : function(data) {
				//TODO 为了测试方便,暂时不用验证码功能
				//flag = true;
				//return true;

				if (data == "验证码错误") {
					$.messager.alert('提示', '验证码错误!', 'info');
					flag = false;
					return false;
				}
				if (data == "系统错误") {
					$.messager.alert('提示', '系统错误!', 'info');
					flag = false;
					return false;
				}
				if (data == "true") {
					flag = true;
					return true;
				}
			},
			error : function() {
				$.messager.alert('提示', '操作失败!', 'info');
				flag = false;
				return false;
			}
		});
		if (!flag) {
			$("#refresh").click();
		}
		return flag;
	}
</script>

</html>