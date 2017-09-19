package com.wendy.bakingrecipes.features.recipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wendy.bakingrecipes.Constant;
import com.wendy.bakingrecipes.R;
import com.wendy.bakingrecipes.data.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SRIN on 9/18/2017.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;
    private Context mContext;
    private final RecipeClickListener listener;

    public RecipeListAdapter(Context context, RecipeClickListener listener) {
        this.mContext = context;
        this.listener = listener;
    }

    public interface RecipeClickListener {
        void onRecipeClick(int clickedRecipeIndex);
    }

    public void setItems(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_recipe_name)
        TextView tvRecipeName;
        @BindView(R.id.tv_recipe_servings)
        TextView tvRecipeServings;
        @BindView(R.id.iv_recipe_image)
        ImageView ivRecipeImage;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(Recipe recipe) {
            tvRecipeName.setText(recipe.name);
            tvRecipeServings.setText(String.valueOf(recipe.servings));

            int imageId = getImageId(recipe.name);

            Picasso.with(mContext)
                    .load(imageId)
                    .into(ivRecipeImage);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onRecipeClick(position);
        }
    }

    private int getImageId(String name) {
        switch (name) {
            case Constant.RECIPE_BROWNIES:
                return R.drawable.brownies;
            case Constant.RECIPE_CHEESECAKE:
                return R.drawable.cheesecake;
            case Constant.RECIPE_NUTELLA_PIE:
                return R.drawable.nutella_pie;
            case Constant.RECIPE_YELLOW_CAKE:
                return R.drawable.yellow_cake;
        }
        return 0;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recipe_item, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

}
