package zcla71.baudoze.biblia.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import zcla71.baudoze.biblia.model.entity.Capitulo;

public interface CapituloRepository extends JpaRepository<Capitulo, Long> {
	public @NonNull Optional<Capitulo> findById(@NonNull Long id);
}
