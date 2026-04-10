CREATE TABLE contrato (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL UNIQUE REFERENCES pedido_aluguel(id),
    tipo_contrato VARCHAR(40) NOT NULL,
    tipo_proprietario VARCHAR(20) NOT NULL,
    observacao VARCHAR(500),
    data_criacao TIMESTAMP NOT NULL
);

CREATE TABLE contrato_credito (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL UNIQUE REFERENCES pedido_aluguel(id),
    banco_id BIGINT NOT NULL REFERENCES banco(id),
    numero_referencia VARCHAR(50) NOT NULL,
    valor_credito NUMERIC(12,2) NOT NULL,
    observacao VARCHAR(500),
    data_criacao TIMESTAMP NOT NULL
);
