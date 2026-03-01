package zcla71.baudoze.tarefa.model.entity;

import java.util.Comparator;

import zcla71.utils.Utils;

public class TarefaComparator implements Comparator<Tarefa> {
	@Override
	public int compare(Tarefa t1, Tarefa t2) {
		if (Utils.trataNull(t1.getCumprida()) && !Utils.trataNull(t2.getCumprida())) {
			return 1;
		}
		if (!Utils.trataNull(t1.getCumprida()) && Utils.trataNull(t2.getCumprida())) {
			return -1;
		}
		if (Utils.trataNull(t1.getPeso()) < Utils.trataNull(t2.getPeso())) {
			return 1;
		}
		if (Utils.trataNull(t1.getPeso()) > Utils.trataNull(t2.getPeso())) {
			return -1;
		}
		return t1.getNome().compareToIgnoreCase(t2.getNome());
	}
}
