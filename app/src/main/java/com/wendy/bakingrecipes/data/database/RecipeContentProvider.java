package com.wendy.bakingrecipes.data.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wendy.bakingrecipes.Constant;

/**
 * Created by SRIN on 9/18/2017.
 */

public class RecipeContentProvider extends ContentProvider {

    public static final int RECIPE = 100;
    public static final int INGREDIENT = 200;
    public static final int STEP = 300;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(Constant.AUTHORITY, RecipeContract.PATH_RECIPE, RECIPE);
        uriMatcher.addURI(Constant.AUTHORITY, IngredientContract.PATH_INGREDIENT, INGREDIENT);
        uriMatcher.addURI(Constant.AUTHORITY, StepContract.PATH_STEP, STEP);

        return uriMatcher;
    }

    private DbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDbHelper = new DbHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri;
        long id;

        switch (match) {
            case RECIPE:
                id = db.insert(RecipeContract.RecipeEntry.TABLE_NAME, null, values);
                if(id > 0) {
                    returnUri = ContentUris.withAppendedId(RecipeContract.RecipeEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            case INGREDIENT:
                id = db.insert(IngredientContract.IngredientEntry.TABLE_NAME, null, values);
                if(id > 0) {
                    returnUri = ContentUris.withAppendedId(IngredientContract.IngredientEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            case STEP:
                id = db.insert(StepContract.StepEntry.TABLE_NAME, null, values);
                if(id > 0) {
                    returnUri = ContentUris.withAppendedId(StepContract.StepEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor returnCursor;

        switch (match) {
            case RECIPE:
                returnCursor = db.query(RecipeContract.RecipeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case INGREDIENT:
                returnCursor = db.query(IngredientContract.IngredientEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case STEP:
                returnCursor = db.query(StepContract.StepEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
