package zcla71.baudoze.planodevida.entity;

import java.util.Collection;

import lombok.Data;

@Data
public class PlanoDeVida {
	private Collection<Pratica> praticas;
	// TODO Jaculatórias, comunhão espiritual (tarefas horárias?)
	// TODO Atos de caridade (registrar?)
	// TODO Fora do plano de vida: método de oração; práticas de devoção; penitência; apostolado; foco do exame particular; ...
}
