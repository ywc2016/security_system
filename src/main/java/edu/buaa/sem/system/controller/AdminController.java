package edu.buaa.sem.system.controller;

import edu.buaa.sem.system.service.SysUserService;
import edu.buaa.sem.utils.EncryptionUtils;
import edu.buaa.sem.validate.model.ImageResult;
import edu.buaa.sem.validate.util.GenerateImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private SysUserService sysUserService;

	@RequestMapping(value = "/fast", method = RequestMethod.GET)
	public String adminLogin(Model model, ServletResponse response, ServletRequest request) {
		if (sysUserService.isLogin()) {
			return "redirect:/admin/index";
		}
		try {
			ImageResult imageResult = GenerateImage.generateImage(request);
			model.addAttribute("tip", imageResult.getTip());
			model.addAttribute("name", imageResult.getName());
			((HttpServletResponse) response).addCookie(new Cookie("note", imageResult.getUniqueKey()));
			((HttpServletRequest) request).getSession().setAttribute("imageResult", imageResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/admin/login";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(@RequestParam(value = "type", required = false, defaultValue = "document") String type,
			Model model) {
		model.addAttribute("type", type);
		return "/admin/index";
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changePassword() {
		return "/admin/changePassword";
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePassword(@RequestParam(value = "oldPassword", required = true) String oldPassword,
			@RequestParam(value = "newPassword", required = true) String newPassword,
			@RequestParam(value = "newPasswordCopy", required = true) String newPasswordCopy, Model model) {
		SysUser pojo = sysUserService.getCurrentUser();
		if (pojo == null) {
			return "/admin/login";
		}
		String message = "";
		if (!EncryptionUtils.getMD5(oldPassword).equals(pojo.getPassword())) {
			message = "原密码错误！";
		} else {
			pojo.setPassword(EncryptionUtils.getMD5(newPassword));
			sysUserService.update(pojo);
			message = "修改成功！";
		}
		model.addAttribute("message", message);

		return "/admin/changePassword";
	}

}
