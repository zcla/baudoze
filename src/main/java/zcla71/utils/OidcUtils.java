package zcla71.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
@Deprecated // Já foi usado com sucesso, mas não utilizo mais. Mantenho aqui caso seja necessário no futuro, pois funciona perfeitamente.
public class OidcUtils {
	public OidcUser getLoggedUser() {
		return (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
