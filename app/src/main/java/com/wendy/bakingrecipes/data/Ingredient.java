package com.wendy.bakingrecipes.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SRIN on 9/18/2017.
 */

public class Ingredient {

    public double quantity;
    public String measure;

    @SerializedName("ingredient")
    public String name;

    public Ingredient() {
    }
}
