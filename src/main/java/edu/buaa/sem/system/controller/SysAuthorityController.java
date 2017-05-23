package edu.buaa.sem.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.buaa.sem.system.model.DatagridModel;
import edu.buaa.sem.system.service.AuthorityService;
import edu.buaa.sem.system.service.SysRoleService;
import edu.buaa.sem.system.service.SysUserRoleService;
import edu.buaa.sem.system.service.SysUserService;

@RequestMapping(value = "admin/system/authority")
@Controller
public class SysAuthorityController {

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private AuthorityService authorityService;

	@RequestMapping(value = "/authorityManagement")
	public String roleManagement() {
		return "admin/system/authorityManagement";
	}

	@RequestMapping(value = "/findByExampleForPagination")
	@ResponseBody
	public Map<String, Object> findByExampleForPagination(DatagridModel model, SysAuthority pojo) {
		List<SysAuthority> pojos = authorityService.findByExampleForPagination(pojo, model.getPage(), model.getRows(),
				model.getSort(), model.getOrder());
		long count = authorityService.countByExample(pojo);
		HashMap<String, Object> responseJson = new HashMap<String, Object>();
		responseJson.put("total", count);
		responseJson.put("rows", pojos);
		return responseJson;
	}

	/**
	 * 根据sysRoleId查找其对应的权限情况，并返回json
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findRoleAuthorityByRoleIdForPagination")
	@ResponseBody
	public Map<String, Object> findRoleAuthorityByRoleIdForPagination(DatagridModel model, SysAuthority pojo,
			long roleId) {

		Map<String, Object> responseJson = authorityService.findRoleAuthorityByRoleIdForPagination(model, pojo, roleId);
		return responseJson;
	}

}
