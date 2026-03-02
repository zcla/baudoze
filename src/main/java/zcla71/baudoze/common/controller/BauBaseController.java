package zcla71.baudoze.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.servlet.ModelAndView;

import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.auth_user.model.service.AuthUserService;

public abstract class BauBaseController {
	@Autowired
	private AuthUserService authUserService;

	protected void addAuthInfo(ModelAndView mav, OidcUser user) {
		mav.addObject("authUserName", user.getAttribute("name"));
		mav.addObject("authUserPicture", user.getAttribute("picture"));
		mav.addObject("authUserEmail", user.getAttribute("email"));
	}

	protected AuthUser getAuthUser(OidcUser oidcUser, Authentication authentication) {
		return authUserService.buscarPorProviderESubject(
			((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId(),
			oidcUser.getSubject()
		);
	}
}
