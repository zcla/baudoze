package zcla71.baudoze.tarefa.model.service;

import java.util.Objects;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.tarefa.model.entity.Tarefa;
import zcla71.baudoze.tarefa.model.repository.TarefaRepository;
import zcla71.baudoze.tarefa.view.entity.TarefaLista;
import zcla71.baudoze.tarefa.view.service.TarefaViewService;

@RequiredArgsConstructor
@Service
@Validated
public class TarefaService {
	final private TarefaViewService tarefaViewService;

	private static boolean alterouMae(Tarefa antes, Tarefa depois) {
		if (antes.getTarefaMae() == null) {
			return depois.getTarefaMae() != null;
		} else {
			if (depois.getTarefaMae() == null) {
				return true;
			} else {
				return !antes.getTarefaMae().getId().equals(depois.getTarefaMae().getId());
			}
		}
	}

	final private TarefaRepository tarefaRepository;

	public Tarefa buscar(AuthUser authUser, @NonNull Long id) {
		return tarefaRepository.findByAuthUserAndId(authUser, id).orElseThrow(() -> new TarefaServiceException("Tarefa não encontrada."));
	}

	public Tarefa novaTarefa(AuthUser authUser) {
		Tarefa result = new Tarefa();
		result.setAuthUser(authUser);
		result.setTitulo("Nova tarefa");
		result.setCumprida(false);
		return result;
	}

	@Transactional
	public Tarefa salvar(@Valid @NonNull Tarefa tarefa) {
		// TODO Testar tentativa de alteração de tarefa de outro usuário
		if (tarefa.getId() == null) {
			// É inclusão
			tarefa.setOrdem(tarefaRepository.proximaOrdem(tarefa.getAuthUser()));
			tarefa.setCumprida(false);
			return tarefaRepository.save(tarefa);
		}

		// É alteração
		Tarefa existente = buscar(tarefa.getAuthUser(), Objects.requireNonNull(tarefa.getId()));

		// Validação: a tarefa mãe não pode ser nem ela mesma nem nenhuma de suas filhas
		if (tarefa.getTarefaMae() != null) {
			TarefaLista tarefaLista = tarefaViewService.listaTarefasMaePossiveis(existente).stream()
					.filter(t -> t.getId().equals(tarefa.getTarefaMae().getId()))
					.findAny()
					.orElse(null);
			if (tarefaLista.getDisabled()) {
				if (tarefaLista.getId().equals(existente.getId())) {
					throw new TarefaServiceException("A tarefa mãe não pode ser ela mesma.", "tarefaMae");
				} else {
					throw new TarefaServiceException("A tarefa mãe não pode ser nenhuma de suas filhas.", "tarefaMae");
				}
			}
		}

		// Faz a alteração
		existente.setTitulo(tarefa.getTitulo());
		existente.setDescricao(tarefa.getDescricao());
		if (alterouMae(existente, tarefa)) {
			// Sempre que mudar a mãe, fica como última filha
			existente.setOrdem(tarefaRepository.proximaOrdem(existente.getAuthUser()));
			existente.setTarefaMae(tarefa.getTarefaMae());
		}
		return tarefaRepository.save(existente);
	}

	@Transactional
	public void excluir(@NonNull Tarefa tarefa) {
		// TODO Impedir exclusão da tarefa de outro usuário
		try {
			tarefaRepository.delete(tarefa);
			tarefaRepository.flush();
		} catch (DataIntegrityViolationException ex) {
			// Erro de FK
			throw new TarefaServiceException("Não é possível excluir uma tarefa que tem filhos.");
		}
	}

	// // TODO Criar um método marcarDesmarcar() para ser usado por marcar() e desmarcar()
	// @Transactional
	// public Tarefa marcar(@NonNull Long id, AuthUser authUser) {
	// 	// TODO Esse trecho é muito repetido; juntar.
	// 	Tarefa tarefa = buscar(id);
	// 	if (tarefa == null) {
	// 		throw new TarefaServiceException("Tarefa não encontrada.");
	// 	}
	// 	if (!tarefa.getAuthUser().getId().equals(authUser.getId())) {
	// 		throw new TarefaServiceException("Tentativa de marcar tarefa de outro usuário.");
	// 	}
	// 	// TODO Até aqui

	// 	if (tarefa.getCumprida()) {
	// 		throw new TarefaServiceException("Tarefa já está cumprida.");
	// 	}
	// 	tarefa.setCumprida(true);
	// 	return tarefaRepository.save(tarefa);
	// }

	// @Transactional
	// public Tarefa desmarcar(@NonNull Long id, AuthUser authUser) {
	// 	// TODO Esse trecho é muito repetido; juntar.
	// 	Tarefa tarefa = buscar(id);
	// 	if (tarefa == null) {
	// 		throw new TarefaServiceException("Tarefa não encontrada.");
	// 	}
	// 	if (!tarefa.getAuthUser().getId().equals(authUser.getId())) {
	// 		throw new TarefaServiceException("Tentativa de marcar tarefa de outro usuário.");
	// 	}
	// 	// TODO Até aqui

	// 	if (!tarefa.getCumprida()) {
	// 		throw new TarefaServiceException("Tarefa já está descumprida.");
	// 	}
	// 	tarefa.setCumprida(false);
	// 	return tarefaRepository.save(tarefa);
	// }
}
