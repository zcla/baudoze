-- drop table if exists
DROP TABLE IF EXISTS tarefa;

-- crete table

CREATE TABLE tarefa (  
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255),
    notas VARCHAR(1024),
    id_mae BIGINT,
    peso INT,
    cumprida BOOLEAN,
    FOREIGN KEY (id_mae) REFERENCES tarefa(id)
);
