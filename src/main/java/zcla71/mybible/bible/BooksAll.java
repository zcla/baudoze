package zcla71.mybible.bible;

import lombok.Data;

@Data
// CREATE TABLE books_all (
public class BooksAll {
    // book_number NUMERIC,
    private Integer book_number;
    // book_color TEXT,
    private String book_color;
    // short_name TEXT,
    private String short_name;
    // title TEXT,
    private String title;
    // long_name TEXT,
    private String long_name;
    // is_present BOOLEAN,
    private Boolean is_present;
    // sorting_order NUMERIC
    private Integer sorting_order;
    // )
}
