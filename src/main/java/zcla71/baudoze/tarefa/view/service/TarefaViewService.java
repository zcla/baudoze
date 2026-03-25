package zcla71.baudoze.tarefa.view.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.tarefa.model.entity.Tarefa;
import zcla71.baudoze.tarefa.view.entity.TarefaLista;
import zcla71.baudoze.tarefa.view.repository.TarefaListaRepository;

@Service
public class TarefaViewService {
	// TarefaLista

	@Autowired
	private TarefaListaRepository tarefaListaRepository;

	public List<TarefaLista> listaTarefas(Long authUserId) {
		return this.tarefaListaRepository.findByAuthUserId(authUserId);
	}

	public List<TarefaLista> listaTarefasMaePossiveis(AuthUser authUser, Tarefa tarefa) {
		List<TarefaLista> result = listaTarefas(authUser.getId());
		if (tarefa.getId() != null) {
			// Não pode ser nem ela mesma nem nenhuma de suas filhas
			Long indent = null;
			for (TarefaLista tarefaLista : result) {
				if (tarefaLista.getId().equals(tarefa.getId())) {
					indent = tarefaLista.getIndent();
				} else {
					if (indent != null) {
						if (tarefaLista.getIndent() <= indent) {
							indent = null;
						}
					}
				}
				tarefaLista.setDisabled(indent != null);
			}
		}
		return result;
	}
}
