package zcla71.baudoze.tarefa.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zcla71.baudoze.auth_user.model.entity.AuthUser;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tarefa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "auth_user_id", nullable = false, referencedColumnName = "id")
	private AuthUser authUser;

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
