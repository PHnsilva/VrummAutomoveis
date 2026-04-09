UPDATE pedido_aluguel
SET status = 'AGUARDANDO_PAGAMENTO'
WHERE status = 'EM_ANALISE';
