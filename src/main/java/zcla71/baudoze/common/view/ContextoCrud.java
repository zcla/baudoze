package zcla71.baudoze.common.view;

public enum ContextoCrud {
	MOSTRAR("mostrar", "Tarefa"),
	INCLUIR("incluir", "Incluir tarefa"),
	EXCLUIR("excluir", "Excluir tarefa"),
	ALTERAR("alterar", "Alterar tarefa");

	public String id;
	public String titulo;

	ContextoCrud(String id, String titulo) {
		this.id = id;
		this.titulo = titulo;
	}
}
