package zcla71.baudoze.tarefa.service.chatgpt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Optional;

public final class CurrentOidcUser {
    private CurrentOidcUser() {}

    public static Optional<OidcUser> get() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof OidcUser u)) {
            return Optional.empty();
        }
        return Optional.of(u);
    }

    public static OidcUser require() {
        return get().orElseThrow(() -> new IllegalStateException("No OidcUser authenticated"));
    }
}
