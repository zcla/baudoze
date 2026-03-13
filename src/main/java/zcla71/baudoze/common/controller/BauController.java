package zcla71.baudoze.common.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import zcla71.baudoze.auth_user.model.entity.AuthUser;

@Controller
public class BauController extends BauBaseController {
	// Controller

	@GetMapping("/")
	public ModelAndView index(@AuthenticationPrincipal AuthUser authUser) {
		ModelAndView result = new ModelAndView("/index");
		addAuthInfo(result, authUser);
		return result;
	}
}
