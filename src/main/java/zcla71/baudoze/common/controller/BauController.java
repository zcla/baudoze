package zcla71.baudoze.common.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BauController extends BauBaseController {
	// Controller

	@GetMapping("/")
	public ModelAndView index(@AuthenticationPrincipal OidcUser user) {
		ModelAndView result = new ModelAndView("/index");
		addAuthInfo(result, user);
		return result;
	}
}
