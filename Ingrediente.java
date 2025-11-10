package src.main.java.br.com.quasetudogostoso.model;
import src.main.java.br.com.quasetudogostoso.model.ReceitaIngrediente;

import java.util.ArrayList;
import java.util.List;

public class Ingrediente {
    private int idIngrediente;
    private String ingrediente;
    private List<ReceitaIngrediente> receita = new ArrayList();
    static List<Ingrediente> ingredientes = new ArrayList();

    public int getIdIngrediente() {
        return this.idIngrediente;
    }

    public void setIdIngrediente(int var1) {
        this.idIngrediente = var1;
    }

    public String getIngrediente() {
        return this.ingrediente;
    }

    public void setIngrediente(String var1) {
        this.ingrediente = var1;
    }
}
