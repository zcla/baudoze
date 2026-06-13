package zcla71.baudoze.biblia.importacao.oraetlabora.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ApiBiblia {
	private String fonte;
	@JsonProperty("total_livros")
	private Integer totalLivros;
	@JsonProperty("antigo_testamento")
	private List<ApiLivro> antigoTestamento;
	@JsonProperty("novo_testamento")
	private List<ApiLivro> novoTestamento;
}
