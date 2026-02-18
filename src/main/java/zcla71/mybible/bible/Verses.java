package zcla71.mybible.bible;

import lombok.Data;

@Data
// CREATE TABLE verses (
public class Verses {
    // book_number NUMERIC,
    private Integer book_number;
    // chapter NUMERIC,
    private Integer chapter;
    // verse NUMERIC,
    private Integer verse;
    // text TEXT
    private String text;
    // )
}
