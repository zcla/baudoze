package zcla71.baudoze.biblia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import zcla71.baudoze.biblia.model.Biblia;
import zcla71.baudoze.biblia.model.Livro;
import zcla71.baudoze.biblia.repository.BibliaRepository;
import zcla71.baudoze.biblia.repository.LivroRepository;

@Service
public class BibliaService {
	// Biblia

	@Autowired
	private BibliaRepository bibliaRepository;
	
	public void incluir(@NonNull Biblia biblia) {
		this.bibliaRepository.save(biblia);
	}

	public Biblia buscaBibliaPorCodigo(String codigo) {
		return this.bibliaRepository.findByCodigo(codigo);
	}

	public Biblia buscaBibliaPorId(@NonNull Long id) {
		return this.bibliaRepository.findById(id).orElse(null);
	}

	// Livro

	@Autowired
	private LivroRepository livroRepository;

	public Livro buscaLivroPorId(@NonNull Long id) {
		return this.livroRepository.findById(id).orElse(null);
	}
}
