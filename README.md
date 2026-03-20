# 🚗 VRUMM Automóveis

Sistema web para apoio à gestão de **aluguéis de automóveis**, com foco no registro, consulta, modificação, cancelamento e avaliação de pedidos, além da documentação dos principais artefatos de análise e projeto, como **casos de uso**, **histórias do usuário**, **diagramas de classes** e **diagramas de pacotes**.

---

## 🚧 Status do Projeto
![Java](https://img.shields.io/badge/Java-17+-007ec6?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-latest-007ec6?style=for-the-badge&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-build-007ec6?style=for-the-badge&logo=apachemaven&logoColor=white)
![React](https://img.shields.io/badge/React-latest-007ec6?style=for-the-badge&logo=react&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-latest-007ec6?style=for-the-badge&logo=typescript&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-latest-007ec6?style=for-the-badge&logo=vite&logoColor=white)
![License](https://img.shields.io/github/license/PHnsilva/VRUMMAUTOMOVEIS?style=for-the-badge)

---

## 📚 Índice
- [Links Úteis](#-links-úteis)
- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Como Rodar Localmente](#-como-rodar-localmente)
- [Build](#-build)
- [Estrutura de Pastas](#-estrutura-de-pastas)
- [Documentação](#-documentação)
- [Autores](#-autores)
- [Licença](#-licença)

---

## 🔗 Links Úteis
- 🐙 **Repositório:** `https://github.com/PHnsilva/VRUMMAUTOMOVEIS`

---

## 📝 Sobre o Projeto
O **VRUMM Automóveis** é um sistema desenvolvido para apoiar a gestão de aluguéis de veículos por meio da Internet.

O sistema permite que **clientes** realizem operações relacionadas aos pedidos de aluguel, enquanto **agentes de empresa** e **agentes bancários** participam da análise, modificação, avaliação e formalização contratual dos pedidos.

Além da implementação do sistema, o repositório também reúne os principais artefatos de documentação do projeto, incluindo:

- histórias do usuário;
- histórias do usuário com cenários;
- casos de uso;
- diagrama de casos de uso;
- diagrama de classes;
- diagrama de pacotes.

O projeto está organizado em duas frentes principais:

- **backend** para regras de negócio, persistência e disponibilização da aplicação;
- **frontend** para a interface web do sistema.

---

## ✨ Funcionalidades
- Cadastro de clientes
- Autenticação de usuários
- Registro de pedidos de aluguel
- Consulta de pedidos
- Modificação de pedidos
- Cancelamento de pedidos
- Consulta de contratos
- Avaliação de pedidos por agentes
- Análise de situação financeira
- Aprovação ou rejeição de pedidos
- Associação de contrato de crédito
- Registro da propriedade do automóvel
- Documentação técnica e de requisitos do sistema

---

## 🛠 Tecnologias

### Backend
- **Java**
- **Spring Boot**
- **Maven**

### Frontend
- **React**
- **TypeScript**
- **Vite**
- **CSS**

### Documentação
- **Markdown**
- **PDF**
- **draw.io / UML**

---

## 🏗️ Arquitetura

O projeto está organizado em uma estrutura separada entre **backend**, **frontend** e **documentação**, facilitando manutenção, evolução e entendimento do sistema.

### Visão Geral
- **`backend/`** → aplicação backend em Java com Spring Boot
- **`frontend/`** → aplicação frontend com React + Vite + TypeScript
- **`docs/`** → documentação de requisitos e diagramas UML

### Camadas do Projeto

#### 1) Backend
Responsável pelas regras de negócio, inicialização da aplicação e recursos da camada servidor.

Principais pontos:
- inicialização da aplicação Spring Boot;
- configuração por `application.properties`;
- recursos e templates;
- scripts de banco em `resources/db/migration`.

#### 2) Frontend
Responsável pela interface web do sistema.

Principais arquivos:
- `App.tsx` → componente principal da aplicação
- `main.tsx` → ponto de entrada do frontend
- `App.css` e `index.css` → estilos globais
- `assets/` → arquivos estáticos

#### 3) Documentação
Responsável pelos artefatos de análise e projeto do sistema.

Inclui:
- histórias do usuário em versão resumida;
- histórias do usuário com cenários;
- casos de uso;
- diagrama de casos de uso;
- diagrama de classes;
- diagrama de pacotes.

### Princípios adotados
- Separação entre frontend, backend e documentação
- Organização por responsabilidade
- Estrutura preparada para evolução incremental
- Documentação integrada ao repositório
- Facilidade de manutenção

---

## 🔧 Como Rodar Localmente

### Pré-requisitos
- **Java 17** ou superior
- **Maven** ou uso do `mvnw`
- **Node.js** (versão LTS recomendada)
- **npm**

### Backend

```bash
cd backend
./mvnw spring-boot:run

---

No Windows:

```bash
cd backend
mvnw.cmd spring-boot:run
```

Por padrão, o backend tende a subir em:

```bash
http://localhost:8080
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

Por padrão, o frontend tende a subir em:

```bash
http://localhost:5173
```

---

## 🧱 Build

### Backend

```bash
cd backend
./mvnw clean package
```

No Windows:

```bash
cd backend
mvnw.cmd clean package
```

### Frontend

```bash
cd frontend
npm run build
npm run preview
```

---

## 📁 Estrutura de Pastas

```txt
.
├── backend/
│   ├── .mvn/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── br/com/vrummautomoveis/api/
│   │   │   │       └── Application.java
│   │   │   └── resources/
│   │   │       ├── db/
│   │   │       │   └── migration/
│   │   │       ├── static/
│   │   │       ├── templates/
│   │   │       └── application.properties
│   │   └── test/
│   ├── .gitattributes
│   ├── .gitignore
│   ├── HELP.md
│   ├── mvnw
│   ├── mvnw.cmd
│   └── pom.xml
│
├── docs/
│   ├── diagramas/
│   │   ├── caso-de-uso/
│   │   │   └── diagrama-casos-de-uso.pdf
│   │   ├── classes/
│   │   │   └── diagrama-classes.pdf
│   │   └── pacotes/
│   │       └── diagrama-pacotes.pdf
│   └── requisitos/
│       ├── casos-de-uso.md
│       ├── historias-de-usuario.md
│       └── historias-de-usuario-cenarios.md
│
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── assets/
│   │   │   └── react.svg
│   │   ├── App.css
│   │   ├── App.tsx
│   │   ├── index.css
│   │   ├── main.tsx
│   │   └── vite-env.d.ts
│   ├── .gitignore
│   ├── eslint.config.js
│   ├── index.html
│   ├── package-lock.json
│   ├── package.json
│   ├── README.md
│   ├── tsconfig.app.json
│   ├── tsconfig.json
│   ├── tsconfig.node.json
│   └── vite.config.ts
│
├── .gitignore
├── LICENSE
├── package-lock.json
└── README.md
```

---

## 📖 Documentação

A documentação do projeto está organizada dentro da pasta `docs/`.

### Requisitos
- [Casos de Uso](docs/requisitos/casos-de-uso.md)
- [Histórias do Usuário](docs/requisitos/historias-de-usuario.md)
- [Histórias do Usuário com Cenários](docs/requisitos/historias-de-usuario-cenarios.md)

### Diagramas
- [Diagrama de Casos de Uso](docs/diagramas/caso-de-uso/diagrama-casos-de-uso.pdf)
- [Diagrama de Classes](docs/diagramas/classes/diagrama-classes.pdf)
- [Diagrama de Pacotes](docs/diagramas/pacotes/diagrama-pacotes.pdf)

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
