package com.wendy.bakingrecipes;

import android.net.Uri;

/**
 * Created by SRIN on 9/18/2017.
 */

public class Constant {

    public static final String AUTHORITY = "com.wendy.bakingrecipes";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String RECIPE_BROWNIES = "Brownies";
    public static final String RECIPE_NUTELLA_PIE = "Nutella Pie";
    public static final String RECIPE_YELLOW_CAKE = "Yellow Cake";
    public static final String RECIPE_CHEESECAKE = "Cheesecake";

    public static final String EXTRA_RECIPE_ID = "recipe_id";
}
