package com.wendy.bakingrecipes.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.wendy.bakingrecipes.App;
import com.wendy.bakingrecipes.data.Ingredient;
import com.wendy.bakingrecipes.data.Recipe;
import com.wendy.bakingrecipes.data.Step;
import com.wendy.bakingrecipes.data.api.Api;
import com.wendy.bakingrecipes.data.database.IngredientContract;
import com.wendy.bakingrecipes.data.database.RecipeContract;
import com.wendy.bakingrecipes.data.database.StepContract;
import com.wendy.bakingrecipes.util.DbUtils;

import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SRIN on 9/18/2017.
 */

public class BakingRecipesApp {

    public static final String API_BASE_URL = "http://go.udacity.com/";

    private static BakingRecipesApp instance;
    public static BakingRecipesApp getInstance() {
        if(instance == null) return new BakingRecipesApp();
        return instance;
    }

    public BakingRecipesApp() {
    }

    private Api api;
    private Api getApi() {
        if(api == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api = retrofit.create(Api.class);
        }
        return api;
    }

    public void loadRecipe() {
        Call<List<Recipe>> call = getApi().getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.isSuccessful()) {
                    List<Recipe> recipes = response.body();

                    for(Recipe itemRecipe: recipes) {
                        for(Ingredient itemIngredient: itemRecipe.ingredients) {
                            saveIngredient(itemRecipe.id, itemIngredient);
                        }

                        for(Step itemStep: itemRecipe.steps) {
                            saveStep(itemRecipe.id, itemStep);
                        }

                        saveRecipe(itemRecipe);
                    }

                    RecipeStatus.getInstance().notifyRecipeUpdated();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("WENDY", t.getMessage());
            }
        });
    }

    public void saveRecipe(Recipe recipe){
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecipeContract.RecipeEntry._ID, recipe.id);
        contentValues.put(RecipeContract.RecipeEntry.COLUMN_NAME, recipe.name);
        contentValues.put(RecipeContract.RecipeEntry.COLUMN_SERVINGS, recipe.servings);
        contentValues.put(RecipeContract.RecipeEntry.COLUMN_IMAGE, recipe.image);

        Uri uri = App.getContext().getContentResolver().insert(RecipeContract.RecipeEntry.CONTENT_URI, contentValues);
    }

    public void saveIngredient(Long recipeId, Ingredient ingredient) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(IngredientContract.IngredientEntry.COLUMN_RECIPE_ID, recipeId);
        contentValues.put(IngredientContract.IngredientEntry.COLUMN_QUANTITY, ingredient.quantity);
        contentValues.put(IngredientContract.IngredientEntry.COLUMN_MEASURE, ingredient.measure);
        contentValues.put(IngredientContract.IngredientEntry.COLUMN_NAME, ingredient.name);

        Uri uri = App.getContext().getContentResolver().insert(IngredientContract.IngredientEntry.CONTENT_URI, contentValues);
    }

    public void saveStep(Long recipeId, Step step) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(StepContract.StepEntry._ID, step.id);
        contentValues.put(StepContract.StepEntry.COLUMN_RECIPE_ID, recipeId);
        contentValues.put(StepContract.StepEntry.COLUMN_SHORT_DESCRIPTION, step.shortDescription);
        contentValues.put(StepContract.StepEntry.COLUMN_DESCRIPTION, step.description);
        contentValues.put(StepContract.StepEntry.COLUMN_VIDEO_URL, step.videoURL);
        contentValues.put(StepContract.StepEntry.COLUMN_THUMBNAIL_URL, step.thumbnailURL);

        Uri uri = App.getContext().getContentResolver().insert(StepContract.StepEntry.CONTENT_URI, contentValues);
    }

    public List<Recipe> getRecipe() {
        Cursor cursor = App.getContext().getContentResolver().query(
                RecipeContract.RecipeEntry.CONTENT_URI,
                null,
                null,
                null,
                RecipeContract.RecipeEntry._ID);
        return DbUtils.convertRecipeCursorToList(cursor);
    }

    public Recipe getRecipeById(Long recipeId) {
        String mSelection = "_id = ?";
        String[] mSelectionArgs = new String[] {String.valueOf(recipeId)};
        Cursor cursor = App.getContext().getContentResolver().query(
                RecipeContract.RecipeEntry.CONTENT_URI,
                null,
                mSelection,
                mSelectionArgs,
                RecipeContract.RecipeEntry._ID);

        List<Recipe> recipes = DbUtils.convertRecipeCursorToList(cursor);
        return recipes.size() == 0 ? null : recipes.get(0);
    }

    public List<Ingredient> getIngredients(Long recipeId) {
        String mSelection = "recipe_id = ?";
        String[] mSelectionArgs = new String[] {String.valueOf(recipeId)};
        Cursor cursor = App.getContext().getContentResolver().query(
                IngredientContract.IngredientEntry.CONTENT_URI,
                null,
                mSelection,
                mSelectionArgs,
                IngredientContract.IngredientEntry._ID);
        return DbUtils.convertIngredientsCursorToList(cursor);
    }

    public List<Step> getSteps(Long recipeId) {
        String mSelection = "recipe_id = ?";
        String[] mSelectionArgs = new String[] {String.valueOf(recipeId)};
        Cursor cursor = App.getContext().getContentResolver().query(
                StepContract.StepEntry.CONTENT_URI,
                null,
                mSelection,
                mSelectionArgs,
                StepContract.StepEntry._ID);
        return DbUtils.convertStepsCursorToList(cursor);
    }

    public Step getStepsById(Long recipeId, int stepId) {
        String mSelection = "recipe_id = ? AND _id = ?";
        String[] mSelectionArgs = new String[]{String.valueOf(recipeId), String.valueOf(stepId)};
        Cursor cursor = App.getContext().getContentResolver().query(
                StepContract.StepEntry.CONTENT_URI,
                null,
                mSelection,
                mSelectionArgs,
                StepContract.StepEntry._ID);
        List<Step> steps = DbUtils.convertStepsCursorToList(cursor);
        return steps.size() == 0 ? null : steps.get(0);
    }
}
