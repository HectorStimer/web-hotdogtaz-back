#  Hot Dog Taz — Backend API

Sistema de gerenciamento de pedidos para a lancheria **Hot Dog Taz**, desenvolvido com Spring Boot, React e PostgreSQL.

---

##  Descrição

A API permite o gerenciamento completo da lancheria, com dois perfis de acesso:

- **Administrador** — gerencia cardápio, usuários, relatórios e fila de pedidos
- **Funcionário** — cria e gerencia pedidos, visualiza fila

---

## Tecnologias

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security + Keycloak (OAuth2/JWT)
- PostgreSQL
- Maven

---

##  Diagrama do Banco

```
CATEGORY
├── id (PK)
└── name

INGREDIENT
├── id (PK)
├── name
└── active

PRODUCT
├── id (PK)
├── name
├── description
├── price
├── active
├── image_url
└── category_id (FK → CATEGORY)

PRODUCT_INGREDIENT
├── product_id (FK → PRODUCT)
└── ingredient_id (FK → INGREDIENT)

USER
├── id (PK)
├── name
├── email
├── password
├── active
├── created_at
└── type (ADMIN | CLERK)

COMMAND
├── id (PK)
├── number
├── table_number
├── status
├── total
├── observation
├── opening_date
└── closing_date

REQUEST
├── id (PK)
├── status
├── observation
├── order_date
├── command_id (FK → COMMAND)
└── user_id (FK → USER)

REQUEST_ITEM
├── id (PK)
├── request_id (FK → REQUEST)
├── product_id (FK → PRODUCT)
├── quantity
├── unit_price
└── observation

REQUEST_ITEM_INGREDIENT
├── request_item_id (FK → REQUEST_ITEM)
├── ingredient_id (FK → INGREDIENT)
└── action (ADD | REMOVE | REPLACE)

REQUEST_EVENT
├── id (PK)
├── request_id (FK → REQUEST)
├── previous_status
├── new_status
└── event_date
```

---

##  Como rodar localmente

### Pré-requisitos

- Java 17+
- Maven
- PostgreSQL
- Keycloak (opcional para desenvolvimento)

### 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/hotdogtaz.git
cd hotdogtaz
```

### 2. Configurar o banco de dados

Crie o banco no PostgreSQL:

```sql
CREATE DATABASE hotdogtaz;
```

### 3. Configurar o `application.yml`

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hotdogtaz
    username: seu_usuario
    password: sua_senha
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### 4. Rodar o projeto

```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`

---

## 📡 Endpoints da API

### Categorias `/api/v1/categories`

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/v1/categories` | Listar todas |
| GET | `/api/v1/categories/{id}` | Buscar por ID |
| POST | `/api/v1/categories` | Criar categoria |
| PUT | `/api/v1/categories/{id}` | Atualizar categoria |
| DELETE | `/api/v1/categories/{id}` | Deletar categoria |

### Ingredientes `/api/v1/ingredients`

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/v1/ingredients` | Listar todos |
| GET | `/api/v1/ingredients/{id}` | Buscar por ID |
| POST | `/api/v1/ingredients` | Criar ingrediente |
| PATCH | `/api/v1/ingredients/{id}/deactivate` | Inativar ingrediente |

### Produtos `/api/v1/products`

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/v1/products` | Listar todos (paginado) |
| GET | `/api/v1/products/active` | Listar ativos (paginado) |
| GET | `/api/v1/products/{id}` | Buscar por ID |
| POST | `/api/v1/products` | Criar produto |
| PUT | `/api/v1/products/{id}` | Atualizar produto |
| PATCH | `/api/v1/products/{id}/deactivate` | Inativar produto |

### Usuários `/api/v1/users`

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/v1/users` | Listar todos |
| GET | `/api/v1/users/{id}` | Buscar por ID |
| GET | `/api/v1/users/status?active=true` | Listar por status |
| POST | `/api/v1/users` | Criar usuário |
| PUT | `/api/v1/users/{id}` | Atualizar usuário |
| PATCH | `/api/v1/users/{id}/deactivate` | Inativar usuário |

### Comandas `/api/v1/commands`

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/v1/commands` | Listar todas (paginado) |
| GET | `/api/v1/commands/{id}` | Buscar por ID |
| POST | `/api/v1/commands` | Abrir comanda |
| PUT | `/api/v1/commands/{id}` | Atualizar comanda |

### Pedidos `/api/v1/requests`

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/v1/requests` | Listar todos |
| GET | `/api/v1/requests/{id}` | Buscar por ID |
| GET | `/api/v1/requests/queue` | Fila de pedidos ativos |
| POST | `/api/v1/requests` | Criar pedido |
| PUT | `/api/v1/requests/{id}` | Atualizar status |
| POST | `/api/v1/requests/{id}/items` | Adicionar item |
| PATCH | `/api/v1/requests/{id}/items/{itemId}` | Atualizar item |
| DELETE | `/api/v1/requests/{id}/items/{itemId}` | Remover item |

---

## 🔐 Autenticação

A API utiliza **Keycloak** para autenticação via OAuth2/JWT.

Perfis de acesso:
- `ADMIN` — acesso total
- `CLERK` — acesso restrito a pedidos e comandas

Para autenticar, envie o token JWT no header:
```
Authorization: Bearer <token>
```

---

## 📁 Estrutura do Projeto

```
src/main/java/com/hector/hotdogtaz/
├── config/          # Configurações (Security)
├── controller/      # Controllers REST
├── dto/
│   ├── request/     # DTOs de entrada
│   └── response/    # DTOs de saída
├── mapper/          # Mapeamento entidade ↔ DTO
├── model/           # Entidades JPA
├── repository/      # Repositórios Spring Data
└── service/         # Regras de negócio
```
