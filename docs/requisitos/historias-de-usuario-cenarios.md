# Histórias do Usuário — Sistema de Aluguel de Carros (Detalhadas)

Este documento apresenta as histórias do usuário do **Sistema de Aluguel de Carros** em formato detalhado, com critérios de aceitação descritos como cenários.

---

## HU01 — Cadastro de cliente

**História:**  
Como **cliente**, quero **me cadastrar no sistema**, para **poder solicitar aluguéis de automóveis**.

**Critérios de Aceitação:**

### Cenário 1: Cadastro realizado com sucesso
**Dado** que o cliente ainda não possui cadastro no sistema  
**E** acessou a funcionalidade de cadastro  
**Quando** informar corretamente seus dados obrigatórios  
**Então** o sistema deve registrar o cliente com sucesso  
**E** disponibilizar seu acesso ao sistema

### Cenário 2: Dados obrigatórios ausentes
**Dado** que o cliente acessou a funcionalidade de cadastro  
**Quando** deixar de preencher dados obrigatórios  
**Então** o sistema deve informar os campos inválidos ou ausentes  
**E** não concluir o cadastro

### Cenário 3: Cadastro com informações profissionais completas
**Dado** que o cliente está preenchendo seu cadastro  
**Quando** informar profissão, entidades empregadoras e rendimentos válidos  
**Então** o sistema deve armazenar essas informações corretamente

### Cenário 4: Limite de entidades empregadoras excedido
**Dado** que o cliente está preenchendo o cadastro  
**Quando** tentar informar mais de 3 entidades empregadoras  
**Então** o sistema deve impedir a operação  
**E** informar o limite máximo permitido

---

## HU02 — Autenticação no sistema

**História:**  
Como **cliente**, quero **me autenticar no sistema**, para **acessar meus pedidos e contratos**.

**Critérios de Aceitação:**

### Cenário 1: Autenticação realizada com sucesso
**Dado** que o cliente possui cadastro válido  
**Quando** informar corretamente suas credenciais de acesso  
**Então** o sistema deve autenticar o cliente  
**E** liberar acesso às funcionalidades disponíveis para seu perfil

### Cenário 2: Credenciais inválidas
**Dado** que o cliente acessou a tela de login  
**Quando** informar credenciais inválidas  
**Então** o sistema deve negar o acesso  
**E** informar falha na autenticação

---

## HU03 — Registro de pedido de aluguel

**História:**  
Como **cliente**, quero **registrar um pedido de aluguel**, para **solicitar um automóvel pela internet**.

**Critérios de Aceitação:**

### Cenário 1: Pedido registrado com sucesso
**Dado** que o cliente está autenticado no sistema  
**E** existem automóveis disponíveis para aluguel  
**Quando** preencher corretamente os dados do pedido  
**Então** o sistema deve registrar o pedido  
**E** marcar o pedido como pendente de análise

### Cenário 2: Dados do pedido incompletos
**Dado** que o cliente está autenticado no sistema  
**Quando** tentar registrar o pedido com dados incompletos ou inválidos  
**Então** o sistema deve informar os erros encontrados  
**E** não concluir o registro do pedido

---

## HU04 — Consulta de pedidos

**História:**  
Como **cliente**, quero **consultar meus pedidos**, para **acompanhar seu andamento**.

**Critérios de Aceitação:**

### Cenário 1: Consulta de pedidos existentes
**Dado** que o cliente está autenticado no sistema  
**E** possui pedidos cadastrados  
**Quando** acessar a área de pedidos  
**Então** o sistema deve listar seus pedidos  
**E** exibir o status atual de cada um

### Cenário 2: Cliente sem pedidos cadastrados
**Dado** que o cliente está autenticado no sistema  
**E** não possui pedidos cadastrados  
**Quando** acessar a área de pedidos  
**Então** o sistema deve informar que não há pedidos disponíveis

---

## HU05 — Modificação de pedido pelo cliente

**História:**  
Como **cliente**, quero **modificar um pedido**, para **corrigir ou atualizar informações**.

**Critérios de Aceitação:**

### Cenário 1: Modificação realizada com sucesso
**Dado** que o cliente está autenticado  
**E** possui um pedido em estado que permite alteração  
**Quando** modificar corretamente os dados do pedido  
**Então** o sistema deve salvar as alterações  
**E** confirmar a atualização do pedido

### Cenário 2: Pedido não pode mais ser alterado
**Dado** que o cliente está autenticado  
**E** o pedido está em estado que não permite modificação  
**Quando** tentar alterar o pedido  
**Então** o sistema deve impedir a alteração  
**E** informar que a modificação não é permitida

---

## HU06 — Cancelamento de pedido

**História:**  
Como **cliente**, quero **cancelar um pedido**, para **desistir da solicitação quando necessário**.

**Critérios de Aceitação:**

