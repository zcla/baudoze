package zcla71.baudoze.tarefa.service.chatgpt;

import java.util.List;
import java.util.Optional;

import zcla71.baudoze.tarefa.service.TarefaEntity;

public interface TarefaRepository {
    Optional<TarefaEntity> findById(Long id);
    List<TarefaEntity> findAll();
    TarefaEntity save(TarefaEntity tarefa);
    void deleteById(Long id);
    boolean existsById(Long id);
    long count();
}
