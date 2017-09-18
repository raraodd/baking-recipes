package com.wendy.bakingrecipes;

import android.app.Application;
import android.content.Context;

/**
 * Created by SRIN on 9/18/2017.
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        App.context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
