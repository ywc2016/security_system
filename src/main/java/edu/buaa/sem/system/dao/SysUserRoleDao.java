package edu.buaa.sem.system.dao;

import edu.buaa.sem.common.BaseDao;
import edu.buaa.sem.common.IDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class SysUserRoleDao extends BaseDao<SysUserRole> implements
		IDao<SysUserRole> {
	public SysUserRoleDao() {
		logger = Logger.getLogger(SysUserRoleDao.class);
	}

	@Override
	public void updateByKeys(SysUserRole pojo, String key, long[] ids) {
	}

}
