package com.quasetudogostoso.model;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {

    private int id;
    private String ingredient;
    private List<RecipeIngredient> recipes = new ArrayList<>();

    // Construtor pro INSERT
    public Ingredient(String ingredient) {
        this.ingredient = ingredient;
    }

    // Construtor pro SELECT
    public Ingredient(int id, String ingredient, List<RecipeIngredient> recipes) {
        this.id = id;
        this.ingredient = ingredient;
        this.recipes = recipes;
    }

    public Ingredient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public List<RecipeIngredient> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeIngredient> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return ""
                + "Id:" + this.getId()
                + "Ingrediente:" + this.getIngredient()
                + "Receitas:" + this.getRecipes();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Ingredient)) {
            return false;
        }
        final Ingredient other = (Ingredient) object;

        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
