package src.main.java.br.com.quasetudogostoso.model;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private int idCategoria;
    private String categoria;
    private boolean ativo;
    private List<CategoriaReceita> receita;

    public Categoria(int var1, String var2, boolean var3) {
        this.idCategoria = var1;
        this.categoria = var2;
        this.ativo = false;
        this.receita = new ArrayList();
    }

    public int getIdCategoria() {
        return this.idCategoria;
    }

    public void setIdCategoria(int var1) {
        this.idCategoria = var1;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String var1) {
        this.categoria = var1;
    }

    public boolean isAtivo() {
        return this.ativo;
    }

    public void setAtivo(boolean var1) {
        this.ativo = var1;
    }

    public List<CategoriaReceita> getReceita() {
        return this.receita;
    }

    public void setReceita(List<CategoriaReceita> var1) {
        this.receita = var1;
    }
}