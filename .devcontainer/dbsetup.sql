-- Tarefa
-- DROP TABLE IF EXISTS tarefa;
-- CREATE TABLE tarefa (  
--     id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
--     nome VARCHAR(255),
--     notas VARCHAR(1024),
--     id_mae BIGINT,
--     peso INT,
--     cumprida BOOLEAN,
--     FOREIGN KEY (id_mae) REFERENCES tarefa(id)
-- );

-----===== Bíblia =====-----

-- drop view if exists

DROP VIEW IF EXISTS biblia_lista;
DROP VIEW IF EXISTS livro_lista;

-- drop table if exists

DROP TABLE IF EXISTS versiculo;
    DROP TABLE IF EXISTS capitulo;
        DROP TABLE IF EXISTS livro;
            DROP TABLE IF EXISTS biblia;

-- crete table

CREATE TABLE biblia (
    id BIGINT NOT NULL AUTO_INCREMENT,
        PRIMARY KEY (id),
    codigo VARCHAR(255) NOT NULL, -- necessário para a importação
    nome VARCHAR(255) NOT NULL,
    fonte VARCHAR(255) DEFAULT NULL,
    idioma VARCHAR(255) DEFAULT NULL
);

CREATE TABLE livro (
    id BIGINT NOT NULL AUTO_INCREMENT,
        PRIMARY KEY (id),
    biblia_id BIGINT NOT NULL,
        FOREIGN KEY (biblia_id) REFERENCES biblia(id),
    nome VARCHAR(255) DEFAULT NULL,
    sigla VARCHAR(255) NOT NULL
);

CREATE TABLE capitulo (
    id BIGINT NOT NULL AUTO_INCREMENT,
        PRIMARY KEY (id),
    livro_id BIGINT NOT NULL,
        FOREIGN KEY (livro_id) REFERENCES livro(id),
    numero VARCHAR(255) NOT NULL
);

CREATE TABLE versiculo (
    id BIGINT NOT NULL AUTO_INCREMENT,
        PRIMARY KEY (id),
    capitulo_id BIGINT NOT NULL,
        FOREIGN KEY (capitulo_id) REFERENCES capitulo(id),
    numero VARCHAR(255) NOT NULL,
    texto TEXT NOT NULL
);

-- create view

CREATE VIEW biblia_lista
AS
SELECT
    b.id,
    b.nome,
    b.idioma,
	(
        SELECT COUNT(l.id)
	    FROM livro l
	    WHERE l.biblia_id = b.id
    ) livros
FROM biblia b
ORDER BY b.nome;

CREATE VIEW livro_lista
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
ORDER BY l.id;
