package zcla71.baudoze.planodevida.dto;

import java.util.List;

import lombok.Data;

@Data
public class PlanoDeVida {
	private List<Identidade> identidades;
	private List<Pratica> praticas; // TODO Reavaliar; parece melhor que as execuções sejam destacadas da prática
	// TODO Jaculatórias, comunhão espiritual (tarefas horárias?)
	// TODO Atos de caridade (registrar?)
	// TODO Fora do plano de vida: método de oração; práticas de devoção; penitência; apostolado; foco do exame particular; ...
}
