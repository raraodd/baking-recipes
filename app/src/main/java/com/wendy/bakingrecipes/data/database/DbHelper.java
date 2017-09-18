package com.wendy.bakingrecipes.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SRIN on 9/18/2017.
 */

public class DbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "baking_recipes_app.db";

    private static final int VERSION  = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_RECIPE = "CREATE TABLE " + RecipeContract.RecipeEntry.TABLE_NAME + " (" +
                RecipeContract.RecipeEntry._ID  + " INTEGER PRIMARY KEY, " +
                RecipeContract.RecipeEntry.COLUMN_NAME + " TEXT, " +
                RecipeContract.RecipeEntry.COLUMN_SERVINGS + " INTEGER, " +
                RecipeContract.RecipeEntry.COLUMN_IMAGE + " TEXT);";

        final String CREATE_TABLE_INGREDIENT = "CREATE TABLE " + IngredientContract.IngredientEntry.TABLE_NAME + " (" +
                IngredientContract.IngredientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                IngredientContract.IngredientEntry.COLUMN_RECIPE_ID  + " INTEGER NOT NULL, " +
                IngredientContract.IngredientEntry.COLUMN_NAME  + " TEXT, " +
                IngredientContract.IngredientEntry.COLUMN_MEASURE + " INTEGER, " +
                IngredientContract.IngredientEntry.COLUMN_QUANTITY + " REAL);";

        final String CREATE_TABLE_STEP = "CREATE TABLE " + StepContract.StepEntry.TABLE_NAME + " (" +
                StepContract.StepEntry._ID + " INTEGER, " +
                StepContract.StepEntry.COLUMN_RECIPE_ID + " INTEGER NOT NULL, " +
                StepContract.StepEntry.COLUMN_SHORT_DESCRIPTION + " TEXT, " +
                StepContract.StepEntry.COLUMN_DESCRIPTION +  " TEXT, " +
                StepContract.StepEntry.COLUMN_VIDEO_URL +  " TEXT, " +
                StepContract.StepEntry.COLUMN_THUMBNAIL_URL +  " TEXT);";

        db.execSQL(CREATE_TABLE_RECIPE);
        db.execSQL(CREATE_TABLE_INGREDIENT);
        db.execSQL(CREATE_TABLE_STEP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                break;
        }
        onCreate(db);
    }
}
