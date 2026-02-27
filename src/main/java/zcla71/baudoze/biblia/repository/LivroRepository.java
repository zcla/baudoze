package zcla71.baudoze.biblia.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import zcla71.baudoze.biblia.model.Livro;

public interface LivroRepository extends CrudRepository<Livro, Long> {
	public @NonNull Optional<Livro> findById(@NonNull Long id);
}
