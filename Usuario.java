
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String dataNasc;
    private int cep;
    private int genero;
    private String senha;
    private String salt;
    private String dataInscricao;
    private String uuId;

    // construtor pro insert
    public Usuario(String nome, String email, String dataNasc, int cep, int genero, String senha) throws Exception {
        this.nome = nome;
        this.email = email;
        this.dataNasc = dataNasc;
        this.cep = cep;
        this.genero = genero;
        this.senha = senha;
        this.salt = "XxX";
        this.uuId = "zZz";
    }

    // construtor pro select
    public Usuario(int id, String nome, String email, String dataNasc, int cep, int genero, String senha, String salt, String dataInscricao, String uuId) throws Exception {
        this.nome = nome;
        this.email = email;
        this.dataNasc = dataNasc;
        this.cep = cep;
        this.genero = genero;
        this.senha = senha;
        this.salt = salt;
        this.dataInscricao = dataInscricao;
        this.uuId = uuId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(String dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public void setUuid(String uuId) {
        this.uuId = uuId;
    }

    public String getUuid() {
        return uuId;
    }

    @Override
    public String toString() {
        return "Usuario{"
                + "id=" + id
                + ", nome=" + nome
                + ", email=" + email
                + ", dataNasc=" + dataNasc
                + ", cep=" + cep
                + ", genero=" + genero
                + ", senha=" + senha
                + ", dataInscricao=" + dataInscricao
                + "}";
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Usuario)) {
            return false;
        }
        final Usuario other = (Usuario) object;

        return this.id == other.id;
    }

    public void criarUsuario() throws Exception {
        Connection conn = DAO.createConnection();
        PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO usuario (nome, email, data_nascimento, cep, genero, senha, salt, uuid) VALUES (?, ?, ?, ?, ?, ?, ?, ?);"
        );
        stmt.setString(1, this.getNome());
        stmt.setString(2, this.getEmail());
        stmt.setString(3, this.getDataNasc());
        stmt.setInt(4, this.getCep());
        stmt.setInt(5, this.getGenero());
        stmt.setString(6, this.getSenha());
        stmt.setString(7, getSalt());
        stmt.setString(8, this.getUuid());
        stmt.execute();
        DAO.closeConnection();
    }

    public static void listarUsuarios(Connection conexao) throws Exception {
        ResultSet rs = conexao.createStatement().executeQuery(
                "SELECT * FROM usuario;"
        );
        Thread.sleep(500);
        while (rs.next()) {
            Usuario usuario = new Usuario(
                    rs.getInt("idusuario"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("data_nascimento"),
                    rs.getInt("cep"),
                    rs.getInt("genero"),
                    rs.getString("senha"),
                    rs.getString("salt"),
                    rs.getString("inscrito"),
                    rs.getString("uuid")
            );
            System.out.println(usuario);
            System.out.println("===================================");
        }
    }
}
