# Vrumm Automóveis

Sistema web para apoio ao fluxo de solicitação, análise e acompanhamento de pedidos de aluguel/financiamento de veículos. O projeto reúne, no mesmo backend, a interface web renderizada no servidor, a API HTTP para integrações e consumo programático, as regras de negócio dos módulos principais e a persistência em PostgreSQL.

Hoje a aplicação atende três contextos operacionais centrais — **cliente**, **empresa** e **banco** — com autenticação por cookie de sessão, páginas em Thymeleaf, documentação OpenAPI/Swagger e migrações versionadas com Flyway.

---

## 🚧 Status do Projeto
![Status](https://img.shields.io/badge/status-em%20desenvolvimento-007ec6?style=for-the-badge)
![Backend](https://img.shields.io/badge/backend-Java%2021%20%7C%20Micronaut%204.10.10-007ec6?style=for-the-badge)
![Views](https://img.shields.io/badge/views-Thymeleaf-007ec6?style=for-the-badge)
![Database](https://img.shields.io/badge/database-PostgreSQL-007ec6?style=for-the-badge)
![Build](https://img.shields.io/badge/build-Maven-007ec6?style=for-the-badge)

---

## 📚 Índice
- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Variáveis de Ambiente](#-variáveis-de-ambiente)
- [Como Rodar Localmente](#-como-rodar-localmente)
- [Build e Testes](#-build-e-testes)
- [Rotas e Endpoints Principais](#-rotas-e-endpoints-principais)
- [Perfis de Acesso](#-perfis-de-acesso)
- [Fluxos Principais](#-fluxos-principais)
- [Estrutura de Pastas](#-estrutura-de-pastas)
- [Documentação Técnica](#-documentação-técnica)
- [Troubleshooting](#-troubleshooting)

---

## 📝 Sobre o Projeto
O **Vrumm Automóveis** centraliza o fluxo de cadastro, autenticação e gestão de pedidos ligados à operação de automóveis, incluindo cadastro de clientes, consulta de veículos, registro de solicitações, avaliação financeira, formalização contratual e acompanhamento do status do pedido.

Diferente de uma API pura, este repositório entrega duas frentes no mesmo backend:

- **interface web server-side**, usando **Micronaut Views + Thymeleaf**;
- **API REST**, exposta em `/api`, com documentação via Swagger/OpenAPI.

Na prática, o sistema funciona como um **monólito modular**, em que cada domínio é organizado por pacote e a aplicação mantém separação clara entre entrada HTTP, orquestração, domínio e persistência.

---

## ✨ Funcionalidades

### Funcionalidades principais
- cadastro e autenticação de usuários;
- identificação do usuário autenticado por cookie de sessão;
- gerenciamento de perfil do usuário autenticado;
- cadastro, edição, consulta e remoção de clientes;
- consulta de automóveis disponíveis para solicitação;
- criação, edição, cancelamento e acompanhamento de pedidos;
- confirmação de pagamento do pedido;
- visualização de status detalhado do pedido;
- registro de contrato operacional e contrato de crédito;
- avaliação financeira do pedido pelo contexto bancário.

### Funcionalidades por contexto operacional
- **Cliente:** cria pedidos, acompanha andamento, edita dados do perfil, confirma pagamento e consulta histórico próprio.
- **Empresa:** acompanha pedidos operacionais, registra contrato e pode recusar solicitações.
- **Banco:** acompanha pedidos sob análise financeira, registra parecer e contrato de crédito.

### Recursos técnicos já presentes
- autenticação por cookie HTTP-only;
- persistência em PostgreSQL;
- migrações versionadas com Flyway;
- tratamento padronizado de erros para a API;
- documentação Swagger/OpenAPI;
- páginas renderizadas no backend com Thymeleaf.

---

## 🛠 Tecnologias

### Stack principal
- **Java 21**
- **Micronaut 4.10.10**
- **Micronaut Data JDBC**
- **Micronaut Views + Thymeleaf**
- **Micronaut Validation**
- **Micronaut Serde Jackson**
- **Micronaut OpenAPI**
- **Flyway**
- **PostgreSQL**
- **Maven**

### Dependências e recursos relevantes
- **HikariCP** para pool de conexões JDBC;
- **Logback** para logging;
- **JUnit 5 / Micronaut Test** para testes;
- **Swagger UI** para exploração da API.

---

## 🏗️ Arquitetura

### Visão Geral
O projeto adota uma arquitetura **monolítica modular**, organizada por domínio e por responsabilidade técnica. O sistema não está estruturado como frontend separado + backend separado; em vez disso, o backend concentra:

- **controllers web** para páginas e formulários;
- **controllers de API** para endpoints REST;
- **camada de aplicação** com façades, casos de uso, serviços de apoio e mapeadores;
- **camada de domínio** com entidades, enums e objetos com regras de negócio;
- **camada de infraestrutura** com persistência e serviços técnicos;
- **camada shared** com exceções, segurança e tratamento global de erros.

Essa decisão é adequada ao estágio atual do projeto porque reduz complexidade operacional, facilita o desenvolvimento local e mantém a lógica centralizada em uma única aplicação.

### Estilo arquitetural adotado
A base do projeto combina três ideias principais:

#### 1. MVC na borda da aplicação
A entrada HTTP é dividida entre:

- **controllers web** (`interfaces/web`) para telas Thymeleaf;
- **controllers de API** (`interfaces/api`) para JSON e integrações.

Os controllers permanecem como ponto de entrada da requisição, cuidando de:

- rota;
- binding de formulário ou payload;
- redirecionamento HTTP;
- escolha da view ou resposta;
- delegação para a camada de aplicação.

Ou seja, o projeto usa **MVC de forma prática**, especialmente na camada web, sem empurrar regra de negócio para os controllers.

#### 2. Façade na camada de aplicação
A aplicação usa classes do tipo `Facade` como ponto de orquestração dos fluxos de negócio.

Exemplos no código:
- `ClienteFacade`
- `AuthSessionFacade`
- `PedidoFacade`

Essas classes funcionam como **porta de entrada da aplicação**, encapsulando o fluxo que os controllers precisam executar. No módulo de pedidos, a fachada foi refinada para delegar internamente a componentes menores, como:

- **use case de comando** (`PedidoCommandUseCase`);
- **query service** (`PedidoQueryService`);
- **serviço de apoio de domínio** (`PedidoDomainSupport`);
- **mappers** para DTOs e formulários.

Essa composição melhora a legibilidade e reduz o acúmulo excessivo de responsabilidade em uma única classe.

#### 3. DDD leve/modular
O projeto não segue um DDD ortodoxo ou uma hexagonal pura, mas já adota elementos importantes de **Domain-Driven Design**:

- organização por **domínio funcional**;
- entidades com comportamento, e não apenas estrutura de dados;
- uso de **Value Object**, como `Cpf`;
- separação entre aplicação, domínio e infraestrutura;
- vocabulário de negócio refletido em classes como `Cliente`, `PedidoAluguel`, `Contrato`, `ContratoCredito`, `Automovel` e `PerfilAcesso`.

Na prática, o projeto hoje pode ser descrito como:

> **MVC na entrada + Façade/Application Layer na orquestração + DDD leve no domínio + persistência JDBC em infraestrutura**.

Esse desenho é coerente com o estágio atual do sistema e oferece um bom equilíbrio entre simplicidade, manutenibilidade e possibilidade de evolução.

### Organização por Domínio
A solução está estruturada principalmente em torno dos seguintes módulos:

- `auth` → autenticação e sessão;
- `cliente` → cadastro, perfil, autenticação e dados principais do usuário;
- `automovel` → catálogo e disponibilidade operacional de veículos;
- `pedido` → fluxo principal de solicitação, análise, pagamento e status;
- `contrato` → formalização contratual e contrato de crédito;
- `agente` → entidades de apoio, como banco e empresa;
- `site` → páginas institucionais e páginas base da aplicação;
- `shared` → segurança, exceções e componentes compartilhados.

Essa escolha melhora a coesão do código porque cada contexto do negócio concentra suas próprias classes de aplicação, domínio, interface e persistência.

### Organização interna dos módulos
Cada módulo segue, em maior ou menor grau, a seguinte divisão:

- `interfaces/web` → controllers de páginas, formulários e redirecionamentos;
- `interfaces/api` → endpoints REST;
- `application/facade` → fachada de orquestração;
- `application/usecase` → fluxos de escrita e comandos;
- `application/query` → consultas e montagem de leitura;
- `application/mapper` → conversão entre domínio, DTO e formulários;
- `application/dto` → contratos de entrada e saída;
- `domain/model` → entidades, enums e objetos centrais do negócio;
- `infrastructure/persistence` → repositórios Micronaut Data/JDBC;
- `infrastructure/service` → serviços técnicos auxiliares.

Nem todos os módulos usam exatamente todas as subpastas, mas o desenho geral já aponta para uma arquitetura consistente.

### Fluxo geral da aplicação
#### Fluxo web
`Usuário -> Controller Web -> Facade -> UseCase/QueryService -> Repository -> PostgreSQL -> View Thymeleaf`

#### Fluxo da API
`Cliente HTTP -> Controller API -> Facade -> Domínio / Repositório -> PostgreSQL -> JSON`

### Exemplo prático: módulo de pedidos
O módulo `pedido` é o coração operacional do sistema e também o melhor exemplo da arquitetura atual.

#### Entrada
Os controllers web (`PedidoController`, `EmpresaPedidoController`, `BancoPedidoController`) recebem ações como:
- criar pedido;
- editar pedido;
- cancelar pedido;
- confirmar pagamento;
- avaliar financeiramente;
- registrar contrato;
- registrar contrato de crédito.

#### Aplicação
Esses controllers delegam para `PedidoFacade`, que atua como fachada pública do módulo.

A partir dela, o fluxo é distribuído entre:
- `PedidoCommandUseCase` para comandos e mudanças de estado;
- `PedidoQueryService` para leitura e montagem de resumo;
- `PedidoDomainSupport` para validações e buscas auxiliares;
- `PedidoStatusMapper` e `PedidoFormMapper` para transformação de dados.

#### Domínio
A entidade `PedidoAluguel` concentra o comportamento principal do pedido, com operações como:
- criação da solicitação;
- atualização;
- cancelamento;
- recusa;
- registro de parecer financeiro;
- finalização do pedido.

Esse ponto é importante: a regra de negócio mais importante não fica espalhada em controller ou utilitário, e sim concentrada no modelo de domínio e na camada de aplicação.

### Decisões arquiteturais importantes

#### Controllers enxutos
Os controllers priorizam:
- receber entrada;
- validar a presença do usuário autenticado;
- redirecionar ou responder;
- delegar para a aplicação.

#### Façades como contratos da aplicação
As façades evitam que a interface converse diretamente com repositórios ou com múltiplas classes internas do domínio.

#### Domínio com encapsulamento
As entidades utilizam atributos encapsulados e métodos orientados ao negócio, reduzindo manipulação arbitrária de estado.

#### DTOs e formulários separados do domínio
A aplicação usa classes específicas para input/output, evitando expor diretamente o domínio à camada HTTP.

#### Persistência isolada
O acesso ao banco está concentrado em repositórios do Micronaut Data, mantendo a persistência fora dos controllers.

#### Exceções padronizadas
A pasta `shared/interfaces/api` centraliza handlers para exceções recorrentes, padronizando o retorno da API.

### Limites atuais da arquitetura
O projeto já está em uma direção boa, mas ainda não é um DDD “forte” ou “puro”. Os principais pontos atuais são:

- parte dos contratos de persistência ainda vive na infraestrutura, não no domínio;
- alguns módulos ainda dependem mais da fachada do que de casos de uso específicos;
- o grau de profundidade do DDD varia conforme o módulo;
- a separação entre leitura e escrita foi fortalecida no módulo de pedidos, mas ainda pode evoluir em outros contextos.

Ainda assim, a base arquitetural atual é sólida para continuar evoluindo sem ruptura total.

---

## 🔐 Variáveis de Ambiente
Atualmente, a configuração principal está em `src/main/resources/application.properties`.

| Variável / Propriedade | Obrigatória | Descrição | Exemplo |
|---|---|---|---|
| `micronaut.server.port` | Sim | Porta HTTP da aplicação | `8080` |
| `datasources.default.url` | Sim | URL do PostgreSQL | `jdbc:postgresql://localhost:5432/vrumm_automoveis` |
| `datasources.default.username` | Sim | Usuário do banco | `admin` |
| `datasources.default.password` | Sim | Senha do banco | `admin` |
| `datasources.default.driver-class-name` | Sim | Driver JDBC | `org.postgresql.Driver` |
| `flyway.datasources.default.enabled` | Sim | Habilita migrações Flyway | `true` |

### Exemplo mínimo de configuração
```properties
micronaut.server.port=8080

micronaut.views.thymeleaf.enabled=true

datasources.default.url=jdbc:postgresql://localhost:5432/vrumm_automoveis
datasources.default.username=admin
datasources.default.password=admin
datasources.default.driver-class-name=org.postgresql.Driver
datasources.default.dialect=POSTGRES

flyway.datasources.default.enabled=true
flyway.datasources.default.locations=classpath:db/migration
```

> Em ambiente real, o ideal é externalizar credenciais sensíveis para variáveis de ambiente ou arquivos de configuração por perfil.

---

## ▶️ Como Rodar Localmente

### Pré-requisitos
- **Java 21**
- **Maven** (`mvn`)
- **PostgreSQL** em execução
- banco `vrumm_automoveis` criado ou configurado conforme sua instância

### Passos
```bash
cd backend
mvn mn:run
```

No Windows:
```bash
mvn mn:run
```

### Endereços úteis em desenvolvimento
- aplicação web: `http://localhost:8080/`
- Swagger UI: `http://localhost:8080/swagger-ui/`
- arquivos OpenAPI: `http://localhost:8080/swagger/`

---

## 🧱 Build e Testes

### Build do projeto
```bash
cd backend
mvn clean package
```

### Execução de testes
```bash
mvn test
```

### Testes atualmente presentes
- teste base da aplicação;
- teste de controller da landing page.

---

## 🔌 Rotas e Endpoints Principais

### Interface web
| Rota | Descrição |
|---|---|
| `/` | landing page da aplicação |
| `/cadastro` | cadastro do usuário via fluxo web |
| `/login` | autenticação via formulário |
| `/logout` | encerramento da sessão |
| `/perfil` | página do perfil autenticado |
| `/perfil/editar` | edição de perfil |
| `/clientes` | gestão web de clientes |
| `/pedidos` | área de pedidos do cliente |
| `/empresa/pedidos` | painel operacional da empresa |
| `/banco/pedidos` | painel operacional do banco |
| `/admin/pedidos` | rota de redirecionamento administrativo |

### API REST
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/auth/cadastro` | cadastra usuário e autentica |
| `POST` | `/api/auth/login` | autentica por e-mail e senha |
| `GET` | `/api/auth/me` | retorna usuário autenticado via cookie |
| `POST` | `/api/auth/logout` | encerra a sessão |
| `GET` | `/api/clientes` | lista clientes |
| `GET` | `/api/clientes/{id}` | busca cliente por id |
| `POST` | `/api/clientes` | cria cliente |
| `PUT` | `/api/clientes/{id}` | atualiza cliente |
| `DELETE` | `/api/clientes/{id}` | remove cliente |

> O detalhamento fino dos payloads pode ser consultado diretamente pela interface Swagger.

---

## 👥 Perfis de Acesso
O domínio trabalha atualmente com três perfis principais, representados por `PerfilAcesso`:

| Perfil | Papel no sistema | Acessos típicos |
|---|---|---|
| `CLIENTE` | usuário final que solicita e acompanha o pedido | perfil, criação e consulta dos próprios pedidos |
| `EMPRESA` | contexto operacional da empresa | acompanhamento operacional, recusa de pedido, contrato |
| `BANCO` | contexto financeiro | parecer financeiro, contrato de crédito e consulta de pedidos |

A autenticação é baseada no cookie `vrumm_cliente_id`, emitido após login ou cadastro.

---

## 🔄 Fluxos Principais

### 1. Cadastro e autenticação
1. o usuário acessa a landing page ou formulário de cadastro;
2. envia os dados de cadastro ou login;
3. a aplicação valida o usuário na camada de aplicação;
4. um cookie HTTP-only é emitido para manter a sessão;
5. o sistema passa a reconhecer o usuário autenticado nas próximas requisições.

### 2. Criação e acompanhamento de pedido
1. o cliente autenticado acessa a área de pedidos;
2. seleciona um automóvel disponível e preenche os dados da solicitação;
3. o módulo `pedido` cria a solicitação e persiste o estado inicial;
4. o cliente acompanha o status do pedido pela interface;
5. o pedido pode evoluir para análise, pagamento, contrato ou encerramento.

### 3. Avaliação financeira e formalização
1. a empresa ou o banco acessa os painéis de pedidos;
2. o banco registra avaliação financeira e, quando aplicável, contrato de crédito;
3. a empresa registra o contrato operacional;
4. o cliente confirma pagamento quando o fluxo exige;
5. o pedido é finalizado ou recusado conforme as regras do domínio.

---

## 📁 Estrutura de Pastas
```txt
VRUMMAUTOMOVEIS/
├── .vscode/                         # Configurações locais do editor
├── backend/                         # Aplicação principal (API + páginas web + regras de negócio)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/vrumm/
│   │   │   │       ├── auth/        # Autenticação, sessão e controle de acesso
│   │   │   │       ├── automovel/   # Domínio de veículos
│   │   │   │       ├── banco/       # Domínio relacionado a instituições bancárias
│   │   │   │       ├── cliente/     # Cadastro, validações e fluxo de clientes
│   │   │   │       ├── contrato/    # Contratos e regras associadas
│   │   │   │       ├── pedido/      # Solicitações, análises e fluxo principal de pedidos
│   │   │   │       ├── shared/      # Componentes compartilhados entre módulos
│   │   │   │       └── config/      # Configurações técnicas da aplicação
│   │   │   └── resources/
│   │   │       ├── views/           # Templates Thymeleaf
│   │   │       ├── static/          # CSS, JS, imagens e arquivos públicos
│   │   │       ├── db/              # Scripts e migrações de banco
│   │   │       └── application.*    # Configurações por ambiente
│   │   └── test/                    # Testes automatizados
│   ├── pom.xml                      # Gerenciamento de dependências e build Maven
│   └── README.md                    # Documentação específica do backend, se existir
├── docs/                            # Documentação técnica e artefatos do projeto
│   ├── diagramas/
│   │   ├── caso-de-uso/             # Diagramas de caso de uso
│   │   ├── classes/                 # Diagramas de classes
│   │   ├── componentes/             # Diagramas de componentes
│   │   └── pacotes/                 # Diagramas de pacotes
│   └── requisitos/                  # Requisitos funcionais e não funcionais
├── .gitignore                       # Arquivos e diretórios ignorados pelo Git
├── LICENSE                          # Licença do projeto
├── package-lock.json                # Lock de dependências Node, quando aplicável
└── README.md                        # Documentação geral do repositório
```

### Leitura rápida da estrutura
- `auth/` → autenticação e sessão;
- `cliente/` → usuários, perfil e cadastro;
- `automovel/` → veículos;
- `pedido/` → fluxo central do sistema;
- `contrato/` → contratos operacional e financeiro;
- `agente/` → banco e empresa;
- `site/` → páginas base e navegação;
- `shared/` → segurança, erros e recursos compartilhados.

---

## 📖 Documentação Técnica
- **Swagger UI:** `/swagger-ui/`
- **Especificação OpenAPI gerada:** `/swagger/`
- **Migrações de banco:** `src/main/resources/db/migration`

### Migrations presentes
O projeto já possui migrações versionadas (`V1` a `V10`) cobrindo, entre outros pontos:
- criação de cliente;
- criação de automóvel;
- criação e evolução do fluxo de pedido;
- expansão do modelo de cliente e pedido;
- criação de entidades de empresa, banco e contratos;
- inclusão de avaliação financeira.

---

## 🧯 Troubleshooting

### 1. Erro ao iniciar por falha no banco
Verifique:
- se o PostgreSQL está rodando;
- se o banco `vrumm_automoveis` existe;
- se usuário e senha batem com `application.properties`.

### 2. Migração Flyway falhou
Se houver mudança manual em migration já aplicada, o Flyway pode acusar checksum mismatch. Nesse caso, revise a migration ou use `repair` apenas quando fizer sentido e com entendimento do impacto.

### 3. Página inicial retorna erro
Confirme que:
- `micronaut.views.thymeleaf.enabled=true` está ativo;
- os templates em `src/main/resources/views` existem;
- os recursos estáticos estão corretamente mapeados.

### 4. Swagger não abre
Confirme se a aplicação subiu corretamente e se os mapeamentos abaixo permanecem habilitados:
- `/swagger/**`
- `/swagger-ui/**`

### 5. Login parece funcionar, mas usuário não permanece autenticado
Verifique se o cookie `vrumm_cliente_id` está sendo emitido e reenviado pelo navegador nas próximas requisições.

---

## 👥 Autores

Projeto desenvolvido em grupo.

| Nome | GitHub | LinkedIn |
|------|--------|----------|
| Pedro H. S. | https://github.com/PHnsilva | https://www.linkedin.com/in/phnsilva1/ |
| Felipe Parreiras | https://github.com/FelipeParreiras | https://www.linkedin.com/in/felipe-parreiras04/ |
| Gabriel Nonato | https://github.com/GpNonato | https://www.linkedin.com/in/gabriel-nonato-3a3a98376/ |

---

## 📄 Licença
Este projeto está sob a licença **MIT**.  
Consulte o arquivo `LICENSE` para mais detalhes.