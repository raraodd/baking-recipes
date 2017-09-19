package com.wendy.bakingrecipes.features.recipedetail.recipestep;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.wendy.bakingrecipes.Constant;
import com.wendy.bakingrecipes.R;
import com.wendy.bakingrecipes.data.Step;
import com.wendy.bakingrecipes.service.BakingRecipesApp;

import java.util.List;

public class RecipeStepActivity extends AppCompatActivity implements RecipeStepFragment.StepActionListener{

    private Long recipeId;
    private int stepId;
    private List<Step> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
            }
        }

        Intent intent = getIntent();
        if(intent != null) {
            recipeId = intent.getLongExtra(Constant.EXTRA_RECIPE_ID, -1);
            stepId = intent.getIntExtra(Constant.EXTRA_STEP_SELECTED_ID, -1);
        }
        if(savedInstanceState != null) {
            recipeId = savedInstanceState.getLong(Constant.EXTRA_RECIPE_ID);
            stepId = savedInstanceState.getInt(Constant.EXTRA_STEP_SELECTED_ID);
        }

        if (savedInstanceState == null) {
            steps = BakingRecipesApp.getInstance().getSteps(recipeId);
            replaceFragment();
        }

        setContentView(R.layout.activity_recipe_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        steps = BakingRecipesApp.getInstance().getSteps(recipeId);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(Constant.EXTRA_RECIPE_ID, recipeId);
        outState.putInt(Constant.EXTRA_STEP_SELECTED_ID, stepId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void replaceFragment() {
        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        recipeStepFragment.setRecipeId(recipeId);
        recipeStepFragment.setStepId(stepId);
        recipeStepFragment.setStepSize(steps.size());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_step_content_view, recipeStepFragment)
                .commit();
    }

    @Override
    public void onNext() {
        if(stepId < steps.size()-1) {
            stepId++;
            replaceFragment();
        }
    }

    @Override
    public void onPrev() {
        if(stepId > 0) {
            stepId--;
            replaceFragment();
        }
    }
}


