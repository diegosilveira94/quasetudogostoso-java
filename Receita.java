import java.sql.Connection;
import java.sql.PreparedStatement;

public class Receita {
    private int id;
    private String titulo;
    private String descricao;
    private String imagem;
    private Usuario usuario;

    // construtor para insert
    public Receita(String titulo, String descricao) throws Exception {
        this.titulo = titulo;
        this.descricao = descricao;

        Connection conn = DAO.createConnection();
        PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO receita (titulo, descricao) VALUES (?, ?);"
        );
        stmt.setString(1, this.getTitulo());
        stmt.setString(2, this.getDescricao());
        stmt.execute();
        DAO.closeConnection();
    }

    // construtor para select
    public Receita(int id, String titulo, String descricao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Receita{"
            + "id=" + id
            + ", titulo=" + titulo
            + ", descricao=" + descricao
            + ", imagem=" + imagem
            + ", usuario=" + (usuario != null ? usuario.getNome() : "null")
            + '}';
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
}


