package zcla71.baudoze.biblia.importacao;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zcla71.baudoze.BauDoZeProperties;
import zcla71.baudoze.BauDoZeProperties.PropBiblia.PropImportacao.PropJsonImporta;
import zcla71.baudoze.biblia.importacao.oraetlabora.model.ApiBiblia;
import zcla71.baudoze.biblia.importacao.oraetlabora.model.ApiCapitulo;
import zcla71.baudoze.biblia.importacao.oraetlabora.model.ApiLivro;
import zcla71.baudoze.biblia.importacao.oraetlabora.model.ApiVersiculo;
import zcla71.baudoze.biblia.model.entity.Biblia;
import zcla71.baudoze.biblia.model.entity.Capitulo;
import zcla71.baudoze.biblia.model.entity.Livro;
import zcla71.baudoze.biblia.model.entity.Versiculo;
import zcla71.baudoze.biblia.model.service.BibliaService;

@RequiredArgsConstructor
@Component
@Slf4j
public class ImportJson {
	final private BauDoZeProperties appProperties;
	final private BibliaService bibliaService;

	@Async
	@EventListener(ApplicationStartedEvent.class)
	public void init() {
		try {
			log.info("init start");
			importaTudo();
			log.info("init end");
		} catch (IOException | URISyntaxException | InterruptedException e) {
			throw new RuntimeException("Erro ao importar dados em Json", e);
		}
	}

	private void importaTudo() throws IOException, InterruptedException, URISyntaxException {
		for (PropJsonImporta biblia : appProperties.getBiblia().getImportacao().getJsonImporta()) {
			jsonImporta(biblia.getUri(), biblia.getCodigo(), biblia.getNome(), biblia.getIdioma());
		}
	}

	private void jsonImporta(String uri, String codigo, String nome, String idioma) throws IOException, InterruptedException, URISyntaxException {
		log.info("jsonImporta(\"" + codigo + "\")");
		if (this.bibliaService.buscaBibliaPorCodigo(codigo) == null) {
			Biblia biblia = fromJson(uri, codigo, nome, idioma);
			if (biblia != null) {
				this.bibliaService.incluir(biblia);
			}
		}
	}

	private Biblia fromJson(String uri, String codigo, String nome, String idioma) throws IOException, InterruptedException, URISyntaxException {
		log.info("fromJson(\"" + codigo + "\")");
		// Biblia
		Biblia result = new Biblia();
		result.setCodigo(codigo);
		result.setNome(nome);
		result.setIdioma(idioma);
		result.setFonte(uri);
		result.setLivros(new ArrayList<>());

		// Livro
		ObjectMapper mapper = new ObjectMapper();
		ApiBiblia apiBiblia = readValueWithRetry(mapper, new URI(uri), ApiBiblia.class);
		result.setFonte(apiBiblia.getFonte());
		List<ApiLivro> apiLivros = new ArrayList<>();
		apiLivros.addAll(apiBiblia.getAntigoTestamento());
		apiLivros.addAll(apiBiblia.getNovoTestamento());
		for (ApiLivro apiLivro : apiLivros) {
			log.info("fromJson(\"" + codigo + "\") => " + apiLivro.getAbbrev());
			Livro livro = new Livro();
			result.getLivros().add(livro);
			livro.setBiblia(result);
			livro.setSigla(apiLivro.getAbbrev());
			livro.setNome(apiLivro.getNome());
			livro.setCapitulos(new ArrayList<>());

			// Capitulo
			for (Integer numCapitulo = 1; numCapitulo <= apiLivro.getCapitulos(); numCapitulo++) {
				ApiCapitulo apiCapitulo = readValueWithRetry(mapper, new URI(uri + "/" + apiLivro.getAbbrev() + "/" + numCapitulo), ApiCapitulo.class);
				log.info("fromJson(\"" + codigo + "\") => " + apiLivro.getAbbrev() + " => " + numCapitulo);
				Capitulo capitulo = new Capitulo();
				livro.getCapitulos().add(capitulo);
				capitulo.setLivro(livro);
				capitulo.setNumero(apiCapitulo.getCapitulo().toString());
				capitulo.setVersiculos(new ArrayList<>());

				// Versiculo
				for (ApiVersiculo apiVersiculo : apiCapitulo.getVersiculos()) {
					Versiculo versiculo = new Versiculo();
					capitulo.getVersiculos().add(versiculo);
					versiculo.setCapitulo(capitulo);
					versiculo.setNumero(apiVersiculo.getNumero().toString());
					versiculo.setTexto(apiVersiculo.getTexto());
				}
			}
		}

		// Fim
		return result;
	}

	public <T> T readValueWithRetry(ObjectMapper mapper, URI uri,Class<T> clazz) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		while (true) {
			HttpRequest request = HttpRequest.newBuilder(uri)
					.GET()
					.build();
			HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
			if (response.statusCode() == 200) {
				try (InputStream is = response.body()) {
					return mapper.readValue(is, clazz);
				}
			}

			// Too Many Requests
			if (response.statusCode() == 429) {
				log.info("Status code 429 (Too Many Requests)");
				String retryAfter = response.headers()
						.firstValue("Retry-After")
						.orElse("60");
				long seconds = Long.parseLong(retryAfter);
				log.info("Aguardando " + seconds + " segundos");
				Thread.sleep(seconds * 1000);
				continue;
			}
			throw new IOException("HTTP " + response.statusCode() + " ao acessar " + uri);
		}
	}
}
