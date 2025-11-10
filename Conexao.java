import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexao {
    public static void main(String[] args) {
        try{
            Connection conexao = DAO.createConnection();
            Usuario usuario = new Usuario("Paulinho da Borracharia", "shaolinMatadorDePorco@email.com", "1994/09/05", 89210040, 1, "cascudo@123", "xXx");
            Receita receita = new Receita("Feijoada", "Só não tem linguiça");

            /* INSERT Usuário */
            // if (isUserEmailTaken(conexao, usuario.getNome())) {
            //     System.out.println("Error: Email '" + usuario.getEmail() + "' already exist. Skipping insert.");
            // } else {
            //     PreparedStatement stmt = conexao.prepareStatement(
            //         "INSERT INTO usuario (user_name, name, password) VALUES (?, ?, ?);"
            //     );
            // stmt.setString(1, usuario.getUserName());
            // stmt.setString(2, usuario.getName());
            // stmt.setString(3, usuario.getPassword());
            // stmt.execute();
            // System.out.println("User inserted sucessfully.");
            // }

            /* SELECT ALL USERS */

            Thread.sleep(1000);
            imprimirUsuarios(conexao);
            imprimirReceitas(conexao);

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
        } catch (Exception e) {
            System.out.println("A database error occurred: " + e.getMessage());
        }
        
    }

    public static void imprimirReceitas(Connection conexao) throws Exception {
        ResultSet rs = conexao.createStatement().executeQuery(
            "SELECT * FROM receita;"
        );
        while(rs.next()) {
            Receita receita2 = new Receita (
                rs.getInt("idreceita"),
                rs.getString("titulo"),
                rs.getString("descricao")
            );
            System.out.println(receita2);
            System.out.println("===================================");
        }
    }

    public static void imprimirUsuarios(Connection conexao) throws Exception {
        ResultSet rs = conexao.createStatement().executeQuery(
            "SELECT * FROM usuario;"
        );
        while(rs.next()){
            Usuario usuario2 = new Usuario(
                rs.getInt("idusuario"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("data_nascimento"),
                rs.getInt("cep"),
                rs.getInt("genero"),
                rs.getString("senha"),
                rs.getString("salt")
            );
            System.out.println(usuario2);
            System.out.println("===================================");
        }
    }
    // Validar se email já existe -> falta imprimir no main
    public static boolean isEmailTaken(Connection conexao, String email) throws SQLException {
        PreparedStatement checkStmt = conexao.prepareStatement(
            "SELECT COUNT(*) FROM usuario WHERE email = ?;"
        );
        checkStmt.setString(1, email);
        ResultSet rs = checkStmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }
}
