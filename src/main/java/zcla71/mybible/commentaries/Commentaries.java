package zcla71.mybible.commentaries;

import lombok.Data;

// CREATE TABLE commentaries (
@Data
public class Commentaries {
    // book_number NUMERIC,
    private Integer book_number;
    // chapter_number_from NUMERIC,
    private Integer chapter_number_from;
    // verse_number_from NUMERIC,
    private Integer verse_number_from;
    // chapter_number_to NUMERIC,
    private Integer chapter_number_to;
    // verse_number_to NUMERIC,
    private Integer verse_number_to;
    // is_preceding NUMERIC,
    private Integer is_preceding;
    // marker TEXT,
    private String marker;
    // text TEXT
    private String text;
    // )
}
