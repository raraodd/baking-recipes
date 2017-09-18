package com.wendy.bakingrecipes.features.recipedetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wendy.bakingrecipes.R;
import com.wendy.bakingrecipes.data.Ingredient;
import com.wendy.bakingrecipes.data.Step;
import com.wendy.bakingrecipes.service.BakingRecipesApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SRIN on 9/18/2017.
 */

public class RecipeDetailFragment extends Fragment implements StepListAdapter.StepItemClickListener {
    @BindView(R.id.rv_ingredients)
    RecyclerView rvIngredients;
    @BindView(R.id.rv_steps)
    RecyclerView rvSteps;

    private Long recipeId;
    private IngredientListAdapter ingredientListAdapter;
    private StepListAdapter stepListAdapter;
    private StepClickListener stepClickListener;

    public RecipeDetailFragment() {
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public interface StepClickListener {
        void onStepClicked(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            stepClickListener = (StepClickListener) context;
        }
        catch (ClassCastException cce) {
            throw new ClassCastException(context.toString() + " should implements interface StepClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);

        Context context = getActivity();

        // recycler view
        rvIngredients.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvIngredients.setHasFixedSize(true);
        rvSteps.setNestedScrollingEnabled(false);
        ingredientListAdapter = new IngredientListAdapter(context);
        ingredientListAdapter.setIngredients(getIngredient());
        rvIngredients.setAdapter(ingredientListAdapter);

        rvSteps.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvSteps.setHasFixedSize(true);
        rvSteps.setNestedScrollingEnabled(false);
        stepListAdapter = new StepListAdapter(context, this);
        stepListAdapter.setSteps(getStep());
        rvSteps.setAdapter(stepListAdapter);

        return view;
    }

    private List<Ingredient> getIngredient() {
        return BakingRecipesApp.getInstance().getIngredients(recipeId);
    }

    private List<Step> getStep() {
        return BakingRecipesApp.getInstance().getSteps(recipeId);
    }

    @Override
    public void onStepItemClicked(int clickedStepIndex) {
        stepClickListener.onStepClicked(clickedStepIndex);
    }
}
