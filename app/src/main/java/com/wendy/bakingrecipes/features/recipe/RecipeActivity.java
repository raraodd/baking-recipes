package com.wendy.bakingrecipes.features.recipe;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wendy.bakingrecipes.Constant;
import com.wendy.bakingrecipes.R;
import com.wendy.bakingrecipes.SimpleIdlingResource;
import com.wendy.bakingrecipes.features.recipedetail.RecipeDetailsActivity;
import com.wendy.bakingrecipes.util.PreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity
        implements RecipeView, RecipeListAdapter.RecipeClickListener {
    @BindView(R.id.rv_recipe_list)
    RecyclerView rvRecipeList;

    private RecipeViewModel viewModel;
    private Context mContext;
    private RecipeListAdapter recipeAdapter;

    private static final int PHONE_PORTRAIT_COLUMN = 1;
    private static final int PHONE_LANDSCAPE_COLUMN = 2;
    private static final int TABLET_PORTRAIT_COLUMN = 2;
    private static final int TABLET_LANDSCAPE_COLUMN = 3;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        viewModel = new RecipeViewModel(this);
        mContext = getBaseContext();
        ButterKnife.bind(this);
        setLayoutManager();
        setAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.attach();
        loadRecipe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.detach();
    }

    public void setLayoutManager() {
        int numCol = getNumOfCol();
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, numCol);
        rvRecipeList.setLayoutManager(layoutManager);
        rvRecipeList.setHasFixedSize(true);
    }

    public void setAdapter() {
        recipeAdapter = new RecipeListAdapter(mContext, this);
        recipeAdapter.setItems(viewModel.recipes);
        rvRecipeList.setAdapter(recipeAdapter);
    }

    @Override
    public void loadRecipe() {
        viewModel.loadRecipe();
    }

    @Override
    public void updateRecipe() {
        recipeAdapter.setItems(viewModel.recipes);
        recipeAdapter.notifyDataSetChanged();
    }

    private int getNumOfCol() {
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (isTablet) {
            return isLandscape ? TABLET_LANDSCAPE_COLUMN : TABLET_PORTRAIT_COLUMN;
        } else {
            return isLandscape ? PHONE_LANDSCAPE_COLUMN : PHONE_PORTRAIT_COLUMN;
        }
    }

    @Override
    public void onRecipeClick(int clickedRecipeIndex) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(Constant.EXTRA_RECIPE_ID, viewModel.recipes.get(clickedRecipeIndex).id);
        Long id = viewModel.recipes.get(clickedRecipeIndex).id;
        PreferenceUtil.setSelectedRecipeId(this, id);

        startActivity(intent);
    }
}
