# Casos de Uso — Sistema de Aluguel de Carros

## Visão geral

Este documento descreve os principais casos de uso do **Sistema de Aluguel de Carros**, considerando os atores identificados a partir dos requisitos fornecidos.

O sistema permite que clientes realizem e acompanhem pedidos de aluguel pela Internet, enquanto agentes de empresa e agentes bancários analisam, modificam e avaliam esses pedidos, incluindo a possibilidade de associar contratos de crédito.

---

## Atores

### Cliente
Usuário individual que utiliza o sistema para solicitar, consultar, modificar e cancelar pedidos de aluguel.

### Agente Empresa
Representante da empresa responsável por analisar pedidos, modificá-los quando necessário, aprová-los ou rejeitá-los, além de registrar informações contratuais relacionadas ao automóvel.

### Agente Banco
Representante do banco responsável pela análise financeira e pela associação de contrato de crédito ao pedido, quando aplicável.

### Agente
Ator genérico que representa funcionalidades comuns entre **Agente Empresa** e **Agente Banco**.

---

## Pré-condições gerais

- O sistema deve estar disponível por meio da Internet.
- O cliente deve possuir cadastro prévio para acessar as funcionalidades do sistema.
- Os agentes devem possuir credenciais válidas para autenticação.
- Os dados do cliente, do automóvel e do pedido devem estar armazenados no sistema para que possam ser consultados, modificados ou avaliados.

---

## Lista de casos de uso

| ID | Caso de Uso | Atores |
|---|---|---|
| UC01 | Cadastrar-se | Cliente |
| UC02 | Autenticar-se | Cliente, Agente Empresa, Agente Banco |
| UC03 | Registrar pedido de aluguel | Cliente |
| UC04 | Consultar pedido | Cliente, Agente |
| UC05 | Modificar pedido | Cliente, Agente Empresa |
| UC06 | Cancelar pedido | Cliente |
| UC07 | Consultar contrato | Cliente |
| UC08 | Avaliar pedido | Agente Empresa, Agente Banco |
| UC09 | Analisar situação financeira | Agente Empresa, Agente Banco |
| UC10 | Aprovar pedido | Agente Empresa, Agente Banco |
| UC11 | Rejeitar pedido | Agente Empresa, Agente Banco |
| UC12 | Disponibilizar contrato para execução | Agente Empresa, Agente Banco |
| UC13 | Associar contrato de crédito | Agente Banco |
| UC14 | Registrar propriedade do automóvel | Agente Empresa |

---

## Especificação dos casos de uso

---

## UC01 — Cadastrar-se

**Atores:** Cliente

**Objetivo:** Permitir que um novo cliente se registre no sistema para utilizar os serviços de aluguel.

**Pré-condições:**
- O cliente ainda não possui cadastro ativo no sistema.

**Fluxo principal:**
1. O cliente acessa a funcionalidade de cadastro.
2. O sistema solicita os dados do cliente.
3. O cliente informa seus dados de identificação, endereço, profissão, empregadores e rendimentos.
4. O sistema valida os dados informados.
5. O sistema registra o novo cliente.
6. O sistema confirma a conclusão do cadastro.

**Fluxos alternativos:**
- 4A. Dados obrigatórios ausentes ou inválidos:
  1. O sistema informa os campos incorretos.
  2. O cliente corrige os dados.
  3. O fluxo retorna ao passo 4.

**Pós-condições:**
- O cliente passa a possuir cadastro no sistema.

---

## UC02 — Autenticar-se

**Atores:** Cliente, Agente Empresa, Agente Banco

**Objetivo:** Permitir que usuários cadastrados acessem as funcionalidades do sistema de acordo com seu perfil.

**Pré-condições:**
- O usuário deve possuir credenciais cadastradas.

**Fluxo principal:**
1. O usuário acessa a tela de login.
2. O sistema solicita credenciais de acesso.
3. O usuário informa login e senha.
4. O sistema valida as credenciais.
5. O sistema autentica o usuário.
6. O sistema libera o acesso às funcionalidades compatíveis com o perfil.

**Fluxos alternativos:**
- 4A. Credenciais inválidas:
  1. O sistema informa falha na autenticação.
  2. O usuário pode tentar novamente.

**Pós-condições:**
- O usuário autenticado inicia sessão no sistema.

---

## UC03 — Registrar pedido de aluguel

**Atores:** Cliente

**Objetivo:** Permitir que o cliente registre um novo pedido de aluguel de automóvel.

**Pré-condições:**
- O cliente deve estar autenticado.
- Deve existir automóvel disponível para locação.

