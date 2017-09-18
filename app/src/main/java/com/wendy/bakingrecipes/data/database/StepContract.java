package com.wendy.bakingrecipes.data.database;

import android.net.Uri;
import android.provider.BaseColumns;

import com.wendy.bakingrecipes.Constant;

/**
 * Created by SRIN on 9/18/2017.
 */

public class StepContract {

    public static final String PATH_STEP = "step";

    public static final class StepEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                Constant.BASE_CONTENT_URI.buildUpon()
                        .appendPath(PATH_STEP)
                        .build();

        public static final String TABLE_NAME = "step";
        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_SHORT_DESCRIPTION = "short_description";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_VIDEO_URL = "video_url";
        public static final String COLUMN_THUMBNAIL_URL = "thumbnail_url";
    }
}
