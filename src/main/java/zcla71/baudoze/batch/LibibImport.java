package zcla71.baudoze.batch;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

import zcla71.baudoze.batch.model.LibibCsvLine;
import zcla71.baudoze.batch.model.Notes;
import zcla71.baudoze.repository.BauDoZeRepository;
import zcla71.baudoze.service.model.Atividade;
import zcla71.baudoze.service.model.Atividade.AtividadeTipo;
import zcla71.baudoze.service.model.Colecao;
import zcla71.baudoze.service.model.Editora;
import zcla71.baudoze.service.model.Etiqueta;
import zcla71.baudoze.service.model.Livro;
import zcla71.baudoze.service.model.ObraLiteraria;
import zcla71.baudoze.service.model.Pessoa;

public class LibibImport {
    public static void main(String[] args) throws Exception {
        List<LibibCsvLine> libib = readLibib();
        BauDoZeRepository repository = BauDoZeRepository.getInstance(false);

        repository.beginTransaction();
        try {
            for (LibibCsvLine line : libib) {
                switch (line.getItemType()) {
                    case "book":
                        String publisher = line.getPublisher();
                        Integer publishDate = null;
                        try {
                            publishDate = Integer.parseInt(line.getPublishDate().split("-")[0]);
                        } catch (NumberFormatException e) {
                            // ignora erros de parsing
                        }
                        Integer length = null;
                        try {
                            length = Integer.parseInt(line.getLength());
                        } catch (NumberFormatException e) {
                            // ignora erros de parsing
                        }
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
                                    publishDate = notes.getPublicacao();
                                }
                                if (notes.getPaginas() != null) {
                                    length = notes.getPaginas();
                                }
                                if (notes.getIsbn13() != null) {
                                    isbn13 = notes.getIsbn13();
                                }
                            } catch (JsonParseException e) {
                                System.err.println("Erro ao fazer o parsing da nota \"" + line.getNotes() + "\":");
                                System.err.println(e);
                            }
                        }

                        ObraLiteraria obra = new ObraLiteraria();
                        obra.setTitulo(line.getTitle());
                        String[] autores = line.getCreators().split(",");
                        obra.setIdsAutores(new ArrayList<>());
                        for (String autor : autores) {
                            Pessoa pessoa = repository.getData().getPessoas().stream().filter(p -> p.getNome().equals(autor)).findFirst().orElse(null);
                            if (pessoa == null) {
                                pessoa = new Pessoa();
                                pessoa.setNome(autor);
                                pessoa.setId(BauDoZeRepository.generateId(pessoa));
                                repository.getData().getPessoas().add(pessoa);
                            }
                            obra.getIdsAutores().add(pessoa.getId());
                        }
                        obra.setId(BauDoZeRepository.generateId(obra));
                        repository.getData().getObras().add(obra);

                        Livro livro = new Livro();
                        livro.setTitulo(line.getTitle());
                        livro.setIdsObras(new ArrayList<>());
                        livro.getIdsObras().add(obra.getId());
                        if ((isbn13 != null) && (isbn13.length() > 0)) {
                            livro.setIsbn13(isbn13);
                        }
                        if ((line.getUpcIsbn10() != null) && (line.getUpcIsbn10().length() > 0)) {
                            livro.setIsbn10(line.getUpcIsbn10());
                        }
                        livro.setIdsEditoras(new ArrayList<>());
                        if ((publisher != null) && (publisher.length() > 0)) {
                            String[] editoras = publisher.split(",");
                            for (String nomeEditora : editoras) {
                                Editora editora = repository.getData().buscaEditoraPorNome(nomeEditora);
                                if (editora == null) {
                                    editora = new Editora();
                                    editora.setNome(nomeEditora);
                                    editora.setId(BauDoZeRepository.generateId(editora));
                                    repository.getData().getEditoras().add(editora);
                                }
                                livro.getIdsEditoras().add(editora.getId());
                            }
                        }
                        livro.setAno(publishDate);
                        livro.setPaginas(length);
                        livro.setEdicao(edicao);
                        livro.setId(BauDoZeRepository.generateId(livro));
                        repository.getData().getLivros().add(livro);

                        if ((line.getGroup() != null) && (line.getGroup().length() > 0)) {
                            Colecao colecao = repository.getData().buscaColecaoPorNome(line.getGroup());
                            if (colecao == null) {
                                colecao = new Colecao();
                                colecao.setNome(line.getGroup());
                                colecao.setIdsLivros(new ArrayList<>());
                                colecao.setId(BauDoZeRepository.generateId(colecao));
                                repository.getData().getColecoes().add(colecao);
                            }
                            colecao.getIdsLivros().add(livro.getId());
                        }

                        livro.setIdsEtiquetas(new ArrayList<>());
                        if ((line.getTags() != null) && (line.getTags().length() > 0)) {
                            String[] tags = line.getTags().split(",");
                            for (String tag : tags) {
                                tag = tag.trim();
                                Etiqueta etiqueta = repository.getData().buscaEtiquetaPorNome(tag);
                                if (etiqueta == null) {
                                    etiqueta = new Etiqueta();
                                    etiqueta.setNome(tag);
                                    etiqueta.setId(BauDoZeRepository.generateId(etiqueta));
                                    repository.getData().getEtiquetas().add(etiqueta);
                                }
                                livro.getIdsEtiquetas().add(etiqueta.getId());
                            }
                        }

                        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                        String added = line.getAdded().trim();
                        LocalDate ldAdded = LocalDate.parse(added, dateFormat);
                        Atividade cadastro = new Atividade();
                        cadastro.setTipo(AtividadeTipo.CADASTRO);
                        cadastro.setData(ldAdded);
                        cadastro.setIdLivro(livro.getId());
                        cadastro.setId(BauDoZeRepository.generateId(cadastro));
                        repository.getData().getAtividades().add(cadastro);

                        String began = line.getBegan().trim();
                        if ((began != null) && (began.length() > 0)) {
                            LocalDate ldBegan = LocalDate.parse(began, dateFormat);
                            Atividade inicioLeitura = new Atividade();
                            inicioLeitura.setTipo(AtividadeTipo.INICIO_LEITURA);
                            inicioLeitura.setData(ldBegan);
                            inicioLeitura.setIdLivro(livro.getId());
                            inicioLeitura.setId(BauDoZeRepository.generateId(inicioLeitura));
                            repository.getData().getAtividades().add(inicioLeitura);
                        }

                        String completed = line.getCompleted().trim();
                        String status = line.getStatus().trim();
                        if ((completed != null) && (completed.length() > 0)) {
                            LocalDate ldCompleted = LocalDate.parse(completed, dateFormat);
                            switch (status) {
                                case "Completed":
                                    Atividade terminoLeitura = new Atividade();
                                    terminoLeitura.setTipo(AtividadeTipo.TERMINO_LEITURA);
                                    terminoLeitura.setData(ldCompleted);
                                    terminoLeitura.setIdLivro(livro.getId());
                                    terminoLeitura.setId(BauDoZeRepository.generateId(terminoLeitura));
                                    repository.getData().getAtividades().add(terminoLeitura);
                                    break;

                                default:
                                    throw new RuntimeException("Status desconhecido: " + status);
                            }
                        }

                        break;

                    default:
                        throw new RuntimeException("ItemType \"" + line.getItemType() + "\" desconhecido");
                }
            }
            repository.commitTransaction();
        } catch (Exception e) {
            repository.rollbackTransaction();
            throw e;
        }
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
