package zcla71.baudoze.common.controller;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.servlet.ModelAndView;

public abstract class BauBaseController {
	protected void addAuthInfo(ModelAndView mav, OidcUser user) {
		mav.addObject("authUserName", user.getAttribute("name"));
		mav.addObject("authUserPicture", user.getAttribute("picture"));
		mav.addObject("authUserEmail", user.getAttribute("email"));
	}
}
