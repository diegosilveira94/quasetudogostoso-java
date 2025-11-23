# Quase Tudo Gostoso - Backend Java

API REST para plataforma de compartilhamento de receitas culinárias.

## 📋 Sobre o Projeto

O **Quase Tudo Gostoso** é uma aplicação backend desenvolvida em Java que oferece uma API RESTful para gerenciamento de usuários. O projeto está em desenvolvimento inicial e futuramente incluirá funcionalidades para receitas culinárias, comentários e categorias.

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
├── src/
│   ├── main/java/com/quasetudogostoso/
│   │   ├── App.java                 # Classe principal
│   │   ├── config/
│   │   │   └── DAO.java             # Data Access Object
│   │   ├── controller/
│   │   │   └── UserController.java  # Controlador REST de usuários
│   │   ├── model/
│   │   │   └── User.java            # Entidade de usuário
│   │   ├── repository/
│   │   │   └── UserRepository.java  # Camada de acesso a dados
│   │   └── service/
│   │       └── UserService.java     # Lógica de negócio
│   └── test/java/                   # Testes unitários
├── database/
│   └── createQTG.sql                # Script de criação do banco
├── pom.xml                           # Configuração Maven
├── README.md                         # Documentação geral
└── API.md                            # Documentação dos endpoints
```

## 🗄️ Funcionalidades Implementadas

- ✅ **CRUD de Usuários** - Criação, listagem, atualização e exclusão
- ✅ **Validação de Email** - Verifica duplicidade no cadastro
- ✅ **Conversão de Gênero** - String ↔ Integer no banco de dados
- 🚧 **Receitas, Ingredientes e Categorias** - Em desenvolvimento

## ⚙️ Configuração e Instalação

### Pré-requisitos

- Java 21 ou superior
- Maven 3.6+
- MySQL 8.0+

### Configuração do Banco de Dados

1. Crie o banco de dados executando o script SQL:

```bash
mysql -u root -p < database/createQTG.sql
```

2. Configure as credenciais do banco em `DAO.java`:

```java
final String URL = "jdbc:mysql://localhost:3306/quasetudogostoso";
final String USER = "root";
final String PASSWORD = "";
```

### Executando o Projeto

1. Clone o repositório:

```bash
git clone https://github.com/diegosilveira94/qtg-java.git
cd quasetudogostoso
```

2. Compile o projeto:

```bash
mvn clean install
```

3. Execute a aplicação:

```bash
mvn exec:java -Dexec.mainClass="com.quasetudogostoso.App"
```

O servidor será iniciado em `http://localhost:3030`

## 📡 API

A documentação completa dos endpoints está disponível em **[API.md](API.md)**.

### Endpoints Disponíveis

| Método | Endpoint          | Descrição                |
| ------ | ----------------- | ------------------------ |
| POST   | `/api/users`      | Criar novo usuário       |
| GET    | `/api/users`      | Listar todos os usuários |
| GET    | `/api/users/{id}` | Buscar usuário por ID    |
| PUT    | `/api/users/{id}` | Atualizar usuário        |
| DELETE | `/api/users/{id}` | Deletar usuário          |

**Exemplo Rápido:**

```bash
# Criar usuário
curl -X POST http://localhost:3030/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@email.com",
    "birthDate": "15/01/1990",
    "cep": 12345678,
    "gender": "Masculino",
    "password": "senha123"
  }'
```

## 🧪 Testes

Execute os testes unitários com:

```bash
mvn test
```

## 🏗️ Arquitetura

O projeto utiliza uma arquitetura em camadas seguindo princípios SOLID:

```
Controller → Service → Repository → DAO → Database
```

- **Controller** - Gerencia requisições HTTP e respostas
- **Service** - Implementa regras de negócio e validações
- **Repository** - Acessa e manipula dados no banco
- **DAO** - Gerencia conexão com o banco de dados
- **Model** - Define as entidades do domínio

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

## 👨‍💻 Autor

Diego Silveira - [@diegosilveira94](https://github.com/diegosilveira94)
