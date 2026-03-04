package zcla71.baudoze.tarefa.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.tarefa.model.entity.Tarefa;
import zcla71.baudoze.tarefa.model.repository.TarefaRepository;

@Service
public class TarefaService {
	@Autowired
	private TarefaRepository tarefaRepository;
	@PersistenceContext
	private EntityManager entityManager;

	public Tarefa buscar(@NonNull Long id) {
		return this.tarefaRepository.findById(id).orElse(null);
	}

	public Tarefa novaTarefa() {
		Tarefa result = new Tarefa();
		result.setTitulo("Nova tarefa");
		return result;
	}

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
			return this.tarefaRepository.save(tarefa);
		}
		Tarefa existente = tarefaRepository.findById(tarefa.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if (!existente.getAuthUser().equals(authUser)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tentativa de alterar uma tarefa de outro usuário!");
		}
		existente.setTitulo(tarefa.getTitulo());
		existente.setDescricao(tarefa.getDescricao());
		existente.setIdMae(tarefa.getIdMae());
		return this.tarefaRepository.save(existente);
	}

	// public void excluir(Tarefa tarefa) throws ValidacaoException {
	// 	ValidacaoException validation = tarefa.validaExcluir(this.tarefaRepository);
	// 	if (!validation.getValidacoes().isEmpty()) {
	// 		throw validation;
	// 	}

	// 	this.tarefaRepository.delete(tarefa);
	// }
}
