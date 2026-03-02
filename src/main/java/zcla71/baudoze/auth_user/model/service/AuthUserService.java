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

	public AuthUser buscarPorProviderESubject(String provider, String subject) {
		return authUserRepository.findByProviderAndSubject(provider, subject).orElse(null);
	}

	@Transactional
	public AuthUser processarLogin(OidcUser oidcUser, String provider) {
		String subject = oidcUser.getSubject();

		return authUserRepository.findByProviderAndSubject(provider, subject)
			.map(u -> {
				// Atualiza dados mutáveis
				atualiza(oidcUser, u);
				return u;
			})
			.orElseGet(() -> {
				AuthUser authUser = new AuthUser(provider, subject);
				atualiza(oidcUser, authUser);
				return authUserRepository.save(authUser);
			});
	}

	private void atualiza(OidcUser oidcUser, AuthUser authUser) {
		authUser.setNome(oidcUser.getFullName());
		authUser.setEmail(oidcUser.getEmail());
		authUser.setUrlImagem(oidcUser.getPicture());
		authUser.registrarLogin();
	}
}
