
import java.sql.Connection;
import java.util.Scanner;

public class Conexao {

    static Scanner scanner = new Scanner(System.in);
    static Connection conexao = DAO.createConnection();

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.print("\n=== Quase Tudo Gostoso ===");
            System.out.print("\n1 - Criar Receita");
            System.out.print("\n2 - Criar Usuário");
            System.out.print("\n3 - Adicionar Categoria");
            System.out.print("\n4 - Adicionar Ingrediente");
            System.out.print("\n5 - Listar Receitas");
            System.out.print("\n6 - Listar Usuários");
            System.out.print("\n7 - Listar Categorias");
            System.out.print("\n8 - Listar Ingredientes");
            System.out.print("\n0 - Sair");
            System.out.print("\nEscolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpa buffer

            switch (opcao) {
                case 1 ->
                    criarReceita();
                case 2 ->
                    criarUsuario();
                case 3 ->
                    criarCategoria();
                case 4 ->
                    criarIngrediente();
                case 5 -> {
                    try {
                        Receita.listarReceitas(conexao);
                    } catch (Exception e) {
                        System.out.println("Erro ao listar receitas: " + e.getMessage());
                    }
                }
                case 6 -> {
                    try {
                        Usuario.listarUsuarios(conexao);
                    } catch (Exception e) {
                        System.out.println("Erro ao listar receitas: " + e.getMessage());
                    }
                }
                case 7 -> {
                    try {
                        Categoria.listarCategorias(conexao);
                    } catch (Exception e) {
                        System.out.println("Erro ao listar categorias: " + e.getMessage());
                    }
                }
                case 8 -> {
                    try {
                        Ingrediente.listarIngredientes(conexao);
                    } catch (Exception e) {
                        System.out.println("Erro ao listar ingredientes: " + e.getMessage());
                    }
                }
                case 0 ->
                    System.out.println("Encerrando...");
                default ->
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public static void criarUsuario() {
        System.out.println("Nome: ");
        String nome = scanner.nextLine();
        System.out.println("E-mail: ");
        String email = scanner.nextLine();
        System.out.println("Data de Nascimento (YYYY-MM-DD): ");
        String dataNasc = scanner.nextLine();
        System.out.println("CEP (Apenas números): ");
        String cepS = scanner.nextLine();
        int cep = Integer.parseInt(cepS); //tratamento cep int 
        System.out.println("Gênero (M/F): ");
        String generoS = scanner.nextLine().toUpperCase();
        int genero = (generoS.equals("M")) ? 1 : 0;
        boolean eqSenha = false;
        String senha = "";
        String senhaConfirmada = "";
        do {
            System.out.println("Senha: ");
            senha = scanner.nextLine();
            System.out.println("Confirme sua senha:");
            senhaConfirmada = scanner.nextLine();

            if (senha.equals(senhaConfirmada)) {
                eqSenha = true;
            } else {
                System.out.println("Senhas não coincidem. Favor digitar novamente.");
            }
        } while (!eqSenha);

        try {
            Usuario usuario = new Usuario(nome, email, dataNasc, cep, genero, senha);
            usuario.criarUsuario();
            System.out.println("\n");
            System.out.println(usuario.getNome() + " - adicionado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar usuário: " + e.getMessage());
        }
    }

    public static void criarReceita() {
        System.out.println("Título: ");
        String titulo = scanner.nextLine();
        System.out.println("Descrição: ");
        String descricao = scanner.nextLine();

        Receita receita = new Receita(titulo, descricao);
        try {
            receita.criarReceita();
            System.out.println("\n");
            System.out.println("Receita criada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar receita: " + e.getMessage());
        }
    }

    public static void criarIngrediente() {
        System.out.println("Ingrediente: ");
        String nome = scanner.nextLine();

        Ingrediente ingrediente = new Ingrediente(nome);
        try {
            ingrediente.criarIngrediente();
            System.out.println("\n");
            System.out.println("Ingrediente inserido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar ingrediente: " + e.getMessage());
        }
    }

    public static void criarCategoria() {
        System.out.println("Categoria: ");
        String cat = scanner.nextLine();

        Categoria categoria = new Categoria(cat);
        try {
            categoria.criarCategoria();
            System.out.println("\n");
            System.out.println("Categoria inserida com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar categoria: " + e.getMessage());
        }
    }
}

/* DELETE id = 2 */
// PreparedStatement stmt = conexao.prepareStatement(
//     "DELETE FROM usuario WHERE id = ?;"
// );
// stmt.setInt(1, 2);
// stmt.execute();
// imprimirUsuarios(conexao);

/* UPDATE id = 1 */
// stmt = conexao.prepareStatement(
//     "UPDATE usuario SET user_name = ?, name = ?, password = ? WHERE id = ?;"
// );
// stmt.setString(1, "maria.dores");
// stmt.setString(2, "Maria das Dores");
// stmt.setString(3, "123457");
// stmt.setInt(4, 1);
// stmt.execute();
        // imprimirUsuarios(conexao);
