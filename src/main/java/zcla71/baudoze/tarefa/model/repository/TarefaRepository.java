package zcla71.baudoze.tarefa.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import zcla71.baudoze.auth_user.model.entity.AuthUser;
import zcla71.baudoze.tarefa.model.entity.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>, TarefaRepositoryCustom {
	public Optional<Tarefa> findByAuthUserAndId(AuthUser authUser, Long id);
}
