package zcla71.baudoze.anotacoes.entity;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
// Referência a uma data; criada para ser usada com DiarioDeAprendizagem
public class ReferenciaData extends Referencia {
	private LocalDate data; // Eu poderia usar Anotacao.dataInclusao, mas manter aqui deixa mais flexível, especialmente pra quem escreve no papel.
}
