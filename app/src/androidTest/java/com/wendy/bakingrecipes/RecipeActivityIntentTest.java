//package com.wendy.bakingrecipes;
//
//import android.app.Activity;
//import android.app.Instrumentation;
//import android.support.test.espresso.Espresso;
//import android.support.test.espresso.IdlingResource;
//import android.support.test.espresso.contrib.RecyclerViewActions;
//import android.support.test.espresso.intent.rule.IntentsTestRule;
//import android.support.test.espresso.matcher.ViewMatchers;
//import android.support.test.runner.AndroidJUnit4;
//
//import com.wendy.bakingrecipes.features.recipe.RecipeActivity;
//import com.wendy.bakingrecipes.features.recipedetail.RecipeDetailsActivity;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.intent.Intents.intended;
//import static android.support.test.espresso.intent.Intents.intending;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
//import static org.hamcrest.Matchers.not;
//
///**
// * Created by SRIN on 9/19/2017.
// */
//@RunWith(AndroidJUnit4.class)
//public class RecipeActivityIntentTest {
//
//    @Rule
//    public IntentsTestRule<RecipeActivity> mActivityTestRule = new IntentsTestRule<>(RecipeActivity.class);
//
//    private IdlingResource mIdlingResource;
//
//    @Before
//    public void registerIdlingResource() {
//        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
//        Espresso.registerIdlingResources(mIdlingResource);
//    }
//
//    @Before
//    public void stubAllExternalIntents() {
//        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
//    }
//
//    @Test
//    public void checkIntent_RecipeDetailActivity() {
//        onView(ViewMatchers.withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
//        intended(hasComponent(RecipeDetailsActivity.class.getName()));
//    }
//
//    @After
//    public void unregisterIdlingResource() {
//        if (mIdlingResource != null) {
//            Espresso.unregisterIdlingResources(mIdlingResource);
//        }
//    }
//}