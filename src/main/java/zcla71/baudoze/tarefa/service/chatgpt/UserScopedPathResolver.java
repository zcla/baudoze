package zcla71.baudoze.tarefa.service.chatgpt;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.nio.file.Path;
import java.util.Objects;

public class UserScopedPathResolver {

    private final Path baseDir;

    public UserScopedPathResolver(Path baseDir) {
        this.baseDir = Objects.requireNonNull(baseDir, "baseDir").normalize();
    }

    public Path fileForUserEmail(String prefix, String email, String ext) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email is blank");
        }
        String userId = UserFileIds.emailHashUrlSafe(email);
        String filename = prefix + "-" + userId + ext;

        Path candidate = baseDir.resolve(filename).normalize();
        if (!candidate.startsWith(baseDir)) {
            throw new SecurityException("Resolved path escapes baseDir");
        }
        return candidate;
    }

    public Path tarefasFileFor(OidcUser user) {
        String email = user.getEmail();
        if (email == null || email.isBlank()) {
            throw new IllegalStateException("OIDC user has no email claim");
        }
        return fileForUserEmail("tarefas", email, ".json");
    }
}
