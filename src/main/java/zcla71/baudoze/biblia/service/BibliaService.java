package zcla71.baudoze.biblia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import zcla71.baudoze.biblia.model.Biblia;
import zcla71.baudoze.biblia.repository.BibliaListaRepository;
import zcla71.baudoze.biblia.repository.BibliaRepository;
import zcla71.baudoze.biblia.repository.LivroListaRepository;
import zcla71.baudoze.biblia.view.BibliaLista;
import zcla71.baudoze.biblia.view.LivroLista;

@Service
public class BibliaService {
	// Biblia

	@Autowired
	private BibliaRepository bibliaRepository;
	
	public void incluir(@NonNull Biblia biblia) {
		this.bibliaRepository.save(biblia);
	}

	public Biblia buscaPorCodigo(String codigo) {
		return this.bibliaRepository.findByCodigo(codigo);
	}

	// BibliaLista

	@Autowired
	private BibliaListaRepository bibliaListaRepository;

	public List<BibliaLista> listarBiblias() {
		return this.bibliaListaRepository.findAll();
	}

	// LivroLista

	@Autowired
	private LivroListaRepository livroListaRepository;

	public List<LivroLista> listarLivros(Long id) {
		return this.livroListaRepository.findByBibliaId(id);
	}
}
