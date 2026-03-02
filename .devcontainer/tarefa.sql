-- drop view if exists
DROP VIEW IF EXISTS tarefa_lista;

-- drop table if exists
DROP TABLE IF EXISTS tarefa;

-- crete table

CREATE TABLE tarefa (  
	id BIGINT NOT NULL AUTO_INCREMENT,
		PRIMARY KEY (id),
	auth_user_id BIGINT NOT NULL,
		FOREIGN KEY (auth_user_id) REFERENCES auth_user(id),
	titulo VARCHAR(255),
	descricao TEXT,
	id_mae BIGINT,
		FOREIGN KEY (id_mae) REFERENCES tarefa(id),
	ordem BIGINT,
	cumprida BOOLEAN
);

-- create view

CREATE VIEW tarefa_lista
AS
SELECT
	t.id,
	t.titulo,
	t.descricao,
	t.cumprida
FROM tarefa t
ORDER BY t.ordem;

-- TODO EXCLUIR TUDO DAQUI PRA BAIXO

DELETE FROM tarefa;
INSERT INTO tarefa (auth_user_id, titulo, descricao, id_mae, ordem, cumprida) VALUES (1, "Tarefa 1", "Descrição", null, 1, FALSE);
