package zcla71.baudoze.biblia.service;

import lombok.Data;
import lombok.EqualsAndHashCode;

// Texto inserido para gerar clareza, indicado com formatação diferente (geralmente em itálico).
// Ex.: Bíblia Pe. Matos Soares Ed. Realeza (1932), Lc 1,8 (cercado por barras): Sucedeu pois que, exercendo //(Zacarias)// diante de Deus as funções de sacerdote na ordem da sua turma, ...
@Data
@EqualsAndHashCode(callSuper=true)
public class TrechoParteAdicao extends TrechoParte {
	private String texto;
}
