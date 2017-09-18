package com.wendy.bakingrecipes.util;

import android.database.Cursor;

import com.wendy.bakingrecipes.data.Ingredient;
import com.wendy.bakingrecipes.data.Recipe;
import com.wendy.bakingrecipes.data.Step;
import com.wendy.bakingrecipes.data.database.IngredientContract;
import com.wendy.bakingrecipes.data.database.RecipeContract;
import com.wendy.bakingrecipes.data.database.StepContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SRIN on 9/18/2017.
 */

public class DbUtils {

    public static List<Recipe> convertRecipeCursorToList(Cursor cursor) {
        List<Recipe> result = new ArrayList<>();

        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Recipe recipe = new Recipe();
                recipe.id = cursor.getLong(cursor.getColumnIndex(RecipeContract.RecipeEntry._ID));
                recipe.name = cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_NAME));
                recipe.servings = cursor.getInt(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_SERVINGS));
                recipe.image = cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_IMAGE));
                result.add(recipe);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return result;
    }

    public static List<Ingredient> convertIngredientsCursorToList(Cursor cursor) {
        List<Ingredient> result = new ArrayList<>();

        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.measure = cursor.getString(cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_MEASURE));
                ingredient.name = cursor.getString(cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_NAME));
                ingredient.quantity = cursor.getDouble(cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_QUANTITY));
                result.add(ingredient);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return result;
    }

    public static List<Step> convertStepsCursorToList(Cursor cursor) {
        List<Step> result = new ArrayList<>();

        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Step step = new Step();
                step.id = cursor.getLong(cursor.getColumnIndex(StepContract.StepEntry._ID));
                step.shortDescription = cursor.getString(cursor.getColumnIndex(StepContract.StepEntry.COLUMN_SHORT_DESCRIPTION));
                step.description = cursor.getString(cursor.getColumnIndex(StepContract.StepEntry.COLUMN_DESCRIPTION));
                step.videoURL = cursor.getString(cursor.getColumnIndex(StepContract.StepEntry.COLUMN_VIDEO_URL));
                step.thumbnailURL = cursor.getString(cursor.getColumnIndex(StepContract.StepEntry.COLUMN_THUMBNAIL_URL));
                result.add(step);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return result;
    }
}
