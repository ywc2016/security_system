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
import edu.buaa.sem.system.service.SysRoleService;
import edu.buaa.sem.system.service.SysUserRoleService;
import edu.buaa.sem.system.service.SysUserService;

@RequestMapping(value = "admin/system/role")
@Controller
public class RoleController {

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@RequestMapping(value = "/roleManagement")
	public String roleManagement() {
		return "admin/system/roleManagement";
	}

	@RequestMapping(value = "/findByExampleForPagination")
	@ResponseBody
	public Map<String, Object> findByExampleForPagination(@Valid DatagridModel datagridModel, @Valid SysRole sysRole) {
		Map<String, Object> responseJson = sysRoleService.findByParamsForPagination(sysRole, datagridModel.getPage(),
				datagridModel.getRows(), datagridModel.getSort(), datagridModel.getOrder());
		return responseJson;
	}

	/**
	 * 根据sysRoleId查找其对应的用户情况，并返回json
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findUserRoleByRoleIdForPagination")
	@ResponseBody
	public Map<String, Object> findUserRoleByRoleIdForPagination(@Valid DatagridModel model,
			@RequestParam(value = "roleId", required = true) String roleId) {
		Map<String, Object> responseJson = sysUserRoleService.findUserRoleByRoleIdForPagination(model, roleId);
		return responseJson;
	}

	/**
	 * 添加角色用户关联
	 */
	@RequestMapping(value = "/addRoleUserRelation", method = RequestMethod.POST)
	@ResponseBody
	public void addRoleUserRelation(@RequestParam(value = "sysUserId", required = true) String sysUserId,
			@RequestParam(value = "sysRoleId", required = true) String sysRoleId,
			@RequestParam(value = "enabled", required = true) String enabled) {

		sysUserRoleService.addRoleUserRelation(sysUserId, sysRoleId, enabled);
	}

	@RequestMapping(value = "/deleteRoleAuthorityRelation", method = RequestMethod.POST)
	@ResponseBody
	public void deleteRoleAuthorityRelation(
			@RequestParam(value = "idCommaString", required = true) String idCommaString) {
		sysUserRoleService.deleteRoleAuthorityRelation(idCommaString);

	}

	@RequestMapping(value = "/addRoleAuthorityRelation", method = RequestMethod.POST)
	@ResponseBody
	public void addRoleAuthorityRelation(@RequestParam(value = "sysAuthorityId", required = true) String sysAuthorityId,
			@RequestParam(value = "sysRoleId", required = true) String sysRoleId,
			@RequestParam(value = "enabled", required = true) String enabled) {

		sysUserRoleService.addRoleAuthorityRelation(sysAuthorityId, sysRoleId, enabled);
	}

	@RequestMapping(value = "/findRoleAuthorityByAuthorityIdForPagination", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findRoleAuthorityByAuthorityIdForPagination(DatagridModel model, SysRole pojo,
			long authorityId) {
		return sysRoleService.findRoleAuthorityByAuthorityIdForPagination(model, pojo, authorityId);
	}
}
