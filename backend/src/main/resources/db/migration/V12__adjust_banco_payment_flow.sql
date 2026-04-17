UPDATE pedido_aluguel
SET status = 'AGUARDANDO_ANALISE_FINANCEIRA'
WHERE status = 'AGUARDANDO_PAGAMENTO'
  AND parecer_financeiro_favoravel IS NULL
  AND valor_pago IS NULL;
