package zcla71.baudoze.biblia.view.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zcla71.baudoze.biblia.view.entity.BibliaLista;
import zcla71.baudoze.biblia.view.entity.CapituloLista;
import zcla71.baudoze.biblia.view.entity.LivroLista;
import zcla71.baudoze.biblia.view.entity.VersiculoLista;
import zcla71.baudoze.biblia.view.repository.BibliaListaRepository;
import zcla71.baudoze.biblia.view.repository.CapituloListaRepository;
import zcla71.baudoze.biblia.view.repository.LivroListaRepository;
import zcla71.baudoze.biblia.view.repository.VersiculoListaRepository;

@Service
public class BibliaViewService {
	// BibliaLista

	@Autowired
	private BibliaListaRepository bibliaListaRepository;

	public List<BibliaLista> listarBiblias() {
		return this.bibliaListaRepository.findAll();
	}

	public BibliaLista buscarBiblia(Long idBiblia) {
		return listarBiblias().stream()
				.filter(b -> b.getId().equals(idBiblia))
				.findFirst()
				.orElse(null);
	}

	// LivroLista

	@Autowired
	private LivroListaRepository livroListaRepository;

	public List<LivroLista> listarLivros(Long idBiblia) {
		return this.livroListaRepository.findByBibliaId(idBiblia);
	}

	public LivroLista buscarLivro(Long idBiblia, Long idLivro) {
		return listarLivros(idBiblia).stream()
				.filter(l -> l.getId().equals(idLivro))
				.findFirst()
				.orElse(null);
	}

	// CapituloLista

	@Autowired
	private CapituloListaRepository capituloListaRepository;

	public List<CapituloLista> listarCapitulos(Long idLivro) {
		return this.capituloListaRepository.findByLivroId(idLivro);
	}

	public CapituloLista buscarCapitulo(Long idLivro, Long idCapitulo) {
		return listarCapitulos(idLivro).stream()
				.filter(c -> c.getId().equals(idCapitulo))
				.findFirst()
				.orElse(null);
	}

	// VersiculoLista

	@Autowired
	private VersiculoListaRepository versiculoListaRepository;

	public List<VersiculoLista> listarVersiculos(Long idCapitulo) {
		return this.versiculoListaRepository.findByCapituloId(idCapitulo);
	}
}
