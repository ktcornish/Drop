package com.steamybeans.drop;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.steamybeans.drop.helpers.TestHelpers;
import com.steamybeans.drop.views.LoginPage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
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

        // 01 Attempt signup if not logged in
        System.out.println("LogoutFT 01 - sign up if not logged in");
        if (!testHelpers.isUserLoggedIn()) {
            testHelpers.signUpTestUser();
        }

        // 02 If signup successful user will appear now be logged in - otherwise user will be
        // engaged in signup activity
        System.out.println("LogoutFT 02 - login on failure of signup");
        if (!testHelpers.isUserLoggedIn()) {
            Espresso.pressBack();
            onView(withId(R.id.ETloginEmailAddress)).perform(typeText("test@user.com"));
            onView(withId(R.id.ETloginPassword)).perform(typeText("password"));
            onView(withId(R.id.ETloginPassword)).perform(closeSoftKeyboard());
            onView(withId(R.id.BTNlogin)).perform(click());

        }

        // 03 Test logout
        System.out.println("LogoutFT 03 - test logout");
        onView(withId(R.id.TBAccount)).perform(click());
        // Cannot find logout button until activity_my_account displayed
        Thread.sleep(500);
        onView(withId(R.id.BTNlogOut)).perform(click());
        onView(withId(R.id.BTNsignUp)).check(matches(isDisplayed()));
    }
}
