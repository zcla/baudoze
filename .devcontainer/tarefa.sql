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

CREATE INDEX idx_tarefa_mae_ordem ON tarefa(id_mae, ordem);

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
	_.indent,
	COALESCE(tm.qtd_filhos, 0) AS qtd_filhos,
	(ord.pos = 1) AS primeiro_filho,
	(ord.pos = ord.total) AS ultimo_filho
FROM temp _
JOIN tarefa t
	ON t.id = _.id
LEFT JOIN (
	SELECT id_mae, COUNT(*) AS qtd_filhos
	FROM tarefa
	GROUP BY id_mae
) tm ON tm.id_mae = t.id
LEFT JOIN (
	SELECT
		id,
		id_mae,
		ROW_NUMBER() OVER (PARTITION BY id_mae ORDER BY ordem) AS pos,
		COUNT(*) OVER (PARTITION BY id_mae) AS total
	FROM tarefa
) ord ON ord.id = t.id
ORDER BY _.path, _.id;
