package zcla71.baudoze;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "app")
public class BauDoZeProperties {
	private Path storagePath = Path.of("./data");
	private String storageProfile = "json";
}
