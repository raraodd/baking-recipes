package com.wendy.bakingrecipes.service;

import java.util.ArrayList;

/**
 * Created by SRIN on 9/18/2017.
 */

public class RecipeStatus {

    private static RecipeStatus instance;
    public static RecipeStatus getInstance() {
        if(instance == null) instance = new RecipeStatus();
        return instance;
    }

    private RecipeStatus() {
    }

    private ArrayList<RecipeObserver> observers = new ArrayList<>();

    public void subscribe(RecipeObserver observer) {
        observers.add(observer);
    }

    public void unSubscribe(RecipeObserver observer) {
        observers.remove(observer);
    }

    public void notifyRecipeUpdated() {
        for(RecipeObserver observer: observers) {
            observer.onRecipeLoaded();
        }
    }
}
