package zcla71.baudoze;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "app")
@Deprecated // TODO Já foi utilizado para pegar propriedades do application.yml; não uso mais, mas deixo para referência futura.
public class BauDoZeProperties {
	private Path storagePath = Path.of("./data");
	private String storageProfile = "json";
}
