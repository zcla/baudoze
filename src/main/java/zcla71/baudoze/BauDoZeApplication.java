package zcla71.baudoze;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties(BauDoZeProperties.class)
@ComponentScan(basePackages = { "zcla71" })
public class BauDoZeApplication {
	public static void main(String[] args) {
		SpringApplication.run(BauDoZeApplication.class, args);
	}

	// TODO Backup automático
	// Autenticação do Google
	// TODO 1. Botão sair.
	// TODO 2. Guardar o último usuário logado.
	// TODO 3. "429. That’s an error. The rate limit for this service has been exceeded. That’s all we know." Guardar imagem do usuário logado?
}
