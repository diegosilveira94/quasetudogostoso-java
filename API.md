# Documentação da API - Quase Tudo Gostoso

## Base URL

```
http://localhost:3030
```

## Autenticação

Atualmente a API não requer autenticação. Funcionalidade em desenvolvimento.

---

## Endpoints de Usuários

### 1. Criar Usuário

Cria um novo usuário no sistema.

**Endpoint:** `POST /api/users`

**Headers:**

```
Content-Type: application/json
```

**Request Body:**

```json
{
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "birthDate": "15/01/1990",
  "cep": 12345678,
  "gender": "Masculino",
  "password": "senha123"
}
```

**Campos:**

| Campo     | Tipo   | Obrigatório | Descrição                                    |
| --------- | ------ | ----------- | -------------------------------------------- |
| name      | string | Sim         | Nome completo do usuário                     |
| email     | string | Sim         | Email único (validado para duplicidade)      |
| birthDate | string | Não         | Data de nascimento (formato: **DD/MM/YYYY**) |
| cep       | number | Não         | CEP do usuário                               |
| gender    | string | Não         | "Masculino", "Feminino" ou "Outro"           |
| password  | string | Sim         | Senha do usuário                             |

**Response (200 OK):**

```json
{
  "id": 0,
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "birthDate": "1990-01-15",
  "cep": 12345678,
  "gender": "Masculino",
  "password": "senha123",
  "salt": "XxX",
  "registrationDate": null,
  "uuId": "zZz"
}
```

**Response (400 Bad Request):**

```json
{
  "error": "Email já cadastrado"
}
```

**Exemplo cURL:**

```bash
curl -X POST http://localhost:3030/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao.silva@email.com",
    "birthDate": "15/01/1990",
    "cep": 12345678,
    "gender": "Masculino",
    "password": "senha123"
  }'
```

---

### 2. Listar Todos os Usuários

Retorna a lista completa de usuários cadastrados.

**Endpoint:** `GET /api/users`

**Response (200 OK):**

```json
[
  {
    "id": 1,
    "name": "João Silva",
    "email": "joao.silva@email.com",
    "birthDate": "1990-01-15",
    "cep": 12345678,
    "gender": "Masculino",
    "registrationDate": "2025-11-22 10:30:00",
    "uuId": "abc123-def456"
  },
  {
    "id": 2,
    "name": "Maria Santos",
    "email": "maria.santos@email.com",
    "birthDate": "1995-06-20",
    "cep": 87654321,
    "gender": "Feminino",
    "registrationDate": "2025-11-22 11:15:00",
    "uuId": "xyz789-uvw321"
  }
]
```

**Observação:** O campo `password` não é retornado por segurança.

**Exemplo cURL:**

```bash
curl -X GET http://localhost:3030/api/users
```

---

### 3. Buscar Usuário por ID

Retorna os dados de um usuário específico.

**Endpoint:** `GET /api/users/{id}`

**Parâmetros de URL:**

| Parâmetro | Tipo    | Descrição     |
| --------- | ------- | ------------- |
| id        | integer | ID do usuário |

**Exemplo:** `GET /api/users/1`

**Response (200 OK):**

```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "birthDate": "1990-01-15",
  "cep": 12345678,
  "gender": "Masculino",
  "registrationDate": "2025-11-22 10:30:00",
  "uuId": "abc123-def456"
}
```

**Response (404 Not Found):**

```json
{
  "error": "Usuário não encontrado"
}
```

**Exemplo cURL:**

```bash
curl -X GET http://localhost:3030/api/users/1
```

---

### 4. Atualizar Usuário

Atualiza os dados de um usuário existente.

**Endpoint:** `PUT /api/users/{id}`

**Parâmetros de URL:**

| Parâmetro | Tipo    | Descrição     |
| --------- | ------- | ------------- |
| id        | integer | ID do usuário |

**Headers:**

```
Content-Type: application/json
```

**Request Body:**

```json
{
  "name": "João Silva Atualizado",
  "email": "joao.novo@email.com",
  "birthDate": "15/01/1990"
}
```

**Campos Atualizáveis:**

| Campo     | Tipo   | Descrição                                         |
| --------- | ------ | ------------------------------------------------- |
| name      | string | Novo nome do usuário                              |
| email     | string | Novo email                                        |
| birthDate | string | Nova data de nascimento (formato: **DD/MM/YYYY**) |

