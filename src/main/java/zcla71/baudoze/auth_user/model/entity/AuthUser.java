package zcla71.baudoze.auth_user.model.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@Entity
@Slf4j
public class AuthUser implements OidcUser {
	// OidcUser

	@Transient
	private OidcUser delegate;

	public void setDelegate(String provider, OidcUser delegate) {
		this.provider = provider;
		this.delegate = delegate;
		this.subject = delegate.getSubject();
		this.nome = delegate.getFullName();
		this.email = delegate.getEmail();
		this.urlImagem = delegate.getPicture();

		// Faz o download da imagem
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(this.urlImagem))
					.build();
			HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
			this.imagem = response.body();
			this.imagemContentType = response
					.headers()
					.firstValue("Content-Type")
					.orElse(URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(this.imagem)));
		} catch (IOException | InterruptedException e) {
			log.warn("Erro ao baixar a url do usuário", e);
		}
	}

	@Override
	@Transient
	public Map<String, Object> getAttributes() {
		return delegate.getAttributes();
	}

	@Override
	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return delegate.getAuthorities();
	}

	@Override
	@Transient
	public String getName() {
		return delegate.getName();
	}

	@Override
	@Transient
	public Map<String, Object> getClaims() {
		return delegate.getClaims();
	}

	@Override
	@Transient
	public OidcUserInfo getUserInfo() {
		return delegate.getUserInfo();
	}

	@Override
	@Transient
	public OidcIdToken getIdToken() {
		return delegate.getIdToken();
	}

	// AuthUser

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String provider; // Provedor: google, keycloak, github, ...

	private String subject;  // ID interno do usuário no provedor (imutável)

	private String nome;

	private String email;

	private String urlImagem;

	@Lob
	private byte[] imagem;

	private String imagemContentType;

	private Instant criadoEm;

	private Instant ultimoLoginEm;

	// Só é chamado no primeiro login
	public AuthUser(String provider, OidcUser delegate) {
		this.setDelegate(provider, delegate);
		criadoEm = Instant.now();
	}

	public void registrarLogin() {
		this.ultimoLoginEm = Instant.now();
	}
}
