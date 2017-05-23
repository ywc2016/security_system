package edu.buaa.sem.system.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.buaa.sem.system.model.DatagridModel;
import edu.buaa.sem.system.service.SysUserService;

@Controller
@RequestMapping("/admin/system")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;

	@RequestMapping(value = "/adminUser")
	public String adminUser() {
		return "admin/system/adminUser";
	}

	@RequestMapping(value = "/findAdminUserByExampleForPagination")
	@ResponseBody
	public Map<String, Object> findAdminUserByExampleForPagination(@Valid DatagridModel model, @Valid SysUser pojo) {
		// pojo.setUserType("系统用户");
		Map<String, Object> responseJson = sysUserService.findByParamsForPagination(pojo, model.getPage(),
				model.getRows(), model.getSort(), model.getOrder());
		return responseJson;
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public void add(@Valid SysUser sysUser) {
		sysUserService.add(sysUser);
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public void update(@Valid SysUser sysUser) {
		sysUserService.update(sysUser);
	}

	@RequestMapping(value = "/disabled")
	@ResponseBody
	public void disabled(@RequestParam(value = "idCommaString", required = true) String idStr) {
		if (idStr != null && !idStr.isEmpty()) {
			sysUserService.disabled(idStr);
		}
	}

	@RequestMapping(value = "/roleManagement")
	public String roleManagement() {
		return "admin/system/roleManagement";
	}

	/**
	 * 根据sysUserId查找其对应的角色情况，并返回json
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findUserRoleByUserIdForPagination", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findUserRoleByUserIdForPagination(@Valid DatagridModel model, SysRole pojo,
			@RequestParam(value = "userId", required = true) String userId) {
		Map<String, Object> responseJson = sysUserService.findUserRoleByUserIdForPagination(model, pojo,
				Long.valueOf(userId));
		return responseJson;
	}

	@RequestMapping(value = "/addUserRole", method = RequestMethod.POST)
	@ResponseBody
	public void addUserRole(@RequestParam(value = "sysUserId", required = true) String sysUserId,
			@RequestParam(value = "sysRoleId", required = true) String sysRoleId,
			@RequestParam(value = "enabled", required = true) String enabled) {
		sysUserService.addUserRole(sysUserId, sysRoleId, enabled);
	}

	@RequestMapping(value = "/deleteUserRole", method = RequestMethod.POST)
	@ResponseBody
	public void deleteUserRole(@RequestParam(value = "idCommaString", required = true) String idCommaString) {
		sysUserService.deleteUserRole(idCommaString);
	}
}
