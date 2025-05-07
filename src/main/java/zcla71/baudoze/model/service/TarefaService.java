package zcla71.baudoze.model.service;

import java.util.List;
import java.util.Optional;

import zcla71.baudoze.model.entity.Tarefa;

public interface TarefaService {
    // public Optional<Tarefa> getById(Long id);

    public List<Tarefa> listar();

    public void incluir(Tarefa tarefa);

    // public void update(Tarefa tarefa);

    // public void delete(Long id);
}
