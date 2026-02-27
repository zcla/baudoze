package zcla71.baudoze.biblia.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import zcla71.baudoze.biblia.view.CapituloLista;

public interface CapituloListaRepository extends Repository<CapituloLista, Long> {
	public List<CapituloLista> findByLivroId(Long id);
}
