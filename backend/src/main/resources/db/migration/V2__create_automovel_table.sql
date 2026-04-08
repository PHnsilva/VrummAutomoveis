CREATE TABLE automovel (
    id BIGSERIAL PRIMARY KEY,
    marca VARCHAR(80) NOT NULL,
    modelo VARCHAR(120) NOT NULL,
    ano_modelo INTEGER NOT NULL,
    cor VARCHAR(40),
    disponivel BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO automovel (marca, modelo, ano_modelo, cor, disponivel) VALUES
    ('Volkswagen', 'Polo Track', 2024, 'Branco', TRUE),
    ('Fiat', 'Argo Drive', 2023, 'Prata', TRUE),
    ('Chevrolet', 'Onix LT', 2024, 'Preto', TRUE);
