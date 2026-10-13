package zcla71.baudoze.anotacoes.entity;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
// TODO Só pode ser usada em Anotacao se tipo == DIARIO_DE_APRENDIZAGEM
// TODO Não pode ser usada em Anotacao se tipo != DIARIO_DE_APRENDIZAGEM
public class ReferenciaData extends Referencia {
	private LocalDate data;
}
