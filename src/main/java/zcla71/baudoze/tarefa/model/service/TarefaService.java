package zcla71.baudoze.tarefa.model.service;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.tarefa.model.entity.Tarefa;
import zcla71.baudoze.tarefa.model.repository.TarefaRepository;

@RequiredArgsConstructor
@Service
public class TarefaService {
	// TODO Essa classe deveria ser independente de tecnologia, por tanto não deveria lançar ResponseStatusException; ver qual é a melhor prática.
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
	@PersistenceContext
	private EntityManager entityManager;

	public Tarefa buscar(@NonNull Long id) {
		return tarefaRepository.findById(id).orElse(null);
	}

	public Tarefa novaTarefa() {
		Tarefa result = new Tarefa();
		result.setTitulo("Nova tarefa");
		return result;
	}

	// TODO Parece que isso ficaria melhor no repository, não?
	@Transactional(propagation = Propagation.MANDATORY)
	public Long proximaOrdem(AuthUser authUser) {
		Long result = (Long) entityManager
				.createNativeQuery("""
						SELECT MAX(t.ordem)
						FROM tarefa t
						WHERE t.auth_user_id = :authUserId
						FOR UPDATE
						""")
				.setParameter("authUserId", authUser.getId())
				.getSingleResult();
		return (result == null ? 0 : result) + 1;
	}

	@Transactional
	public Tarefa salvar(@NonNull Tarefa tarefa, AuthUser authUser) {
		if (tarefa.getId() == null) { // Tarefa nova
			tarefa.setAuthUser(authUser);
			tarefa.setOrdem(proximaOrdem(authUser));
			tarefa.setCumprida(false);
			return tarefaRepository.save(tarefa);
		}
		// Alteração
		@SuppressWarnings("null") // Já foi tratado no if, acima
		Tarefa existente = tarefaRepository.findById(tarefa.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if (!existente.getAuthUser().getId().equals(authUser.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tentativa de alterar uma tarefa de outro usuário!");
		}
		existente.setTitulo(tarefa.getTitulo());
		existente.setDescricao(tarefa.getDescricao());
		if (alterouMae(existente, tarefa)) {
			// Sempre que mudar a mãe, fica como última filha
			existente.setOrdem(proximaOrdem(authUser));
			existente.setTarefaMae(tarefa.getTarefaMae());
		}
		return tarefaRepository.save(existente);
	}

	@Transactional
	public void excluir(@NonNull Long id, AuthUser authUser) {
		Tarefa tarefa = buscar(id);
		if (tarefa == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		if (!tarefa.getAuthUser().getId().equals(authUser.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tentativa de excluir tarefa de outro usuário!");
		}
		// Não precisa rearrumar a ordem
		tarefaRepository.delete(tarefa);
	}

	// TODO Criar um método marcarDesmarcar() para ser usado por marcar() e desmarcar()
	@Transactional
	public Tarefa marcar(@NonNull Long id, AuthUser authUser) {
		// TODO Esse trecho é muito repetido; juntar.
		Tarefa tarefa = buscar(id);
		if (tarefa == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		if (!tarefa.getAuthUser().getId().equals(authUser.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tentativa de marcar tarefa de outro usuário!");
		}
		// TODO Até aqui

		if (tarefa.getCumprida()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tarefa já está cumprida.");
		}
		tarefa.setCumprida(true);
		return tarefaRepository.save(tarefa);
	}

	@Transactional
	public Tarefa desmarcar(@NonNull Long id, AuthUser authUser) {
		// TODO Esse trecho é muito repetido; juntar.
		Tarefa tarefa = buscar(id);
		if (tarefa == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		if (!tarefa.getAuthUser().getId().equals(authUser.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tentativa de marcar tarefa de outro usuário!");
		}
		// TODO Até aqui

		if (!tarefa.getCumprida()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tarefa já está descumprida.");
		}
		tarefa.setCumprida(false);
		return tarefaRepository.save(tarefa);
	}
}
