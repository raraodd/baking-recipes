package com.wendy.bakingrecipes.data.database;

import android.net.Uri;
import android.provider.BaseColumns;

import com.wendy.bakingrecipes.Constant;

/**
 * Created by SRIN on 9/18/2017.
 */

public class RecipeContract {

    public static final String PATH_RECIPE = "recipe";

    public static final class RecipeEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                Constant.BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_RECIPE)
                .build();

        public static final String TABLE_NAME = "recipe";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SERVINGS = "servings";
        public static final String COLUMN_IMAGE = "image";
    }
}
