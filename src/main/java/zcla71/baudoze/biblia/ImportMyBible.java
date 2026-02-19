package zcla71.baudoze.biblia;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;

import zcla71.baudoze.biblia.model.Biblia;
import zcla71.mybible.MyBible;
import zcla71.mybible.MyBibleUtils;

public class ImportMyBible {
	// TODO O BibliaRepository não vai funcionar se não for um componente
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, IOException, SQLException, URISyntaxException {
		// TODO Fazer isso ser automático (verificar se existe e importar se não)
		new ImportMyBible().downloadAll();
	}

	public void downloadAll() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, IOException, SQLException, URISyntaxException {
        // ----- https://www.ph4.org/b4_1.php
        // [pt-br] BAM 1959 - Bíblia Ave Maria (The words of Jesus are highlighted in red)
		download("https://www.ph4.org/_dl.php?back=bbl&a=BAM&b=mybible&c", "ph4.org/BAM1959", "Bíblia Ave Maria");
        // [pt-br] BEP 1990 - Bíblia Sagrada — Edição Pastoral (The words of Jesus are highlighted in red)
		// [pt-br] BJRD 2002 - Bíblia de Jerusalém
		// [pt-br] CNBB 2002 - Bíblia CNBB (Nova Capa) (The words of Jesus are highlighted in red)
		// [pt-br] DBFC 1955 - Difusora Bíblica (The words of Jesus are highlighted in red) (Franciscanos Capuchinhos)
		// [pt-br] DIF - Difusora Bíblica - Franciscanos Capuchinhos
		// [pt-pt] BPT 2009 - a BÍBLIA para todos Edição Católica (The words of Jesus are highlighted in red; Sociedade Bíblica de Portugal)
		// [es] EUNSA 1997 - Sagrada Biblia (Universidad de Navarra)
		// [en] RSV-2CE 2006 - Revised Standard Version, Second Catholic Edition (The words of Jesus are highlighted in red)
	}

	private void download(String url, String codigo, String nome) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, IOException, SQLException, URISyntaxException {
		MyBible myBible = MyBibleUtils.loadFromZipFile(new URI(url));
		Biblia biblia = fromMyBible(url, codigo, nome, myBible);
		new BibliaService().incluir(biblia);
	}

	private Biblia fromMyBible(String url, String codigo, String nome, MyBible myBible) {
		// Biblia
		Biblia result = new Biblia();
		result.setCodigo(codigo);
		result.setNome(nome);
		result.setFonte(url);
		result.setLivros(new ArrayList<>());

		// TODO Livro

		// TODO Capitulo

		// TODO Versiculo

		// Fim
		return result;
	}
}
