package zcla71.baudoze.biblia.importacao.html;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zcla71.baudoze.BauDoZeProperties.PropBiblia.PropImportacao.PropHtmlImporta;
import zcla71.baudoze.biblia.model.entity.Biblia;
import zcla71.baudoze.biblia.model.entity.Capitulo;
import zcla71.baudoze.biblia.model.entity.Livro;
import zcla71.baudoze.biblia.model.entity.Versiculo;
import zcla71.baudoze.biblia.model.service.BibliaService;

@RequiredArgsConstructor
@Component
@Slf4j
public class ImportHtmlParoquiadasgracasCom extends ImportHtml {
	final private BibliaService bibliaService;

	public void htmlImporta(PropHtmlImporta phi) throws IOException, InterruptedException {
		log.info("htmlImporta(\"" + phi.getCodigo() + "\")");
		if (this.bibliaService.buscaBibliaPorCodigo(phi.getCodigo()) == null) {
			Biblia biblia = fromHtml(phi);
			if (biblia != null) {
				this.bibliaService.incluir(biblia);
			}
		}
	}

	private Biblia fromHtml(PropHtmlImporta phi) throws IOException, InterruptedException {
		log.info("fromHtml(\"" + phi.getCodigo() + "\")");
		// Biblia
		Biblia result = newBiblia(phi);

		// Livro
		Document docBiblia = getDocument(phi.getUri());
		Elements linksLivros = docBiblia.select("div.biblebook a");
		for (Element linkLivro : linksLivros) {
			log.info("fromHtml(\"" + phi.getCodigo() + "\") => " + linkLivro.text());
			Livro livro = new Livro();
			result.getLivros().add(livro);
			livro.setBiblia(result);
			String nomeSigla = linkLivro.text();
			String[] spl = nomeSigla.split(" \\(|\\)");
			livro.setSigla(spl[1]);
			livro.setNome(spl[0]);
			livro.setCapitulos(new ArrayList<>());
			
			// Capitulo
			String livroUri = linkLivro.attr("href");
			Document docLivro = getDocument(livroUri);
			Elements linksCapitulos = docLivro.select("ul.chapterlist a");
			for (Element linkCapitulo : linksCapitulos) {
				log.info("fromHtml(\"" + phi.getCodigo() + "\") => " + linkLivro.text() + " => " + linkCapitulo.text());
				Capitulo capitulo = new Capitulo();
				livro.getCapitulos().add(capitulo);
				capitulo.setLivro(livro);
				capitulo.setNumero(linkCapitulo.text());
				capitulo.setVersiculos(new ArrayList<>());

				// Versiculo
				String capituloUri = linkCapitulo.attr("href");
				Document docCapitulo = getDocument(capituloUri);
				Element conteudo = docCapitulo.selectFirst("div.moduletable.span8");
				conteudo.select("header").remove();
				String numeroAtual = null;
				StringBuilder textoAtual = new StringBuilder();
				for (Node node : conteudo.childNodes()) {
					if (node instanceof Element element && "sup".equals(element.tagName())) {
						// É novo versículo
						if (numeroAtual != null) {
							capitulo.getVersiculos().add(getVersiculo(capitulo, numeroAtual, textoAtual.toString()));
						}
						numeroAtual = element.text().trim();
						textoAtual.setLength(0);
					} else {
						// É continuação do versículo anterior
						boolean append = true;
						// Retira as quebras de linha
						if (node instanceof Element element && "br".equals(element.tagName())) {
							append = false;
						}
						// Retira os títulos
						if (node.outerHtml().equals(node.outerHtml().toUpperCase())) {
							append = false;
						}
						if (append) {
							textoAtual.append(node.outerHtml());
						}
					}
				}
				if (numeroAtual != null) {
					capitulo.getVersiculos().add(getVersiculo(capitulo, numeroAtual, textoAtual.toString()));
				}
			}
		}

		// Fim
		return result;
	}

	private Versiculo getVersiculo(Capitulo capitulo, String numero, String texto) {
		Versiculo versiculo = new Versiculo();
		capitulo.getVersiculos().add(versiculo);
		versiculo.setCapitulo(capitulo);
		versiculo.setNumero(numero);
		versiculo.setTexto(texto.trim());
		return versiculo;
	}
}
