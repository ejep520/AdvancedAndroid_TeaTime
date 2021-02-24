package com.example.android.teatime;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class IdlingResourceMenuActivityTest {

    @Rule
    public final ActivityScenarioRule<MenuActivity> mActivityScenario =
            new ActivityScenarioRule<>(MenuActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mActivityScenario.getScenario().onActivity(activity -> {
            mIdlingResource = activity.getIdlingResource();
            IdlingRegistry.getInstance().register(mIdlingResource);
        });
    }

    @Test
    public void idlingResourceTest() {
        Espresso.onData(Matchers.anything())
                .inAdapterView(ViewMatchers.withId(R.id.tea_grid_view))
                .atPosition(0)
                .perform(ViewActions.click());
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
