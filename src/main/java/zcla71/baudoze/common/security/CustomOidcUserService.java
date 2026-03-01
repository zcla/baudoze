package zcla71.baudoze.common.security;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import zcla71.baudoze.auth_user.model.service.AuthUserService;

@Component
public class CustomOidcUserService extends OidcUserService {
	private final AuthUserService authUserService;

	public CustomOidcUserService(AuthUserService authUserService) {
		this.authUserService = authUserService;
	}

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcUser oidcUser = super.loadUser(userRequest);

		authUserService.processarLogin(
			oidcUser,
			userRequest.getClientRegistration().getRegistrationId()
		);

		return oidcUser;
	}
}
