package com.quasetudogostoso.model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private int id;
    private String title;
    private String description;
    private String imageURL;
    private User author;
    private int idPreparation;
    private int idDifficulty;
    private int idCost;
    private List<RecipeIngredient> ingredients = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    // Construtor pro INSERT
    public Recipe(String title, String description, String imageURL, User author, int idPreparation, int idDifficulty, int idCost) {
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.author = author;
        this.idPreparation = 1; // Default value
        this.idDifficulty = 1;  // Default value
        this.idCost = 1; // Default value
    }

    // Construtor pro SELECT
    public Recipe(int id, String title, String description, String imageURL, User author, int idPreparation, int idDifficulty, int idCost, List<RecipeIngredient> ingredients, List<Category> categories) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.author = author;
        this.idPreparation = idPreparation;
        this.idDifficulty = idDifficulty;
        this.idCost = idCost;
        this.ingredients = ingredients;
        this.categories = categories;
    }

    public Recipe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getIdPreparation() {
        return idPreparation;
    }

    public void setIdPreparation(int idPreparation) {
        this.idPreparation = idPreparation;
    }

    public int getIdDifficulty() {
        return idDifficulty;
    }

    public void setIdDifficulty(int idDifficulty) {
        this.idDifficulty = idDifficulty;
    }

    public int getIdCost() {
        return idCost;
    }

    public void setIdCost(int idCost) {
        this.idCost = idCost;
    }

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return ""
                + "Id:" + this.getId()
                + "Título:" + this.getTitle()
                + "Descrição:" + this.getDescription()
                + "URL da Imagem:" + this.getImageURL()
                + "Autor:" + this.getAuthor()
                + "Id Preparação:" + this.getIdPreparation()
                + "Id Dificuldade:" + this.getIdDifficulty()
                + "Id Custo:" + this.getIdCost()
                + "Ingredientes:" + this.getIngredients();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Recipe)) {
            return false;
        }
        final Recipe other = (Recipe) object;

        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
