package zcla71.baudoze.biblia.model.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Capitulo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "livro_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_capitulo_livro"))
	private Livro livro;

	@Column(nullable = false)
	private String numero;

	@OneToMany(mappedBy = "capitulo", cascade = CascadeType.ALL)
	private List<Versiculo> versiculos;
}
