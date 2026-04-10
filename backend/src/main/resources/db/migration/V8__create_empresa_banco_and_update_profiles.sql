CREATE TABLE empresa (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL
);

CREATE TABLE banco (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    codigo VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO empresa (nome) VALUES ('Vrumm Operadora') ON CONFLICT DO NOTHING;
INSERT INTO banco (nome, codigo) VALUES ('Banco Vrumm', 'VRM001') ON CONFLICT DO NOTHING;

ALTER TABLE cliente
    ADD COLUMN empresa_id BIGINT,
    ADD COLUMN banco_id BIGINT;

UPDATE cliente
SET perfil = 'EMPRESA',
    empresa_id = (SELECT MIN(id) FROM empresa)
WHERE perfil = 'ADMIN';

ALTER TABLE cliente
    ADD CONSTRAINT fk_cliente_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id),
    ADD CONSTRAINT fk_cliente_banco FOREIGN KEY (banco_id) REFERENCES banco(id);
