package edu.buaa.sem.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.buaa.sem.user.dao.UserDao;

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
