package zcla71.baudoze.tarefa.service.chatgpt;

import java.nio.file.Path;

@FunctionalInterface
public interface FilePathProvider {
    Path get();
}
