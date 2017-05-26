package edu.buaa.sem.user.service;

import edu.buaa.sem.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	public boolean isExistUserName(String userName) {

		return userDao.isExistUserName(userName);
	}

	public boolean isPasswordRight(String userName, String password) {
		return userDao.isPasswordRight(userName, password);
	}
}
