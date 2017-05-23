package edu.buaa.sem.user.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import edu.buaa.sem.common.BaseDao;

@Repository
public class UserDao extends BaseDao<SysUser> {

	public UserDao() {
		logger = Logger.getLogger(UserDao.class);
	}

	public boolean isExistUserName(String userName) {
		List pojos = findByPropertyEqual("name", userName, "String");
		if (pojos == null || pojos.size() == 0) {
			return false;
		}
		logger.debug("isExistUserName.");
		return true;
	}

	public boolean isPasswordRight(String userName, String password) {
		List pojos = findByPropertyEqual("name", userName, "String");
		SysUser user = (SysUser) pojos.get(0);
		if (user.getPassword().equals(password)) {
			logger.debug("isPasswordRight right.");
			return true;
		}
		logger.info("isPasswordRight wrong.");
		return false;
	}
}
