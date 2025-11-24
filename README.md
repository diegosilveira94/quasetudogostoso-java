# Quase Tudo Gostoso - Backend Java

API REST para plataforma de compartilhamento de receitas culinárias.

## 📋 Sobre o Projeto

O **Quase Tudo Gostoso** é uma aplicação backend desenvolvida em Java que oferece uma API RESTful para gerenciamento de usuários, receitas, categorias e ingredientes.

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Maven** - Gerenciamento de dependências
- **MySQL** - Banco de dados relacional
- **HttpServer (com.sun.net.httpserver)** - Servidor HTTP nativo
- **GSON** - Serialização/Deserialização JSON
- **JUnit 5** - Testes unitários

## 📁 Estrutura do Projeto

```
quasetudogostoso/
├── src/main/java/com/quasetudogostoso/
│   ├── App.java                        # Classe principal
│   ├── config/
│   │   └── DAO.java                    # Data Access Object
│   ├── controller/                     # Controllers REST
│   │   ├── UserController.java
│   │   ├── RecipeController.java
│   │   ├── CategoryController.java
│   │   ├── IngredientController.java
│   │   └── RecipeIngredientController.java
│   ├── model/                          # Entidades
│   │   ├── User.java
│   │   ├── Recipe.java
│   │   ├── Category.java
│   │   ├── Ingredient.java
│   │   └── RecipeIngredient.java
│   ├── repository/                     # Acesso a dados
│   └── service/                        # Lógica de negócio
├── database/createQTG.sql              # Script do banco
├── pom.xml
└── API.md                              # Documentação completa
```

## 🗄️ Funcionalidades Implementadas

- ✅ **CRUD de Usuários** - Gerenciamento completo de usuários
- ✅ **CRUD de Receitas** - Criação, listagem, atualização e exclusão
- ✅ **CRUD de Categorias** - Gerenciamento com ativação/desativação
- ✅ **CRUD de Ingredientes** - Com busca por nome
- ✅ **Ingredientes de Receitas** - Associação receita-ingrediente-quantidade
- ✅ **Validações** - Email único, autor obrigatório, limites de caracteres

## ⚙️ Configuração e Instalação

### Pré-requisitos

- Java 21 ou superior
- Maven 3.6+
- MySQL 8.0+

### Configuração do Banco

1. Execute o script SQL:

```bash
mysql -u root -p < database/createQTG.sql
```

2. Configure em `DAO.java`:

```java
final String URL = "jdbc:mysql://localhost:3306/quasetudogostoso";
final String USER = "root";
final String PASSWORD = "";
```

### Executando

```bash
# Clonar
git clone https://github.com/diegosilveira94/qtg-java.git
cd quasetudogostoso

# Compilar e executar
mvn clean install
mvn exec:java -Dexec.mainClass="com.quasetudogostoso.App"
```

Servidor: `http://localhost:3030`

## 📡 API Endpoints

| Recurso             | Método | Endpoint                              |
| ------------------- | ------ | ------------------------------------- |
| **Usuários**        | GET    | `/api/users`                          |
|                     | GET    | `/api/users/{id}`                     |
|                     | POST   | `/api/users`                          |
|                     | PUT    | `/api/users/{id}`                     |
|                     | DELETE | `/api/users/{id}`                     |
| **Receitas**        | GET    | `/api/recipes`                        |
|                     | GET    | `/api/recipes/{id}`                   |
|                     | GET    | `/api/recipes?author={userId}`        |
|                     | POST   | `/api/recipes`                        |
|                     | PUT    | `/api/recipes/{id}`                   |
|                     | DELETE | `/api/recipes/{id}`                   |
| **Categorias**      | GET    | `/api/categories`                     |
|                     | GET    | `/api/categories/active`              |
|                     | GET    | `/api/categories/{id}`                |
|                     | POST   | `/api/categories`                     |
|                     | PUT    | `/api/categories/{id}`                |
|                     | DELETE | `/api/categories/{id}`                |
|                     | GET    | `/api/categories/{id}/activate`       |
|                     | GET    | `/api/categories/{id}/deactivate`     |
| **Ingredientes**    | GET    | `/api/ingredients`                    |
|                     | GET    | `/api/ingredients?search={termo}`     |
|                     | GET    | `/api/ingredients/{id}`               |
|                     | POST   | `/api/ingredients`                    |
|                     | PUT    | `/api/ingredients/{id}`               |
|                     | DELETE | `/api/ingredients/{id}`               |
| **Receita-Ingred.** | GET    | `/api/recipes/{id}/ingredients`       |
|                     | POST   | `/api/recipes/{id}/ingredients`       |
|                     | PUT    | `/api/recipes/{id}/ingredients/{ing}` |
|                     | DELETE | `/api/recipes/{id}/ingredients/{ing}` |
|                     | DELETE | `/api/recipes/{id}/ingredients`       |

📄 **Documentação completa:** [API.md](API.md)

## 🧪 Testes

Execute os testes unitários com:

```bash
mvn test
```

## 🏗️ Arquitetura

```
Controller → Service → Repository → DAO → Database
```

- **Controller** - Rotas HTTP e validação de entrada
- **Service** - Regras de negócio e validações
- **Repository** - Acesso e manipulação de dados
- **DAO** - Gerenciamento de conexão
- **Model** - Entidades do domínio

## 📚 Referências

### Estrutura/Arquitetura

- https://yashodharanawaka.medium.com/solid-principles-explained-with-java-and-reactjs-aaba0a9cf6df
- https://dev.to/gustavonunesn/entendendo-service-repository-e-controller-pt-1-1cjk
- https://programming.am/lesson-20-project-building-a-simple-restful-api-with-java-30102f29f8a8
- https://medium.com/@felipe.damasceno.b/padr%C3%B5es-de-projeto-e-o-data-access-object-dao-7d7e4818866c

### Conversão de Datas

- https://pt.stackoverflow.com/questions/108057/como-converter-uma-string-em-data-ou-date
- https://www.w3schools.com/java/java_date.asp

### Autenticação de Usuário

- https://medium.com/@queenlattie720/programming-with-java-user-management-application-part-4-users-and-authentication-54ce735b1876

## 👥 Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.

## 📄 Licença

Este projeto está sob a licença MIT.
