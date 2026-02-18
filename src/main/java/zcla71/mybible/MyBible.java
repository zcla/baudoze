package zcla71.mybible;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import lombok.Data;
import zcla71.mybible.bible.BibleModule;
import zcla71.mybible.bible.Books;
import zcla71.mybible.bible.BooksAll;
import zcla71.mybible.bible.Introductions;
import zcla71.mybible.bible.Stories;
import zcla71.mybible.bible.Verses;
import zcla71.mybible.commentaries.Commentaries;
import zcla71.mybible.commentaries.CommentariesModule;
import zcla71.mybible.common.ContentFragments;
import zcla71.mybible.common.Info;
import zcla71.sqlite.SQLiteDb;

@Data
// Documentação: https://docs.google.com/document/d/12rf4Pqy13qhnAW31uKkaWNTBDTtRbNW0s7cM0vcimlA/
public class MyBible {
	public static MyBible fromZipFile(URI uri) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, IOException, SQLException {
		return new MyBible(uri);
	}

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

	private MyBible(URI uri) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, SQLException {
		url = uri.toString();
		downloadedFileName = getDownloadFileName(uri);
		downloadTimestamp = LocalDateTime.now();

		Collection<File> apagarAoFinal = new ArrayList<>();

		try {
			// Baixa o arquivo zip
			File tempDir = getTempDirectory();
			File zippedFile = File.createTempFile(removeExtension(downloadedFileName) + ".", ".zip", tempDir);
			apagarAoFinal.add(zippedFile);
			Files.createDirectories(tempDir.toPath());
			try (
				BufferedInputStream in = new BufferedInputStream(uri.toURL().openStream());
				FileOutputStream fos = new FileOutputStream(zippedFile);
			) {
				byte dataBuffer[] = new byte[1024];
				int bytesRead;
				while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
					fos.write(dataBuffer, 0, bytesRead);
				}
			}

			// Extrai os arquivos
			Collection<File> unzippedFiles = new ArrayList<>();
			Path targetDir = getTempDirectory().toPath().normalize();
			try (
				ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zippedFile))
			) {
				for (ZipEntry ze; (ze = zipIn.getNextEntry()) != null; ) {
					Path resolvedPath = targetDir.resolve(ze.getName()).normalize();
					if (!resolvedPath.startsWith(targetDir)) {
						// see: https://snyk.io/research/zip-slip-vulnerability
						throw new RuntimeException("Entry with an illegal path: " + ze.getName());
					}
					if (ze.isDirectory()) {
						Files.createDirectories(resolvedPath);
					} else {
						Files.createDirectories(resolvedPath.getParent());
						Files.copy(zipIn, resolvedPath, StandardCopyOption.REPLACE_EXISTING);
					}
					unzippedFiles.add(resolvedPath.toFile());
				}
			}
			apagarAoFinal.addAll(unzippedFiles);

			// Extrai os dados
			String baseFileName = getTempDirectory().toPath().normalize() + "/" + removeExtension(downloadedFileName);
			String bibleFileName = baseFileName + ".SQLite3";
			String commentariesFileName = baseFileName + ".commentaries.SQLite3";
			for (File unzippedFile : unzippedFiles) {
				String tipo = null;
				if ((tipo == null) && unzippedFile.getPath().equalsIgnoreCase(bibleFileName)) {
					tipo = "bible";
					sqlBible(unzippedFile.getPath());
				}
				if ((tipo == null) && unzippedFile.getPath().equalsIgnoreCase(commentariesFileName)) {
					tipo = "commentaries";
					sqlCommentaries(unzippedFile.getPath());
				}
				if (tipo == null) {
					throw new RuntimeException("Arquivo desconhecido: " + unzippedFile.getPath());
				}
			}

		} finally {
			for (File arq : apagarAoFinal) {
				arq.delete();
			}
		}	
	}
	private String getDownloadFileName(URI uri) {
		String result = null;
		try {
			URL url = uri.toURL();
			URLConnection con = url.openConnection();
			String fieldValue = con.getHeaderField("Content-Disposition");
			if (fieldValue != null && fieldValue.contains("filename=\"")) {
				result = fieldValue.substring(fieldValue.indexOf("filename=\"") + 10, fieldValue.length() - 1);
			}
		} catch (Exception e) {
			// ignora
		}

		// Os headers não colaboram; tenta pela url.
		if (result == null) {
			result = uri.toString();
			result = result.substring(result.lastIndexOf("/") + 1);
		}

		return result;
	}

	private File getTempDirectory() {
		return new File("./temp");
	}

	private String removeExtension(String fileName) {
		if (fileName == null) {
			return null;
		}
		int pos = fileName.lastIndexOf(".");
		if (pos == -1) {
			return fileName;
		}
		return fileName.substring(0, pos);
	}

	private void sqlBible(String nomeArq) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		this.bible = new BibleModule();
		SQLiteDb sqLiteDb = new SQLiteDb(nomeArq);
		try (
			Connection conn = sqLiteDb.getConnection();
		) {
			Collection<String> tableNames = sqLiteDb.getTableNames(conn);
			for (String tableName : tableNames) {
				switch (tableName) {
					case "info":
						List<Info> info = sqLiteDb.getData(conn, tableName, Info.class);
						this.bible.setInfo(info);
						break;

					case "books":
						List<Books> books = sqLiteDb.getData(conn, tableName, Books.class);
						this.bible.setBooks(books);
						break;

					case "books_all":
						List<BooksAll> booksAll = sqLiteDb.getData(conn, tableName, BooksAll.class);
						this.bible.setBooksAll(booksAll);
						break;

					case "verses":
						List<Verses> verses = sqLiteDb.getData(conn, tableName, Verses.class);
						this.bible.setVerses(verses);
						break;

					case "introductions":
						List<Introductions> introductions = sqLiteDb.getData(conn, tableName, Introductions.class);
						this.bible.setIntroductions(introductions);
						break;

					case "stories":
						List<Stories> stories = sqLiteDb.getData(conn, tableName, Stories.class);
						this.bible.setStories(stories);
						break;

					// Desconhecidos / não documentados
					case "android_metadata":
						// Aparecem em alguns arquivos; ignora
						break;

					default:
						throw new RuntimeException("Tabela desconhecida: " + tableName);
				}
			}
		}
	}

	private void sqlCommentaries(String nomeArq) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
		this.commentaries = new CommentariesModule();
		SQLiteDb sqLiteDb = new SQLiteDb(nomeArq);
		try (
			Connection conn = sqLiteDb.getConnection();
		) {
			Collection<String> tableNames = sqLiteDb.getTableNames(conn);
			for (String tableName : tableNames) {
				switch (tableName) {
					case "info":
						Collection<Info> info = sqLiteDb.getData(conn, tableName, Info.class);
						this.commentaries.setInfo(info);
						break;

					case "commentaries":
						Collection<Commentaries> commentaries = sqLiteDb.getData(conn, tableName, Commentaries.class);
						this.commentaries.setCommentaries(commentaries);
						break;

					case "content_fragments":
						Collection<ContentFragments> contentFragments = sqLiteDb.getData(conn, tableName, ContentFragments.class);
						this.commentaries.setContentFragments(contentFragments);
						break;

					// Desconhecidos / não documentados
					case "android_metadata":
					case "introductions": // TODO Na Bíblia onde ele aparece (BPT'09D), ele está vazio; talvez fosse legal fazer um filtro só de tabelas que têm dados.
						// Aparecem em alguns arquivos; ignora
						break;

					default:
						throw new RuntimeException("Tabela desconhecida: " + tableName);
				}
			}
		}
	}
}
