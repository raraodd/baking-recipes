package com.wendy.bakingrecipes.data.api;

import com.wendy.bakingrecipes.data.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by wendy on 9/2/17.
 */

public interface Api {

    @GET("android-baking-app-json")
    Call<List<Recipe>> getRecipes();

}
