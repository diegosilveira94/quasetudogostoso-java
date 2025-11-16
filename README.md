# Quase Tudo Gostoso - Sistema de Receitas

## Descrição

Projeto acadêmico de um sistema de gerenciamento de receitas culinárias desenvolvido em Java. Este é um backend simples que demonstra conceitos de Programação Orientada a Objetos e integração com banco de dados MySQL.

## Tecnologias Utilizadas

- Java (JDK 24)
- MySQL/MariaDB
- JDBC (Java Database Connectivity)

## Pré-requisitos

- JDK 24 ou superior instalado
- MySQL ou MariaDB instalado e em execução
- Usuário root do MySQL configurado (sem senha, ou ajuste as credenciais em `DAO.java`)

## Instruções de Instalação e Execução

### 1. Configurar o Banco de Dados

Execute o script SQL para criar o banco de dados e as tabelas necessárias:

```bash
mysql -u root -p < scripts/createQTG.sql
```

Ou importe o arquivo `scripts/createQTG.sql` através do MySQL Workbench ou phpMyAdmin.

Este script irá:

- Criar o schema `quasetudogostoso`
- Criar todas as tabelas necessárias (usuario, receita, ingrediente, etc.)
- Configurar as relações entre as tabelas

### 2. Verificar as Credenciais do Banco

Abra o arquivo `DAO.java` e verifique se as credenciais estão corretas:

```java
final String URL = "jdbc:mysql://localhost:3306/quasetudogostoso";
final String USER = "root";
final String PASSWORD = ""; // Ajuste se necessário
```

### 3. Executar o Programa

Execute a classe principal `Conexao.java`:

```bash
javac -cp .:lib/* Conexao.java
java -cp .:lib/* Conexao
```

Ou utilize sua IDE favorita (VS Code, IntelliJ, Eclipse) e execute o método `main` da classe `Conexao`.

## Funcionalidades

O sistema oferece um menu interativo com as seguintes opções:

1. **Criar Receita** - Cadastrar uma nova receita no banco de dados
2. **Criar Usuário** - Cadastrar um novo usuário
3. **Adicionar Categoria** - Cadastrar novas categorias de receitas
4. **Adicionar Ingrediente** - Cadastrar novos ingredientes
5. **Listar Receitas** - Visualizar todas as receitas cadastradas
6. **Listar Usuários** - Visualizar todos os usuários cadastrados
7. **Listar Categorias** - Visualizar todas as categorias cadastradas
8. **Listar Ingredientes** - Visualizar todos os ingredientes

## Autores

<table>
  <tr align="center">
    <td>
      <a href="https://github.com/diegosilveira94">
        <img src="https://github.com/diegosilveira94.png" width="80" height="80"><br>
        Diego Silveira
      </a>
    </td>
    <td>
      <a href="https://github.com/daniloz-c">
        <img src="https://github.com/daniloz-c.png" width="80" height="80"><br>
        Danilo Cesar
      </a>
    </td>
  </tr>
</table>
