package zcla71.mybible;

import java.time.LocalDateTime;

import lombok.Data;
import zcla71.mybible.bible.BibleModule;
import zcla71.mybible.commentaries.CommentariesModule;

@Data
// Documentação: https://docs.google.com/document/d/12rf4Pqy13qhnAW31uKkaWNTBDTtRbNW0s7cM0vcimlA/
public class MyBible {
	// Meus dados
	private String url;
	private String downloadedFileName;
	private LocalDateTime downloadTimestamp;
	
	// Módulos
	private BibleModule bible = null;
	// TODO Dictionary Module
	// TODO Subheadings Module
	// TODO Cross References Module
	// Commentaries Module
	private CommentariesModule commentaries = null;
	// TODO Reading Plan Module
	// TODO Devotions Module
}
