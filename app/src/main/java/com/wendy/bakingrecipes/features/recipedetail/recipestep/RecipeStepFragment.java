package com.wendy.bakingrecipes.features.recipedetail.recipestep;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.wendy.bakingrecipes.Constant;
import com.wendy.bakingrecipes.R;
import com.wendy.bakingrecipes.data.Step;
import com.wendy.bakingrecipes.service.BakingRecipesApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by SRIN on 9/19/2017.
 */

public class RecipeStepFragment extends Fragment {
    @Nullable
    @BindView(R.id.tv_step_id)
    TextView tvStepId;
    @Nullable
    @BindView(R.id.tv_step_short_description)
    TextView tvShortDescription;
    @BindView(R.id.step_exo_player_view)
    SimpleExoPlayerView playerView;
    @Nullable
    @BindView(R.id.tv_step_description)
    TextView tvDescription;
    @Nullable
    @BindView(R.id.btn_next)
    Button btnNext;
    @Nullable
    @BindView(R.id.btn_prev)
    Button btnPrev;

    private Long recipeId;
    private int stepId;
    private int stepSize;
    private Step step;
    private StepActionListener mCallback;
    private SimpleExoPlayer exoPlayer;
    private TrackSelector trackSelector;
    private long trackPosition;

    public interface StepActionListener {
        void onNext();
        void onPrev();
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            recipeId = savedInstanceState.getLong(Constant.EXTRA_RECIPE_ID);
            stepId = savedInstanceState.getInt(Constant.EXTRA_STEP_SELECTED_ID);
            trackPosition = savedInstanceState.getLong(Constant.EXTRA_TRACK_POSITION);
            stepSize = savedInstanceState.getInt(Constant.EXTRA_STEPS_SIZE);
            Log.d("WENDY", "savedInstanceState Track position: " + trackPosition);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(Constant.EXTRA_RECIPE_ID, recipeId);
        outState.putInt(Constant.EXTRA_STEP_SELECTED_ID, stepId);
        outState.putInt(Constant.EXTRA_STEPS_SIZE, stepSize);
        outState.putLong(Constant.EXTRA_TRACK_POSITION, trackPosition);
        Log.d("WENDY", "onSaveInstanceState Track position: " + trackPosition);
    }

    @Override
    public void onResume() {
        super.onResume();
        initComponent();
    }

    @Override
    public void onPause() {
        super.onPause();
        trackPosition = exoPlayer != null ? exoPlayer.getCurrentPosition() : 0;
        releasePlayer();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (StepActionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement StepActionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    private void initComponent() {
        step = BakingRecipesApp.getInstance().getStepsById(recipeId, stepId);

        if(tvStepId != null) {
            tvStepId.setText("Step " + stepId);
            tvShortDescription.setText(step.shortDescription);
            tvDescription.setText(step.description);
            if(stepId == 0) {
                btnPrev.setEnabled(false);
                btnPrev.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorDisabled));
            }
            if(stepId == stepSize-1) {
                btnNext.setEnabled(false);
                btnNext.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorDisabled));
            }
        }

        if(step.videoURL.equals("")) {
            playerView.setVisibility(View.GONE);
        } else {
            initializePlayer(Uri.parse(step.videoURL));
        }
    }

    private void initializePlayer(Uri uri) {
        if(exoPlayer == null) {
            trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);
        }

        // Preparing media source
        String userAgent = Util.getUserAgent(getActivity(), getString(R.string.app_name));
        MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.seekTo(trackPosition);
        Log.d("WENDY", "initializePlayer Track position: " + trackPosition);

        //todo add position
    }

    private void releasePlayer() {
        if(exoPlayer != null) {
            exoPlayer.setPlayWhenReady(false);
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
            trackSelector = null;
        }
    }
    @Optional
    @OnClick(R.id.btn_prev)
    public void previousStep() {
        mCallback.onPrev();
    }

    @Optional
    @OnClick(R.id.btn_next)
    public void nextStep() {
        mCallback.onNext();
    }
}
