package com.wendy.bakingrecipes.features.recipedetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wendy.bakingrecipes.R;
import com.wendy.bakingrecipes.data.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SRIN on 9/18/2017.
 */

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepViewHolder> {

    private List<Step> steps;
    private Context mContext;
    private final StepItemClickListener listener;

    public StepListAdapter(Context context, StepItemClickListener listener) {
        this.mContext = context;
        this.listener = listener;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public interface StepItemClickListener {
        void onStepItemClicked(int clickedStepIndex);
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_step_id)
        TextView tvStepId;
        @BindView(R.id.tv_step_short_description)
        TextView tvShortDescription;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(Step step) {
            tvStepId.setText("STEP " + step.id);
            tvShortDescription.setText(step.shortDescription);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onStepItemClicked(position);
        }
    }


    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.step_item, parent, false);
        StepViewHolder viewHolder= new StepViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        Step step = steps.get(position);
        holder.bind(step);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

}
