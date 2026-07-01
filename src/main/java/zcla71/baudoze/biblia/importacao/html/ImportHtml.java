package zcla71.baudoze.biblia.importacao.html;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import lombok.extern.slf4j.Slf4j;
import zcla71.baudoze.BauDoZeProperties.PropBiblia.PropImportacao.PropHtmlImporta;
import zcla71.baudoze.biblia.entity.Biblia;

@Slf4j
public abstract class ImportHtml {
	protected Document getDocument(String url) throws IOException, InterruptedException {
		int tentativa = 0;
		Document result = null;
		while (true) {
			try {
				tentativa++;
				result = Jsoup.connect(url).get();
				return result;
			} catch (IOException e) {
				if (tentativa > 10) {
					throw e;
				} else {
					log.warn("Erro: " + e.getMessage() + ". Aguardando para fazer a tentativa " + tentativa + ".");
					Thread.sleep(30 * 1000); // 30 segundos
				}
			}
		}
	}

	protected Biblia newBiblia(PropHtmlImporta phi) {
		Biblia result = new Biblia();

		result.setCodigo(phi.getCodigo());
		result.setNome(phi.getNome());
		result.setIdioma(phi.getIdioma());
		result.setFonte(phi.getUri());
		result.setLivros(new ArrayList<>());

		return result;
	}
}
