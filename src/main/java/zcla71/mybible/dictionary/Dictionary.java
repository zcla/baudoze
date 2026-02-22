package zcla71.mybible.dictionary;

import lombok.Data;

// CREATE TABLE dictionary (
@Data
public class Dictionary {
    // topic TEXT,
    private String topic;
    // definition TEXT NOT NULL,
    private String definition;
    // short_definition TEXT,
    private String short_definition;
    // lexeme TEXT,
    private String lexeme;
    // transliteration TEXT,
    private String transliteration;
    // pronunciation TEXT,
    private String pronunciation;
    // )
}
