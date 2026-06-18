package zcla71.baudoze.biblia.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import zcla71.baudoze.biblia.model.entity.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
	public @NonNull Optional<Livro> findById(@NonNull Long id);
}
