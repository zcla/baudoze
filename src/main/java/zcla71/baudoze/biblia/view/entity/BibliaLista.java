package zcla71.baudoze.biblia.view.entity;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Immutable
public class BibliaLista {
	@Id
	private Long id;
	private String nome;
	private String idioma;
	private Integer livros;
	private String fonte;

	@Transient
	public String getBandeira() {
		if (this.idioma == null) {
			return null;
		}
		String[] spl = this.idioma.split("-");
		return spl[spl.length - 1];
	}

	@Transient
	public String getFonteDominio() {
		return fonte.split("/")[2];
	}
}
