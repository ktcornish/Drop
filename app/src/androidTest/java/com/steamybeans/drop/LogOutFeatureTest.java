package com.steamybeans.drop;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.steamybeans.drop.helpers.TestHelpers;
import com.steamybeans.drop.views.LoginPage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LogOutFeatureTest {

    private TestHelpers testHelpers;

    @Rule
    public ActivityTestRule<LoginPage> mainActivityTestRule = new ActivityTestRule<LoginPage>(LoginPage.class);

    @Test
    public void LoggingOut() throws Exception {
        testHelpers = new TestHelpers();
        testHelpers.signUpTestUser();
        Thread.sleep(2000);
        testHelpers.deleteCurrentUser();
        Thread.sleep(3000);
        onView(withId(R.id.TBAccount)).perform(click());
        onView(withId(R.id.BTNlogOut)).perform(click());
        onView(withId(R.id.BTNsignUp)).check(matches(isDisplayed()));
    }
}
