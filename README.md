#  Hot Dog Taz — Backend API

API REST do sistema de gerenciamento da lancheria **Hot Dog Taz**, desenvolvida com Spring Boot e PostgreSQL.

---

##  Descrição

A API permite o gerenciamento completo da lancheria, com dois perfis de acesso:

- **Administrador (ADMIN)** — gerencia cardápio, usuários, ingredientes, categorias, comandas e pedidos
- **Funcionário (CLERK)** — cria e gerencia comandas e pedidos, visualiza fila da cozinha

---

##  Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 17+ | Linguagem principal |
| Spring Boot | 4.0.2 | Framework principal |
| Spring Data JPA | - | Acesso ao banco de dados |
| Spring Security | - | Autenticação e autorização |
| OAuth2 Resource Server | - | Validação de tokens JWT |
| Hibernate | 7.2.1 | ORM (mapeamento objeto-relacional) |
| PostgreSQL | 16 | Banco de dados relacional |
| Keycloak | 24.0 | Servidor de autenticação (SSO) |
| Maven | - | Gerenciador de dependências |
| Docker | - | Containers para banco e Keycloak |

---

##  Diagrama do Banco de Dados

```
CATEGORY ────────────────────────────────────────────────────
├── id (PK)
└── name

INGREDIENT ───────────────────────────────────────────────────
├── id (PK)
├── name
└── active

PRODUCT ──────────────────────────────────────────────────────
├── id (PK)
├── name
├── description
├── price
├── active
├── image_url
└── category_id (FK → CATEGORY)

PRODUCT_INGREDIENT  [chave composta] ─────────────────────────
├── product_id (FK → PRODUCT)
└── ingredient_id (FK → INGREDIENT)

USER ─────────────────────────────────────────────────────────
├── id (PK)
├── name
├── email
├── password
├── active
├── created_at
└── type (ADMIN | CLERK)

COMMAND ──────────────────────────────────────────────────────
├── id (PK)
├── number
├── table_number
├── status
├── total
├── observation
├── opening_date
└── closing_date

REQUEST ──────────────────────────────────────────────────────
├── id (PK)
├── status (CREATED | IN_PREPARATION | READY | COMPLETED | CANCELED)
├── observation
├── order_date
├── command_id (FK → COMMAND)
└── user_id (FK → USER)

REQUEST_ITEM ─────────────────────────────────────────────────
├── id (PK)
├── request_id (FK → REQUEST)
├── product_id (FK → PRODUCT)
├── quantity
├── unit_price
└── observation

REQUEST_ITEM_INGREDIENT  [chave composta] ────────────────────
├── request_item_id (FK → REQUEST_ITEM)
├── ingredient_id (FK → INGREDIENT)
└── action (ADD | REMOVE | REPLACE)

REQUEST_EVENT ────────────────────────────────────────────────
├── id (PK)
├── request_id (FK → REQUEST)
├── previous_status
├── new_status
└── event_date
```

### Relacionamentos

- **CATEGORY** → **PRODUCT**: uma categoria pode ter vários produtos
- **PRODUCT** ↔ **INGREDIENT**: relação many-to-many via `PRODUCT_INGREDIENT`
- **COMMAND** → **REQUEST**: uma comanda pode ter vários pedidos
- **USER** → **REQUEST**: um usuário pode criar vários pedidos
- **REQUEST** → **REQUEST_ITEM**: um pedido pode ter vários itens
- **REQUEST_ITEM** ↔ **INGREDIENT**: relação many-to-many via `REQUEST_ITEM_INGREDIENT` (personaliza ingredientes do item)
- **REQUEST** → **REQUEST_EVENT**: cada mudança de status gera um evento

---

##  Estrutura do Projeto

