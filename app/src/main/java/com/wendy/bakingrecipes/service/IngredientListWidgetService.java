package com.wendy.bakingrecipes.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.wendy.bakingrecipes.Constant;
import com.wendy.bakingrecipes.R;
import com.wendy.bakingrecipes.data.Ingredient;
import com.wendy.bakingrecipes.data.Recipe;
import com.wendy.bakingrecipes.util.PreferenceUtil;

/**
 * Created by SRIN on 9/19/2017.
 */

public class IngredientListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientListRemoteViewsFactory(getApplicationContext());
    }
}

class IngredientListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    Recipe recipe;

    public IngredientListRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Long id = PreferenceUtil.getSelectedRecipeId(mContext);
        recipe = BakingRecipesApp.getInstance().getRecipeById(id);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipe != null && recipe.getIngredients() != null ? recipe.getIngredients().size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = recipe.getIngredients().get(position);
        Log.d("WENDY", "position : " + position+"");

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_item);
        views.setTextViewText(R.id.tv_ingredient, ingredient.toString());

        Intent intent = new Intent();
        intent.putExtra(Constant.EXTRA_RECIPE_ID, recipe.id);
        views.setOnClickFillInIntent(R.id.tv_ingredient, intent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
