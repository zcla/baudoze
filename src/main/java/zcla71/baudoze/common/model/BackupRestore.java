package zcla71.baudoze.common.model;

import java.util.Collection;

import lombok.Data;
import zcla71.baudoze.tarefa.model.entity.Tarefa;
import zcla71.baudoze.tarefa.model.service.TarefaService;

@Data
public class BackupRestore {
	private Collection<Tarefa> tarefas;

	public BackupRestore(TarefaService tarefaService) {
		// this.tarefas = tarefaService.listar();
	}
}
