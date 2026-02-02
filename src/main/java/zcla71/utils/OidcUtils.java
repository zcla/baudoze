package zcla71.utils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
public class OidcUtils {
	private final JsonUtils jsonUtils;

	public OidcUtils(JsonUtils jsonUtils) {
		this.jsonUtils = jsonUtils;
	}

	public OidcUser getLoggedUser() {
		return (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public Path getStoragePath(Path baseDir, String fileName) {
		try {
			OidcUser loggedUser = getLoggedUser();
			Path result = baseDir.resolve(Base64.getUrlEncoder().withoutPadding()
					.encodeToString(MessageDigest.getInstance("SHA-256")
							.digest(loggedUser.getEmail().trim().toLowerCase().getBytes(StandardCharsets.UTF_8)))
					+ File.separator + fileName).normalize();
			if (!result.startsWith(baseDir)) {
				throw new SecurityException("Resolved path escapes baseDir");
			}
			File userDataFile = new File(result.getParent().normalize() + File.separator + "_userData.json");
			if (!userDataFile.exists()) {
				this.jsonUtils.writeToFile(loggedUser, userDataFile);
			}
			return result;
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("SHA-256 not available", e);
		}
	}
}
