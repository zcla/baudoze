package zcla71.baudoze.biblia;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import zcla71.baudoze.biblia.model.Biblia;
import zcla71.baudoze.biblia.model.Capitulo;
import zcla71.baudoze.biblia.model.Livro;
import zcla71.baudoze.biblia.model.Versiculo;
import zcla71.mybible.MyBible;
import zcla71.mybible.MyBibleUtils;
import zcla71.mybible.bible.BooksAll;
import zcla71.mybible.bible.Verses;

@Component
@Slf4j
public class ImportMyBible {
	private BibliaService bibliaService;

	public ImportMyBible(BibliaService bibliaService) {
		this.bibliaService = bibliaService;
	}

	@Async
	@EventListener(ApplicationStartedEvent.class)
    public void init() {
		try {
			// TODO Criar um flag no BibliaService para não permitir uso enquanto estiver inicializando
			log.info("init start");
			importaTudo();
			log.info("init end");
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | NoSuchFieldException | IOException | SQLException
				| URISyntaxException e) {
			throw new RuntimeException("Erro ao importar dados do MyBible", e);
		}
	}

	public void importaTudo() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, IOException, SQLException, URISyntaxException {
		// ----- https://www.ph4.org/b4_1.php
		importa("https://www.ph4.org/_dl.php?back=bbl&a=BAM&b=mybible&c", "ph4.org/BAM", "Bíblia Ave Maria", "pt");
		importa("https://www.ph4.org/_dl.php?back=bbl&a=BEP&b=mybible&c", "ph4.org/BEP", "Bíblia Sagrada - Edição Pastoral", "pt");
		importa("https://www.ph4.org/_dl.php?back=bbl&a=BJRD&b=mybible&c", "ph4.org/BJRD", "Bíblia de Jerusalém", "pt");
		importa("https://www.ph4.org/_dl.php?back=bbl&a=BPT'09D&b=mybible&c", "ph4.org/BPT'09D", "a BÍBLIA para todos Edição Católica", "pt");
		importa("https://www.ph4.org/_dl.php?back=bbl&a=CNBB&b=mybible&c", "ph4.org/CNBB", "Bíblia CNBB (Nova Capa)", "pt");
		importa("https://www.ph4.org/_dl.php?back=bbl&a=DBFC&b=mybible&c", "ph4.org/DBFC", "Difusora Bíblica (Franciscanos Capuchinhos)", "pt");
		importa("https://www.ph4.org/_dl.php?back=bbl&a=DIF&b=mybible&c", "ph4.org/DIF", "Difusora Bíblica - Franciscanos Capuchinhos", "pt");
		importa("https://www.ph4.org/_dl.php?back=bbl&a=EUNSA&b=mybible&c", "ph4.org/EUNSA", "Sagrada Biblia (Universidad de Navarra)", "es");
		importa("https://www.ph4.org/_dl.php?back=bbl&a=RSV-CE&b=mybible&c", "ph4.org/RSV-CE", "Revised Standard Version, Second Catholic Edition", "en");
	}

	private void importa(String url, String codigo, String nome, String idioma) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, IOException, SQLException, URISyntaxException {
		log.info("importa(\"" + codigo + "\")");
		if (this.bibliaService.buscaPorCodigo(codigo) == null) {
			MyBible myBible = MyBibleUtils.loadFromZipFile(new URI(url));
			Biblia biblia = fromMyBible(url, codigo, nome, idioma, myBible);
			this.bibliaService.incluir(biblia);
		}
	}

	private Biblia fromMyBible(String url, String codigo, String nome, String idioma, MyBible myBible) {
		log.info("fromMyBible(\"" + codigo + "\")");
		// Biblia
		Biblia result = new Biblia();
		result.setCodigo(codigo);
		result.setNome(nome);
		result.setIdioma(idioma);
		result.setFonte(url);
		result.setLivros(new ArrayList<>());

		// Livro
		for (BooksAll booksAll : myBible.getBible().getBooksAll()) {
			if (booksAll.getIs_present()) {
				Livro livro = new Livro();
				result.getLivros().add(livro);
				livro.setBiblia(result);
				livro.setSigla(booksAll.getShort_name());
				livro.setNome(booksAll.getTitle());
				if (livro.getNome() == null) {
					livro.setNome(booksAll.getLong_name());
				}
				livro.setCapitulos(new ArrayList<>());
			}
		}

		// Capitulo
		String last = null;
		for (Verses verses : myBible.getBible().getVerses()) {
			String livroSigla = myBible.getBible().getBooksAll().stream().filter(b -> b.getBook_number().equals(verses.getBook_number())).findFirst().get().getShort_name();
			Livro livro = result.getLivros().stream().filter(l -> l.getSigla().equals(livroSigla)).findFirst().get();
			String capituloNumero = verses.getChapter().toString();
			String current = livroSigla + "|" + capituloNumero;
			if (!current.equals(last)) {
				Capitulo capitulo = new Capitulo();
				livro.getCapitulos().add(capitulo);
				capitulo.setLivro(livro);
				capitulo.setNumero(capituloNumero);
				capitulo.setVersiculos(new ArrayList<>());
				last = current;
			}
			// Versiculo
			Capitulo capitulo = livro.getCapitulos().stream().filter(c -> c.getNumero().equals(capituloNumero)).findFirst().get();
			Versiculo versiculo = new Versiculo();
			capitulo.getVersiculos().add(versiculo);
			versiculo.setCapitulo(capitulo);
			versiculo.setNumero(verses.getVerse().toString());
			versiculo.setTexto(verses.getText());
		}

		// Fim
		return result;
	}
}
