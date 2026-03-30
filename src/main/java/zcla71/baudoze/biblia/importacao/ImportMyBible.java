package zcla71.baudoze.biblia.importacao;

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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zcla71.baudoze.BauDoZeProperties;
import zcla71.baudoze.BauDoZeProperties.PropBiblia.PropImportacao.PropPh4Importa;
import zcla71.baudoze.BauDoZeProperties.PropBiblia.PropImportacao.PropSqliteImporta;
import zcla71.baudoze.biblia.model.entity.Biblia;
import zcla71.baudoze.biblia.model.entity.Capitulo;
import zcla71.baudoze.biblia.model.entity.Livro;
import zcla71.baudoze.biblia.model.entity.Versiculo;
import zcla71.baudoze.biblia.model.service.BibliaService;
import zcla71.mybible.MyBible;
import zcla71.mybible.MyBibleUtils;
import zcla71.mybible.bible.BooksAll;
import zcla71.mybible.bible.Verses;

@RequiredArgsConstructor
@Component
@Slf4j
public class ImportMyBible {
	final private BauDoZeProperties appProperties;
	final private BibliaService bibliaService;

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
		for (PropPh4Importa biblia : appProperties.getBiblia().getImportacao().getPh4Importa()) {
			ph4Importa(biblia.getUri(), biblia.getCodigo(), biblia.getNome(), biblia.getIdioma());
		}
		for (PropSqliteImporta biblia : appProperties.getBiblia().getImportacao().getSqliteImporta()) {
			sqLiteImporta(biblia.getUri(), biblia.getCodigo(), biblia.getNome(), biblia.getIdioma());
		}
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