### Cenário 1: Cancelamento realizado com sucesso
**Dado** que o cliente está autenticado  
**E** possui um pedido apto para cancelamento  
**Quando** confirmar a operação de cancelamento  
**Então** o sistema deve alterar o status do pedido para cancelado  
**E** informar a conclusão da operação

### Cenário 2: Cancelamento não permitido
**Dado** que o cliente está autenticado  
**E** o pedido não está em estado que permita cancelamento  
**Quando** tentar cancelá-lo  
**Então** o sistema deve impedir o cancelamento  
**E** informar o motivo da restrição

---

## HU07 — Consulta de contrato

**História:**  
Como **cliente**, quero **consultar um contrato**, para **verificar as condições do aluguel**.

**Critérios de Aceitação:**

### Cenário 1: Contrato disponível para consulta
**Dado** que o cliente está autenticado  
**E** existe contrato associado ao seu pedido  
**Quando** acessar a área de contratos  
**Então** o sistema deve exibir os dados do contrato correspondente

### Cenário 2: Contrato ainda indisponível
**Dado** que o cliente está autenticado  
**E** ainda não existe contrato associado ao pedido  
**Quando** tentar consultar o contrato  
**Então** o sistema deve informar que não há contrato disponível

---

## HU08 — Consulta de pedidos pelo agente empresa

**História:**  
Como **agente empresa**, quero **consultar pedidos**, para **analisar solicitações recebidas**.

**Critérios de Aceitação:**

### Cenário 1: Consulta de pedidos com sucesso
**Dado** que o agente empresa está autenticado  
**Quando** acessar a lista de pedidos  
**Então** o sistema deve exibir os pedidos disponíveis para análise  
**E** apresentar seus dados principais

### Cenário 2: Não há pedidos para análise
**Dado** que o agente empresa está autenticado  
**Quando** acessar a lista de pedidos sem registros disponíveis  
**Então** o sistema deve informar que não há pedidos pendentes

---

## HU09 — Modificação de pedido pelo agente empresa

**História:**  
Como **agente empresa**, quero **modificar pedidos**, para **ajustar informações necessárias**.

**Critérios de Aceitação:**

### Cenário 1: Alteração realizada pelo agente empresa
**Dado** que o agente empresa está autenticado  
**E** o pedido permite modificação  
**Quando** alterar os dados autorizados do pedido  
**Então** o sistema deve registrar as alterações  
**E** atualizar as informações do pedido

### Cenário 2: Alteração inválida
**Dado** que o agente empresa está autenticado  
**Quando** informar dados inválidos ao modificar o pedido  
**Então** o sistema deve rejeitar a alteração  
**E** informar os erros encontrados

---

## HU10 — Avaliação de pedido pelo agente empresa

**História:**  
Como **agente empresa**, quero **avaliar pedidos**, para **decidir se podem seguir para contratação**.

**Critérios de Aceitação:**

### Cenário 1: Avaliação registrada com sucesso
**Dado** que o agente empresa está autenticado  
**E** existe pedido pendente de avaliação  
**Quando** analisar o pedido e registrar seu parecer  
**Então** o sistema deve associar a avaliação ao pedido

### Cenário 2: Pedido indisponível para avaliação
**Dado** que o agente empresa está autenticado  
**Quando** tentar avaliar um pedido inexistente ou indisponível  
**Então** o sistema deve impedir a operação  
**E** informar a inconsistência

---

## HU11 — Aprovação de pedido pelo agente empresa

**História:**  
Como **agente empresa**, quero **aprovar pedidos**, para **permitir o avanço do processo de locação**.

**Critérios de Aceitação:**

### Cenário 1: Aprovação realizada com sucesso
**Dado** que o agente empresa está autenticado  
**E** o pedido foi avaliado positivamente  
**Quando** aprovar o pedido  
**Então** o sistema deve atualizar seu status para aprovado  
**E** permitir avanço para a etapa contratual

### Cenário 2: Aprovação sem avaliação prévia
**Dado** que o agente empresa está autenticado  
**E** o pedido não foi adequadamente avaliado  
**Quando** tentar aprová-lo  
**Então** o sistema deve impedir a aprovação  
**E** informar que a avaliação é necessária

---

## HU12 — Rejeição de pedido pelo agente empresa

**História:**  
Como **agente empresa**, quero **rejeitar pedidos**, para **impedir a continuidade de solicitações inadequadas**.

**Critérios de Aceitação:**

### Cenário 1: Rejeição realizada com sucesso
**Dado** que o agente empresa está autenticado  
**E** o pedido foi analisado  
**Quando** rejeitar o pedido informando justificativa  
**Então** o sistema deve registrar a rejeição  
**E** atualizar o status do pedido para rejeitado

### Cenário 2: Rejeição sem justificativa
**Dado** que o agente empresa está autenticado  
**Quando** tentar rejeitar um pedido sem informar justificativa, caso exigida  
**Então** o sistema deve impedir a operação  
**E** solicitar a justificativa correspondente

