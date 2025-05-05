package zcla71.baudoze.model.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;

import zcla71.baudoze.BauDoZeException;
import zcla71.baudoze.model.entity.Tarefa;
import zcla71.baudoze.model.repository.TarefaRepository;

@Service
@Primary // Para ter precedência sobre TarefaServiceApi, que é só um exemplo.
public class TarefaServiceRepository implements TarefaService {
    @Autowired
    private TarefaRepository tarefaRepository;

    // public Optional<Tarefa> getById(Long id) {
    //     return this.tarefaRepository.findById(id);
    // }

    public List<Tarefa> list() {
        List<Tarefa> result = new ArrayList<>();
        this.tarefaRepository.findAll().forEach(result::add);
        return result;
    }

    // public void add(Tarefa tarefa) {
    //     this.tarefaRepository.save(tarefa);
    // }

    // public void update(Tarefa tarefa) {
    //     Optional<Tarefa> tarefaBD = this.tarefaRepository.findById(tarefa.getId());
    //     if (tarefaBD.isPresent()) {
    //         this.tarefaRepository.save(tarefa);
    //     } else {
    //         throw new BauDoZeException("Tarefa não encontrada!");
    //     };
    // }

    // public void delete(Long id) {
    //     Optional<Tarefa> tarefaBD = this.tarefaRepository.findById(id);
    //     if (tarefaBD.isPresent()) {
    //         this.tarefaRepository.delete(tarefaBD.get());
    //     } else {
    //         throw new BauDoZeException("Tarefa não encontrada!");
    //     };
    // }
}
