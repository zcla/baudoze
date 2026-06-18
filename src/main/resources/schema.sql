--------- auth_user ---------

CREATE TABLE IF NOT EXISTS auth_user (
	id INTEGER PRIMARY KEY,
    provider TEXT NOT NULL,
    subject TEXT NOT NULL,
    nome TEXT,
    email TEXT,
    url_imagem TEXT,
    imagem BLOB,
    imagem_content_type TEXT,
    criado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ultimo_login_em DATETIME DEFAULT CURRENT_TIMESTAMP,
	UNIQUE (provider, subject)
);

--------- biblia ---------

CREATE TABLE IF NOT EXISTS biblia (
	id INTEGER PRIMARY KEY,
	codigo TEXT NOT NULL, -- necessário para a importação
	nome TEXT NOT NULL,
	fonte TEXT,
	idioma TEXT
);

CREATE TABLE IF NOT EXISTS livro (
	id INTEGER PRIMARY KEY,
	biblia_id INTEGER NOT NULL,
	nome TEXT,
	sigla TEXT NOT NULL,
	FOREIGN KEY (biblia_id) REFERENCES biblia(id)
);

CREATE TABLE IF NOT EXISTS capitulo (
	id INTEGER PRIMARY KEY,
	livro_id INTEGER NOT NULL,
	numero TEXT NOT NULL,
	FOREIGN KEY (livro_id) REFERENCES livro(id)
);

CREATE TABLE IF NOT EXISTS versiculo (
	id INTEGER PRIMARY KEY,
	capitulo_id INTEGER NOT NULL,
	numero TEXT NOT NULL,
	texto TEXT NOT NULL,
	FOREIGN KEY (capitulo_id) REFERENCES capitulo(id)
);

-- create view

CREATE VIEW IF NOT EXISTS biblia_lista
AS
SELECT
	b.id,
	b.nome,
	b.idioma,
	b.fonte,
	(
		SELECT COUNT(l.id)
		FROM livro l
		WHERE l.biblia_id = b.id
	) livros
FROM biblia b
ORDER BY b.nome; -- TODO Não deveria ter order by aqui; isso é da aplicação.

CREATE VIEW IF NOT EXISTS livro_lista
AS
SELECT
	l.id,
	l.biblia_id,
	l.sigla,
	l.nome,
	(
		SELECT COUNT(c.id)
		FROM capitulo c
		WHERE c.livro_id = l.id
	) capitulos
FROM livro l
ORDER BY l.id; -- TODO Não deveria ter order by aqui; isso é da aplicação.

CREATE VIEW IF NOT EXISTS capitulo_lista
AS
SELECT
	c.id,
	l.biblia_id,
	c.livro_id,
	c.numero,
	(
		SELECT COUNT(v.id)
		FROM versiculo v
		WHERE v.capitulo_id = c.id
	) versiculos
FROM capitulo c
JOIN livro l
	ON l.id = c.livro_id
ORDER BY c.id; -- TODO Não deveria ter order by aqui; isso é da aplicação.

CREATE VIEW IF NOT EXISTS versiculo_lista
AS
SELECT
	v.id,
	l.biblia_id,
	c.livro_id,
	v.capitulo_id,
	v.numero,
	v.texto
FROM versiculo v
JOIN capitulo c
	ON c.id = v.capitulo_id
JOIN livro l
	ON l.id = c.livro_id
ORDER BY v.id; -- TODO Não deveria ter order by aqui; isso é da aplicação.
