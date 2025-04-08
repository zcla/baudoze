package zcla71.baudoze.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import zcla71.baudoze.BauDoZeException;
import zcla71.baudoze.entity.Tarefa;
import zcla71.baudoze.service.TarefaService;
import zcla71.baudoze.view.tarefa.ListaTarefa;

@Controller
 // TODO Tratar os BauDoZeException erro ao invés de lançar?
public class TarefaWebController {
    @Autowired
    private TarefaService tarefaService;

    @GetMapping("/tarefa")
    public String listar(Model model) throws BauDoZeException {
        List<Tarefa> mTarefas = this.tarefaService.list();
        List<ListaTarefa> vTarefas = new ArrayList<>();
        Integer indent = 0; // TODO Fazer direito https://github.com/zcla/todo/blob/main/js/todo.js
        for (Tarefa mTarefa : mTarefas) {
            vTarefas.add(new ListaTarefa(mTarefa, indent++));
        }
        model.addAttribute("tarefas", vTarefas);
        return "/tarefa/lista";
    }

    @GetMapping("/tarefa/incluir")
    public String incluir(Model model) throws BauDoZeException {
        Tarefa tarefa = new Tarefa();
        tarefa.setPeso(0);
        model.addAttribute("tarefa", tarefa);
        // TODO Adicionar lista de tarefas para idMae
        return "/tarefa/incluir";
    }

    @PostMapping("/tarefa/incluir_ok")
    public String incluirOk(@ModelAttribute Tarefa tarefa) throws BauDoZeException {
        // TODO Validar
        this.tarefaService.add(tarefa);
        return "redirect:/tarefa";
    }

    // @GetMapping("/tarefa/{id}")
    // public String mostrar(@PathVariable String id, Model model) throws BauDoZeException {
    //     Optional<Tarefa> tarefa = this.tarefaService.getById(Long.parseLong(id));
    //     if (tarefa.isPresent()) {
    //         // TODO Gerar o indent
    //         model.addAttribute("tarefa", tarefa.get());
    //         return "/tarefa/busca";
    //     } else {
    //         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada.");
    //     }
    // }

    @GetMapping("/tarefa/{id}/alterar")
    public String alterar(@PathVariable String id, Model model) throws BauDoZeException {
        Optional<Tarefa> tarefa = this.tarefaService.getById(Long.parseLong(id));
        if (tarefa.isPresent()) {
            model.addAttribute("tarefa", tarefa);
            return "/tarefa/alterar";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada.");
        }
    }

    // @PostMapping("/tarefa/{id}/alterar_ok")
    // public String alterar(@ModelAttribute Tarefa tarefa) throws BauDoZeException {
    //     // TODO Alterar
    //     return "redirect:/tarefa";
    // }

    @PostMapping("/tarefa/{id}/excluir")
    public String excluir(@PathVariable String id) throws BauDoZeException {
        Optional<Tarefa> tarefa = this.tarefaService.getById(Long.parseLong(id));
        if (tarefa.isPresent()) {
            this.tarefaService.delete(Long.parseLong(id));
            return "redirect:/tarefa";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada.");
        }
    }
}
