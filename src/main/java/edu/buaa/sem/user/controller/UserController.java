package edu.buaa.sem.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.buaa.sem.user.service.UserService;

@Controller
@RequestMapping(value = "/admin/user")
public class UserController {
	@Autowired
	private UserService userService;

	public boolean isExistUserName(String userName) {
		return userService.isExistUserName(userName);
	}

	public boolean isPasswordRight(String userName, String password) {
		return userService.isPasswordRight(userName, password);
	}
}
