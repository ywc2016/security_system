package edu.buaa.sem.user.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import edu.buaa.sem.common.BaseDao;
import edu.buaa.sem.common.IDao;

@Repository
public class InfStaffDao extends BaseDao<InfStaff> implements IDao<InfStaff> {

	@Override
	public void updateByKeys(InfStaff pojo, String key, long[] ids) {

	}

	public InfStaffDao() {
		logger = Logger.getLogger(InfStaffDao.class);
	}

}
