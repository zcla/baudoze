package zcla71.baudoze.common.view;

@Deprecated
public enum ContextoCrud {
	MOSTRAR("mostrar", ""),
	INCLUIR("incluir", "Incluir "),
	EXCLUIR("excluir", "Excluir "),
	ALTERAR("alterar", "Alterar ");

	public String id;
	public String tituloPrefixo;

	ContextoCrud(String id, String tituloPrefixo) {
		this.id = id;
		this.tituloPrefixo = tituloPrefixo;
	}
}
