
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Ingrediente {

    private int id;
    private String ingrediente;

    // construtor pro insert
    public Ingrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    // construtor pro select
    public Ingrediente(int id, String ingrediente) {
        this.id = id;
        this.ingrediente = ingrediente;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngrediente() {
        return this.ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    @Override
    public String toString() {
        return "Ingrediente{"
                + "id=" + id
                + ", titulo=" + ingrediente
                + "}";
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Ingrediente)) {
            return false;
        }
        final Ingrediente other = (Ingrediente) object;
        return this.id == other.id;
    }

    public void criarIngrediente() throws Exception {
        Connection conn = DAO.createConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO ingrediente (ingrediente) VALUES (?);"
        );
        stmt.setString(1, this.ingrediente);
        stmt.execute();
        DAO.closeConnection();
    }

    public static void listarIngredientes(Connection conexao) throws Exception {
        ResultSet rs = conexao.createStatement().executeQuery(
                "SELECT * FROM ingrediente;"
        );
        Thread.sleep(500);
        while (rs.next()) {
            Ingrediente ingrediente = new Ingrediente(
                    rs.getInt("idingrediente"),
                    rs.getString("ingrediente")
            );
            System.out.println(ingrediente);
            System.out.println("===================================");
        }
    }
}
