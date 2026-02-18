package zcla71.mybible.bible;

import lombok.Data;

@Data
// CREATE TABLE books (
public class Books {
    // book_number NUMERIC,
    private Integer book_number;
    // book_color TEXT,
    private String book_color;
    // short_name TEXT,
    private String short_name;
    // long_name TEXT,
    private String long_name;
    // sorting_order NUMERIC
    private Integer sorting_order;
    // )
}
