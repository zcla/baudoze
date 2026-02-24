package zcla71.baudoze.biblia.view;

import java.util.List;

import lombok.Data;

@Data
public class BibliaListaView {
	private List<BibliaListaViewBiblia> biblias;

	public BibliaListaView(List<BibliaListaViewBiblia> biblias) {
		this.biblias = biblias;
	}
}
