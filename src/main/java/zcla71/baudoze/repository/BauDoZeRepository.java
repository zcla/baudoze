package zcla71.baudoze.repository;

import zcla71.baudoze.repository.model.BauDoZeRepositoryData;
import zcla71.repository.Repository;
import zcla71.repository.JsonRepository;
// import zcla71.repository.TiddlyWikiRepository;

public class BauDoZeRepository extends JsonRepository<BauDoZeRepositoryData> {
// public class BauDoZeRepository extends TiddlyWikiRepository<BauDoZeRepositoryData> {
    public static String generateId(Object object) {
        // TODO Tem que garantir ids únicos por tabela
        // TODO Gera <tipo>.<chaveSecundaria>.<diferenciadorParaCasoDeColisao>
        // Sugestão: Definir sempre como "0" a princípio
        //           Incrementar até não encontrar colisão
        return Repository.generateId(object);
    }

    private static final String JSON_FILE_LOCATION = "data/baudoze.json"; // TODO Jogar pro application.properties
    // private static final String TIDDLYWIKI_FILE_LOCATION = "data/baudoze.html"; // TODO Jogar pro application.properties

    public static BauDoZeRepository getInstance(Boolean readFile) throws Exception {
        return new BauDoZeRepository(readFile);
    }

    private BauDoZeRepository(Boolean readFile) throws Exception {
        super(BauDoZeRepositoryData.class, JSON_FILE_LOCATION, readFile);
        // super(BauDoZeRepositoryData.class, TIDDLYWIKI_FILE_LOCATION, readFile);
    }
}
