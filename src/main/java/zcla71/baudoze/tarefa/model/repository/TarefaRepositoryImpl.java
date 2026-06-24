package zcla71.baudoze.tarefa.model.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class TarefaRepositoryImpl implements TarefaRepositoryCustom {
	@PersistenceContext(unitName = "user")
    private EntityManager entityManager;

    @Override
	@Transactional
	public Long proximaOrdem() {
		Number result = (Number) entityManager
				.createNativeQuery("""
						SELECT COALESCE(MAX(ordem), 0) + 1
						FROM tarefa
						""")
				.getSingleResult();
		return result.longValue();
	}
}
