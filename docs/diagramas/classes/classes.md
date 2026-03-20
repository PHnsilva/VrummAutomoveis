
# classes.md

## Objetivo
Este arquivo documenta as decisões do diagrama de classes do sistema de aluguel de carros e serve como apoio para manter alinhamento entre modelo UML e implementação.

## Classes principais

### Usuario
Classe abstrata com os dados comuns de autenticação e identificação.

Atributos:
- id
- nome
- cpf
- rg
- endereco

Operações:
- login()
- logout()

### Credencial
Representa o cadastro prévio necessário para acessar o sistema.

Atributos:
- email
- senhaHash
- ativo

### Cliente
Especialização de Usuario.

Atributos:
- profissao

Operações:
- criarPedido()
- cancelarPedido()
- modificarPedido()
- consultarPedido()

### Agente
Classe abstrata para agentes do sistema.

Operações:
- avaliarPedido()
- modificarPedido()

### Empresa e Banco
Especializações de Agente.

Decisão importante:
- Banco foi separado de Empresa para deixar explícito que apenas bancos podem conceder contrato de crédito.

### PedidoAluguel
Representa a solicitação de aluguel.

Atributos:
- id
- dataInicio
- dataFim
- status

Operações:
- criar()
- cancelar()
- modificar()
- validarPedido()

### Contrato
Representa a formalização do aluguel.

Atributos:
- id
- tipo
- status

Operações:
- gerarContrato()

### Automovel
Representa o veículo alugado.

Atributos:
- id
- matricula
- placa
- marca
- modelo
- ano

### ContratoCredito
Contrato opcional vinculado ao aluguel.

Atributos:
- id
- valor
- aprovado

Operações:
- avaliarCredito()

### Proprietario
Interface usada para explicitar quem fica como proprietário final do automóvel no contrato.

Implementações:
- Cliente
- Empresa
- Banco

### Empregador e Rendimento
Classes auxiliares para representar vínculos de renda informados pelo cliente.

## Relacionamentos principais
- Usuario 1 -- 1 Credencial
- Cliente herda de Usuario
- Agente herda de Usuario
- Empresa herda de Agente
- Banco herda de Agente
- Cliente 1 -- 0..* PedidoAluguel
- Agente 0..* -- 0..* PedidoAluguel
- PedidoAluguel 1 -- 0..1 Contrato
- Contrato 1 -- 1 Automovel
- Contrato 1 -- 1 Proprietario
- Automovel 1 -- 0..1 ContratoCredito
- Banco 1 -- 0..* ContratoCredito
- Cliente 1 -- 0..3 Empregador
- Cliente 1 -- 0..3 Rendimento

## Ajustes feitos em relação à versão anterior
1. A propriedade do automóvel deixou de ficar implícita e passou a ser modelada com a interface Proprietario.
2. ContratoCredito não depende mais de Agente genérico. Agora ele depende de Banco.
3. Foi adicionada a classe Credencial para deixar o cadastro prévio explícito.
4. Foram removidas duplicidades entre atributos tipados e associações.
5. As cardinalidades foram revisadas para refletir melhor o enunciado.

## Regras de modelagem adotadas
- Preferir associação UML quando a relação entre classes já está representada graficamente.
- Evitar repetir a mesma relação como atributo tipado e associação ao mesmo tempo.
- Usar herança quando há especialização real de comportamento.
- Usar interface quando diferentes tipos podem assumir o mesmo papel no contrato.

## Observação
Se o professor preferir uma modelagem ainda mais fiel para empregadores e rendimentos correspondentes, é possível substituir Empregador e Rendimento por uma classe associativa como VinculoProfissional, contendo empregador e valor do rendimento no mesmo vínculo.