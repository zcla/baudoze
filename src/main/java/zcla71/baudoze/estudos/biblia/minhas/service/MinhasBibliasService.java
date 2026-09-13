package zcla71.baudoze.estudos.biblia.minhas.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import zcla71.baudoze.estudos.biblia.minhas.dto.MinhaBiblia;
import zcla71.baudoze.estudos.biblia.minhas.dto.MinhasBiblias;
import zcla71.baudoze.estudos.biblia.minhas.dto.TabelaLinha;
import zcla71.baudoze.estudos.biblia.minhas.dto.Volume;

@Service
public class MinhasBibliasService {
	public List<TabelaLinha> getTabela() throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // TODO Remover depois de completar
		MinhasBiblias minhasBiblias = mapper.readValue(getClass().getResourceAsStream("/templates/estudos/biblia/minhas/minhasBiblias.json"), MinhasBiblias.class);

		MinhaBiblia ultBiblia = null;
		TabelaLinha ultLinha = null;

		List<TabelaLinha> result = new ArrayList<>();
		for (MinhaBiblia biblia : minhasBiblias.getBiblias()) {
			if (biblia.getCodigo().isBlank()) {
				continue;
			}
			for (Volume volume : biblia.getVolumes()) {
				TabelaLinha linha = new TabelaLinha();

				if (biblia == ultBiblia) {
					Objects.requireNonNull(ultLinha).setCodigoRowspan(ultLinha.getCodigoRowspan() + 1);
				} else {
					linha.setCodigo(biblia.getCodigo());
					ultBiblia = biblia;
					ultLinha = linha;
				}
				linha.setNome(volume.getNome());
				linha.setEditoras(volume.getEditoras());
				linha.setEdicao(volume.getEdicao());
				linha.setPublicacao(volume.getPublicacao());
				linha.setIdiomas(volume.getIdiomas());
				linha.setIsbn(volume.getIsbn());
				linha.setFormato(switch (volume.getFormato()) {
					case "fisico" -> "Física";
					case "kindle" -> "Kindle";
					case "pdf" -> "PDF";
					default -> volume.getFormato();
				});

				result.add(linha);
			}
		}

		return result;
	}
}
