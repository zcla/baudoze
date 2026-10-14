package zcla71.baudoze.anotacoes.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Revisao {
	public Long id;
	public Anotacao anotacao;
	public LocalDateTime dataHora;
	public String comentarios;
}