**Fluxo principal:**
1. O cliente acessa a funcionalidade de registro de pedido.
2. O sistema solicita os dados necessários do pedido.
3. O cliente informa os dados do aluguel desejado.
4. O sistema registra o pedido com status inicial.
5. O sistema confirma o registro do pedido.

**Fluxos alternativos:**
- 3A. Dados insuficientes ou inconsistentes:
  1. O sistema informa o erro.
  2. O cliente corrige os dados.
  3. O fluxo retorna ao passo 3.

**Pós-condições:**
- O pedido de aluguel é armazenado no sistema para análise posterior.

---

## UC04 — Consultar pedido

**Atores:** Cliente, Agente

**Objetivo:** Permitir a visualização das informações de um pedido de aluguel.

**Pré-condições:**
- O usuário deve estar autenticado.
- O pedido deve existir no sistema.

**Fluxo principal:**
1. O usuário acessa a funcionalidade de consulta de pedidos.
2. O sistema apresenta os pedidos disponíveis ao perfil do usuário.
3. O usuário seleciona um pedido.
4. O sistema exibe os detalhes do pedido.

**Fluxos alternativos:**
- 2A. Nenhum pedido encontrado:
  1. O sistema informa que não há pedidos disponíveis para consulta.

**Pós-condições:**
- O usuário visualiza os dados atualizados do pedido.

---

## UC05 — Modificar pedido

**Atores:** Cliente, Agente Empresa

**Objetivo:** Permitir a alteração das informações de um pedido de aluguel.

**Pré-condições:**
- O usuário deve estar autenticado.
- O pedido deve existir e estar em estado que permita alteração.

**Fluxo principal:**
1. O usuário localiza o pedido desejado.
2. O usuário seleciona a opção de modificação.
3. O sistema apresenta os dados atuais do pedido.
4. O usuário altera as informações desejadas.
5. O sistema valida as alterações.
6. O sistema salva as modificações.
7. O sistema confirma a atualização.

**Fluxos alternativos:**
- 5A. Alterações inválidas:
  1. O sistema informa as inconsistências encontradas.
  2. O usuário corrige os dados.
  3. O fluxo retorna ao passo 5.

**Pós-condições:**
- O pedido é atualizado no sistema.

---

## UC06 — Cancelar pedido

**Atores:** Cliente

**Objetivo:** Permitir que o cliente cancele um pedido de aluguel registrado.

**Pré-condições:**
- O cliente deve estar autenticado.
- O pedido deve existir e estar apto para cancelamento.

**Fluxo principal:**
1. O cliente consulta seus pedidos.
2. O cliente seleciona o pedido desejado.
3. O cliente escolhe a opção de cancelamento.
4. O sistema solicita confirmação.
5. O cliente confirma o cancelamento.
6. O sistema registra o cancelamento do pedido.
7. O sistema informa a conclusão da operação.

**Fluxos alternativos:**
- 5A. O cliente desiste da operação:
  1. O sistema mantém o pedido sem alterações.

**Pós-condições:**
- O pedido passa a constar como cancelado.

---

## UC07 — Consultar contrato

**Atores:** Cliente

**Objetivo:** Permitir que o cliente consulte o contrato relacionado ao seu aluguel.

**Pré-condições:**
- O cliente deve estar autenticado.
- Deve existir contrato associado ao pedido.

**Fluxo principal:**
1. O cliente acessa a área de contratos.
2. O sistema lista os contratos disponíveis.
3. O cliente seleciona um contrato.
4. O sistema exibe os dados do contrato.

**Fluxos alternativos:**
- 2A. Não existe contrato associado:
  1. O sistema informa que não há contrato disponível para consulta.

**Pós-condições:**
- O cliente visualiza as informações do contrato.

---

## UC08 — Avaliar pedido

**Atores:** Agente Empresa, Agente Banco

**Objetivo:** Permitir a avaliação de um pedido de aluguel por parte dos agentes responsáveis.

**Pré-condições:**
- O agente deve estar autenticado.
- O pedido deve estar pendente de avaliação.

**Fluxo principal:**
1. O agente consulta os pedidos pendentes.
2. O agente seleciona um pedido.
3. O sistema apresenta os dados do cliente, do automóvel e da solicitação.
4. O agente analisa o pedido.
5. O agente registra o resultado da avaliação.

**Relacionamentos:**
- **Inclui:** UC09 — Analisar situação financeira

**Pós-condições:**
- O pedido passa a ter uma avaliação registrada.

---

## UC09 — Analisar situação financeira

**Atores:** Agente Empresa, Agente Banco

**Objetivo:** Verificar a situação financeira do contratante antes da aprovação do pedido.

