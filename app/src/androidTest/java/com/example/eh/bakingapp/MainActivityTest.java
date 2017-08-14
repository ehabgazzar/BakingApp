package com.example.eh.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsAnything.anything;

/**
 * Created by Eh on 8/14/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule= new ActivityTestRule<MainActivity>(MainActivity.class);
    @Test
    public void CheckIfRecipeDisplayed() {
        onView((withId(R.id.gridview_recipes))).check(matches(isDisplayed()));

      //  onView((withId(R.id.gridview_recipes))).perform(click());
    }
    @Test
    public void ClickRecipeDisplayed() {

        //onView((withId(R.id.gridview_recipes))).perform(click());

        onData(anything()).inAdapterView(withId(R.id.gridview_recipes)).atPosition(0).
                onChildView(withId(R.id.recipe_name)).perform(click());
    }


}
