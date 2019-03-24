package com.steamybeans.drop;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.steamybeans.drop.helpers.TestHelpers;
import com.steamybeans.drop.views.LoginPage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LogOutFeatureTest {

    private TestHelpers testHelpers;

    @Rule
    public ActivityTestRule<LoginPage> mainActivityTestRule = new ActivityTestRule<LoginPage>(LoginPage.class);

    @Test
    public void LoggingOut() throws Exception {
        testHelpers = new TestHelpers();
        Thread.sleep(2000);
        testHelpers.signUpTestUser();
        Thread.sleep(2500);
        onView(withId(R.id.TBAccount)).perform(click());
        onView(withId(R.id.BTNlogOut)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.BTNsignUp)).check(matches(isDisplayed()));
        Thread.sleep(2000);
        DeleteUser();
    }

    public void DeleteUser() throws Exception {
        testHelpers = new TestHelpers();
        testHelpers.logInTestUser();
        Thread.sleep(2000);
        testHelpers.deleteCurrentUser();
        Thread.sleep(2000);
    }
}
