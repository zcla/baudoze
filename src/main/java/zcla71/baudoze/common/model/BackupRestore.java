package zcla71.baudoze.common.model;

import java.util.Collection;

import lombok.Data;
import zcla71.baudoze.tarefa.service.TarefaEntity;
import zcla71.baudoze.tarefa.service.TarefaService;

@Data
public class BackupRestore {
	private Collection<TarefaEntity> tarefas;

	public BackupRestore(TarefaService tarefaService) {
		this.tarefas = tarefaService.listar();
	}
}
