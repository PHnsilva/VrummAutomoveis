ALTER TABLE pedido_aluguel
    ADD COLUMN banco_avaliador_id BIGINT,
    ADD COLUMN parecer_financeiro_favoravel BOOLEAN,
    ADD COLUMN parecer_financeiro_observacao VARCHAR(500),
    ADD COLUMN data_parecer_financeiro TIMESTAMP;

ALTER TABLE pedido_aluguel
    ADD CONSTRAINT fk_pedido_banco_avaliador FOREIGN KEY (banco_avaliador_id) REFERENCES banco(id);
