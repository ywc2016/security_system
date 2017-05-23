<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改密码</title>
<%@ include file="./backStatic.jsp"%>
<script type="text/javascript">
	function check() {
		var oldPassword = $("#oldPassword").val();
		var newPassword = $("#newPassword").val();
		var newPasswordCopy = $("#newPasswordCopy").val();
		if (oldPassword == "" || newPassword == "" || newPasswordCopy == "") {
			$.messager.alert('信息', '请将密码输入完整!', 'info');
			return false;
		} else if (newPassword != newPasswordCopy) {
			$.messager.alert('信息', '两次新密码输入不一致!', 'info');
			return false;
		} else {
			return true;
		}
	}
</script>
<style type="text/css">
.change-password-form {
	border-top: 1px solid #D3D3D3;
	padding: 20px;
}

.change-password-form label {
	color: #333333;
	font-size: 15px;
	font-family: 微软雅黑, 微软雅黑, Verdana, Tahoma;
	float: right;
}

.lable-tip  {
	color: red;
	font-size: 15px;
	font-family: 微软雅黑, 微软雅黑, Verdana, Tahoma;
	text-align: center;
}

.change-password-form input[type="password"] {
	width: 200px;
	
}

.color-btn {
	border-radius: 4px;
	border-style: solid;
	border-width: 1px;
	border-color: #F89406 #F89406 #AD6704;
	cursor: pointer;
	padding-left: 16px;
	padding-right: 16px;
	padding-top: 6px;
	padding-bottom: 22px;
	background-color: #FBB450;
	box-shadow: 0 1px 0 rgba(255, 255, 255, 0.2) inset, 0 1px 1px
		rgba(0, 0, 0, 0.05);
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
	text-align: center;
	color: #fafafa;
	font-size: 14px;
	font-weight: bold;
}

.color-btn:hover,.color-btn:focus {
	color: #333333;
	text-decoration: none;
	background-color: #F89406;
	-webkit-transition: background-position 0.1s linear;
	-moz-transition: background-position 0.1s linear;
	-o-transition: background-position 0.1s linear;
	transition: background-position 0.1s linear;
}
</style>
</head>
<body>
	<div align="center">
		<div class="easyui-panel" title="修改密码"
			style="width: 600px; height: 500px; padding: 10px;">
			<div>
				<form action="./admin/changePassword" method="post"
					class="change-password-form">
					<table>
						<tr>
							<td  colspan="2" class="lable-tip">${message}</td>
						</tr>
						<tr>
							<td><label for=oldPassword>原密码：</label></td>
							<td><input type="password" id="oldPassword"
								name="oldPassword" class="easyui-textbox" /></td>
						</tr>
						<tr>
							<td><label for="newPassword">新密码：</label></td>
							<td><input type="password" id="newPassword"
								name="newPassword" class="easyui-textbox" /></td>
						</tr>
						<tr>
							<td><label for="newPasswordCopy">确认新密码：</label></td>
							<td><input type="password" id="newPasswordCopy"
								name="newPasswordCopy" class="easyui-textbox" /></td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: right;"><input
								id="submit" type="submit" value="确定" class="color-btn"
								onclick="return check();" />
						</tr>
					</table>
					<sec:csrfInput />
				</form>
			</div>
		</div>
	</div>
</body>
</html>