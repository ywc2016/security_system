package edu.buaa.sem.system.service;

import edu.buaa.sem.common.BaseService;
import edu.buaa.sem.system.dao.SysRoleAuthorityDao;
import edu.buaa.sem.system.dao.SysUserRoleDao;
import edu.buaa.sem.system.model.DatagridModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserRoleService extends BaseService {

	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleAuthorityDao sysRoleAuthorityDao;

	public void saveOrUpdate(SysUserRole pojo) {
		sysUserRoleDao.saveOrUpdate(pojo);
	}

	public void deleteByKeys(String propertyName, String[] value, String type) {
		sysUserRoleDao.deleteByKeys(propertyName, value, type);
	}

	public List<SysUserRole> findByPropertyEqual(String propertyName, String value, String type) {
		List<SysUserRole> pojos = sysUserRoleDao.findByPropertyEqual(propertyName, value, type);
		return pojos;
	}

	public Map<String, Object> findUserRoleByRoleIdForPagination(DatagridModel model, String roleId) {
		SysUser pojo = new SysUser();
		// 查询相应的分页user列表，以供特定role勾选
		List<SysUser> pojos = sysUserService.findByExampleForPagination(pojo, model.getPage(), model.getRows(),
				model.getSort(), model.getOrder());
		long count = sysUserService.countByExample(pojo);

		// 查询特定user与role的关联情况
		List<SysUserRole> jointPojos = findByPropertyEqual("sysRoleId", String.valueOf(roleId), "long");

		// 遍历pojos（userList）,对每个user查看是否已与选定的role关联上了，再将需要的属性放入hashMap中
		List<HashMap<String, Object>> pojoList = sysUserService.matchUserRole(pojos, jointPojos);

		Map<String, Object> responseJson = new HashMap<String, Object>();
		responseJson.put("total", count);
		responseJson.put("rows", pojoList);

		return responseJson;
	}

	public void addRoleUserRelation(String sysUserId, String sysRoleId, String enabled) {
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setEnabled(enabled);
		sysUserRole.setSysRoleId(Long.valueOf(sysRoleId));
		sysUserRole.setSysUserId(Long.valueOf(sysUserId));
		sysUserRoleDao.saveOrUpdate(sysUserRole);
	}

	public void deleteRoleUserRelation(String idCommaString) {
		sysUserRoleDao.delete(sysUserRoleDao.findByKey(Long.valueOf(idCommaString)));
	}

	public void addRoleAuthorityRelation(String sysAuthorityId, String sysRoleId, String enabled) {
		SysRoleAuthority sysRoleAuthority = new SysRoleAuthority();
		sysRoleAuthority.setEnabled(enabled);
		sysRoleAuthority.setSysAuthorityId(Long.valueOf(sysAuthorityId));
		sysRoleAuthority.setSysRoleId(Long.valueOf(sysRoleId));
		sysRoleAuthorityDao.saveOrUpdate(sysRoleAuthority);
	}

	public void deleteRoleAuthorityRelation(String idCommaString) {
		sysRoleAuthorityDao.delete(sysRoleAuthorityDao.findByKey(Long.valueOf(idCommaString)));
	}

}
