package zcla71.baudoze.estudos.biblia.minhas.dto;

import java.util.List;

import lombok.Data;

@Data
public class MinhaBiblia {
	private String codigo;
	private String nome;
	private List<Volume> volumes;
}
