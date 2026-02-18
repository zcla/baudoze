package zcla71.mybible.bible;

import lombok.Data;

@Data
// CREATE TABLE introductions (
public class Introductions {
    // book_number NUMERIC,
    private Integer book_number;
    // introduction TEXT
    private String introduction;
    // )
}