**Pré-condições:**
- O agente deve estar autenticado.
- O pedido deve conter os dados do contratante.

**Fluxo principal:**
1. O agente acessa os dados financeiros do contratante.
2. O sistema apresenta profissão, empregadores e rendimentos informados.
3. O agente analisa a capacidade financeira do contratante.
4. O agente registra o parecer financeiro.

**Pós-condições:**
- O parecer financeiro fica associado ao pedido.

---

## UC10 — Aprovar pedido

**Atores:** Agente Empresa, Agente Banco

**Objetivo:** Aprovar um pedido de aluguel após análise satisfatória.

**Pré-condições:**
- O agente deve estar autenticado.
- O pedido deve ter sido avaliado.
- O parecer financeiro deve ser positivo.

**Fluxo principal:**
1. O agente acessa o pedido avaliado.
2. O agente seleciona a opção de aprovação.
3. O sistema registra a aprovação.
4. O sistema atualiza o status do pedido.
5. O sistema torna o pedido apto para execução contratual.

**Relacionamentos:**
- **Inclui:** UC12 — Disponibilizar contrato para execução

**Pós-condições:**
- O pedido passa a constar como aprovado.

---

## UC11 — Rejeitar pedido

**Atores:** Agente Empresa, Agente Banco

**Objetivo:** Rejeitar um pedido de aluguel quando ele não atende aos critérios definidos.

**Pré-condições:**
- O agente deve estar autenticado.
- O pedido deve ter sido avaliado.

**Fluxo principal:**
1. O agente acessa o pedido avaliado.
2. O agente seleciona a opção de rejeição.
3. O sistema solicita a justificativa.
4. O agente informa a justificativa.
5. O sistema registra a rejeição e a justificativa.
6. O sistema atualiza o status do pedido.

**Pós-condições:**
- O pedido passa a constar como rejeitado.

---

## UC12 — Disponibilizar contrato para execução

**Atores:** Agente Empresa, Agente Banco

**Objetivo:** Tornar o contrato disponível para formalização e execução após aprovação do pedido.

**Pré-condições:**
- O pedido deve estar aprovado.

**Fluxo principal:**
1. O sistema identifica que o pedido foi aprovado.
2. O sistema gera ou libera o contrato correspondente.
3. O sistema associa o contrato ao pedido.
4. O sistema disponibiliza o contrato para consulta e execução.

**Pós-condições:**
- O contrato fica disponível no sistema.

---

## UC13 — Associar contrato de crédito

**Atores:** Agente Banco

**Objetivo:** Vincular um contrato de crédito a um pedido de aluguel quando houver financiamento bancário.

**Pré-condições:**
- O agente banco deve estar autenticado.
- O pedido deve exigir contrato de crédito.

**Fluxo principal:**
1. O agente banco acessa o pedido.
2. O agente seleciona a opção de associação de crédito.
3. O sistema solicita os dados do contrato de crédito.
4. O agente informa os dados necessários.
5. O sistema valida e associa o contrato de crédito ao pedido.
6. O sistema confirma a vinculação.

**Pós-condições:**
- O pedido passa a estar associado a um contrato de crédito.

---

## UC14 — Registrar propriedade do automóvel

**Atores:** Agente Empresa

**Objetivo:** Registrar a quem pertence o automóvel associado ao aluguel.

**Pré-condições:**
- O agente empresa deve estar autenticado.
- O pedido deve possuir automóvel associado.

**Fluxo principal:**
1. O agente empresa acessa o pedido ou contrato correspondente.
2. O agente seleciona a opção de registro de propriedade.
3. O sistema apresenta as opções de propriedade: cliente, empresa ou banco.
4. O agente informa a propriedade correta.
5. O sistema salva a informação.

**Pós-condições:**
- A propriedade do automóvel fica registrada no sistema.

---

## Relacionamentos entre casos de uso

- **UC08 — Avaliar pedido** inclui **UC09 — Analisar situação financeira**
- **UC10 — Aprovar pedido** inclui **UC12 — Disponibilizar contrato para execução**

---

## Observações finais

- O cliente é o principal responsável pela criação e manutenção inicial de seus pedidos.
- Os agentes atuam na análise, aprovação, rejeição e formalização contratual.
- O agente banco possui responsabilidade específica sobre crédito.
- O agente empresa possui responsabilidade específica sobre o registro da propriedade do automóvel.
- O sistema pode ser dividido em dois subsistemas: um voltado à **gestão de pedidos e contratos** e outro à **construção dinâmica das páginas web**, embora o segundo represente uma visão arquitetural, e não um caso de uso do usuário.

---