⚠️ **Importante:** Apenas os campos `name`, `email` e `birthDate` podem ser atualizados. Outros campos enviados no body serão ignorados.

**Response (200 OK):**

```json
{
  "status": "Usuário atualizado!"
}
```

**Response (400 Bad Request):**

```json
{
  "error": "Erro ao atualizar usuário"
}
```

**Exemplo cURL:**

```bash
curl -X PUT http://localhost:3030/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva Atualizado",
    "email": "joao.novo@email.com",
    "birthDate": "15/01/1990"
  }'
```

---

### 5. Deletar Usuário

Remove um usuário do sistema.

**Endpoint:** `DELETE /api/users/{id}`

**Parâmetros de URL:**

| Parâmetro | Tipo    | Descrição     |
| --------- | ------- | ------------- |
| id        | integer | ID do usuário |

**Response (200 OK):**

```json
{
  "status": "Usuário deletado!"
}
```

**Response (400 Bad Request):**

```json
{
  "error": "Erro ao deletar usuário"
}
```

**Exemplo cURL:**

```bash
curl -X DELETE http://localhost:3030/api/users/1
```

---

## Códigos de Status HTTP

| Código | Status      | Descrição                                      |
| ------ | ----------- | ---------------------------------------------- |
| 200    | OK          | Requisição executada com sucesso               |
| 400    | Bad Request | Dados inválidos, email duplicado ou erro geral |
| 404    | Not Found   | Recurso não encontrado                         |

---

## Regras de Negócio

### Validação de Email

- Emails devem ser únicos no sistema
- Ao tentar cadastrar um email já existente, retorna erro 400

### Campo Gender (Gênero)

O campo `gender` aceita valores em string e é convertido para inteiro no banco:

| Valor Enviado | Valor no Banco | Descrição |
| ------------- | -------------- | --------- |
| "Masculino"   | 1              | Masculino |
| "Feminino"    | 2              | Feminino  |
| "Outro"       | 3              | Outro     |

### Atualização Parcial de Usuário

- Apenas 3 campos podem ser atualizados: `name`, `email`, `birthDate`
- Campos como `password`, `cep`, `gender` não podem ser alterados via PUT
- Para alterar esses campos, será necessário implementar endpoints específicos

---

## Exemplos de Uso Completo

### Fluxo Típico

```bash
# 1. Criar um usuário
curl -X POST http://localhost:3030/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Ana Costa",
    "email": "ana@email.com",
    "birthDate": "10/03/1992",
    "cep": 11223344,
    "gender": "Feminino",
    "password": "senha456"
  }'

# 2. Listar todos os usuários
curl -X GET http://localhost:3030/api/users

# 3. Buscar o usuário criado (assumindo ID = 5)
curl -X GET http://localhost:3030/api/users/5

# 4. Atualizar o nome do usuário
curl -X PUT http://localhost:3030/api/users/5 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Ana Paula Costa",
    "email": "ana@email.com",
    "birthDate": "10/03/1992"
  }'

# 5. Deletar o usuário
curl -X DELETE http://localhost:3030/api/users/5
```

---

## Estrutura do Objeto User

```json
{
  "id": 1, // ID único (auto-incremento)
  "name": "João Silva", // Nome completo
  "email": "joao@email.com", // Email único
  "birthDate": "15/01/1990", // Data nascimento (DD/MM/YYYY no envio)
  "cep": 12345678, // CEP (apenas números)
  "gender": "Masculino", // Masculino/Feminino/Outro
  "password": "senha123", // Senha (apenas no POST)
  "salt": "XxX", // Salt para hash (gerado)
  "registrationDate": "2025-11-22 10:30:00", // Data de cadastro (auto)
  "uuId": "abc123-def456" // UUID único (gerado)
}
```

---

## Notas para Desenvolvedores

- O servidor roda na porta **3030** por padrão (configurável em `App.java`)
- A conexão com MySQL está configurada em `DAO.java`
- **Conversão de Data:** A API aceita datas no formato brasileiro `DD/MM/YYYY` e converte automaticamente para `YYYY-MM-DD` (formato MySQL) via `UserService.formatBirthDateSQL()`
- Senhas não são criptografadas atualmente (funcionalidade em desenvolvimento)
- O campo `salt` está com valor fixo "XxX" (será implementado futuramente)
- O `uuId` está com valor fixo "zZz" no momento do cadastro
