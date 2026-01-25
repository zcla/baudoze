package zcla71.utils;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import zcla71.baudoze.tarefa.service.chatgpt.CurrentOidcUser;

public class OidcUtils {
	public static DefaultOidcUser getLoggedUser() {
		return (DefaultOidcUser) CurrentOidcUser.require();
	}
}
