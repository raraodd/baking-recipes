package com.wendy.bakingrecipes.features.recipedetail;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wendy.bakingrecipes.Constant;
import com.wendy.bakingrecipes.R;
import com.wendy.bakingrecipes.features.recipedetail.recipestep.RecipeStepActivity;
import com.wendy.bakingrecipes.features.recipedetail.recipestep.RecipeStepFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity
        implements RecipeDetailsView, RecipeDetailFragment.StepClickListener, RecipeStepFragment.StepActionListener {

    @BindView(R.id.recipe_detail_content_view)
    FrameLayout container;

    @Nullable
    @BindView(R.id.step_detail_linear_layout)
    LinearLayout stepLinearLayout;

    private Long recipeId;
    private RecipeDetailsViewModel viewModel;
    private int stepSelectedId;
    private boolean isTwoPane;

    // for two pane

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
            stepSelectedId = savedInstanceState.getInt(Constant.EXTRA_STEP_SELECTED_ID);
        }

        viewModel = new RecipeDetailsViewModel(this, recipeId);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if(savedInstanceState == null) {
            replaceRecipeDetailFragment();
            if (findViewById(R.id.recipe_step_content_view) != null) {
                isTwoPane = true;
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadIngredients();
        loadSteps();
        getSupportActionBar().setTitle(viewModel.recipe.name);

        replaceRecipeDetailFragment();
        if(isTwoPane) {
            replaceRecipeStepFragment();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putLong(Constant.EXTRA_RECIPE_ID, recipeId);
        outState.putInt(Constant.EXTRA_STEP_SELECTED_ID, stepSelectedId);
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
        stepSelectedId = position;
        if(isTwoPane) {
            replaceRecipeStepFragment();
        } else {
            Intent intent = new Intent(this, RecipeStepActivity.class);
            intent.putExtra(Constant.EXTRA_RECIPE_ID, recipeId);
            intent.putExtra(Constant.EXTRA_STEP_SELECTED_ID, stepSelectedId);
            startActivity(intent);
        }
    }

    @Override
    public void loadIngredients() {
        viewModel.loadIngredients();
    }

    @Override
    public void loadSteps() {
        viewModel.loadSteps();
    }

    public void replaceRecipeDetailFragment() {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setRecipeId(recipeId);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_detail_content_view, recipeDetailFragment)
                .commit();
    }

    public void replaceRecipeStepFragment() {
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setRecipeId(recipeId);
        recipeStepFragment.setStepId(stepSelectedId);
        recipeStepFragment.setStepSize(viewModel.steps.size());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_step_content_view, recipeStepFragment)
                .commit();
    }

    @Override
    public void onNext() {
        if(stepSelectedId < viewModel.steps.size()-1) {
            stepSelectedId++;
            replaceRecipeStepFragment();
        }
    }

    @Override
    public void onPrev() {
        if(stepSelectedId > 0) {
            stepSelectedId--;
            replaceRecipeStepFragment();
        }
    }
}
