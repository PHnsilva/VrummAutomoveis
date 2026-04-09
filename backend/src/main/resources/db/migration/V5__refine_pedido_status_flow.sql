ALTER TABLE pedido_aluguel
    ADD COLUMN IF NOT EXISTS valor_pago NUMERIC(12,2);

UPDATE pedido_aluguel
SET status = 'AGUARDANDO_PAGAMENTO'
WHERE status = 'APROVADO';

UPDATE pedido_aluguel
SET status = 'RECUSADO'
WHERE status = 'REPROVADO';
