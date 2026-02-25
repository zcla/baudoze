package zcla71.baudoze.biblia.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import zcla71.baudoze.biblia.view.LivroLista;

public interface LivroListaRepository extends Repository<LivroLista, Long> {
	public List<LivroLista> findByBibliaId(Long id);
}
