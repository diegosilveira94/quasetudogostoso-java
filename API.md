# 📚 Documentação da API - Quase Tudo Gostoso

**Base URL:** `http://localhost:3030`  
**Autenticação:** Em desenvolvimento

---

## 📑 Índice

- [Usuários](#-usuários)
- [Receitas](#-receitas)
- [Categorias](#-categorias)
- [Ingredientes](#-ingredientes)
- [Ingredientes da Receita](#-ingredientes-da-receita)
- [Códigos HTTP](#-códigos-de-status-http)
- [Regras Gerais](#-regras-gerais)

---

## 👤 Usuários

### 📋 Resumo dos Endpoints

| Método | Endpoint          | Descrição         |
| ------ | ----------------- | ----------------- |
| POST   | `/api/users`      | Criar usuário     |
| GET    | `/api/users`      | Listar todos      |
| GET    | `/api/users/{id}` | Buscar por ID     |
| PUT    | `/api/users/{id}` | Atualizar usuário |
| DELETE | `/api/users/{id}` | Deletar usuário   |

### 1️⃣ Criar Usuário

**`POST /api/users`**

**Request Body:**

```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "birthDate": "15/01/1990",
  "cep": 12345678,
  "gender": "Masculino",
  "password": "senha123"
}
```

| Campo     | Tipo   | Obrigatório | Validação                        |
| --------- | ------ | ----------- | -------------------------------- |
| name      | string | ✅          | Nome completo                    |
| email     | string | ✅          | Email único, deve conter "@"     |
| birthDate | string | ❌          | Formato DD/MM/YYYY               |
| cep       | number | ❌          | Apenas números                   |
| gender    | string | ❌          | "Masculino", "Feminino", "Outro" |
| password  | string | ✅          | Senha do usuário                 |

**Response 200:**

```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@email.com",
  "birthDate": "1990-01-15",
  "gender": "Masculino",
  "registrationDate": "2025-11-24 10:30:00"
}
```

**Response 400:**

```json
{
  "error": "Email já cadastrado"
}
```

### 2️⃣ Listar Usuários

**`GET /api/users`**

**Response 200:**

```json
[
  {
    "id": 1,
    "name": "João Silva",
    "email": "joao@email.com",
    "birthDate": "1990-01-15",
    "gender": "Masculino"
  }
]
```

### 3️⃣ Buscar Usuário por ID

**`GET /api/users/{id}`**

**Response 200:**

```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@email.com",
  "birthDate": "1990-01-15"
}
```

**Response 404:**

```json
{
  "error": "Usuário não encontrado"
}
```

### 4️⃣ Atualizar Usuário

**`PUT /api/users/{id}`**

**Request Body:**

```json
{
  "name": "João Silva Atualizado",
  "email": "joao.novo@email.com",
  "birthDate": "15/01/1990"
}
```

⚠️ Apenas `name`, `email` e `birthDate` podem ser atualizados.

**Response 200:**

```json
{
  "status": "Usuário atualizado!"
}
```

### 5️⃣ Deletar Usuário

**`DELETE /api/users/{id}`**

**Response 200:**

```json
{
  "status": "Usuário deletado!"
}
```

---

## 🍲 Receitas

### 📋 Resumo dos Endpoints

| Método | Endpoint            | Descrição         |
| ------ | ------------------- | ----------------- |
| POST   | `/api/recipes`      | Criar receita     |
| GET    | `/api/recipes`      | Listar todas      |
| GET    | `/api/recipes/{id}` | Buscar por ID     |
| PUT    | `/api/recipes/{id}` | Atualizar receita |
| DELETE | `/api/recipes/{id}` | Deletar receita   |

### 1️⃣ Criar Receita

**`POST /api/recipes`**

**Request Body:**

```json
{
  "title": "Bolo de Chocolate",
  "description": "Delicioso bolo caseiro",
  "imageURL": "https://exemplo.com/bolo.jpg",
  "author": {
    "id": 1
  },
  "idPreparation": 1,
  "idDifficulty": 1,
  "idCost": 1
}
```

| Campo         | Tipo   | Obrigatório | Validação                  |
| ------------- | ------ | ----------- | -------------------------- |
| title         | string | ✅          | 3-100 caracteres           |
| description   | string | ❌          | Descrição da receita       |
| imageURL      | string | ❌          | URL da imagem              |
| author        | object | ✅          | Objeto com ID do usuário   |
| idPreparation | number | ❌          | ID do tempo de preparo     |
| idDifficulty  | number | ❌          | ID do nível de dificuldade |
| idCost        | number | ❌          | ID do custo                |

**Response 200:**

```json
{
  "status": "Receita criada com sucesso!"
}
```

### 2️⃣ Listar Receitas

**`GET /api/recipes`**

Retorna todas as receitas com informações do autor.

**Response 200:**

```json
[
  {
    "id": 1,
    "title": "Bolo de Chocolate",
    "description": "Delicioso bolo caseiro",
    "author": {
      "id": 1,
      "name": "João Silva"
    }
  }
]
```

### 3️⃣ Listar Receitas por Autor

**`GET /api/recipes?author={userId}`**

Retorna receitas de um autor específico.

### 4️⃣ Buscar Receita por ID

**`GET /api/recipes/{id}`**

**Response 200:**

```json
{
  "id": 1,
  "title": "Bolo de Chocolate",
  "description": "Delicioso bolo caseiro",
  "author": {
    "id": 1,
    "name": "João Silva"
  }
}
```

**Response 404:**

```json
{
  "error": "Receita não encontrada"
}
```

### 5️⃣ Atualizar Receita

**`PUT /api/recipes/{id}`**

**Request Body:**

```json
{
  "title": "Bolo de Chocolate Premium",
  "description": "Nova descrição",
  "imageURL": "nova-url.jpg",
  "idPreparation": 2,
  "idDifficulty": 2,
  "idCost": 2
}
```

**Response 200:**

```json
{
  "status": "Receita atualizada!"
}
```

### 6️⃣ Deletar Receita

**`DELETE /api/recipes/{id}`**

**Response 200:**

```json
{
  "status": "Receita deletada!"
}
```

---

## 📂 Categorias

### 📋 Resumo dos Endpoints

| Método | Endpoint                          | Descrição           |
| ------ | --------------------------------- | ------------------- |
| POST   | `/api/categories`                 | Criar categoria     |
| GET    | `/api/categories`                 | Listar todas        |
| GET    | `/api/categories/active`          | Listar ativas       |
| GET    | `/api/categories/{id}`            | Buscar por ID       |
| PUT    | `/api/categories/{id}`            | Atualizar categoria |
| DELETE | `/api/categories/{id}`            | Deletar categoria   |
| GET    | `/api/categories/{id}/activate`   | Ativar categoria    |
| GET    | `/api/categories/{id}/deactivate` | Desativar categoria |

### 1️⃣ Criar Categoria

**`POST /api/categories`**

**Request Body:**

```json
{
  "category": "Doces",
  "active": true
}
```

| Campo    | Tipo    | Obrigatório | Validação       |
| -------- | ------- | ----------- | --------------- |
| category | string  | ✅          | 3-80 caracteres |
| active   | boolean | ❌          | true/false      |

**Validações:**

- Nome único (não duplicado)
- 3-80 caracteres obrigatórios

**Response 200:**

```json
{
  "status": "Categoria criada!"
}
```

**Response 400:**

```json
{
  "error": "Categoria já existe"
}
```

### 2️⃣ Listar Categorias

**`GET /api/categories`**

Retorna todas as categorias (ativas e inativas).

**Response 200:**

```json
[
  {
    "id": 1,
    "category": "Doces",
    "active": true
  },
  {
    "id": 2,
    "category": "Salgados",
    "active": false
  }
]
```

### 3️⃣ Listar Categorias Ativas

**`GET /api/categories/active`**

Retorna apenas categorias ativas.

**Response 200:**

```json
[
  {
    "id": 1,
    "category": "Doces",
    "active": true
  }
]
```

### 4️⃣ Buscar Categoria por ID

**`GET /api/categories/{id}`**

**Response 200:**

```json
{
  "id": 1,
  "category": "Doces",
  "active": true
}
```

**Response 404:**

```json
{
  "error": "Categoria não encontrada"
}
```

### 5️⃣ Atualizar Categoria

**`PUT /api/categories/{id}`**

**Request Body:**

```json
{
  "category": "Sobremesas",
  "active": true
}
```

**Response 200:**

```json
{
  "status": "Categoria atualizada!"
}
```

### 6️⃣ Deletar Categoria

**`DELETE /api/categories/{id}`**

**Response 200:**

```json
{
  "status": "Categoria deletada!"
}
```

### 7️⃣ Ativar Categoria

**`GET /api/categories/{id}/activate`**

Define `active = true`.

**Response 200:**

```json
{
  "status": "Categoria ativada!"
}
```

### 8️⃣ Desativar Categoria

**`GET /api/categories/{id}/deactivate`**

Define `active = false`.

**Response 200:**

```json
{
  "status": "Categoria desativada!"
}
```

---

## 🥕 Ingredientes

### 📋 Resumo dos Endpoints

| Método | Endpoint                      | Descrição             |
| ------ | ----------------------------- | --------------------- |
| POST   | `/api/ingredients`            | Criar ingrediente     |
| GET    | `/api/ingredients`            | Listar todos          |
| GET    | `/api/ingredients?search={q}` | Buscar por nome       |
| GET    | `/api/ingredients/{id}`       | Buscar por ID         |
| PUT    | `/api/ingredients/{id}`       | Atualizar ingrediente |
| DELETE | `/api/ingredients/{id}`       | Deletar ingrediente   |

### 1️⃣ Criar Ingrediente

**`POST /api/ingredients`**

**Request Body:**

```json
{
  "ingredient": "Farinha de trigo"
}
```

| Campo      | Tipo   | Obrigatório | Validação       |
| ---------- | ------ | ----------- | --------------- |
| ingredient | string | ✅          | 2-90 caracteres |

**Validações:**

- Nome único (não duplicado)
- 2-90 caracteres obrigatórios

**Response 200:**

```json
{
  "status": "Ingrediente criado!"
}
```

**Response 400:**

```json
{
  "error": "Ingrediente já existe"
}
```

### 2️⃣ Listar Ingredientes

**`GET /api/ingredients`**

Retorna todos os ingredientes cadastrados.

**Response 200:**

```json
[
  {
    "id": 1,
    "ingredient": "Farinha de trigo"
  },
  {
    "id": 2,
    "ingredient": "Açúcar"
  }
]
```

### 3️⃣ Buscar por Nome

**`GET /api/ingredients?search={termo}`**

Busca ingredientes que contenham o termo (LIKE %termo%).

**Exemplo:** `/api/ingredients?search=fari`

**Response 200:**

```json
[
  {
    "id": 1,
    "ingredient": "Farinha de trigo"
  },
  {
    "id": 5,
    "ingredient": "Farinha de milho"
  }
]
```

### 4️⃣ Buscar Ingrediente por ID

**`GET /api/ingredients/{id}`**

**Response 200:**

```json
{
  "id": 1,
  "ingredient": "Farinha de trigo"
}
```

**Response 404:**

```json
{
  "error": "Ingrediente não encontrado"
}
```

### 5️⃣ Atualizar Ingrediente

**`PUT /api/ingredients/{id}`**

**Request Body:**

```json
{
  "ingredient": "Farinha de trigo integral"
}
```

**Response 200:**

```json
{
  "status": "Ingrediente atualizado!"
}
```

### 6️⃣ Deletar Ingrediente

**`DELETE /api/ingredients/{id}`**

**Response 200:**

```json
{
  "status": "Ingrediente deletado!"
}
```

---

## 🥘 Ingredientes da Receita

**Base:** `/api/recipes/{recipeId}/ingredients`

### 📋 Resumo dos Endpoints

| Método | Endpoint                                             | Descrição             |
| ------ | ---------------------------------------------------- | --------------------- |
| POST   | `/api/recipes/{recipeId}/ingredients`                | Adicionar ingrediente |
| GET    | `/api/recipes/{recipeId}/ingredients`                | Listar ingredientes   |
| PUT    | `/api/recipes/{recipeId}/ingredients/{ingredientId}` | Atualizar quantidade  |
| DELETE | `/api/recipes/{recipeId}/ingredients/{ingredientId}` | Remover ingrediente   |
| DELETE | `/api/recipes/{recipeId}/ingredients`                | Remover todos         |

### 1️⃣ Adicionar Ingrediente à Receita

**`POST /api/recipes/{recipeId}/ingredients`**

**Request Body:**

```json
{
  "ingredient": {
    "id": 5
  },
  "quantity": 2.5,
  "idMeasurement": 1
}
```

| Campo         | Tipo   | Obrigatório | Validação                    |
| ------------- | ------ | ----------- | ---------------------------- |
| ingredient    | object | ✅          | Objeto com ID do ingrediente |
| quantity      | number | ✅          | Maior que zero               |
| idMeasurement | number | ✅          | ID da unidade de medida      |

**Validações:**

- Receita e ingrediente devem existir
- Quantidade > 0
- Ingrediente não pode estar duplicado na receita

**Response 200:**

```json
{
  "status": "Ingrediente adicionado à receita!"
}
```

**Response 400:**

```json
{
  "error": "Ingrediente já existe nesta receita"
}
```

### 2️⃣ Listar Ingredientes da Receita

**`GET /api/recipes/{recipeId}/ingredients`**

Retorna todos os ingredientes da receita com quantidades.

**Response 200:**

```json
[
  {
    "ingredient": {
      "id": 1,
      "ingredient": "Farinha de trigo"
    },
    "quantity": 2.5,
    "idMeasurement": 1
  },
  {
    "ingredient": {
      "id": 3,
      "ingredient": "Açúcar"
    },
    "quantity": 1.0,
    "idMeasurement": 1
  }
]
```

### 3️⃣ Atualizar Quantidade

**`PUT /api/recipes/{recipeId}/ingredients/{ingredientId}`**

**Request Body:**

```json
{
  "quantity": 3.0,
  "idMeasurement": 2
}
```

**Response 200:**

```json
{
  "status": "Ingrediente atualizado!"
}
```

### 4️⃣ Remover Ingrediente

**`DELETE /api/recipes/{recipeId}/ingredients/{ingredientId}`**

Remove um ingrediente específico da receita.

**Response 200:**

```json
{
  "status": "Ingrediente removido!"
}
```

### 5️⃣ Remover Todos os Ingredientes

**`DELETE /api/recipes/{recipeId}/ingredients`**

Remove todos os ingredientes da receita.

**Response 200:**

```json
{
  "status": "Todos os ingredientes removidos!"
}
```

---

## 📊 Códigos de Status HTTP

| Código | Significado            | Quando Ocorre                               |
| ------ | ---------------------- | ------------------------------------------- |
| 200    | ✅ Sucesso             | Operação realizada com sucesso              |
| 400    | ⚠️ Requisição Inválida | Dados inválidos, duplicados ou faltantes    |
| 404    | ❌ Não Encontrado      | Recurso (usuário, receita, etc.) não existe |

---

## 📐 Regras Gerais

### Formato de Datas

- **Entrada:** `DD/MM/YYYY` (formato brasileiro)
- **Saída:** `YYYY-MM-DD` (formato MySQL)
- Exemplo: `15/01/1990` → `1990-01-15`

### Campo Gender

Conversão string → integer no banco:

| Valor Enviado | Valor no Banco |
| ------------- | -------------- |
| "Masculino"   | 1              |
| "Feminino"    | 2              |
| "Outro"       | 3              |

### Validações Comuns

- **Email único:** Não permite duplicatas
- **Nomes únicos:** Categorias e ingredientes não podem duplicar
- **Autor obrigatório:** Receitas devem ter um autor válido
- **Quantidade > 0:** Ingredientes da receita devem ter quantidade positiva

### Configuração

- **Porta:** `3030` (configurável em `App.java`)
- **Banco:** MySQL configurado em `DAO.java`
- **Headers:** `Content-Type: application/json` em todas as requisições POST/PUT

---

## 🚀 Exemplos de Uso

### Fluxo Completo: Criar Receita com Ingredientes

```bash
# 1. Criar usuário (autor)
curl -X POST http://localhost:3030/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Ana Silva",
    "email": "ana@email.com",
    "password": "senha123"
  }'

# 2. Criar categoria
curl -X POST http://localhost:3030/api/categories \
  -H "Content-Type: application/json" \
  -d '{
    "category": "Doces",
    "active": true
  }'

# 3. Criar ingredientes
curl -X POST http://localhost:3030/api/ingredients \
  -H "Content-Type: application/json" \
  -d '{"ingredient": "Farinha de trigo"}'

curl -X POST http://localhost:3030/api/ingredients \
  -H "Content-Type: application/json" \
  -d '{"ingredient": "Açúcar"}'

curl -X POST http://localhost:3030/api/ingredients \
  -H "Content-Type: application/json" \
  -d '{"ingredient": "Ovos"}'

# 4. Criar receita
curl -X POST http://localhost:3030/api/recipes \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Bolo de Chocolate",
    "description": "Delicioso bolo caseiro",
    "imageURL": "https://exemplo.com/bolo.jpg",
    "author": {"id": 1},
    "idPreparation": 1,
    "idDifficulty": 1,
    "idCost": 1
  }'

# 5. Adicionar ingredientes à receita
curl -X POST http://localhost:3030/api/recipes/1/ingredients \
  -H "Content-Type: application/json" \
  -d '{
    "ingredient": {"id": 1},
    "quantity": 2.5,
    "idMeasurement": 1
  }'

curl -X POST http://localhost:3030/api/recipes/1/ingredients \
  -H "Content-Type: application/json" \
  -d '{
    "ingredient": {"id": 2},
    "quantity": 1.0,
    "idMeasurement": 1
  }'

# 6. Buscar receita completa
curl -X GET http://localhost:3030/api/recipes/1

# 7. Listar ingredientes da receita
curl -X GET http://localhost:3030/api/recipes/1/ingredients
```

### Buscar Receitas por Autor

```bash
curl -X GET http://localhost:3030/api/recipes?author=1
```

### Buscar Ingredientes

```bash
# Listar todos
curl -X GET http://localhost:3030/api/ingredients

# Buscar por nome
curl -X GET http://localhost:3030/api/ingredients?search=farinha
```

### Gerenciar Categorias

```bash
# Listar apenas ativas
curl -X GET http://localhost:3030/api/categories/active

# Desativar categoria
curl -X GET http://localhost:3030/api/categories/1/deactivate

# Reativar categoria
curl -X GET http://localhost:3030/api/categories/1/activate
```
