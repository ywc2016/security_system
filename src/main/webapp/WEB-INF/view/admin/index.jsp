<%@ page language="java" import="java.util.Date"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="./backStatic.jsp"%>
<title>电子文档安全管理系统</title>
<style type="text/css">
.banner {
	height: 50px;
	text-align: left;
	color: #444444;
	font-size: 14px;
	font-family: 微软雅黑, 微软雅黑, Verdana, Tahoma;
}

.toolbar {
	float: right;
}

.toolbar a {
	position: relative;
	display: inline-block;
	font-size: 14px;
	width: 90px;
	height: 30px;
	line-height: 30px;
	text-align: center;
}

a:link {
	color: #000000;
	text-decoration: none
}

a:visited {
	color: #000000;
	text-decoration: none
}

a:hover {
	color: #ff7f24;
	text-decoration: underline;
}

a:active {
	color: #ff7f24;
	text-decoration: underline;
}

#north {
	padding-top: 15px;
}

#northNav {
	width: 860px;
	float: left;
}

#northNav>a {
	font-size: 18px;
	overflow: hidden;
	font-weight: 600;
	margin-left: 15px;
}

#northWel {
	width: 400px;
	float: right;
	text-align: right;
}

#northWel>a, #northWel>span {
	font-size: 16px;
	overflow: hidden;
	font-weight: 600;
	margin-right: 15px;
}
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" class="banner">
		<div id="north">
			<div id="northNav">
				<a href="./admin/index?type=document">文档</a>
				<sec:authorize ifAnyGranted="AUTH_AUDIT_DOCUMENT">
					<a href="./admin/index?type=audit">审核</a>
				</sec:authorize>
				<sec:authorize ifAnyGranted="AUTH_SYSTEM_ADMIN">
					<a href="./admin/index?type=system">系统</a>
				</sec:authorize>
			</div>
			<div class="toolbar">
				<span>欢迎： <sec:authentication property="principal.staffName" />&nbsp;&nbsp;&nbsp;&nbsp;
				</span> <a href="#" target="_blank">系统操作手册</a>
				<sec:authorize ifAnyGranted="AUTH_ADMIN_CHANGE_PASSWORD">
					<a href="javascript:void(0)" id="menu-user"
						onclick="changePassword();"><img
						src="./images/icons/glyphicons_003_user.png" alt="user">修改密码</a>
				</sec:authorize>
				<a href="javascript:void(0)" id="menu-logout"><img
					src="./images/icons/glyphicons_387_log_out.png" alt="logout">退出系统</a>
			</div>
		</div>
	</div>

	<c:choose>
		<c:when test="${type eq 'document'}">
			<div data-options="region:'west'" title="文档管理" style="width: 15%;">
				<div class="easyui-accordion" data-options="fit:true,border:false">
					<div title="文档管理" data-options="iconCls:'icon-9'"
						style="overflow: auto; padding-top: 10px;">
						<ul id="document" class="easyui-tree">
							<li><div onclick="view();">文档管理</div></li>
							<li><div onclick="versionControl();">版本控制</div></li>
						</ul>
					</div>
					<sec:authorize ifAnyGranted="AUTH_BACKUP">
						<div title="文档备份" data-options="iconCls:'icon-9'"
							style="overflow: auto; padding-top: 10px;">
							<ul id="document-backup" class="easyui-tree">
								<li><div onclick="backup();">备份管理</div></li>
							</ul>
						</div>
					</sec:authorize>
				</div>
			</div>
			<script type="text/javascript">
				$(function() {
					window.parent.$('#center-frame').attr("src",
							"./admin/document/documentView");
				});
				function view() {
					window.parent.$('#center-frame').attr("src",
							"./admin/document/documentView");
				}
				function versionControl() {
					window.parent.$('#center-frame').attr("src",
							"./admin/documentVersion/versionControl");
				}
				function backup() {
					window.parent.$('#center-frame').attr("src",
							"./admin/document/backup/backupManagement");
				}
			</script>
		</c:when>
		<c:when test="${type eq 'audit'}">

			<div data-options="region:'west'" title="文档审核" style="width: 15%;">
				<div class="easyui-accordion" data-options="fit:true,border:false">
					<div title="文档审核" data-options="iconCls:'icon-9'"
						style="overflow: auto; padding-top: 10px;">
						<ul id="audit" class="easyui-tree">
							<li><div onclick="auditManagement();">审核管理</div></li>
						</ul>
					</div>
				</div>
			</div>

			<script type="text/javascript">
				$(function() {
					window.parent.$('#center-frame').attr("src",
							"./admin/audit/auditManagement");

				});
				function auditManagement() {
					window.parent.$('#center-frame').attr("src",
							"./admin/audit/auditManagement");

				}
			</script>
		</c:when>
		<c:when test="${type eq 'system'}">

			<div data-options="region:'west'" title="系统管理" style="width: 15%;">

				<div class="easyui-accordion" data-options="fit:true,border:false">
					<div title="用户管理" data-options="iconCls:'icon-9'"
						style="overflow: auto; padding-top: 10px;">
						<ul class="easyui-tree">
							<li><div onclick="adminUserManagement();">系统用户</div></li>
						</ul>
					</div>
					<div title="权限控制" data-options="iconCls:'icon-9'"
						style="overflow: auto; padding-top: 10px;">
						<ul class="easyui-tree">
							<!--    <li><div onclick="userManagement();">用户管理</div></li> -->
							<li><div onclick="roleManagement();">角色管理</div></li>
							<li><div onclick="authorityManagement();">权限管理</div></li>
						</ul>
					</div>
				</div>
				<script type="text/javascript">
					$(function() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/adminUser");
					});
					function defaultImages() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/defaultImages/index");
					}
					function menuProperty() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/menuProperty/index");
					}
					function sensitiveWordProperty() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/sensitiveWordProperty/index");
					}
					function searchProperty() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/searchProperty/index");
					}
					function dictionaryManagement() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/dictionaryManagement");
					}
					function statisticalInformation() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/statisticalInformation");
					}
					function roleManagement() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/roleManagement");
					}
					function authorityManagement() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/authority/authorityManagement");
					}
					function resourceManagement() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/resourceManagement");
					}
					function interfaceManagement() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/interfaceManagement");
					}
					function systemProperty() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/systemProperty/index");
					}
					function weixinProperty() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/weixinConfigProperty/index");
					}
					function weiboProperty() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/weiboConfigProperty/index");
					}
					function qqProperty() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/qqConfigProperty/index");
					}
					function demandProperty() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/demandFormProperty/index");
					}
					function scheduleJob() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/scheduleManagement");
					}
					function emailModelManagement() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/emailModelManagement");
					}
					function chartManagement() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/chartManagement");
					}
					function adminUserManagement() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/adminUser");
					}
					function commonUserManagement() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/commonUser");
					}
					function realNameUserManagement() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/realNameUser");
					}
					function pictureHouse() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/picture/daotu");
					}
					function avatarManagement() {
						window.parent.$('#center-frame').attr("src",
								"./admin/system/picture/avatar");
					}
				</script>
			</div>
		</c:when>
		<c:when test="${type eq 'stock'}">
			<div data-options="region:'west'" title="库存管理" style="width: 15%;">
				<div class="easyui-accordion" data-options="fit:true,border:false">
					<div title="库存管理" data-options="iconCls:'icon-9'"
						style="overflow: auto; padding-top: 10px;">
						<ul id="allInsuType" class="easyui-tree">
							<li><div onclick="stockManagement();">查看库存信息</div></li>
						</ul>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				$(function() {
					window.parent.$('#center-frame').attr("src",
							"./admin/stock/stockManagement");
				});
				function stockManagement() {
					window.parent.$('#center-frame').attr("src",
							"./admin/stock/stockManagement");
				}
			</script>
		</c:when>
		<c:when test="${type eq 'finance'}">
			<div data-options="region:'west'" title="财务管理" style="width: 15%;">
				<div class="easyui-accordion" data-options="fit:true,border:false">
					<div title="财务管理" data-options="iconCls:'icon-9'"
						style="overflow: auto; padding-top: 10px;">
						<ul id="allInsuType" class="easyui-tree">
							<li><div onclick="receiveableManagement();">查看应收账款</div></li>
							<li><div onclick="payableManagement();">查看应付账款</div></li>
							<li><div onclick="receivedManagement();">查看销售业务账款</div></li>
							<li><div onclick="paidManagement();">查看采购业务账款</div></li>
						</ul>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				$(function() {
					window.parent.$('#center-frame').attr("src",
							"./admin/finance/receiveableManagement");
				});
				function receiveableManagement() {
					window.parent.$('#center-frame').attr("src",
							"./admin/finance/receiveableManagement");
				}
				function payableManagement() {
					window.parent.$('#center-frame').attr("src",
							"./admin/finance/payableManagement");
				}
				function receivedManagement() {
					window.parent.$('#center-frame').attr("src",
							"./admin/finance/receivedManagement");
				}
				function paidManagement() {
					window.parent.$('#center-frame').attr("src",
							"./admin/finance/paidManagement");
				}
			</script>
		</c:when>
		<c:when test="${type eq 'staff'}">
			<div data-options="region:'west'" title="人力资源管理" style="width: 15%;">
				<div class="easyui-accordion" data-options="fit:true,border:false">
					<div title="人力资源管理" data-options="iconCls:'icon-9'"
						style="overflow: auto; padding-top: 10px;">
						<ul id="allInsuType" class="easyui-tree">
							<li><div onclick="staffManagement();">查看员工信息</div></li>
						</ul>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				$(function() {
					window.parent.$('#center-frame').attr("src",
							"./admin/staff/staffManagement");
				});
				function staffManagement() {
					window.parent.$('#center-frame').attr("src",
							"./admin/staff/staffManagement");
				}
			</script>
		</c:when>
		<c:when test="${type eq 'customer'}">
			<div data-options="region:'west'" title="客户管理" style="width: 15%;">
				<div class="easyui-accordion" data-options="fit:true,border:false">
					<div title="客户管理" data-options="iconCls:'icon-9'"
						style="overflow: auto; padding-top: 10px;">
						<ul id="allInsuType" class="easyui-tree">
							<li><div onclick="customerManagement();">查看客户信息</div></li>
							<li><div onclick="creditManagement();">查看客户信用信息</div></li>
						</ul>
					</div>
				</div>
			</div>
			<script type="text/javascript">
				$(function() {
					window.parent.$('#center-frame').attr("src",
							"./admin/customer/customerManagement");
				});
				function customerManagement() {
					window.parent.$('#center-frame').attr("src",
							"./admin/customer/customerManagement");
				}
				function creditManagement() {
					window.parent.$('#center-frame').attr("src",
							"./admin/customer/creditManagement");
				}
			</script>
		</c:when>

		<c:otherwise>
			<div data-options="region:'west'" style="width: 15%;">
				<div class="easyui-accordion" data-options="fit:true,border:false">
				</div>
			</div>
		</c:otherwise>
	</c:choose>

	<div data-options="region:'center'" style="width: 100%;">
		<iframe id="center-frame" scrolling="auto" frameborder="0"
			style="width: 100%; height: 100%;" marginwidth="0" marginheight="0"></iframe>
	</div>

	<div style="display: none;">
		<form action="./admin/j_spring_security_logout" method="post">
			<input type="submit" id="admin-logout" value="退出" />
			<sec:csrfInput />
		</form>
	</div>


	<div id="loading" class="easyui-dialog" modal="true" title="数据处理"
		style="width: 210px; height: 100px; padding: 10px 20px" closed="true"
		modal="true">
		<div id="tipContent" class="fitem">
			<p>数据处理中,请稍候...</p>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			$('#menu-logout').click(function() {
				$('#admin-logout').click();
			})
		});
		function changePassword() {
			window.parent.$('#center-frame').attr("src",
					"./admin/changePassword");
		}
	</script>
</body>
</html>