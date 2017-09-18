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
    public static final String EXTRA_STEP_SELECTED_ID = "step_selected_id";
    public static final String EXTRA_STEPS_SIZE = "step_size";
    public static final String EXTRA_TRACK_POSITION = "track_position";
}
