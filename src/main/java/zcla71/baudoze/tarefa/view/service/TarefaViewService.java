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
			// Não pode ser ela mesma
			result = result.stream().filter(t -> !t.getId().equals(tarefa.getId())).toList();
			// TODO Não pode ser uma de suas filhas
		}
		return result;
	}

}
