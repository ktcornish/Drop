package com.steamybeans.drop;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.steamybeans.drop.helpers.TestHelpers;
import com.steamybeans.drop.views.LoginPage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginFeatureTest {

    @Rule
    public ActivityTestRule<LoginPage> mainActivityTestRule = new ActivityTestRule<LoginPage>(LoginPage.class);

    @Test
    public void LoggingIn() throws Exception {
        TestHelpers testHelpers = new TestHelpers();

        // 01 Log out if app launches with user signed in
        if (testHelpers.isUserLoggedIn()) {
            onView(withId(R.id.TBAccount)).perform(click());
            Thread.sleep(500);
            Espresso.closeSoftKeyboard();
            Thread.sleep(500);
            onView(withId(R.id.BTNlogOut)).perform(click());
        }

        // 02 Sign up test user
        testHelpers.signUpTestUser();

        // 03 Return to login activity
        if (testHelpers.isUserLoggedIn()) {
            onView(withId(R.id.TBAccount)).perform(click());
            Thread.sleep(500);
            onView(withId(R.id.BTNlogOut)).perform(click());
        }
        else { Espresso.pressBack(); }

        // 04 Test login
        onView(withId(R.id.ETloginEmailAddress)).perform(typeText("test@user.com"));
        onView(withId(R.id.ETloginPassword)).perform(typeText("password"));
        onView(withId(R.id.ETloginPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.BTNlogin)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.toolbar_top)).check(matches(isDisplayed()));
    }
}
