# Histórias do Usuário — Sistema de Aluguel de Carros

## Cliente

### HU01 — Cadastro no sistema
**História:**  
Como cliente, quero me cadastrar no sistema, para poder solicitar aluguéis de automóveis.

**Critérios de aceitação:**
- O sistema deve permitir o cadastro apenas de usuários ainda não registrados.
- O sistema deve solicitar RG, CPF, nome e endereço.
- O sistema deve permitir informar profissão.
- O sistema deve permitir informar até 3 entidades empregadoras com seus rendimentos.
- O sistema deve validar os campos obrigatórios antes de concluir o cadastro.

### HU02 — Autenticação
**História:**  
Como cliente, quero autenticar-me, para acessar meus pedidos e contratos.

**Critérios de aceitação:**
- O sistema deve solicitar credenciais de acesso.
- O sistema deve permitir acesso apenas com credenciais válidas.
- O sistema deve informar erro em caso de credenciais inválidas.

### HU03 — Registro de pedido de aluguel
**História:**  
Como cliente, quero registrar um pedido de aluguel, para solicitar um automóvel pela internet.

**Critérios de aceitação:**
- O cliente deve estar autenticado.
- O sistema deve permitir selecionar ou informar os dados do automóvel desejado.
- O sistema deve registrar o pedido para análise posterior.
- O sistema deve confirmar o envio do pedido.

### HU04 — Consulta de pedidos
**História:**  
Como cliente, quero consultar meus pedidos, para acompanhar seu andamento.

**Critérios de aceitação:**
- O sistema deve listar apenas os pedidos do cliente autenticado.
- O sistema deve exibir o status de cada pedido.
- O sistema deve permitir visualizar os detalhes de um pedido.

### HU05 — Alteração de pedido
**História:**  
Como cliente, quero modificar um pedido, para corrigir ou atualizar informações.

**Critérios de aceitação:**
- O sistema deve permitir alteração apenas em pedidos permitidos pela regra de negócio.
- O sistema deve validar os novos dados informados.
- O sistema deve salvar as alterações realizadas.

### HU06 — Cancelamento de pedido
**História:**  
Como cliente, quero cancelar um pedido, para desistir da solicitação quando necessário.

**Critérios de aceitação:**
- O sistema deve permitir cancelamento apenas de pedidos elegíveis.
- O sistema deve solicitar confirmação antes de cancelar.
- O sistema deve atualizar o status do pedido para cancelado.

### HU07 — Consulta de contrato
**História:**  
Como cliente, quero consultar um contrato, para verificar as condições do aluguel.

**Critérios de aceitação:**
- O sistema deve permitir visualizar contratos vinculados aos pedidos do cliente.
- O sistema deve exibir as informações do contrato de forma clara.
- O sistema deve informar quando não houver contrato associado.

## Agente Empresa

### HU08 — Consulta de pedidos
**História:**  
Como agente empresa, quero consultar pedidos, para analisar solicitações recebidas.

**Critérios de aceitação:**
- O sistema deve listar os pedidos disponíveis para análise.
- O sistema deve permitir visualizar os dados do cliente e do automóvel.
- O sistema deve exibir o status atual de cada pedido.

### HU09 — Modificação de pedido
**História:**  
Como agente empresa, quero modificar pedidos, para ajustar informações necessárias.

**Critérios de aceitação:**
- O sistema deve permitir alterar dados autorizados pela regra de negócio.
- O sistema deve registrar as alterações efetuadas.
- O sistema deve validar os dados atualizados.

### HU10 — Avaliação de pedido
**História:**  
Como agente empresa, quero avaliar pedidos, para decidir se podem seguir para contratação.

**Critérios de aceitação:**
- O sistema deve exibir os dados necessários para análise.
- O sistema deve permitir registrar parecer sobre o pedido.
- O sistema deve manter o resultado da avaliação associado ao pedido.

### HU11 — Aprovação de pedido
**História:**  
Como agente empresa, quero aprovar pedidos, para permitir o avanço do processo de locação.

**Critérios de aceitação:**
- O sistema deve permitir aprovar pedidos avaliados positivamente.
- O sistema deve atualizar o status do pedido para aprovado.
- O sistema deve preparar o pedido para a etapa contratual.

### HU12 — Rejeição de pedido
**História:**  
Como agente empresa, quero rejeitar pedidos, para impedir a continuidade de solicitações inadequadas.

**Critérios de aceitação:**
- O sistema deve permitir rejeitar pedidos avaliados.
- O sistema deve registrar a rejeição do pedido.
- O sistema deve atualizar o status do pedido para rejeitado.

### HU13 — Registro da propriedade do automóvel
**História:**  
Como agente empresa, quero registrar a propriedade do automóvel, para indicar a vinculação correta no contrato.

**Critérios de aceitação:**
- O sistema deve permitir indicar se o automóvel pertence ao cliente, à empresa ou ao banco.
- O sistema deve salvar a informação de propriedade no contrato ou pedido correspondente.

## Agente Banco

### HU14 — Avaliação financeira
**História:**  
Como agente banco, quero avaliar financeiramente um pedido, para verificar viabilidade de crédito.

**Critérios de aceitação:**
- O sistema deve apresentar profissão, empregadores e rendimentos do contratante.
- O sistema deve permitir registrar parecer financeiro.
- O resultado da análise deve ficar vinculado ao pedido.

### HU15 — Aprovação de crédito
**História:**  
Como agente banco, quero aprovar a concessão de crédito, para viabilizar pedidos compatíveis com financiamento.

**Critérios de aceitação:**
- O sistema deve permitir aprovar pedidos com análise financeira positiva.
- O sistema deve registrar a aprovação do crédito.
- O sistema deve atualizar o status correspondente.

### HU16 — Rejeição de crédito
**História:**  
Como agente banco, quero rejeitar a concessão de crédito, para impedir financiamentos inviáveis.

**Critérios de aceitação:**
- O sistema deve permitir rejeitar pedidos com análise financeira insuficiente.
- O sistema deve registrar a rejeição do crédito.
- O sistema deve atualizar o status correspondente.

### HU17 — Associação de contrato de crédito
**História:**  
Como agente banco, quero associar um contrato de crédito ao aluguel, para vincular o financiamento ao pedido.

**Critérios de aceitação:**
- O sistema deve permitir vincular um contrato de crédito a um pedido aprovado.
- O sistema deve registrar o banco responsável pelo crédito.
- O sistema deve manter a associação disponível para consulta.