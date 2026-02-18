package zcla71.mybible.commentaries;

import java.util.Collection;

import lombok.Data;
import zcla71.mybible.common.ContentFragments;
import zcla71.mybible.common.Info;

@Data
public class CommentariesModule {
	private Collection<Info> info;
	private Collection<ContentFragments> contentFragments;
	private Collection<Commentaries> commentaries;
	// TODO CREATE TABLE books (book_number NUMERIC, short_name TEXT)
}
