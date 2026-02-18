package zcla71.mybible.bible;

import java.util.List;

import lombok.Data;
import zcla71.mybible.common.Info;

@Data
public class BibleModule {
    private List<Info> info;
    private List<Books> books;
    private List<BooksAll> booksAll;
    private List<Verses> verses;
    private List<Introductions> introductions;
    private List<Stories> stories;
    // TODO CREATE TABLE morphology_indications (indication TEXT, applicable_to TEXT, language as TEXT, meaning TEXT)
    // TODO CREATE TABLE morphology_topics (indication TEXT, topic TEXT)
}
