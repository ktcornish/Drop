package com.steamybeans.drop;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    public void deleteCurrentUser() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        firebaseUser.delete();
    }

    @Rule
    public ActivityTestRule<LoginPage> mainActivityTestRule = new ActivityTestRule<LoginPage>(LoginPage.class);

    @Test
    public void LoggingOut() throws Exception {
        onView(withId(R.id.BTNsignUp)).perform(click());
        onView(withId(R.id.ETsignupEmailAddress)).perform(typeText("test@user.com"));
        onView(withId(R.id.ETsignupPassword)).perform(typeText("password"));
        onView(withId(R.id.ETsignupPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.BTNcompleteSignUp)).perform(click());
        Thread.sleep(2000);
        deleteCurrentUser();
        Thread.sleep(2000);
        onView(withId(R.id.TBAccount)).perform(click());
        onView(withId(R.id.BTNlogOut)).perform(click());
        onView(withId(R.id.BTNsignUp)).check(matches(isDisplayed()));
    }
}
