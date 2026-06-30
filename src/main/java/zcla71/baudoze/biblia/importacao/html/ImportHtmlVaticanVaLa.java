package zcla71.baudoze.biblia.importacao.html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
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
public class ImportHtmlVaticanVaLa extends ImportHtml {
	final private BibliaService bibliaService;

	final private Map<String, String> MAP_LIVROS = Map.ofEntries(
		Map.entry("Liber Genesis", "Gen"),
		Map.entry("Liber Exodus", "Ex"),
		Map.entry("Liber Leviticus", "Lev"),
		Map.entry("Liber Numeri", "Num"),
		Map.entry("Liber Deuteronomii", "Deut"),
		Map.entry("Liber Iosue", "Ios"),
		Map.entry("Liber Iudicum", "Iudic"),
		Map.entry("Liber Ruth", "Rut"),
		Map.entry("Liber I Samuelis", "1Sam"),
		Map.entry("Liber II Samuelis", "2Sam"),
		Map.entry("Liber I Regum", "1Reg"),
		Map.entry("Liber II Regum", "2Reg"),
		Map.entry("Liber I Paralipomenon", "1Chr"),
		Map.entry("Liber II Paralipomenon", "2Chr"),
		Map.entry("Liber Esdrae", "Esd"),
		Map.entry("Liber Nehemiae", "Neh"),
		Map.entry("Liber Thobis", "Tob"),
		Map.entry("Liber Iudith", "Iudt"),
		Map.entry("Liber Esther", "Est"),
		Map.entry("Liber Iob", "Iob"),
		Map.entry("Liber Psalmorum", "Ps"),
		Map.entry("Liber Proverbiorum", "Prov"),
		Map.entry("Liber Ecclesiastes", "Qoh"),
		Map.entry("Canticum Canticorum", "Cant"),
		Map.entry("Liber Sapientiae", "Sap"),
		Map.entry("Liber Ecclesiasticus", "Sir"),
		Map.entry("Liber Isaiae", "Is"),
		Map.entry("Liber Ieremiae", "Ier"),
		Map.entry("Lamentationes", "Lam"),
		Map.entry("Liber Baruch", "Bar"),
		Map.entry("Prophetia Ezechielis", "Ez"),
		Map.entry("Prophetia Danielis", "Dan"),
		Map.entry("Prophetia Osee", "Os"),
		Map.entry("Prophetia Ioel", "Ioel"),
		Map.entry("Prophetia Amos", "Am"),
		Map.entry("Prophetia Abdiae", "Abd"),
		Map.entry("Prophetia Ionae", "Ion"),
		Map.entry("Prophetia Michaeae", "Mic"),
		Map.entry("Prophetia Nahum", "Nah"),
		Map.entry("Prophetia Habacuc", "Hab"),
		Map.entry("Prophetia Sophoniae", "Soph"),
		Map.entry("Prophetia Aggaei", "Ag"),
		Map.entry("Prophetia Zachariae", "Zac"),
		Map.entry("Prophetia Malachiae", "Mal"),
		Map.entry("Liber I Maccabaeorum", "1Mac"),
		Map.entry("Liber II Maccabaeorum", "2Mac"),
		Map.entry("Evangelium secundum Matthaeum", "Mt"),
		Map.entry("Evangelium secundum Marcum", "Mc"),
		Map.entry("Evangelium secundum Lucam", "Lc"),
		Map.entry("Evangelium secundum Ioannem", "Io"),
		Map.entry("Actus Apostolorum", "Act"),
		Map.entry("Epistula ad Romanos", "Rom"),
		Map.entry("Epistula I ad Corinthios", "1Cor"),
		Map.entry("Epistula II ad Corinthios", "2Cor"),
		Map.entry("Epistula ad Galatas", "Gal"),
		Map.entry("Epistula ad Ephesios", "Eph"),
		Map.entry("Epistula ad Philippenses", "Phil"),
		Map.entry("Epistula ad Colossenses", "Col"),
		Map.entry("Epistula I ad Thessalonicenses", "1Th"),
		Map.entry("Epistula II ad Thessalonicenses", "2Th"),
		Map.entry("Epistula I ad Timotheum", "1Tim"),
		Map.entry("Epistula II ad Timotheum", "2Tim"),
		Map.entry("Epistula ad Titum", "Tit"),
		Map.entry("Epistulam ad Philemonem", "Phm"),
		Map.entry("Epistula ad Hebraeos", "Hebr"),
		Map.entry("Epistula Iacobi", "Iac"),
		Map.entry("Epistula I Petri", "1Petr"),
		Map.entry("Epistula II Petri", "2Petr"),
		Map.entry("Epistula I Ioannis", "1Io"),
		Map.entry("Epistula II Ioannis", "2Io"),
		Map.entry("Epistula III Ioannis", "3Io"),
		Map.entry("Epistula Iudae", "Iud"),
		Map.entry("Apocalypsis Ioannis", "Ap")
	);

	public void htmlImporta(PropHtmlImporta phi) throws IOException, InterruptedException {
		log.info("htmlImporta(\"" + phi.getCodigo() + "\")");
		if (this.bibliaService.buscaBibliaPorCodigo(phi.getCodigo()) == null) {
			Biblia biblia = fromHtml(phi);
			if (biblia != null) {
				this.bibliaService.incluir(biblia);
				// TODO novo_testamento
			}
		}
	}

