package zcla71.baudoze.biblia.service;

import java.util.List;

import lombok.Data;

@Data
public class Capitulo {
	private String id;
    private String numero;
    private List<Versiculo> versiculos;
	private Texto texto;
}
