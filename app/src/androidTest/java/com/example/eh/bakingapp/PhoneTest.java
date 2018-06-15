package com.example.eh.bakingapp;

import android.os.SystemClock;
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

import static android.support.test.espresso.matcher.ViewMatchers.withId;
@RunWith(AndroidJUnit4.class)

public class PhoneTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule= new ActivityTestRule<MainActivity>(MainActivity.class);
    @Test
    public void CheckIfRecipeDisplayed() {
        onView((withId(R.id.gridview_recipes))).check(matches(isDisplayed()));

    }

    @Test
    public void CheckRecipeNameDisplayed() {
        onView(withId(R.id.gridview_recipes)).perform(RecyclerViewActions.scrollToPosition(3));
        SystemClock.sleep(2000);

        onView(withText("Cheesecake")).check(matches(isDisplayed()));

    }
    @Test
    public void ClickDisplayedRecipe() {

       onView(withId(R.id.gridview_recipes)).perform(RecyclerViewActions.scrollToPosition(3));
        SystemClock.sleep(2000);

        onView(ViewMatchers.withId(R.id.gridview_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3,
                        click()));
       // onView(withId(R.id.gridview_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
//        onData(anything()).inAdapterView(withId(R.id.gridview_recipes)).atPosition(0).
//                onChildView(withId(R.id.recipe_name)).perform(click());

//        onData(anything()).inAdapterView(withId(R.id.gridview_recipes)).atPosition(1).
//                onChildView(withId(R.id.recipe_name)).perform(click());
    }
    @Test
    public void CheckIfDetailRecipeDisplayed() {

        onView((withId(R.id.gridview_recipes))).check(matches(isDisplayed()));
        SystemClock.sleep(2000);

        onView(ViewMatchers.withId(R.id.gridview_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3,
                        click()));
        onView((withId(R.id.detail_fragment))).check(matches(isDisplayed()));

    }


    @Test
    public void ClickStepItem() {

        onView(withId(R.id.gridview_recipes)).perform(RecyclerViewActions.scrollToPosition(3));
        SystemClock.sleep(3000);

        onView(ViewMatchers.withId(R.id.gridview_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3,
                        click()));
        onView(ViewMatchers.withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        click()));

    }


    @Test
    public void ClickNextStepItem() {

        onView(withId(R.id.gridview_recipes)).perform(RecyclerViewActions.scrollToPosition(3));
        SystemClock.sleep(3000);

        onView(ViewMatchers.withId(R.id.gridview_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3,
                        click()));
        onView(ViewMatchers.withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        click()));
        onView(ViewMatchers.withId(R.id.button2))
                .perform(click());
        SystemClock.sleep(2000);

    }

    @Test
    public void ClickPreviousStepItem() {

        onView(withId(R.id.gridview_recipes)).perform(RecyclerViewActions.scrollToPosition(3));
        SystemClock.sleep(3000);

        onView(ViewMatchers.withId(R.id.gridview_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3,
                        click()));
        onView(ViewMatchers.withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        click()));
        onView(ViewMatchers.withId(R.id.button3))
                .perform(click());

        SystemClock.sleep(2000);
    }


}
