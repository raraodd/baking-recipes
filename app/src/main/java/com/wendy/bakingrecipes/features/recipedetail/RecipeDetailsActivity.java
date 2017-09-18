package com.wendy.bakingrecipes.features.recipedetail;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.wendy.bakingrecipes.Constant;
import com.wendy.bakingrecipes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity
        implements RecipeDetailsView, RecipeDetailFragment.StepClickListener {
    @BindView(R.id.recipe_detail_content_view)
    FrameLayout container;

    private Long recipeId;
    private RecipeDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent != null) {
            recipeId = intent.getLongExtra(Constant.EXTRA_RECIPE_ID, 0);
        } else if(savedInstanceState != null) {
            recipeId = savedInstanceState.getLong(Constant.EXTRA_RECIPE_ID);
        }

        viewModel = new RecipeDetailsViewModel(this, recipeId);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setRecipeId(recipeId);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_detail_content_view, recipeDetailFragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadIngredients();
        loadSteps();
        getSupportActionBar().setTitle(viewModel.recipe.name);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putLong(Constant.EXTRA_RECIPE_ID, recipeId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStepClicked(int position) {
        Toast.makeText(this, "Step : " + viewModel.steps.get(position).id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadIngredients() {
        viewModel.loadIngredients();
    }

    @Override
    public void loadSteps() {
        viewModel.loadSteps();
    }


}
