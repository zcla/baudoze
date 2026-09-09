package zcla71.baudoze.estudos.biblia.concordancia.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import zcla71.baudoze.estudos.biblia.concordancia.dto.Concordancia;
import zcla71.baudoze.estudos.biblia.concordancia.dto.Parte;
import zcla71.baudoze.estudos.biblia.concordancia.dto.TabelaLinha;
import zcla71.baudoze.estudos.biblia.concordancia.dto.Titulo;

@Service 
public class ConcordanciaService {
	public List<TabelaLinha> getTabela() throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Concordancia concordancia = mapper.readValue(getClass().getResourceAsStream("/templates/estudos/biblia/concordancia/concordanciaDosSantosEvangelhos.json"), Concordancia.class);

		List<TabelaLinha> result = new ArrayList<>();
		for (Parte parte : concordancia.getPartes()) {
			String parteTitulo = parte.getTitulo();
			TabelaLinha linhaParte = null;

			for (Titulo titulo : parte.getTitulos()) {
				// TODO Tirar o if abaixo quando o estudo terminar
				if (titulo.getTitulo().length() == 0) {
					continue;
				}

				TabelaLinha linha = new TabelaLinha();
				if (linhaParte == null) {
					linhaParte = linha;
				}
				linhaParte.setParteRowspan(linhaParte.getParteRowspan() + 1);

				linha.setParte(parteTitulo);
				parteTitulo = null;
				linha.setTitulo(titulo.getTitulo());
				for (String pericope : titulo.getPericopes()) {
					String sigla = pericope.split(" ")[0];
					String texto = pericope.substring(sigla.length() + 1);
					switch (sigla) {
						case "Mt":
							linha.setMt(texto);
							break;
						case "Mc":
							linha.setMc(texto);
							break;
						case "Lc":
							linha.setLc(texto);
							break;
						case "Jo":
							linha.setJo(texto);
							break;
						default:
							throw new RuntimeException("Sigla desconhecida");
					}
				}

				result.add(linha);
			}
		}
		return result;
	}
}
