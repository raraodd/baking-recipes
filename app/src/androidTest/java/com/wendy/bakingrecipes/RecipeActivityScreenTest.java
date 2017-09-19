package com.wendy.bakingrecipes;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import com.wendy.bakingrecipes.features.recipe.RecipeActivity;
import com.wendy.bakingrecipes.features.recipedetail.RecipeDetailsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;

/**
 * Created by SRIN on 9/19/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeActivityScreenTest {

    private static final String SAMPLE_INGREDIENT = "350.0 G  Bittersweet chocolate (60-70% cacao)";

    @Rule
    public IntentsTestRule<RecipeActivity> mActivityTestRule = new IntentsTestRule<>(RecipeActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void checkText_RecipeActivity() {
        onView(withId(R.id.rv_recipe_list)).perform(scrollToPosition(0));
        onView(withText(Constant.RECIPE_NUTELLA_PIE)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_recipe_list)).perform(scrollToPosition(1));
        onView(withText(Constant.RECIPE_BROWNIES)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_recipe_list)).perform(scrollToPosition(2));
        onView(withText(Constant.RECIPE_BROWNIES)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_recipe_list)).perform(scrollToPosition(3));
        onView(withText(Constant.RECIPE_BROWNIES)).check(matches(isDisplayed()));
    }

    @Test
    public void clickRecipeItem_OpensRecipeDetailsActivity() {
        onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(allOf(instanceOf(TextView.class), withText(Constant.RECIPE_BROWNIES))).check(matches(isDisplayed()));
        onView(withId(R.id.rv_ingredients)).perform(scrollToPosition(0));
        onView(allOf(instanceOf(TextView.class), withText(SAMPLE_INGREDIENT))).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}