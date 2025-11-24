package com.quasetudogostoso.model;

public class RecipeIngredient {

    private int id;
    private Recipe recipe;
    private Ingredient ingredient;
    private Float quantity;
    private int idMeasurement;

    // Construtor pro INSERT
    public RecipeIngredient(Recipe recipe, Ingredient ingredient, Float quantity) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.idMeasurement = 1; // Default value
    }

    // Construtor pro SELECT
    public RecipeIngredient(int id, Recipe recipe, Ingredient ingredient, Float quantity, int idMeasurement) {
        this.id = id;
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.idMeasurement = idMeasurement;
    }

    public RecipeIngredient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public int getIdMeasurement() {
        return idMeasurement;
    }

    public void setIdMeasurement(int idMeasurement) {
        this.idMeasurement = idMeasurement;
    }

    @Override
    public String toString() {
        return ""
                + "Id:" + this.getId()
                + "Receita:" + this.getRecipe()
                + "Ingrediente:" + this.getIngredient()
                + "Quantidade:" + this.getQuantity()
                + "Id Medida:" + this.getIdMeasurement();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof RecipeIngredient)) {
            return false;
        }
        final RecipeIngredient other = (RecipeIngredient) object;

        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
