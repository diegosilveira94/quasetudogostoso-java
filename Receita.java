
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Receita {

    private int id;
    private String titulo;
    private String descricao;
    private String imagem;
    private int idUsuario;
    private int idPreparo;
    private int idDificuldade;
    private int idCusto;

    // construtor pro insert
    public Receita(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagem = "receita.jpeg";
        this.idUsuario = 1;
        this.idPreparo = 1;
        this.idDificuldade = 1;
        this.idCusto = 1;
    }

    // construtor pro select
    public Receita(int id, String titulo, String descricao, String imagem, int idUsuario, int idPreparo, int idDificuldade, int idCusto) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagem = imagem;
        this.idUsuario = idUsuario;
        this.idPreparo = idPreparo;
        this.idDificuldade = idDificuldade;
        this.idCusto = idCusto;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPreparo() {
        return idPreparo;
    }

    public void setIdPreparo(int idPreparo) {
        this.idPreparo = idPreparo;
    }

    public int getIdDificuldade() {
        return idDificuldade;
    }

    public void setIdDificuldade(int idDificuldade) {
        this.idDificuldade = idDificuldade;
    }

    public int getIdCusto() {
        return idCusto;
    }

    public void setIdCusto(int idCusto) {
        this.idCusto = idCusto;
    }

    @Override
    public String toString() {
        return "Receita{"
                + "id=" + id
                + ", titulo=" + titulo
                + ", descricao=" + descricao
                + ", imagem=" + imagem
                + ", idUsuario=" + idUsuario
                + ", idPreparo=" + idPreparo
                + ", idDificuldade=" + idDificuldade
                + ", idCusto=" + idCusto
                + "}";
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Receita)) {
            return false;
        }
        final Receita other = (Receita) object;
        return this.id == other.id;
    }

    public void criarReceita() throws Exception {
        Connection conn = DAO.createConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO receita (titulo, descricao, cadastro_idusuario, preparo_idpreparo, dificuldade_iddificuldade, custo_idcusto) VALUES (?, ?, ?, ?, ?, ?);"
        );
        stmt.setString(1, this.titulo);
        stmt.setString(2, this.descricao);
        stmt.setInt(3, this.idUsuario);
        stmt.setInt(4, this.idPreparo);
        stmt.setInt(5, this.idDificuldade);
        stmt.setInt(6, this.idCusto);
        stmt.execute();
        DAO.closeConnection();
    }

    public static void listarReceitas(Connection conexao) throws Exception {
        ResultSet rs = conexao.createStatement().executeQuery(
                "SELECT * FROM receita;"
        );
        Thread.sleep(500);
        while (rs.next()) {
            Receita receita = new Receita(
                    rs.getInt("idreceita"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getString("imagem"),
                    rs.getInt("cadastro_idusuario"),
                    rs.getInt("preparo_idpreparo"),
                    rs.getInt("dificuldade_iddificuldade"),
                    rs.getInt("custo_idcusto")
            );
            System.out.println(receita);
            System.out.println("===================================");
        }
    }
}
