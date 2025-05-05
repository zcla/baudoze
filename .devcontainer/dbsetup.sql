DROP TABLE IF EXISTS tarefa;

CREATE TABLE tarefa (  
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255),
    notas VARCHAR(1024),
    id_mae INT,
    peso INT,
    cumprida BOOLEAN,
    FOREIGN KEY (id_mae) REFERENCES tarefa(id)
);