```
src/main/java/com/hector/hotdogtaz/
│
├── config/
│   ├── CorsConfig.java             # Libera requisições do frontend (localhost:5173)
│   └── SecurityConfig.java         # Define regras de acesso por perfil e valida JWT
│
├── controller/
│   ├── CategoryController.java     # Endpoints de categorias
│   ├── CommandController.java      # Endpoints de comandas
│   ├── IngredientController.java   # Endpoints de ingredientes
│   ├── ItemRequestController.java  # Endpoints de itens de pedido
│   ├── ProductController.java      # Endpoints de produtos
│   ├── RequestController.java      # Endpoints de pedidos
│   └── UserController.java         # Endpoints de usuários
│
├── dto/
│   ├── request/                    # DTOs de entrada (dados que chegam do front)
│   │   ├── category/
│   │   │   ├── CreateCategoryDTO.java
│   │   │   └── UpdateCategoryDTO.java
│   │   ├── command/
│   │   │   ├── CreateCommandDTO.java
│   │   │   └── UpdateCommandDTO.java
│   │   ├── ingredient/
│   │   │   └── IngredientDTO.java
│   │   ├── product/
│   │   │   ├── CreateProductDTO.java
│   │   │   └── UpdateProductDTO.java
│   │   ├── request/
│   │   │   ├── AddItemRequestDTO.java
│   │   │   ├── CreateItemRequestDTO.java
│   │   │   ├── CreateRequestDTO.java
│   │   │   ├── IngredientActionDTO.java
│   │   │   └── UpdateRequestDTO.java
│   │   └── user/
│   │       ├── CreateUserDTO.java
│   │       └── UpdateUserDTO.java
│   │
│   └── response/                   # DTOs de saída (dados que vão para o front)
│       ├── CategoryResponseDTO.java
│       ├── CommandResponseDTO.java
│       ├── ErrorResponseDTO.java
│       ├── IngredientResponseDTO.java
│       ├── ItemRequestResponseDTO.java
│       ├── ProductResponseDTO.java
│       ├── RequestEventResponseDTO.java
│       ├── RequestResponseDTO.java
│       ├── RequestSummaryDTO.java
│       └── UserResponseDTO.java
│
├── exception/
│   ├── BusinessException.java          # Erros de regra de negócio → HTTP 400
│   ├── ResourceNotFoundException.java  # Recurso não encontrado → HTTP 404
│   ├── GlobalExceptionHandler.java     # Centraliza o tratamento de todas as exceptions
│   └── ErrorResponseDTO.java           # Formato padrão do JSON de erro
│
├── mapper/
│   ├── CategoryMapper.java         # Converte Category ↔ CategoryResponseDTO
│   ├── CommandMapper.java          # Converte Command ↔ CommandResponseDTO
│   ├── IngredientMapper.java       # Converte Ingredient ↔ IngredientResponseDTO
│   ├── ItemRequestMapper.java      # Converte ItemRequest ↔ ItemRequestResponseDTO
│   ├── ProductMapper.java          # Converte Product ↔ ProductResponseDTO
│   ├── RequestEventMapper.java     # Converte RequestEvent ↔ RequestEventResponseDTO
│   ├── RequestMapper.java          # Converte Request ↔ RequestResponseDTO
│   └── UserMapper.java             # Converte User ↔ UserResponseDTO
│
├── model/
│   ├── Category.java               # Entidade categoria
│   ├── Command.java                # Entidade comanda
│   ├── Ingredient.java             # Entidade ingrediente
│   ├── ItemRequest.java            # Entidade item de pedido
│   ├── Product.java                # Entidade produto
│   ├── ProductIngredient.java      # Entidade relação produto-ingrediente
│   ├── ProductIngredientId.java    # Chave composta de ProductIngredient
│   ├── Request.java                # Entidade pedido (com enum Status)
│   ├── RequestEvent.java           # Entidade evento de status do pedido
│   ├── RequestItemIngredient.java  # Entidade personalização de ingrediente no item
│   ├── RequestItemIngredientId.java# Chave composta de RequestItemIngredient
│   └── User.java                   # Entidade usuário (com enum UserType)
│
├── repository/
│   ├── CategoryRepository.java     # existsByName()
│   ├── CommandRepository.java      # básico
│   ├── IngredientRepository.java   # básico
│   ├── ItemRequestRepository.java  # básico
│   ├── ProductRepository.java      # findByActiveTrue(), findByIngredientsIngredient()
│   ├── RequestRepository.java      # findByStatus(), findByStatusNot()
│   └── UserRepository.java         # existsByEmail(), findByActiveTrue()
│
├── service/
│   ├── CategoryService.java        # Regras: verifica nome duplicado antes de salvar
│   ├── CommandService.java         # Regras: recalcula total ao fechar comanda
│   ├── IngredientService.java      # Regras: inativação de ingrediente
│   ├── ItemRequestService.java     # Regras: valida status da comanda antes de adicionar item
│   ├── ProductService.java         # Regras: busca categoria e ingredientes por ID
│   ├── RequestService.java         # Regras: cria evento automático a cada mudança de status
│   └── UserService.java            # Regras: valida email duplicado antes de salvar
│
└── HotdogtazApplication.java       # Classe principal — ponto de entrada da aplicação
```

---

##  Como rodar

### Pré-requisitos

- [Java 17+](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)

### 1. Clonar o repositório

```bash
git clone https://github.com/HectorStimer/web-hotdogtaz-back.git
cd web-hotdogtaz-back
```

### 2. Subir os containers com Docker

```bash
docker compose up -d
```

Isso vai subir:
- **PostgreSQL** na porta `5432`
- **Keycloak** na porta `8180`

```bash
docker ps          # verificar se estão rodando
docker compose down  # parar os containers
```

### 3. Configurar o Keycloak

Acessa `http://localhost:8180` com login `admin` / senha `admin123` e siga os passos:

**Criar Realm:**
- Clica em **Keycloak** (canto superior esquerdo) → **Create Realm**
- Nome: `hotdogtaz` → **Create**

**Criar Client:**
- Menu lateral → **Clients** → **Create client**
- Client ID: `hotdogtaz-front` → **Next**
- Ativa **Standard flow** → **Next**
- Valid redirect URIs: `http://localhost:5173/*`
- Web origins: `http://localhost:5173`
- **Save**

