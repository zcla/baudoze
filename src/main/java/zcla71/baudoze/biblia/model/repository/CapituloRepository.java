package zcla71.baudoze.biblia.model.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import zcla71.baudoze.biblia.model.entity.Capitulo;

public interface CapituloRepository extends CrudRepository<Capitulo, Long> {
	public @NonNull Optional<Capitulo> findById(@NonNull Long id);
}
