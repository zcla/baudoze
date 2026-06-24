--------- tarefa ---------

CREATE TABLE IF NOT EXISTS tarefa (
	id INTEGER PRIMARY KEY,
	titulo TEXT NOT NULL,
	descricao TEXT,
	id_mae INTEGER,
	ordem INTEGER NOT NULL,
	cumprida BOOLEAN NOT NULL DEFAULT FALSE,
	FOREIGN KEY (id_mae) REFERENCES tarefa(id)
);

CREATE INDEX IF NOT EXISTS idx_tarefa_mae_ordem ON tarefa(id_mae, ordem);

CREATE VIEW IF NOT EXISTS tarefa_lista
AS
WITH RECURSIVE temp AS (
	-- Tarefas "root"
	SELECT
		t.id,
		0 AS indent,
		PRINTF('%010d', t.ordem) AS path
	FROM tarefa t
	WHERE t.id_mae IS NULL
	UNION ALL
	-- Tarefas "filhas"
	SELECT
		tf.id,
		tm.indent + 1 AS indent,
		tm.path || '.' || PRINTF('%010d', tf.ordem) AS path
	FROM tarefa tf
	JOIN temp tm ON tf.id_mae = tm.id
)
SELECT
	t.id,
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
