package com.wendy.bakingrecipes.data.database;

import android.net.Uri;
import android.provider.BaseColumns;

import com.wendy.bakingrecipes.Constant;

/**
 * Created by SRIN on 9/18/2017.
 */

public class IngredientContract {

    public static final String PATH_INGREDIENT = "ingredient";

    public static final class IngredientEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                Constant.BASE_CONTENT_URI.buildUpon()
                        .appendPath(PATH_INGREDIENT)
                        .build();

        public static final String TABLE_NAME = "ingredient";
        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_MEASURE = "measure";
    }
}
