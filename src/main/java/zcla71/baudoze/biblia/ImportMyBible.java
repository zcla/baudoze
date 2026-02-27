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
import zcla71.baudoze.biblia.model.entity.Biblia;
import zcla71.baudoze.biblia.model.entity.Capitulo;
import zcla71.baudoze.biblia.model.entity.Livro;
import zcla71.baudoze.biblia.model.entity.Versiculo;
import zcla71.baudoze.biblia.model.service.BibliaService;
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
		// believers-sword https://github.com/Bible-Projects/believers-sword-next/tree/main/Modules/Bible
		sqLiteImporta(
			"https://github.com/Bible-Projects/believers-sword-next/raw/refs/heads/main/Modules/Bible/B%C3%ADblia%20Ave-Maria%201959.SQLite3",
			"github/believers-sword/BAM1959",
			"Bíblia Ave-Maria 1959",
			"pt-br"
		);
		sqLiteImporta(
			"https://github.com/Bible-Projects/believers-sword-next/raw/refs/heads/main/Modules/Bible/B%C3%ADblia%20Padre%20Matos%20Soares%201950.SQLite3",
			"github/believers-sword/BPMS1950",
			"Bíblia Padre Matos Soares 1950",
			"pt-br");

		// ph4 https://www.ph4.org/b4_index.php
		ph4Importa(
			"https://www.ph4.org/_dl.php?back=bbl&a=BAM&b=mybible&c",
			"ph4.org/BAM",
			"Bíblia Ave Maria",
			"pt-br");
		ph4Importa(
			"https://www.ph4.org/_dl.php?back=bbl&a=BEP&b=mybible&c",
			"ph4.org/BEP",
			"Bíblia Sagrada Edição Pastoral 1990",
			"pt-br");
		ph4Importa(
			"https://www.ph4.org/_dl.php?back=bbl&a=BJRD&b=mybible&c",
			"ph4.org/BJRD",
			"Bíblia de Jerusalém 2002",
			"pt-br");
		ph4Importa(
			"https://www.ph4.org/_dl.php?back=bbl&a=BPT'09D&b=mybible&c",
			"ph4.org/BPT'09D",
			"A Bíblia para todos Edição Católica",
			"pt-pt");
		ph4Importa(
			"https://www.ph4.org/_dl.php?back=bbl&a=CNBB&b=mybible&c",
			"ph4.org/CNBB",
			"Bíblia CNBB 2002",
			"pt-br");
		ph4Importa(
			"https://www.ph4.org/_dl.php?back=bbl&a=DBFC&b=mybible&c",
			"ph4.org/DBFC",
			"Bíblia Difusora Bíblica 1955 (Franciscanos Capuchinhos)",
			"pt-pt");
		ph4Importa(
			"https://www.ph4.org/_dl.php?back=bbl&a=DIF&b=mybible&c",
			"ph4.org/DIF",
			"Bíblia Difusora Bíblica (Franciscanos Capuchinhos)",
			"pt-pt");
		ph4Importa(
			"https://www.ph4.org/_dl.php?back=bbl&a=EUNSA&b=mybible&c",
			"ph4.org/EUNSA",
			"Bíblia de Navarra",
			"es-es");
		ph4Importa(
			"https://www.ph4.org/_dl.php?back=bbl&a=RSV-CE&b=mybible&c",
			"ph4.org/RSV-CE",
			"Revised Standard Version, Second Catholic Edition",
			"en-us");
	}

	private void ph4Importa(String strUri, String codigo, String nome, String idioma) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, IOException, SQLException, URISyntaxException {
		log.info("ph4Importa(\"" + codigo + "\")");
		if (this.bibliaService.buscaBibliaPorCodigo(codigo) == null) {
			MyBible myBible = MyBibleUtils.loadFromZipFile(new URI(strUri));
			Biblia biblia = fromMyBible(strUri, codigo, nome, idioma, myBible);
			if (biblia != null) {
				this.bibliaService.incluir(biblia);
			}
		}
	}

	private void sqLiteImporta(String strUri, String codigo, String nome, String idioma) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, IOException, SQLException, URISyntaxException {
		log.info("sqLiteImporta(\"" + codigo + "\")");
		if (this.bibliaService.buscaBibliaPorCodigo(codigo) == null) {
			MyBible myBible = MyBibleUtils.loadFromSqliteURI(new URI(strUri));
			Biblia biblia = fromMyBible(strUri, codigo, nome, idioma, myBible);
			if (biblia != null) {
				this.bibliaService.incluir(biblia);
			}
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
		for (Verses verses : myBible.getBible().getVerses()) {
			String livroSigla = myBible.getBible().getBooksAll().stream().filter(b -> b.getBook_number().equals(verses.getBook_number())).findFirst().get().getShort_name();
			Livro livro = result.getLivros().stream().filter(l -> l.getSigla().equals(livroSigla)).findFirst().get();
			String capituloNumero = verses.getChapter().toString();
			Capitulo capitulo = livro.getCapitulos().stream().filter(c -> c.getNumero().equals(capituloNumero)).findFirst().orElse(null);
			if (capitulo == null) {
				capitulo = new Capitulo();
				livro.getCapitulos().add(capitulo);
				capitulo.setLivro(livro);
				capitulo.setNumero(capituloNumero);
				capitulo.setVersiculos(new ArrayList<>());
			}
			// Versiculo
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
