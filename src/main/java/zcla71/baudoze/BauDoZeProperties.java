package zcla71.baudoze;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "app")
public class BauDoZeProperties {
	// app.biblia
	private PropBiblia biblia;
	@Data
	public static class PropBiblia {
		// app.biblia.importacao
		private PropImportacao importacao;
		@Data
		public static class PropImportacao {
			// app.biblia.importacao.ph4-importa
			private List<PropPh4Importa> ph4Importa;
			@Data
			public static class PropPh4Importa {
				private String codigo;
				private String uri;
				private String nome;
				private String idioma;
			}

			// app.biblia.importacao.sqLite-importa
			private List<PropSqliteImporta> sqliteImporta;
			@Data
			public static class PropSqliteImporta {
				private String codigo;
				private String uri;
				private String nome;
				private String idioma;
			}
		}
	}
}
