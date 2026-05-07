package zcla71.baudoze.tarefa.model.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import zcla71.baudoze.auth_user.model.entity.AuthUser;

@Repository
public class TarefaRepositoryImpl implements TarefaRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
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
}
