ALTER TABLE cliente
    ADD COLUMN IF NOT EXISTS endereco_logradouro VARCHAR(150);

UPDATE cliente
SET endereco_logradouro = 'Endereco nao informado'
WHERE endereco_logradouro IS NULL
   OR btrim(endereco_logradouro) = '';

ALTER TABLE cliente
    ALTER COLUMN endereco_logradouro SET NOT NULL;
