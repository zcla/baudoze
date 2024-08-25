package zcla71.baudoze.batch;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

import zcla71.baudoze.batch.model.LibibCsvLine;
import zcla71.baudoze.batch.model.Notes;
import zcla71.tiddlywiki.Tiddler;
import zcla71.tiddlywiki.TiddlyWiki;

public class LibibToTiddlywiki {
    public static void main(String[] args) throws Exception {
        DateTimeFormatter dateFormatLibib = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateFormatTiddlyWiki = DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS");

        List<LibibCsvLine> libib = readLibib();
        TiddlyWiki wiki = new TiddlyWiki(new File("data/livros.html"));
        for (LibibCsvLine line : libib) {
            switch (line.getItemType()) {
                case "book":
                    String publisher = line.getPublisher();
                    String publishDate = null;
                    try {
                        publishDate = line.getPublishDate().split("-")[0];
                    } catch (NumberFormatException e) {
                        // ignora erros de parsing
                    }
                    String length = line.getLength();
                    String edicao = null;
                    String isbn13 = line.getEanIsbn13();
                    if ((line.getNotes() != null) && (line.getNotes().length() > 0)) {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            Notes notes = objectMapper.readValue(line.getNotes(), Notes.class);
                            // ignora notes.id
                            edicao = notes.getEdicao();
                            if (notes.getEditora() != null) {
                                publisher = notes.getEditora();
                            }
                            if (notes.getPublicacao() != null) {
                                publishDate = notes.getPublicacao().toString();
                            }
                            if (notes.getPaginas() != null) {
                                length = notes.getPaginas().toString();
                            }
                            if (notes.getIsbn13() != null) {
                                isbn13 = notes.getIsbn13();
                            }
                        } catch (JsonParseException e) {
                            System.err.println("Erro ao fazer o parsing da nota \"" + line.getNotes() + "\":");
                            System.err.println(e);
                        }
                    }

                    Tiddler livro = wiki.getByTagAndTitle("Livro", line.getTitle());
                    if (livro == null) {
                        livro = new Tiddler(line.getTitle());
                        wiki.insert(livro);
                        livro.setTags("Livro TenhoImpresso _Revisar");
                    }

                    livro.getCustomProperties().put("titulo", line.getTitle());

                    if ((isbn13 != null) && (isbn13.length() > 0)) {
                        livro.getCustomProperties().put("isbn13", isbn13);
                    }

                    if ((line.getUpcIsbn10() != null) && (line.getUpcIsbn10().length() > 0)) {
                        livro.getCustomProperties().put("isbn10", line.getUpcIsbn10());
                    }

                    if ((publisher != null) && (publisher.length() > 0)) {
                        String propEditoras = "";
                        String[] editoras = publisher.split(",");
                        for (String nomeEditora : editoras) {
                            nomeEditora = nomeEditora.trim();
                            String title = nomeEditora + " (editora)";
                            Tiddler editora = wiki.getByTagAndTitle("Editora", title);
                            if (editora == null) {
                                editora = new Tiddler(title);
                                wiki.insert(editora);
                                editora.setTags("Editora _Revisar");
                                editora.getCustomProperties().put("nome", nomeEditora);
                            }
                            propEditoras += " [[" + editora.getTitle() + "]]";
                        }
                        livro.getCustomProperties().put("editoras", propEditoras.trim());
                    }

                    if ((publishDate != null) && (!publishDate.isEmpty())) {
                        livro.getCustomProperties().put("publicacao", publishDate);
                    }

                    if ((length != null) && (!length.isEmpty())) {
                        livro.getCustomProperties().put("paginas", length);
                    }

                    if ((edicao != null) && (!edicao.isEmpty())) {
                        livro.getCustomProperties().put("edicao", edicao);
                    }

                    if ((line.getAdded() != null) && (line.getAdded().length() > 0)) {
                        livro.setCreated(LocalDate.parse(line.getAdded(), dateFormatLibib).atStartOfDay());
                    }

                    if ((line.getCreators() != null) && (line.getCreators().length() > 0)) {
                        String propAutores = "";
                        String[] autores = line.getCreators().split(",");
                        for (String autor : autores) {
                            autor = autor.trim();
                            String title = autor;
                            Tiddler pessoa = wiki.getByTagAndTitle("Pessoa", title);
                            if (pessoa == null) {
                                pessoa = new Tiddler(title);
                                wiki.insert(pessoa);
                                pessoa.setTags("Pessoa _Revisar");
                                pessoa.getCustomProperties().put("nome", autor);
                            }
                            propAutores += " [[" + pessoa.getTitle() + "]]";
                            livro.getCustomProperties().put("autores", propAutores.trim());
                        }
                    }

                    if ((line.getTags() != null) && (line.getTags().length() > 0)) {
                        String[] tags = line.getTags().split(",");
                        for (String tag : tags) {
                            tag = tag.trim();
                            livro.setTags(livro.getTags() + " [[" + tag + "]]");
                        }
                    }

                    if ((line.getGroup() != null) && (line.getGroup().length() > 0)) {
                        String title = line.getGroup().trim() + " (coleção)";
                        Tiddler colecao = wiki.getByTagAndTitle("Coleção", title);
                        if (colecao == null) {
                            colecao = new Tiddler(title);
                            wiki.insert(colecao);
                            colecao.setTags("Coleção _Revisar");
                            colecao.getCustomProperties().put("nome", line.getGroup().trim());
                        }
                        livro.getCustomProperties().put("colecao", "[[" + colecao.getTitle() + "]]");
                    }

                    String status = line.getStatus().trim();
                    switch (status) {
                        case "Abandoned":
                            status = "Abandonado";
                            break;
                        case "Completed":
                            status = "Lido";
                            break;
                        case "In progress":
                            status = "Lendo";
                            break;
                        case "Not begun":
                        case "":
                            status = null;
                            break;
                        default:
                            throw new RuntimeException("Não sei tratar o status " + status);
                    }
                    if ((status != null) && (status.length() > 0)) {
                        livro.getCustomProperties().put("status", status);
                    }

                    String began = line.getBegan().trim();
                    if ((began != null) && (began.length() > 0)) {
                        LocalDateTime ldBegan = LocalDate.parse(began, dateFormatLibib).atStartOfDay();
                        String twBegan = ldBegan.format(dateFormatTiddlyWiki);
                        String title = livro.getTitle() + "\\" + twBegan;
                        Tiddler leitura = wiki.getByTagAndTitle("Leitura", title);
                        if (leitura == null) {
                            leitura = new Tiddler(title);
                            wiki.insert(leitura);
                            leitura.setTags("Leitura");
                        }
                        leitura.getCustomProperties().put("livro", "[[" + livro.getTitle() + "]]");
                        leitura.getCustomProperties().put("inicio", twBegan);

                        String completed = line.getCompleted().trim();
                        if ((completed != null) && (completed.length() > 0)) {
                            LocalDateTime ldCompleted = LocalDate.parse(completed, dateFormatLibib).atStartOfDay();
                            String twCompleted = ldCompleted.format(dateFormatTiddlyWiki);
                            if (status != null) { // Não tem risco; só pra tirar o warning :)
                                switch (status) {
                                    case "Lido":
                                        leitura.getCustomProperties().put("fim", twCompleted);
                                        break;
    
                                    default:
                                        throw new RuntimeException("Status desconhecido: " + status);
                                }
                            }
                        }
                    }
                    break;

                default:
                    throw new RuntimeException("ItemType \"" + line.getItemType() + "\" desconhecido");
            }
        }
        wiki.save();
    }

    private static List<LibibCsvLine> readLibib() throws URISyntaxException, IOException {
        File file = new File("data/libib.csv");
        Reader reader = Files.newBufferedReader(file.toPath());
        CSVReader csvReader = new CSVReader(reader);
        try {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            List<LibibCsvLine> result = new CsvToBeanBuilder(csvReader).withType(LibibCsvLine.class).build().parse();
            return result;
        } finally {
            csvReader.close();
        }
    }
}
