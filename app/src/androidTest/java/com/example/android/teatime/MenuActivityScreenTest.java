/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.teatime;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.anything;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This test demos a user clicking on a GridView item in MenuActivity which opens up the
 * corresponding OrderActivity.
 * <p>
 * This test does not utilize Idling Resources yet. If idling is set in the MenuActivity,
 * then this test will fail. See the IdlingResourcesTest for an identical test that
 * takes into account Idling Resources.
 */


@RunWith(AndroidJUnit4.class)
public class MenuActivityScreenTest {

    @Rule
    public final ActivityScenarioRule<MenuActivity> menuActivityScenarioRule =
            new ActivityScenarioRule<>(MenuActivity.class);

    @Test
    public void clickGridViewItem_OpensOrderActivity() {

        Espresso.onData(anything())
                .inAdapterView(ViewMatchers.withId(R.id.tea_grid_view))
                .atPosition(1)
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.tea_name_text_view))
                .check(matches(ViewMatchers.withText("Green Tea")));
    }

}