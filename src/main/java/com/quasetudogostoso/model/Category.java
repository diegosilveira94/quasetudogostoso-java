package com.quasetudogostoso.model;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private int id;
    private String category;
    private boolean active;
    private List<Recipe> recipes = new ArrayList<>();

    // Construtor pro INSERT
    public Category(String category, boolean active) {
        this.category = category;
        this.active = active;
    }

    // Construtor pro SELECT
    public Category(int id, String category, boolean active, List<Recipe> recipes) {
        this.id = id;
        this.category = category;
        this.active = active;
        this.recipes = recipes;
    }

    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return ""
                + "Id:" + this.getId()
                + "Categoria:" + this.getCategory()
                + "Ativo:" + this.isActive();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Category)) {
            return false;
        }
        final Category other = (Category) object;

        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
