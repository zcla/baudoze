package zcla71.baudoze.biblia.importacao;

import java.io.IOException;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zcla71.baudoze.BauDoZeProperties;
import zcla71.baudoze.BauDoZeProperties.PropBiblia.PropImportacao.PropHtmlImporta;
import zcla71.baudoze.biblia.importacao.html.ImportHtmlParoquiadasgracasCom;
import zcla71.baudoze.biblia.importacao.html.ImportHtmlVaticanVaLa;

@RequiredArgsConstructor
@Component
@Slf4j
public class ImportHtml {
	final private BauDoZeProperties appProperties;
	final private ImportHtmlParoquiadasgracasCom importParoquiadasgracasCom;
	final private ImportHtmlVaticanVaLa importHtmlVaticanVaLa;

	@Async
	@EventListener(ApplicationStartedEvent.class)
	public void init() {
		try {
			log.info("init start");
			importaTudo();
			log.info("init end");
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException("Erro ao importar dados em Html", e);
		}
	}

	private void importaTudo() throws IOException, InterruptedException {
		for (PropHtmlImporta biblia : appProperties.getBiblia().getImportacao().getHtmlImporta()) {
			switch (biblia.getCodigo()) {
				case "paroquiadasgracas.com":
					importParoquiadasgracasCom.htmlImporta(biblia);
					break;
				case "vatican.va/la":
					importHtmlVaticanVaLa.htmlImporta(biblia);
					break;
				default:
					throw new RuntimeException("Código " + biblia.getCodigo() + " desconhecido.");
			}
		}
	}
}
