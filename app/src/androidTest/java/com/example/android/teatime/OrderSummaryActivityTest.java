package com.example.android.teatime;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.OngoingStubbing;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import java.util.Set;

import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsNot;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class OrderSummaryActivityTest {

    private static final String emailMessage = "I just ordered a delicious tea from TeaTime. Next time you are craving a tea, check them out!";

    @Rule
    public ActivityScenarioRule<OrderSummaryActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(OrderSummaryActivity.class);

    @Before
    public void ThisRule() {
        Intents.init();
        Matcher<Intent> filter = IntentMatchers.isInternal();
        OngoingStubbing stubbing = Intents.intending(AllOf.allOf(IsNot.not(filter),
                IsNot.not(IntentMatchers.hasCategories(Set.of(Intent.CATEGORY_LAUNCHER)))));
        stubbing.respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void clickSendEmailButton_SendsEmail() {
        Espresso.onView(ViewMatchers.withId(R.id.send_email_button))
                .perform(ViewActions.click());
        Intents.intended(AllOf.allOf(
                IntentMatchers.hasAction(Intent.ACTION_SENDTO),
                IntentMatchers.hasExtra(Intent.EXTRA_TEXT, emailMessage)));
    }

    @After
    public void AfterTest() {
        Intents.release();
    }
}
