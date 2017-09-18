package com.wendy.bakingrecipes.features.recipedetail;

import com.wendy.bakingrecipes.data.Ingredient;
import com.wendy.bakingrecipes.data.Recipe;
import com.wendy.bakingrecipes.data.Step;
import com.wendy.bakingrecipes.service.BakingRecipesApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 9/2/17.
 */

public class RecipeDetailsViewModel {

    Long recipeId;
    Recipe recipe;
    List<Ingredient> ingredients = new ArrayList<>();
    List<Step> steps = new ArrayList<>();
    RecipeDetailsView view;

    public RecipeDetailsViewModel(RecipeDetailsView view, Long recipeId) {
        this.view = view;
        this.recipeId = recipeId;
        recipe = BakingRecipesApp.getInstance().getRecipeById(recipeId);
    }

    public void loadIngredients() {
        ingredients = BakingRecipesApp.getInstance().getIngredients(recipeId);
    }

    public void loadSteps() {
        steps = BakingRecipesApp.getInstance().getSteps(recipeId);
    }
}
