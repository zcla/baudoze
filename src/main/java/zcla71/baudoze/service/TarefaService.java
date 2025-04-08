package zcla71.baudoze.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zcla71.baudoze.BauDoZeException;
import zcla71.baudoze.entity.Tarefa;
import zcla71.baudoze.repository.TarefaRepository;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository tarefaRepository;

    public Optional<Tarefa> getById(Long id) throws BauDoZeException {
        return this.tarefaRepository.findById(id);
    }

    public List<Tarefa> list() throws BauDoZeException {
        List<Tarefa> result = new ArrayList<>();
        this.tarefaRepository.findAll().forEach(result::add);
        return result;
    }

    public void add(Tarefa tarefa) throws BauDoZeException {
        this.tarefaRepository.save(tarefa);
    }

    public void update(Tarefa tarefa) throws BauDoZeException {
        Optional<Tarefa> tarefaBD = this.tarefaRepository.findById(tarefa.getId());
        if (tarefaBD.isPresent()) {
            this.tarefaRepository.save(tarefa);
        } else {
            throw new BauDoZeException("Tarefa não encontrada!");
        };
    }

    public void delete(Long id) throws BauDoZeException {
        Optional<Tarefa> tarefaBD = this.tarefaRepository.findById(id);
        if (tarefaBD.isPresent()) {
            this.tarefaRepository.delete(tarefaBD.get());
        } else {
            throw new BauDoZeException("Tarefa não encontrada!");
        };
    }
}
