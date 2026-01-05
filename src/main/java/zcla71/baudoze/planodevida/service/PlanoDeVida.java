package zcla71.baudoze.planodevida.service;

import java.util.Collection;

import lombok.Data;

@Data
public class PlanoDeVida {
	private Collection<Pratica> praticas;
	// TODO Jaculatórias, comunhão espiritual (tarefas horárias?)
	// TODO Atos de caridade (registrar?)
}
