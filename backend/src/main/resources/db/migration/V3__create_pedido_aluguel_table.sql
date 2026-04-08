CREATE TABLE pedido_aluguel (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    automovel_id BIGINT NOT NULL,
    status VARCHAR(30) NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    observacao VARCHAR(500),
    valor_entrada NUMERIC(12,2),
    prazo_meses INTEGER,
    renda_declarada NUMERIC(12,2),
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    CONSTRAINT fk_pedido_automovel FOREIGN KEY (automovel_id) REFERENCES automovel(id)
);

CREATE INDEX idx_pedido_cliente_id ON pedido_aluguel(cliente_id);
CREATE INDEX idx_pedido_automovel_id ON pedido_aluguel(automovel_id);
CREATE INDEX idx_pedido_status ON pedido_aluguel(status);