**Criar Roles:**
- Menu lateral → **Realm roles** → **Create role**
- Cria: `ADMIN`
- Repete e cria: `CLERK`

**Criar usuário:**
- Menu lateral → **Users** → **Create new user**
- Username: `admin` → **Create**
- Aba **Credentials** → **Set password** → define senha → desativa **Temporary** → **Save**
- Aba **Role mapping** → **Assign role** → seleciona `ADMIN` → **Assign**

### 4. Configurar o `application.yml`

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8180/realms/hotdogtaz
  datasource:
    url: jdbc:postgresql://localhost:5432/hotdogtaz
    username: hotdogtaz
    password: hotdogtaz123
    driver-class-name: org.postgresql.Driver
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update
    show-sql: true
```

### 5. Rodar o projeto

Pelo IntelliJ: clica em **Run** na classe `HotdogtazApplication`

Ou pelo terminal:
```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`

---

##  Endpoints da API

### Categorias `/api/v1/categories`
| Método | Rota | Descrição | Perfil |
|---|---|---|---|
| GET | `/api/v1/categories` | Listar todas | ADMIN, CLERK |
| GET | `/api/v1/categories/{id}` | Buscar por ID | ADMIN, CLERK |
| POST | `/api/v1/categories` | Criar | ADMIN, CLERK |
| PUT | `/api/v1/categories/{id}` | Atualizar | ADMIN, CLERK |
| DELETE | `/api/v1/categories/{id}` | Deletar | ADMIN, CLERK |

### Ingredientes `/api/v1/ingredients`
| Método | Rota | Descrição | Perfil |
|---|---|---|---|
| GET | `/api/v1/ingredients` | Listar todos | ADMIN, CLERK |
| GET | `/api/v1/ingredients/{id}` | Buscar por ID | ADMIN, CLERK |
| POST | `/api/v1/ingredients` | Criar | ADMIN, CLERK |
| PATCH | `/api/v1/ingredients/{id}/deactivate` | Inativar | ADMIN, CLERK |

### Produtos `/api/v1/products`
| Método | Rota | Descrição | Perfil |
|---|---|---|---|
| GET | `/api/v1/products` | Listar todos (paginado) | ADMIN, CLERK |
| GET | `/api/v1/products/active` | Listar ativos (paginado) | ADMIN, CLERK |
| GET | `/api/v1/products/{id}` | Buscar por ID | ADMIN, CLERK |
| POST | `/api/v1/products` | Criar | ADMIN, CLERK |
| PUT | `/api/v1/products/{id}` | Atualizar | ADMIN, CLERK |
| PATCH | `/api/v1/products/{id}/deactivate` | Inativar | ADMIN, CLERK |

### Usuários `/api/v1/users`
| Método | Rota | Descrição | Perfil |
|---|---|---|---|
| GET | `/api/v1/users` | Listar todos | ADMIN |
| GET | `/api/v1/users/{id}` | Buscar por ID | ADMIN |
| GET | `/api/v1/users/status?active=true` | Listar por status | ADMIN |
| POST | `/api/v1/users` | Criar | ADMIN |
| PUT | `/api/v1/users/{id}` | Atualizar | ADMIN |
| PATCH | `/api/v1/users/{id}/deactivate` | Inativar | ADMIN |

### Comandas `/api/v1/commands`
| Método | Rota | Descrição | Perfil |
|---|---|---|---|
| GET | `/api/v1/commands` | Listar todas (paginado) | ADMIN, CLERK |
| GET | `/api/v1/commands/{id}` | Buscar por ID | ADMIN, CLERK |
| POST | `/api/v1/commands` | Abrir comanda | ADMIN, CLERK |
| PUT | `/api/v1/commands/{id}` | Atualizar comanda | ADMIN, CLERK |

### Pedidos `/api/v1/requests`
| Método | Rota | Descrição | Perfil |
|---|---|---|---|
| GET | `/api/v1/requests` | Listar todos | ADMIN, CLERK |
| GET | `/api/v1/requests/{id}` | Buscar por ID | ADMIN, CLERK |
| GET | `/api/v1/requests/queue` | Fila de pedidos ativos | ADMIN, CLERK |
| POST | `/api/v1/requests` | Criar pedido | ADMIN, CLERK |
| PUT | `/api/v1/requests/{id}` | Atualizar status | ADMIN, CLERK |
| POST | `/api/v1/requests/{id}/items` | Adicionar item | ADMIN, CLERK |
| PATCH | `/api/v1/requests/{id}/items/{itemId}` | Atualizar item | ADMIN, CLERK |
| DELETE | `/api/v1/requests/{id}/items/{itemId}` | Remover item | ADMIN, CLERK |

---

##  Autenticação

A API usa **Keycloak** com OAuth2/JWT disponível em `http://localhost:8180`.

Envie o token no header de todas as requisições:
```
Authorization: Bearer <token>
```

Perfis:
- `ADMIN` — acesso total ao sistema
- `CLERK` — acesso restrito (não acessa endpoints de usuários)
