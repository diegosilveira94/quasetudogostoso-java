# Quase Tudo Gostoso - Backend Java

API REST para plataforma de compartilhamento de receitas culinárias.

## 📋 Sobre o Projeto

O **Quase Tudo Gostoso** é uma aplicação backend desenvolvida em Java que oferece uma API RESTful para gerenciamento de receitas culinárias, usuários, comentários e categorias. O sistema permite que usuários compartilhem suas receitas, avaliem receitas de outros usuários e organizem conteúdo por categorias, tipos de refeição e cozinhas regionais.

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
│   ├── main/
│   │   └── java/
│   │       └── com/quasetudogostoso/
│   │           ├── App.java                 # Classe principal
│   │           ├── config/
│   │           │   └── DAO.java             # Data Access Object
│   │           ├── controller/
│   │           │   └── UserController.java  # Controlador de usuários
│   │           ├── dto/                     # Data Transfer Objects
│   │           ├── model/
│   │           │   └── User.java            # Modelo de usuário
│   │           ├── repository/
│   │           │   └── UserRepository.java  # Repositório de usuários
│   │           └── service/
│   │               └── UserService.java     # Serviço de usuários
│   └── test/
├── database/
│   └── createQTG.sql                        # Script de criação do banco
├── pom.xml                                   # Configuração Maven
└── README.md
```

## 🗄️ Modelo de Dados

O sistema gerencia as seguintes entidades principais:

- **Usuários** - Cadastro e autenticação
- **Receitas** - Informações detalhadas de receitas
- **Ingredientes** - Ingredientes utilizados nas receitas
- **Categorias** - Classificação de receitas (Doces, Salgados, etc.)
- **Comentários** - Avaliações e comentários de usuários
- **Preparos** - Modo de preparo e tempo de preparo
- **Utensílios** - Utensílios necessários para cada receita
- **Cozinhas** - Tipos de cozinha (Brasileira, Italiana, etc.)
- **Refeições** - Tipos de refeição (Café da manhã, Almoço, etc.)

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

## 📡 Endpoints da API

### Usuários

#### POST /users

Cria um novo usuário.

**Exemplo de Request Body:**

```json
{
  "nome": "Diego",
  "email": "diego@gmail.com",
  "DataNasc": "05/09/1994",
  "cep": 89210040,
  "genero": "M",
  "senha": "senha123"
}
```

#### GET /users

Lista todos os usuários.

#### GET /users/{id}

Obtém um usuário específico.

#### PUT /users/{id}

Atualiza um usuário existente.

#### DELETE /users/{id}

Remove um usuário.

## 🧪 Testes

Execute os testes unitários com:

```bash
mvn test
```

## 🏗️ Padrões de Arquitetura

O projeto segue os princípios SOLID e utiliza uma arquitetura em camadas:

- **Controller** - Recebe requisições HTTP e retorna respostas
- **Service** - Contém a lógica de negócio
- **Repository** - Gerencia acesso aos dados
- **DAO** - Padrão de acesso a dados
- **DTO** - Transferência de dados entre camadas
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

## 👨‍💻 Autor

Diego Silveira - [@diegosilveira94](https://github.com/diegosilveira94)
