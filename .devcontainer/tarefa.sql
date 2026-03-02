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
WITH RECURSIVE temp AS (
	-- Tarefas "root"
	SELECT
		t.id,
		t.auth_user_id, -- Performance: para que o MySQL possa aplicar um WHERE externo antes de executar a view, evitando a montagem da tabela inteira
		0 AS indent,
		CAST(LPAD(t.ordem, 10, '0') AS CHAR(255)) AS path
	FROM tarefa t
	WHERE t.id_mae IS NULL
	UNION ALL
	-- Tarefas "filhas"
	SELECT
		tf.id,
		tf.auth_user_id,
		tm.indent + 1 AS indent,
		CONCAT(tm.path, '.', LPAD(tf.ordem, 10, '0')) AS path
	FROM tarefa tf
	JOIN temp tm ON tf.id_mae = tm.id
)
SELECT
	t.id,
	t.auth_user_id,
	t.titulo,
	t.descricao,
	t.cumprida,
	_.indent
FROM temp _
JOIN tarefa t
	ON t.id = _.id
ORDER BY _.path, _.id;

-- TODO EXCLUIR TUDO DAQUI PRA BAIXO

DELETE FROM tarefa;
INSERT INTO tarefa (auth_user_id, titulo, descricao, id_mae, ordem, cumprida) VALUES (1, "Tarefa 1", "Descrição", null, 1, FALSE);
INSERT INTO tarefa (auth_user_id, titulo, descricao, id_mae, ordem, cumprida) VALUES (1, "Tarefa 2", "Descrição", null, 2, FALSE);
INSERT INTO tarefa (auth_user_id, titulo, descricao, id_mae, ordem, cumprida) VALUES (1, "Tarefa 1.1", "Descrição", 1, 3, FALSE);
INSERT INTO tarefa (auth_user_id, titulo, descricao, id_mae, ordem, cumprida) VALUES (1, "Tarefa 1.2", "Descrição", 1, 4, FALSE);
