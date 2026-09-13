package zcla71.baudoze.estudos.biblia.minhas.dto;

import java.util.List;

import lombok.Data;

@Data
public class MinhaBiblia {
	private String codigo;
	private List<Volume> volumes;
}
