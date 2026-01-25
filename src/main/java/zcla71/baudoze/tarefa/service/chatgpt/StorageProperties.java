package zcla71.baudoze.tarefa.service.chatgpt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

@ConfigurationProperties(prefix = "app.storage")
public class StorageProperties {
    private Path basePath = Path.of("./data");

    public Path getBasePath() { return basePath; }
    public void setBasePath(Path basePath) { this.basePath = basePath; }
}
