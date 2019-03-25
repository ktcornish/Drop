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
public class LogOutFeatureTest {

    private TestHelpers testHelpers;

    @Rule
    public ActivityTestRule<LoginPage> mainActivityTestRule = new ActivityTestRule<LoginPage>(LoginPage.class);

    @Test
    public void LoggingOut() throws Exception {
        testHelpers = new TestHelpers();

        // 01 Attempt signup if not logged in
        if (!testHelpers.isUserLoggedIn()) {
            testHelpers.signUpTestUser();
        }

        /*
            02 If signup successful user will now be logged in - otherwise user will be engaged in
            signup activity and will need to log out
         */
        if (!testHelpers.isUserLoggedIn()) {
            Espresso.pressBack();
            onView(withId(R.id.ETloginEmailAddress)).perform(typeText("test@user.com"));
            onView(withId(R.id.ETloginPassword)).perform(typeText("password"));
            onView(withId(R.id.ETloginPassword)).perform(closeSoftKeyboard());
            onView(withId(R.id.BTNlogin)).perform(click());
        }

        // 03 Test logout
        Thread.sleep(2000);
        onView(withId(R.id.TBAccount)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.BTNlogOut)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.BTNsignUp)).check(matches(isDisplayed()));
    }
}
