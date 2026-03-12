package zcla71.baudoze.common.controller;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.servlet.ModelAndView;

public abstract class BauBaseController {
	protected void addAuthInfo(ModelAndView mav, OidcUser user) {
		// TODO Seria melhor _auth.userName, ...
		mav.addObject("_authUserName", user.getAttribute("name"));
		mav.addObject("_authUserPicture", user.getAttribute("picture"));
		// mav.addObject("_authUserEmail", user.getAttribute("email"));
	}
}
