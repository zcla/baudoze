package zcla71.baudoze.client;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import zcla71.baudoze.entity.Tarefa;

// TODO Tratar erros (404 quando não há tarefa com a id informada, por exemplo)
@RestController
public class TarefaClient {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/client/tarefa")
    public ResponseEntity<List<Tarefa>> listar() {
        String url = "http://localhost:8080/api/v1/tarefa";
        ResponseEntity<List<Tarefa>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Tarefa>>() {}
        );
        List<Tarefa> tarefas = response.getBody();
        return new ResponseEntity<List<Tarefa>>(tarefas, HttpStatus.OK);
    }

    @GetMapping("/client/tarefa/{id}")
    public ResponseEntity<Tarefa> buscar(@PathVariable Long id) {
        String url = "http://localhost:8080/api/v1/tarefa/{id}";
        ResponseEntity<Tarefa> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Tarefa>() {},
                id
        );
        Tarefa tarefa = response.getBody();
        return new ResponseEntity<Tarefa>(tarefa, HttpStatus.OK);
    }

    @PostMapping("/client/tarefa")
    public void incluir(@RequestBody Tarefa tarefa) {
        String url = "http://localhost:8080/api/v1/tarefa";
        restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(tarefa),
                new ParameterizedTypeReference<Tarefa>() {}
        );
    }

    @PutMapping("/client/tarefa")
    public void alterar(@RequestBody Tarefa tarefa) {
        String url = "http://localhost:8080/api/v1/tarefa";
        restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(tarefa),
                new ParameterizedTypeReference<Tarefa>() {}
        );
    }

    @DeleteMapping("/client/tarefa/{id}")
    public void excluir(@PathVariable Long id) {
        String url = "http://localhost:8080/api/v1/tarefa/{id}";
        restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<Tarefa>() {},
                id
        );
    }

    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<Object> handleNotFoundException(RestClientResponseException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getLocalizedMessage());
        body.put("statusCode", ex.getStatusCode());
        body.put("statusText", ex.getStatusText());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
