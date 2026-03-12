package zcla71.baudoze.auth_user.model.service;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.auth_user.model.repository.AuthUserRepository;

@Service
public class AuthUserService {
	private final AuthUserRepository authUserRepository;

	public AuthUserService(AuthUserRepository authUserRepository) {
		this.authUserRepository = authUserRepository;
	}

	// Só é usado no login
	@Transactional
	public AuthUser buscarPorProviderEOidcUser(String provider, OidcUser oidcUser) {
		String subject = oidcUser.getSubject();

		AuthUser result = authUserRepository.findByProviderAndSubject(provider, subject).orElse(null);
		if (result == null) {
			result = new AuthUser(provider, oidcUser);
		} else {
			result.setDelegate(provider, oidcUser);
		}
		authUserRepository.save(result);
		return result;
	}

	@Transactional
	public void registrarLogin(AuthUser authUser) {
		authUser.registrarLogin();
		authUserRepository.save(authUser);
	}
}
