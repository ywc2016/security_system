package edu.buaa.sem.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.buaa.sem.system.service.SysRoleAuthorityService;
import edu.buaa.sem.system.service.SysRoleService;

@Controller
@RequestMapping(value = "admin/system/roleAuthority")
public class SysRoleAuthorityController {
	@Autowired
	private SysRoleAuthorityService sysRoleAuthorityService;

	/**
	 * 添加 权限角色关联
	 */
	@RequestMapping(value = "/addRoleAuthority")
	@ResponseBody
	public void addRoleAuthority(@RequestParam(value = "sysRoleId", required = true) String sysRoleId,
			@RequestParam(value = "sysAuthorityId", required = true) String sysAuthorityId,
			@RequestParam(value = "enabled", required = true) String enabled) {
		sysRoleAuthorityService.addRoleAuthority(sysRoleId, sysAuthorityId, enabled);
	}

	@RequestMapping(value = "/deleteRoleAuthority")
	@ResponseBody
	public void deleteRoleAuthority(@RequestParam(value = "idCommaString", required = true) String idCommaString) {
		sysRoleAuthorityService.deleteRoleAuthority(idCommaString);
	}
}
