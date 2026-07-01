package zcla71.baudoze.dados.dto;

import java.util.ArrayList;
import java.util.List;

import zcla71.baudoze.biblia.entity.Biblia;
import zcla71.baudoze.biblia.entity.Capitulo;
import zcla71.baudoze.biblia.entity.Livro;
import zcla71.baudoze.biblia.entity.Versiculo;
import zcla71.baudoze.biblia.repository.BibliaRepository;

public class Download {
	public List<DownloadBiblia> biblia;

	public Download(BibliaRepository bibliaRepository) {
		Iterable<Biblia> biblias = bibliaRepository.findAll();
		this.biblia = new ArrayList<>();
		for (Biblia biblia : biblias) {
			this.biblia.add(new DownloadBiblia(biblia));
		}
	}

	public class DownloadBiblia {
		public String codigo;
		public String nome;
		public String idioma;
		public String fonte;
		public List<DownloadLivro> livros;

		public DownloadBiblia(Biblia biblia) {
			this.codigo = biblia.getCodigo();
			this.nome = biblia.getNome();
			this.idioma = biblia.getIdioma();
			this.fonte = biblia.getFonte();
			this.livros = new ArrayList<>();
			for (Livro livro : biblia.getLivros()) {
				this.livros.add(new DownloadLivro(livro));
			}
		}
	}

	public class DownloadLivro {
		public String sigla;
		public String nome;
		public List<DownloadCapitulo> capitulos;

		public DownloadLivro(Livro livro) {
			this.sigla = livro.getSigla();
			this.nome = livro.getNome();
			this.capitulos = new ArrayList<>();
			for (Capitulo capitulo : livro.getCapitulos()) {
				this.capitulos.add(new DownloadCapitulo(capitulo));
			}
		}
	}

	public class DownloadCapitulo {
		public String numero;
		public List<DownloadVersiculo> versiculos;

		public DownloadCapitulo(Capitulo capitulo) {
			this.numero = capitulo.getNumero();
			this.versiculos = new ArrayList<>();
			for (Versiculo versiculo : capitulo.getVersiculos()) {
				this.versiculos.add(new DownloadVersiculo(versiculo));
			}
		}
	}

	public class DownloadVersiculo {
		public String numero;
		public String texto;

		public DownloadVersiculo(Versiculo versiculo) {
			this.numero = versiculo.getNumero();
			this.texto = versiculo.getTexto();
		}
	}
}
