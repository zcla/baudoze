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

INSERT INTO tarefa (nome, notas, id_mae, peso, cumprida) VALUES ('<b>Todo dia</b>', '', null, 9999, false);
INSERT INTO tarefa (nome, notas, id_mae, peso, cumprida) VALUES ('Homilia Diária', 'página <a href="https://padrepauloricardo.org/programas/homilia-diaria" target="_blank">diária</a> <a href="https://padrepauloricardo.org/programas/homilia-dominical" target="_blank">dominical</a> <a href="https://savethevideo.com/" target="_blank">download</a> &bull; youtube <a href="https://www.youtube.com/@padrepauloricardo/videos" target="_blank">vídeos</a> <a href="https://ytmp3s.nu/" target="_blank">download</a> &bull; <a href="https://web.whatsapp.com/" target="_blank">Whatsapp</a>', LAST_INSERT_ID(), 9, false);
