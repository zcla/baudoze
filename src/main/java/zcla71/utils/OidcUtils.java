package zcla71.utils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class OidcUtils {
	public static OidcUser getLoggedUser() {
		return (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public static Path getStoragePath(Path baseDir, String prefix, String ext) {
		try {
			Path result = baseDir.resolve(prefix + "-"
					+ Base64.getUrlEncoder().withoutPadding().encodeToString(MessageDigest.getInstance("SHA-256")
							.digest(getLoggedUser().getEmail().trim().toLowerCase().getBytes(StandardCharsets.UTF_8)))
					+ ext).normalize();
			if (!result.startsWith(baseDir)) {
				throw new SecurityException("Resolved path escapes baseDir");
			}
			return result;
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("SHA-256 not available", e);
		}
	}
}
