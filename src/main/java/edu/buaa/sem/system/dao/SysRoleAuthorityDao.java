package edu.buaa.sem.system.dao;

import edu.buaa.sem.common.BaseDao;
import edu.buaa.sem.common.IDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class SysRoleAuthorityDao extends BaseDao<SysRoleAuthority> implements
		IDao<SysRoleAuthority> {
	public SysRoleAuthorityDao() {
		logger = Logger.getLogger(SysRoleAuthorityDao.class);
	}

	@Override
	public void updateByKeys(SysRoleAuthority pojo, String key, long[] ids) {
	}

}
