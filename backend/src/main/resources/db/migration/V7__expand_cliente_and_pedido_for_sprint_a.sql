ALTER TABLE cliente ADD COLUMN IF NOT EXISTS endereco_cep VARCHAR(9);
ALTER TABLE cliente ADD COLUMN IF NOT EXISTS endereco_logradouro VARCHAR(150);
ALTER TABLE cliente ADD COLUMN IF NOT EXISTS endereco_numero VARCHAR(20);
ALTER TABLE cliente ADD COLUMN IF NOT EXISTS endereco_complemento VARCHAR(80);
ALTER TABLE cliente ADD COLUMN IF NOT EXISTS endereco_bairro VARCHAR(80);
ALTER TABLE cliente ADD COLUMN IF NOT EXISTS endereco_cidade VARCHAR(80);
ALTER TABLE cliente ADD COLUMN IF NOT EXISTS endereco_uf VARCHAR(2);

CREATE TABLE IF NOT EXISTS cliente_rendimento (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL REFERENCES cliente(id) ON DELETE CASCADE,
    ordem INTEGER NOT NULL,
    entidade_empregadora VARCHAR(120) NOT NULL,
    valor_mensal NUMERIC(12,2) NOT NULL,
    CONSTRAINT uk_cliente_rendimento_ordem UNIQUE (cliente_id, ordem)
);

ALTER TABLE automovel ADD COLUMN IF NOT EXISTS matricula VARCHAR(20);
ALTER TABLE automovel ADD COLUMN IF NOT EXISTS placa VARCHAR(10);

UPDATE automovel
SET matricula = COALESCE(matricula, 'AUTO-' || id::text),
    placa = COALESCE(placa, 'VRM' || LPAD(id::text, 4, '0'))
WHERE matricula IS NULL OR placa IS NULL;

ALTER TABLE automovel ALTER COLUMN matricula SET NOT NULL;
ALTER TABLE automovel ALTER COLUMN placa SET NOT NULL;
