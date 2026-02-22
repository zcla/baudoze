package zcla71.mybible.dictionary;

import java.util.Collection;

import lombok.Data;
import zcla71.mybible.common.Info;

@Data
public class DictionaryModule {
	private Collection<Info> info;
	private Collection<Dictionary> dictionary;
	// TODO CREATE TABLE lookup_words (topic TEXT, topic_nf2 TEXT, topic_has NUMERIC NOT NULL DEFAULT 0)
	// TODO CREATE TABLE morphology_indications (indication TEXT, applicable_to TEXT, meaning TEXT)
	// TODO CREATE TABLE morphology_topics (indication TEXT, topic TEXT)
	// TODO CREATE TABLE cognate_strong_numbers (group_id NUMERIC, strong_number TEXT)
	// TODO CREATE TABLE synonymous_strong_numbers (group_id NUMERIC, strong_number TEXT)
	private Collection<Words> words;
	// TODO CREATE TABLE lookup_words (variation TEXT, standard_form TEXT, book_number NUMERIC NOT NULL DEFAULT 0, chapter_number NUMERIC NOT NULL DEFAULT 0, verse_number NUMERIC NOT NULL DEFAULT 0)
	// TODO CREATE TABLE words_processing (type TEXT, input TEXT, output TEXT)
	// TODO CREATE TABLE lookup_references (topic TEXT, book_number NUMERIC, chapter_number NUMERIC, verse_number NUMERIC)
}
