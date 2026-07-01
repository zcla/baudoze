package zcla71.baudoze.tarefa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zcla71.baudoze.tarefa.dto.TarefaLista;
import zcla71.baudoze.tarefa.entity.Tarefa;
import zcla71.baudoze.tarefa.repository.TarefaListaRepository;

@RequiredArgsConstructor
@Service
public class TarefaListaService {
	// TarefaLista

	final private TarefaListaRepository tarefaListaRepository;

	public List<TarefaLista> listaTarefas() {
		return this.tarefaListaRepository.findAll();
	}

	public List<TarefaLista> listaTarefasMaePossiveis(Tarefa tarefa) {
		List<TarefaLista> result = listaTarefas();

		// Não pode ser nem ela mesma nem nenhuma de suas filhas
		if (tarefa.getId() != null) { // Se for inclusão não precisa desse check
			Long indent = null; // Se null, pode ser mãe; usada também para desabilitar todas as filhas
			for (TarefaLista tarefaLista : result) {
				if (tarefaLista.getId().equals(tarefa.getId())) { // Não pode ser mãe dela mesma
					indent = tarefaLista.getIndent();
				} else {
					if (indent != null) { // É a mãe ou filha da mãe :)
						if (tarefaLista.getIndent() <= indent) { // Se saiu da árvore da mãe, volta a poder ser mãe
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
