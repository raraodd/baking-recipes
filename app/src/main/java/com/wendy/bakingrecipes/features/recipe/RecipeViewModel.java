package com.wendy.bakingrecipes.features.recipe;

import com.wendy.bakingrecipes.data.Recipe;
import com.wendy.bakingrecipes.service.BakingRecipesApp;
import com.wendy.bakingrecipes.service.RecipeObserver;
import com.wendy.bakingrecipes.service.RecipeStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wendy on 9/2/17.
 */

public class RecipeViewModel implements RecipeObserver {

    public List<Recipe> recipes = new ArrayList<>();

    private RecipeView view;
    public RecipeViewModel(RecipeView view) {
        this.view = view;
    }

    public void attach() {
        RecipeStatus.getInstance().subscribe(this);
    }

    public void detach() {
        RecipeStatus.getInstance().unSubscribe(this);
    }

    public void loadRecipe() {
        if(BakingRecipesApp.getInstance().getRecipe().size() == 0)
            BakingRecipesApp.getInstance().loadRecipe();
        else
            onRecipeLoaded();
    }

    @Override
    public void onRecipeLoaded() {
        recipes = BakingRecipesApp.getInstance().getRecipe();
        view.updateRecipe();
    }
}
