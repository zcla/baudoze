-- drop table if exists
DROP TABLE IF EXISTS auth_user;

-- crete table

CREATE TABLE auth_user (
	id BIGINT NOT NULL AUTO_INCREMENT,
		PRIMARY KEY (id),
    provider VARCHAR(255) NOT NULL,
        -- INDEX idx_auth_user_provider (provider),
    subject VARCHAR(255) NOT NULL,
        -- INDEX idx_auth_user_subject (subject),
        CONSTRAINT UNIQUE (provider, subject),
    nome VARCHAR(255),
    email VARCHAR(255),
    url_imagem VARCHAR(255),
    criado_em TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    ultimo_login_em TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
        ON UPDATE CURRENT_TIMESTAMP(6)
);
