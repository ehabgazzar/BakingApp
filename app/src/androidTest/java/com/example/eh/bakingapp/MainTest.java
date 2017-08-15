package com.example.eh.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onView;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsAnything.anything;

/**
 * Created by Eh on 8/14/2017.
 */
@RunWith(AndroidJUnit4.class)

public class MainTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule= new ActivityTestRule<MainActivity>(MainActivity.class);
/*
    @Test
    public void CheckIfRecipeDisplayed() {
        onView((withId(R.id.gridview_recipes))).check(matches(isDisplayed()));

    }
*/
    @Test
    public void ClickDisplayedRecipe() {

        onView(ViewMatchers.withId(R.id.gridview_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        click()));
//        onView(withId(R.id.gridview_recipes)).perform(RecyclerViewActions.scrollToPosition(3));
       // onView(withId(R.id.gridview_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
//        onData(anything()).inAdapterView(withId(R.id.gridview_recipes)).atPosition(0).
//                onChildView(withId(R.id.recipe_name)).perform(click());

//        onData(anything()).inAdapterView(withId(R.id.gridview_recipes)).atPosition(1).
//                onChildView(withId(R.id.recipe_name)).perform(click());
    }
/*
    @Test
    public void CheckIfDetailRecipeDisplayed() {

        onView((withId(R.id.gridview_recipes))).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.gridview_recipes)).atPosition(0).
                onChildView(withId(R.id.recipe_name)).perform(click());
        onView((withId(R.id.detail_fragment))).check(matches(isDisplayed()));

    }
*/




}
