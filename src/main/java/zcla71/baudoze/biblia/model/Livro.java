package zcla71.baudoze.biblia.model;

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
public class Livro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "biblia_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_livro_biblia"))
	private Biblia biblia;

	@Column(nullable = false)
	private String sigla;

	private String nome;

	@OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
	private List<Capitulo> capitulos;
}
