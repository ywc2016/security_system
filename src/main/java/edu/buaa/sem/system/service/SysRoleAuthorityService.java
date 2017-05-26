package edu.buaa.sem.system.service;

import edu.buaa.sem.system.dao.SysRoleAuthorityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleAuthorityService {
	@Autowired
	private SysRoleAuthorityDao sysRoleAuthorityDao;

	public void addRoleAuthority(String sysRoleId, String sysAuthorityId, String enabled) {
		SysRoleAuthority sysRoleAuthority = new SysRoleAuthority();
		sysRoleAuthority.setEnabled(enabled);
		sysRoleAuthority.setSysAuthorityId(Long.valueOf(sysAuthorityId));
		sysRoleAuthority.setSysRoleId(Long.valueOf(sysRoleId));
		sysRoleAuthorityDao.saveOrUpdate(sysRoleAuthority);
	}

	public void deleteRoleAuthority(String idCommaString) {
		sysRoleAuthorityDao.delete(sysRoleAuthorityDao.findByKey(Long.valueOf(idCommaString)));
	}
}
