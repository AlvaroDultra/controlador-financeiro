# 💰 Controlador Financeiro Pessoal

Este projeto é uma aplicação fullstack desenvolvida com o objetivo de gerenciar receitas e despesas pessoais de forma simples, rápida e eficiente. Ele permite ao usuário cadastrar entradas e saídas, consultar saldo, visualizar registros e remover itens específicos ou todos de uma vez.

---

## 🚀 Tecnologias Utilizadas

### Backend (Java + Spring Boot)
- **Spring Boot 3**: Framework principal para construção da API REST.
- **Spring Web**: Para mapeamento das rotas HTTP (controllers).
- **Spring Data JPA**: Para integração com banco de dados usando repositórios.
- **PostgreSQL**: Banco de dados relacional utilizado.
- **Lombok**: Reduz boilerplate com anotações como `@Getter`, `@Setter`, `@AllArgsConstructor`, etc.
- **JPA (Hibernate)**: ORM para persistência dos dados.

### Frontend (React + Vite)
- **React 19**: Biblioteca principal para criação da interface do usuário.
- **Vite**: Ferramenta de build e dev server ultrarrápido para React.
- **Axios**: Cliente HTTP usado para comunicação com a API.


---

## 🧩 Arquitetura

### Backend
O backend segue uma arquitetura simples, dividida em camadas:

```
com.ucsal.financeiro
│
├── controller      → Controladores REST (endpoints HTTP)
├── model           → Entidades JPA (Despesa, Receita, Categoria)
├── repository      → Interfaces que estendem JpaRepository
├── service         → Lógica de negócio (inclusão, exclusão, listagem, saldo)
└── application.properties → Configuração do banco PostgreSQL
```

Exemplos de funcionalidades expostas:
- `GET /receitas` – Lista todas as receitas
- `POST /despesas` – Cria uma nova despesa
- `DELETE /despesas/limpar` – Remove todas as despesas
- `GET /saldo` – Calcula o saldo atual (total receitas - despesas)
- `DELETE /receitas/{id}` – Remove uma receita específica

---

### Frontend

O frontend se comunica com a API via Axios, utilizando hooks (`useEffect`, `useState`) para buscar dados, exibir na tela e enviar formulários.

Componentes e lógica principais:
- `App.jsx`: Componente principal da aplicação.
- `fetchReceitas`, `fetchDespesas`, `getSaldo`: funções que acessam a API.
- `handleSubmit`, `handleLimparTudo`, `handleRemoverPorId`: ações disparadas pelos botões do usuário.
- Layout responsivo com botões, listas e inputs controlados.

---

## 🛠️ Como rodar localmente

### Pré-requisitos
- Java 17+
- Node.js 18+
- PostgreSQL
- Docker (opcional)
- Git

### 🐘 Banco de Dados

1. Crie um banco de dados no PostgreSQL chamado `financeiro`.
2. Configure `application.properties` no backend com suas credenciais:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/financeiro
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### ▶️ Backend

```bash
cd backend
./mvnw spring-boot:run
```

A API rodará em: `http://localhost:8080`

### 💻 Frontend

```bash
cd frontend
npm install
npm run dev
```

A interface estará em: `http://localhost:5173`

---

## 🧪 Testes com Insomnia / Postman

- Enviar `POST` para `/receitas` ou `/despesas` com body JSON:

```json
{
  "descricao": "Salário",
  "valor": 5000,
  "data": "2025-09-17",
  "categoria": { "id": 1 }
}
```

- Buscar saldo com `GET /saldo`
- Limpar registros com `DELETE /despesas/limpar`
- Remover específico com `DELETE /despesas/{id}`

---

## 📁 Estrutura de Pastas

```
raiz/
├── backend/
│   └── src/main/java/com/ucsal/financeiro/...
│
├── frontend/
│   ├── public/
│   ├── src/
│   │   ├── App.jsx
│   │   └── ...
│   └── vite.config.js
│
└── README.md
```

---

## ✍️ Autor

Projeto desenvolvido por [Álvaro Dultra] como aplicação prática para organização financeira e prática de desenvolvimento fullstack.

---