---

## HU13 — Registro da propriedade do automóvel

**História:**  
Como **agente empresa**, quero **registrar a propriedade do automóvel**, para **indicar a vinculação correta no contrato**.

**Critérios de Aceitação:**

### Cenário 1: Registro de propriedade com sucesso
**Dado** que o agente empresa está autenticado  
**E** existe automóvel vinculado ao pedido ou contrato  
**Quando** informar corretamente a propriedade do automóvel  
**Então** o sistema deve registrar se o automóvel pertence ao cliente, à empresa ou ao banco

### Cenário 2: Informação de propriedade inválida
**Dado** que o agente empresa está autenticado  
**Quando** tentar registrar uma propriedade inválida  
**Então** o sistema deve impedir o salvamento  
**E** informar os valores aceitos

---

## HU14 — Avaliação financeira pelo agente banco

**História:**  
Como **agente banco**, quero **avaliar financeiramente um pedido**, para **verificar viabilidade de crédito**.

**Critérios de Aceitação:**

### Cenário 1: Avaliação financeira registrada com sucesso
**Dado** que o agente banco está autenticado  
**E** existe pedido pendente de análise financeira  
**Quando** analisar os dados profissionais e de rendimento do contratante  
**Então** o sistema deve permitir registrar o parecer financeiro  
**E** associá-lo ao pedido

### Cenário 2: Dados financeiros insuficientes
**Dado** que o agente banco está autenticado  
**E** o pedido possui dados financeiros incompletos  
**Quando** tentar realizar a análise  
**Então** o sistema deve informar que os dados são insuficientes  
**E** impedir a conclusão da avaliação financeira

---

## HU15 — Aprovação de crédito

**História:**  
Como **agente banco**, quero **aprovar a concessão de crédito**, para **viabilizar pedidos compatíveis com financiamento**.

**Critérios de Aceitação:**

### Cenário 1: Crédito aprovado com sucesso
**Dado** que o agente banco está autenticado  
**E** o pedido possui análise financeira favorável  
**Quando** aprovar a concessão de crédito  
**Então** o sistema deve registrar a aprovação  
**E** atualizar a situação do pedido ou crédito associado

### Cenário 2: Tentativa de aprovação sem análise favorável
**Dado** que o agente banco está autenticado  
**E** não existe parecer financeiro favorável  
**Quando** tentar aprovar o crédito  
**Então** o sistema deve impedir a aprovação  
**E** informar a pendência existente

---

## HU16 — Rejeição de crédito

**História:**  
Como **agente banco**, quero **rejeitar a concessão de crédito**, para **impedir financiamentos inviáveis**.

**Critérios de Aceitação:**

### Cenário 1: Crédito rejeitado com sucesso
**Dado** que o agente banco está autenticado  
**E** o pedido foi analisado financeiramente  
**Quando** rejeitar a concessão de crédito  
**Então** o sistema deve registrar a rejeição  
**E** atualizar a situação correspondente

### Cenário 2: Rejeição sem análise
**Dado** que o agente banco está autenticado  
**E** o pedido ainda não passou pela análise financeira necessária  
**Quando** tentar rejeitar o crédito  
**Então** o sistema deve impedir a operação  
**E** informar a necessidade de análise prévia

---

## HU17 — Associação de contrato de crédito

**História:**  
Como **agente banco**, quero **associar um contrato de crédito ao aluguel**, para **vincular o financiamento ao pedido**.

**Critérios de Aceitação:**

### Cenário 1: Associação de contrato de crédito com sucesso
**Dado** que o agente banco está autenticado  
**E** o pedido exige financiamento  
**Quando** informar corretamente os dados do contrato de crédito  
**Então** o sistema deve associar o contrato ao pedido  
**E** registrar o banco responsável

### Cenário 2: Dados inválidos do contrato de crédito
**Dado** que o agente banco está autenticado  
**Quando** tentar associar um contrato de crédito com dados inválidos ou incompletos  
**Então** o sistema deve impedir a associação  
**E** informar os erros encontrados

---

## HU18 — Disponibilização de contrato para execução

**História:**  
Como **agente**, quero **disponibilizar o contrato para execução**, para **formalizar a etapa final do processo de aluguel**.

**Critérios de Aceitação:**

### Cenário 1: Contrato disponibilizado com sucesso
**Dado** que o pedido foi aprovado  
**E** as condições necessárias foram atendidas  
**Quando** o agente concluir a etapa de liberação contratual  
**Então** o sistema deve disponibilizar o contrato para execução  
**E** vinculá-lo ao pedido correspondente

### Cenário 2: Tentativa de disponibilização sem aprovação
**Dado** que o pedido ainda não foi aprovado  
**Quando** tentar disponibilizar o contrato para execução  
**Então** o sistema deve impedir a operação  
**E** informar que a aprovação prévia é obrigatória

---