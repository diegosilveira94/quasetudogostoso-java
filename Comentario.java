package src.main.java.br.com.quasetudogostoso.model;

import java.util.ArrayList;
import java.util.List;

public class Comentario {
    private int nota;
    private String dataComentario;
    private int idComentario;
    private Usuario usuario;
    private List<Comentario> comentario;

    public Comentario() {
        this.comentario = new ArrayList<>();
    }
    public Comentario(int nota, String dataComentario, Usuario usuario) {
        this.dataComentario = dataComentario;
        this.nota = nota;
        this.usuario = usuario;
    }

    public int getNota() {
        return this.nota;
    }

    public void setNota(int var1) {
        this.nota = var1;
    }

    public String getDataComentario() {
        return this.dataComentario;
    }

    public void setDataComentario(String var1) {
        this.dataComentario = var1;
    }

    public int getIdComentario() {
        return this.idComentario;
    }

    public void setIdComentario(int var1) {
        this.idComentario = var1;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario var1) {
        this.usuario = var1;
    }
}