	private Biblia fromHtml(PropHtmlImporta phi) throws IOException, InterruptedException {
		log.info("fromHtml(\"" + phi.getCodigo() + "\")");

		// Bíblia
		Biblia result = newBiblia(phi);

		// Testamentos
		Document docBiblia = getDocument(phi.getUri());
		Elements linksTestamentos = docBiblia.select("div#corpo table table a[href~=^.+testamentum_lt\\.html$]");
		for (Element linkTestamento : linksTestamentos) {
			result.getLivros().addAll(fromTestamento(linkTestamento.absUrl("href")));
		}
		result.getLivros().forEach(l -> l.setBiblia(result));

		// Fim
		return result;
	}

	private List<Livro> fromTestamento(String url) throws IOException, InterruptedException {
		log.info("fromTestamento(\"" + url + "\")");

		// Testamento
		List<Livro> result = new ArrayList<>();

		// Livros
		Document docTestamento = getDocument(url);
		Elements linksLivros = docTestamento.select("div#corpo table table a[href~=^nova-vulgata_.+_lt\\.html$]");
		for (Element linkLivro : linksLivros) {
			result.add(fromLivro(linkLivro.absUrl("href"), linkLivro.text()));
		}

		return result;
	}

	private Livro fromLivro(String url, String nome) throws IOException, InterruptedException {
		log.info("fromTestamento(\"" + url + "\")");

		// Livro
		Livro result = new Livro();
		// result.setBiblia(); // em fromHtml()
		result.setSigla(MAP_LIVROS.get(nome));
		result.setNome(nome);
		result.setCapitulos(new ArrayList<>());
		Document docLivro = getDocument(url);
		Elements pCapituloNumeros = docLivro.select("p a[name~=^(PSALMUS )?\\d+$]");

		// Abd, Phm, 2Io, 3Io, Iud: livros sem capítulos
		List<String> livrosSemCapitulos = List.of("Abd", "Phm", "2Io", "3Io", "Iud");
		if (livrosSemCapitulos.contains(result.getSigla())) {
			// Abd, Phm, 2Io
			pCapituloNumeros = docLivro.select("td td p:nth-last-child(2)");
			// 3Io
			if (pCapituloNumeros.isEmpty()) {
				pCapituloNumeros = docLivro.select("td td font p");
			}
			// Iud
			if (pCapituloNumeros.text().strip().length() == 0) {
				pCapituloNumeros = docLivro.select("td td p:nth-last-child(3)");
			}
		}

		for (Element pCapituloNumero : pCapituloNumeros) {
			Element pCapitulo = pCapituloNumero;
			List<String> capituloElementos = List.of("p");
			while (!capituloElementos.contains(pCapitulo.normalName())) {
				pCapitulo = pCapitulo.parentElement();
			}

			Element a = pCapitulo.select("a").first();
			String numCapitulo = "";
			if (a != null) {
				numCapitulo = pCapitulo.select("a").first().text();
			}
			if (numCapitulo.startsWith("PSALMUS ")) {
				numCapitulo = numCapitulo.substring(8);
			}
			Capitulo capitulo = new Capitulo();
			capitulo.setLivro(result);
			capitulo.setNumero(numCapitulo);
			capitulo.setVersiculos(new ArrayList<>());
			List<Node> children = pCapitulo.childNodes();
			Versiculo ultVersiculo = null;
			for (Node child : children) {
				if (child instanceof TextNode) {
					String texto = child.nodeValue().replace('\u00A0', ' ').strip();
					if (texto.length() > 0) {
						// ----- Erros conhecidos nas páginas -----
						// Num 1,1: falta o número do versículo
						if (result.getSigla().equals("Num") && capitulo.getNumero().equals("1") && capitulo.getVersiculos().size() == 0) {
							texto = "1 " + texto;
						}
						// Iudic 19,1: antes dele tem um texto que é continuação de 18,31
						if (result.getSigla().equals("Iudic") && capitulo.getNumero().equals("19") && capitulo.getVersiculos().size() == 0) {
							if (texto.startsWith("In ")) {
								Capitulo cap18 = result.getCapitulos().stream().filter(c -> c.getNumero().equals("18")).findFirst().get();
								Versiculo ver31 = cap18.getVersiculos().stream().filter(v -> v.getNumero().equals("31")).findFirst().get();
								ver31.setTexto(ver31.getTexto() + "\n" + texto);
								continue;
							}
						}
						// Ps 10-147: tira a referência à Vulgata
						if (result.getSigla().equals("Ps") && capitulo.getVersiculos().size() == 0) {
							if (texto.matches("^\\(.+\\)$")) {
								continue;
							}
						}
						// Bar 6: tem um versículo sem numeração; padronizei como "0".
						if (result.getSigla().equals("Bar") && capitulo.getNumero().equals("6") && capitulo.getVersiculos().size() == 0) {
							texto = "0 " + texto;
						}
						// Act 17,1: falta o espaço entre o número do versículo e o texto.
						if (result.getSigla().equals("Act") && capitulo.getNumero().equals("17") && capitulo.getVersiculos().size() == 0) {
							if (texto.startsWith("1Cum")) {
								texto = "1 " + texto.substring(1);
							}
						}

						String numVersiculo = texto.split(" ")[0];
						if (numVersiculo.matches("\\d+.?")) {
							String textoVersiculo = texto.substring(numVersiculo.length()).strip();
							Versiculo versiculo = new Versiculo();
							versiculo.setCapitulo(capitulo);
							versiculo.setNumero(numVersiculo);
							versiculo.setTexto(textoVersiculo);
							capitulo.getVersiculos().add(versiculo);
							ultVersiculo = versiculo;
						} else {
							String textoVersiculo = texto.strip();
							ultVersiculo.setTexto(ultVersiculo.getTexto() + "\n" + textoVersiculo);
						}
					}
				}
			}
			result.getCapitulos().add(capitulo);
		}
		return result;
	}
}
