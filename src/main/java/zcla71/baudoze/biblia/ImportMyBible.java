package zcla71.baudoze.biblia;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

import zcla71.baudoze.biblia.model.Biblia;
import zcla71.mybible.MyBible;

public class ImportMyBible {
	public void downloadAll() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, IOException, SQLException, URISyntaxException {
        // ----- https://www.ph4.org/b4_1.php
        // [pt-br] BAM 1959 - Bíblia Ave Maria (The words of Jesus are highlighted in red)
		download("https://www.ph4.org/_dl.php?back=bbl&a=BAM&b=mybible&c");
        // [pt-br] BEP 1990 - Bíblia Sagrada — Edição Pastoral (The words of Jesus are highlighted in red)
		// [pt-br] BJRD 2002 - Bíblia de Jerusalém
		// [pt-br] CNBB 2002 - Bíblia CNBB (Nova Capa) (The words of Jesus are highlighted in red)
		// [pt-br] DBFC 1955 - Difusora Bíblica (The words of Jesus are highlighted in red) (Franciscanos Capuchinhos)
		// [pt-br] DIF - Difusora Bíblica - Franciscanos Capuchinhos
		// [pt-pt] BPT 2009 - a BÍBLIA para todos Edição Católica (The words of Jesus are highlighted in red; Sociedade Bíblica de Portugal)
		// [es] EUNSA 1997 - Sagrada Biblia (Universidad de Navarra)
		// [en] RSV-2CE 2006 - Revised Standard Version, Second Catholic Edition (The words of Jesus are highlighted in red)
	}

	private void download(String url) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, IOException, SQLException, URISyntaxException {
		MyBible bam = MyBible.fromZipFile(new URI(url));
		// TODO Biblia biblia = new Biblia(bam);
		// TODO new BibliaService().incluir(biblia);
	}
}
