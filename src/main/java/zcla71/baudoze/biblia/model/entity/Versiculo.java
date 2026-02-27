package zcla71.baudoze.biblia.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Versiculo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "capitulo_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_versiculo_capitulo"))
	private Capitulo capitulo;

	@Column(nullable = false)
	private String numero;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String texto;
}
