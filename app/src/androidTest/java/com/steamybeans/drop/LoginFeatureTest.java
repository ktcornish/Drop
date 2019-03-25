package com.steamybeans.drop;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.steamybeans.drop.firebase.Authentication;
import com.steamybeans.drop.firebase.User;
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
public class LoginFeatureTest {

    private TestHelpers testHelpers;

    @Rule
    public ActivityTestRule<LoginPage> mainActivityTestRule = new ActivityTestRule<LoginPage>(LoginPage.class);

    @Test
    public void LoggingIn() throws Exception {
        testHelpers = new TestHelpers();

        // 01 Log out if app launches with user signed in
        System.out.println("LoginFT 01 - log out");
        if (testHelpers.isUserLoggedIn()) {
            onView(withId(R.id.TBAccount)).perform(click());
            Thread.sleep(500);
            onView(withId(R.id.BTNlogOut)).perform(click());
        }

        // 02 Sign up test user.
        System.out.println("LoginFT 02 - sign up test user");
        testHelpers.signUpTestUser();
        Thread.sleep(1000);

        // 03 Return to login activity
        System.out.println("LoginFT 03 - log out if signup successful/go back if not");
        if (testHelpers.isUserLoggedIn()) {
            onView(withId(R.id.TBAccount)).perform(click());
            Thread.sleep(500);
            onView(withId(R.id.BTNlogOut)).perform(click());
        }
        else { Espresso.pressBack(); }

        // 04 Test login
        System.out.println("LoginFT 04 - enter test user details and log in");
        onView(withId(R.id.ETloginEmailAddress)).perform(typeText("test@user.com"));
        onView(withId(R.id.ETloginPassword)).perform(typeText("password"));
        onView(withId(R.id.ETloginPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.BTNlogin)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.toolbar_top)).check(matches(isDisplayed()));
    }
}
