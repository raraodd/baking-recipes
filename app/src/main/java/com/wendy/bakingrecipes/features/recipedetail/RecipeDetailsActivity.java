package com.wendy.bakingrecipes.features.recipedetail;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

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
    private boolean isTwoPane = false;

    private RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
    private RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();

    // for two pane

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent != null) {
            recipeId = intent.getLongExtra(Constant.EXTRA_RECIPE_ID, 0);
        }
        if(savedInstanceState != null) {
            recipeId = savedInstanceState.getLong(Constant.EXTRA_RECIPE_ID);
            stepSelectedId = savedInstanceState.getInt(Constant.EXTRA_STEP_SELECTED_ID);
        }

        viewModel = new RecipeDetailsViewModel(this, recipeId);
        loadIngredients();
        loadSteps();
        getSupportActionBar().setTitle(viewModel.recipe.name);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (stepLinearLayout != null) {
            isTwoPane = true;

            if(savedInstanceState == null) {
                recipeDetailFragment.setRecipeId(recipeId);
                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_detail_content_view, recipeDetailFragment)
                        .commit();

                recipeStepFragment.setRecipeId(recipeId);
                recipeStepFragment.setStepId(stepSelectedId);
                recipeStepFragment.setStepSize(viewModel.steps.size());
                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_step_content_view, recipeStepFragment)
                        .commit();
            } else {
                recipeDetailFragment = (RecipeDetailFragment)
                        getSupportFragmentManager().getFragment(savedInstanceState, Constant.EXTRA_FRAGMENT_DETAIL);
                recipeDetailFragment.setRecipeId(recipeId);
                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_detail_content_view, recipeDetailFragment)
                        .commit();

                recipeStepFragment = (RecipeStepFragment)
                        fragmentManager.getFragment(savedInstanceState, Constant.EXTRA_FRAGMENT_STEP);
                recipeStepFragment.setRecipeId(recipeId);
                recipeStepFragment.setStepId(stepSelectedId);
                recipeStepFragment.setStepSize(viewModel.steps.size());
                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_step_content_view, recipeStepFragment)
                        .commit();

            }

        } else {
            isTwoPane = false;

            if(savedInstanceState == null) {
                recipeDetailFragment.setRecipeId(recipeId);
                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_detail_content_view, recipeDetailFragment)
                        .commit();
            }
            else {
                recipeDetailFragment = (RecipeDetailFragment)
                        getSupportFragmentManager().getFragment(savedInstanceState, Constant.EXTRA_FRAGMENT_DETAIL);
                recipeDetailFragment.setRecipeId(recipeId);
                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_detail_content_view, recipeDetailFragment)
                        .commit();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(Constant.EXTRA_RECIPE_ID, recipeId);
        outState.putInt(Constant.EXTRA_STEP_SELECTED_ID, stepSelectedId);
        getSupportFragmentManager().putFragment(outState, Constant.EXTRA_FRAGMENT_DETAIL, recipeDetailFragment);
        if(isTwoPane)
            getSupportFragmentManager().putFragment(outState, Constant.EXTRA_FRAGMENT_STEP, recipeStepFragment);
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
        recipeDetailFragment.setRecipeId(recipeId);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_detail_content_view, recipeDetailFragment)
                .commit();
    }

    public void replaceRecipeStepFragment() {
        recipeStepFragment = new RecipeStepFragment();
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
        if(isTwoPane && stepSelectedId < viewModel.steps.size()-1) {
            stepSelectedId++;
            replaceRecipeStepFragment();
        }
    }

    @Override
    public void onPrev() {
        if(isTwoPane && stepSelectedId > 0) {
            stepSelectedId--;
            replaceRecipeStepFragment();
        }
    }
}
