package zcla71.baudoze.tarefa.model.repository;

import zcla71.baudoze.auth_user.model.entity.AuthUser;

public interface TarefaRepositoryCustom {
	public Long proximaOrdem(AuthUser authUser);
}
