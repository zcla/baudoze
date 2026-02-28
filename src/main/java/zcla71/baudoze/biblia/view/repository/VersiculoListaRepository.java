package zcla71.baudoze.biblia.view.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import zcla71.baudoze.biblia.view.entity.VersiculoLista;

public interface VersiculoListaRepository extends Repository<VersiculoLista, Long> {
	public List<VersiculoLista> findByCapituloId(Long idCapitulo);
}
