package zcla71.baudoze.common.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import zcla71.baudoze.auth_user.model.entity.AuthUser;

public final class UserContext {
	public static AuthUser getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof AuthUser authUser) {
			return authUser;
		}
		return null;
	}

	public static Long getUserId() {
		AuthUser authUser = getUser();
		if (authUser != null) {
			return authUser.getId();
		}
		return null;
	}
}
