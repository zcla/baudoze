package zcla71.baudoze.biblia.repository;

import org.springframework.data.repository.CrudRepository;

import zcla71.baudoze.biblia.model.Biblia;

public interface BibliaRepository extends CrudRepository<Biblia, Long> {
	public Biblia findByCodigo(String codigo);
}
