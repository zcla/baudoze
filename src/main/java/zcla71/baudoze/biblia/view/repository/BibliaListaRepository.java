package zcla71.baudoze.biblia.view.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import zcla71.baudoze.biblia.view.entity.BibliaLista;

public interface BibliaListaRepository extends Repository<BibliaLista, Long> {
	public List<BibliaLista> findAll();
}
