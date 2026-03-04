package zcla71.baudoze.common.security;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
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
	public OidcUser loadUser(OidcUserRequest userRequest) {
		OidcUser oidcUser = super.loadUser(userRequest);

		// TODO Ver https://chatgpt.com/share/69a651e1-84c0-8004-b794-7f4bbec7eda3 => Procurar por "O que é @AuthenticationPrincipal?"
		// TODO Salvar o usuário aqui? Talvez seja bom.
		// TODO Salvar a imagem localmente para evitar o erro 429 - Too many requests.

		authUserService.processarLogin(
			oidcUser,
			userRequest.getClientRegistration().getRegistrationId()
		);

		return oidcUser;
	}
}
