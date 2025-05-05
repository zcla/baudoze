package zcla71.baudoze.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import zcla71.baudoze.model.entity.Tarefa;
import zcla71.baudoze.model.service.TarefaService;
import zcla71.baudoze.view.model.TarefaViewModelLista;
import zcla71.baudoze.view.model.TarefaViewModelListaTarefa;

public class TarefaController {
    // Singleton

    private static TarefaController instance;

    public static TarefaController getInstance(TarefaService tarefaService) {
        if (instance == null) {
            TarefaController.instance = new TarefaController(tarefaService);
        }
        return TarefaController.instance;
    }

    private TarefaService tarefaService;

    private TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    // Funções utilitárias

    private List<Tarefa> sort(List<Tarefa> tarefas) {
        return this.sort(tarefas, null);
    }

    private List<Tarefa> sort(List<Tarefa> tarefas, Long idMae) {
        List<Tarefa> result = new ArrayList<>();
        List<Tarefa> filhas = tarefas.stream().filter(t -> {
            if (idMae == null) {
                return t.getIdMae() == null;
            } else {
                return idMae.equals(t.getIdMae());
            }
        }).collect(Collectors.toList());
        filhas.sort(new Comparator<Tarefa>() {
            @Override
            public int compare(Tarefa t1, Tarefa t2) {
                if (trataNull(t1.getCumprida()) && !trataNull(t2.getCumprida())) {
                    return 1;
                }
                if (!trataNull(t1.getCumprida()) && trataNull(t2.getCumprida())) {
                    return -1;
                }
                if (trataNull(t1.getPeso()) < trataNull(t2.getPeso())) {
                    return 1;
                }
                if (trataNull(t1.getPeso()) > trataNull(t2.getPeso())) {
                    return -1;
                }
                return t1.getNome().compareToIgnoreCase(t2.getNome());
            }
        });
        for (Tarefa filha : filhas) {
            result.add(filha);
            result.addAll(sort(tarefas, filha.getId()));
        }
        return result;
    }

    private Boolean trataNull(Boolean b) {
        return b == null ? Boolean.FALSE : b;
    }

    private Integer trataNull(Integer i) {
        return i == null ? 0 : i;
    }

    // Negócio

    public TarefaViewModelLista listar() {
        List<Tarefa> tarefas = this.sort(tarefaService.list());
        TarefaViewModelLista result = new TarefaViewModelLista();
        result.setTarefas(new ArrayList<>());
        for (Tarefa tarefa : tarefas) {
            Integer indent = 0;
            Tarefa temp = tarefa;
            while (temp.getIdMae() != null) {
                final Tarefa finalTemp = temp;
                temp = tarefas.stream().filter(t -> finalTemp.getIdMae().equals(t.getId())).findFirst().get();
                indent++;
            }
            result.getTarefas().add(new TarefaViewModelListaTarefa(tarefa, indent));
        }
        return result;
    }
}
