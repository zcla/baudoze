package zcla71.mybible.bible;

import lombok.Data;

@Data
// CREATE TABLE stories (
public class Stories {
    // book_number NUMERIC,
    private Integer book_number;
    // chapter NUMERIC,
    private Integer chapter;
    // verse NUMERIC,
    private Integer verse;
    // order_if_several NUMERIC,
    private Integer order_if_several;
    // title TEXT
    private String title;
    // )
}
