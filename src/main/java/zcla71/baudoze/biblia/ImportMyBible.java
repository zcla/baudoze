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
		// [pt-br] BAM 1959 - Bíblia Ave Maria (The words of Jesus are highlighted in red)
		importa("https://www.ph4.org/_dl.php?back=bbl&a=BAM&b=mybible&c", "ph4.org/BAM", "Bíblia Ave Maria", "pt");
		// [pt-br] BEP 1990 - Bíblia Sagrada — Edição Pastoral (The words of Jesus are highlighted in red)
		importa("https://www.ph4.org/_dl.php?back=bbl&a=BEP&b=mybible&c", "ph4.org/BEP", "Bíblia Sagrada - Edição Pastoral", "pt");
		// [pt-br] BJRD 2002 - Bíblia de Jerusalém
		// [pt-br] CNBB 2002 - Bíblia CNBB (Nova Capa) (The words of Jesus are highlighted in red)
		// [pt-br] DBFC 1955 - Difusora Bíblica (The words of Jesus are highlighted in red) (Franciscanos Capuchinhos)
		// [pt-br] DIF - Difusora Bíblica - Franciscanos Capuchinhos
		// [pt-pt] BPT 2009 - a BÍBLIA para todos Edição Católica (The words of Jesus are highlighted in red; Sociedade Bíblica de Portugal)
		// [es] EUNSA 1997 - Sagrada Biblia (Universidad de Navarra)
		// [en] RSV-2CE 2006 - Revised Standard Version, Second Catholic Edition (The words of Jesus are highlighted in red)
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
