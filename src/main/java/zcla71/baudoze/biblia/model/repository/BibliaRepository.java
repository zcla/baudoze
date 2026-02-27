package zcla71.baudoze.biblia.model.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import zcla71.baudoze.biblia.model.entity.Biblia;

public interface BibliaRepository extends CrudRepository<Biblia, Long> {
	public Biblia findByCodigo(String codigo);
	public @NonNull Optional<Biblia> findById(@NonNull Long id);
}
