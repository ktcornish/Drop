package com.steamybeans.drop;

import com.steamybeans.drop.helpers.TestHelpers;
import com.steamybeans.drop.views.LoginPage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LogOutFeatureTest {

    private TestHelpers testHelpers;

    @Rule
    public ActivityTestRule<LoginPage> mainActivityTestRule = new ActivityTestRule<LoginPage>(LoginPage.class);
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void LoggingOut() throws Exception {
        testHelpers = new TestHelpers();

        // log in if user isn't signed in
        if (!testHelpers.isUserLoggedIn()) {
            onView(withId(R.id.ETloginEmailAddress)).perform(typeText("test@user.com"));
            onView(withId(R.id.ETloginPassword)).perform(typeText("password"));
            Espresso.closeSoftKeyboard();
            Thread.sleep(1000);
            onView(withId(R.id.BTNlogin)).perform(click());
        }

        // test logout
        Thread.sleep(3000);
        onView(withId(R.id.TBAccount)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.BTNlogOut)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.BTNsignUp)).check(matches(isDisplayed()));
    }
}
