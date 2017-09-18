package com.wendy.bakingrecipes.data;

import com.wendy.bakingrecipes.service.BakingRecipesApp;

import java.util.List;

/**
 * Created by SRIN on 9/18/2017.
 */

public class Recipe {

    public Long id;
    public String name;
    public List<Ingredient> ingredients;
    public List<Step> steps;
    public int servings;
    public String image;

    public Recipe() {
    }

    public Recipe(Long id, String name, int servings, String image) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    public List<Ingredient> getIngredients() {
        return BakingRecipesApp.getInstance().getIngredients(id);
    }
}
