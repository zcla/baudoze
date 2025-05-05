package zcla71.baudoze.api.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import zcla71.baudoze.BauDoZeException;
import zcla71.baudoze.model.entity.Tarefa;
import zcla71.baudoze.model.service.TarefaService;
import zcla71.baudoze.model.service.TarefaServiceRepository;

@RestController
public class TarefaApiController {
    @Autowired
    private TarefaServiceRepository tarefaService;
 
    @GetMapping("/api/v1/tarefa")
    public ResponseEntity<List<Tarefa>> listar() {
        List<Tarefa> tarefas = this.tarefaService.list();
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

    // @GetMapping("/api/v1/tarefa/{id}")
    // public ResponseEntity<Tarefa> buscar(@PathVariable Long id) {
    //     try {
    //         Optional<Tarefa> tarefa = this.tarefaService.getById(id);
    //         if (tarefa.isPresent()) {
    //             return new ResponseEntity<>(tarefa.get(), HttpStatus.OK);
    //         } else {
    //             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada!");
    //         }
    //     } catch (BauDoZeException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    // @PostMapping("/api/v1/tarefa")
    // public void incluir(@RequestBody Tarefa tarefa) {
    //     try {
    //         this.tarefaService.add(tarefa);
    //     } catch (BauDoZeException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    // @PutMapping("/api/v1/tarefa")
    // public void alterar(@RequestBody Tarefa tarefa) {
    //     try {
    //         Optional<Tarefa> tarefaAntes = this.tarefaService.getById(tarefa.getId());
    //         if (tarefaAntes.isPresent()) {
    //             this.tarefaService.update(tarefa);
    //         } else {
    //             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada!");
    //         }
    //     } catch (BauDoZeException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    // @DeleteMapping("/api/v1/tarefa/{id}")
    // public void excluir(@PathVariable Long id) {
    //     try {
    //         Optional<Tarefa> tarefa = this.tarefaService.getById(id);
    //         if (tarefa.isPresent()) {
    //             this.tarefaService.delete(id);
    //         } else {
    //             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada!");
    //         }
    //     } catch (BauDoZeException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleNotFoundException(ResponseStatusException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getReason());
        body.put("status", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
