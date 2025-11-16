
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Categoria {

    private int id;
    private String categoria;
    private int ativo;

    // construtor pro insert
    public Categoria(String categoria) {
        this.categoria = categoria;
    }

    // construtor pro select
    public Categoria(int id, String categoria, int ativo) {
        this.id = id;
        this.categoria = categoria;
        this.ativo = 0;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int isAtivo() {
        return this.ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Categoria{"
                + "id=" + id
                + ", categoria=" + categoria
                + "}";
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Categoria)) {
            return false;
        }
        final Categoria other = (Categoria) object;
        return this.id == other.id;
    }

    public void criarCategoria() throws Exception {
        Connection conn = DAO.createConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO categoria (categoria, ativo) VALUES (?, ?);"
        );
        stmt.setString(1, this.categoria);
        stmt.setInt(2, this.ativo);
        stmt.execute();
        DAO.closeConnection();
    }

    public static void listarCategorias(Connection conexao) throws Exception {
        ResultSet rs = conexao.createStatement().executeQuery(
                "SELECT * FROM categoria;"
        );
        Thread.sleep(500);
        while (rs.next()) {
            Categoria categoria = new Categoria(
                    rs.getInt("idcategoria"),
                    rs.getString("categoria"),
                    rs.getInt("ativo")
            );
            System.out.println(categoria);
            System.out.println("===================================");
        }
    }
}
