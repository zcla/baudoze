package zcla71.baudoze.auth_user.model.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class AuthUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String provider; // Provedor: google, keycloak, github, ...

	private String subject;  // ID interno do usuário no provedor (imutável)

	private String nome;

	private String email;

	private String urlImagem;

	private Instant criadoEm;

	private Instant ultimoLoginEm;

	public AuthUser(String provider, String subject) {
		this.provider = provider;
		this.subject = subject;
		this.criadoEm = Instant.now();
		registrarLogin();
	}

	public void registrarLogin() {
		this.ultimoLoginEm = Instant.now();
	}
}
