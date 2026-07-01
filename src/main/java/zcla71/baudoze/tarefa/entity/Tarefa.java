package zcla71.baudoze.tarefa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Tarefa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Informe o título.")
	@Size(max = 150, message = "O título deve ter no máximo 255 caracteres")
	private String titulo;

	private String descricao;

	@ManyToOne
	@JoinColumn(name = "id_mae", referencedColumnName = "id")
	private Tarefa tarefaMae;

	private Long ordem;

	private Boolean cumprida;
}
