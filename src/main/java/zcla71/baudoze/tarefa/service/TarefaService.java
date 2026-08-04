package zcla71.baudoze.tarefa.service;

import java.util.List;
import java.util.Objects;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.sqlite.SQLiteErrorCode;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import zcla71.baudoze.common.service.BauServiceTipoException;
import zcla71.baudoze.tarefa.dto.TarefaLista;
import zcla71.baudoze.tarefa.entity.Tarefa;
import zcla71.baudoze.tarefa.repository.TarefaRepository;

@RequiredArgsConstructor
@Service
@Validated
public class TarefaService {
	final private TarefaListaService tarefaViewService;

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

	public Tarefa buscar(@NonNull Long id) {
		return tarefaRepository.findById(id).orElseThrow(() -> new TarefaServiceException(BauServiceTipoException.NAO_ENCONTRADO, "Tarefa não encontrada."));
	}

	public Tarefa novaTarefa() {
		Tarefa result = new Tarefa();
		result.setTitulo("Nova tarefa");
		result.setCumprida(false);
		return result;
	}

	@Transactional
	public Tarefa salvar(@Valid @NonNull Tarefa tarefa) {
		if (tarefa.getId() == null) {
			// É inclusão
			tarefa.setOrdem(tarefaRepository.proximaOrdem());
			tarefa.setCumprida(false);
			return tarefaRepository.save(tarefa);
		}
		
		// É alteração
		Tarefa existente = buscar(Objects.requireNonNull(tarefa.getId()));

		// Validação: a tarefa mãe não pode ser nem ela mesma nem nenhuma de suas filhas
		if (tarefa.getTarefaMae() != null) {
			TarefaLista tarefaLista = tarefaViewService.listaTarefasMaePossiveis(existente).stream()
					.filter(t -> t.getId().equals(tarefa.getTarefaMae().getId()))
					.findAny()
					.orElse(null);
			if (tarefaLista.getDisabled()) {
				if (tarefaLista.getId().equals(existente.getId())) {
					throw new TarefaServiceException(BauServiceTipoException.REGRA_DE_NEGOCIO, "A tarefa mãe não pode ser ela mesma.", "tarefaMae");
				} else {
					throw new TarefaServiceException(BauServiceTipoException.REGRA_DE_NEGOCIO, "A tarefa mãe não pode ser nenhuma de suas filhas.", "tarefaMae");
				}
			}
		}

		// Faz a alteração
		existente.setTitulo(tarefa.getTitulo());
		existente.setDescricao(tarefa.getDescricao());
		if (alterouMae(existente, tarefa)) {
			// Sempre que mudar a mãe, fica como última filha
			existente.setOrdem(tarefaRepository.proximaOrdem());
			existente.setTarefaMae(tarefa.getTarefaMae());
		}
		return tarefaRepository.save(existente);
	}

	@Transactional
	public void excluir(@NonNull Tarefa tarefa) {
		try {
			Tarefa existente = Objects.requireNonNull(buscar(Objects.requireNonNull(tarefa.getId())));
			tarefaRepository.delete(existente);
			tarefaRepository.flush(); // Sem o flush o delete só acontece depois, e nunca entra no catch abaixo
		} catch (JpaSystemException ex) {
			Throwable root = NestedExceptionUtils.getMostSpecificCause(ex);
			if (root instanceof org.sqlite.SQLiteException sqlEx) {
				if (sqlEx.getResultCode() == SQLiteErrorCode.SQLITE_CONSTRAINT_FOREIGNKEY) {
					// Erro de FK
					throw new TarefaServiceException(BauServiceTipoException.REGRA_DE_NEGOCIO, "Não é possível excluir uma tarefa que tem filhos.");
				}
			}
			throw ex;
		}
	}

	@Transactional
	public Tarefa marcar(@NonNull Tarefa tarefa) {
		Tarefa existente = buscar(Objects.requireNonNull(tarefa.getId()));

		if (existente.getCumprida()) {
			throw new TarefaServiceException(BauServiceTipoException.CONFLITO, "Tarefa já está cumprida.");
		}
		existente.setCumprida(true);
		return tarefaRepository.save(existente);
	}

	@Transactional
	public Tarefa desmarcar(@NonNull Tarefa tarefa) {
		Tarefa existente = buscar(Objects.requireNonNull(tarefa.getId()));

		if (!existente.getCumprida()) {
			throw new TarefaServiceException(BauServiceTipoException.CONFLITO, "Tarefa já está descumprida.");
		}
		existente.setCumprida(false);
		return tarefaRepository.save(existente);
	}

	@Transactional
	public void desmarcarTodas() {
		List<Tarefa> tarefas = tarefaRepository.findAll();
		for (Tarefa tarefa : tarefas) {
			tarefa.setCumprida(false);
			tarefaRepository.save(tarefa);
		}
	}

	@Transactional
	public void moverFinal(@NonNull Tarefa tarefa) {
		tarefa.setOrdem(tarefaRepository.proximaOrdem());
		tarefaRepository.save(tarefa);
		reordenaFilhas(tarefa.getTarefaMae());
	}

	@Transactional
	public void moverInicio(@NonNull Tarefa tarefa) {
		tarefa.setOrdem(0L);
		tarefaRepository.save(tarefa);
		reordenaFilhas(tarefa.getTarefaMae());
	}

	@Transactional
	private void reordenaFilhas(Tarefa tarefaMae) {
		List<Tarefa> filhasDaMae = tarefaRepository.findByTarefaMae(tarefaMae);
		filhasDaMae.sort((t1, t2) -> t1.getOrdem().compareTo(t2.getOrdem()));
		long ordem = 0;
		for (Tarefa filha : filhasDaMae) {
			filha.setOrdem(++ordem);
			tarefaRepository.save(filha);
		}
	}
}
