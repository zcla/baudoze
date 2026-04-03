package zcla71.baudoze.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, CustomOidcUserService customOidcUserService) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				// Arquivos estáticos
				.requestMatchers(
						"/",
						"/favicon.ico",
						"/3rdp/**",
						"/css/**",
						"/img/**",
						"/js/**").permitAll()
				// Páginas internas
				.requestMatchers("/_erro/**").permitAll()
				// Bíblia
				.requestMatchers("/biblia/**").permitAll()
				// Todo o resto precisa de login
				.anyRequest().authenticated()
			)
			.oauth2Login(oauth2 -> oauth2
				.userInfoEndpoint(userInfo -> userInfo
					.oidcUserService(customOidcUserService)
				)
			);

		return http.build();
	}
}
