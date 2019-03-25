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
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SignUpFeatureTest {

    private TestHelpers testHelpers;

    @Rule
    public ActivityTestRule<LoginPage> mainActivityTestRule = new ActivityTestRule<LoginPage>(LoginPage.class);

    @Test
    public void clickSignUpButton_toOpenSignUpPage() throws Exception {
        onView(withId(R.id.BTNsignUp)).perform(click());
        onView(withId(R.id.BTNcompleteSignUp)).check(matches(isDisplayed()));
    }

    @Test
    public void fillInEmailAndPassword() throws Exception {
        testHelpers = new TestHelpers();
        onView(withId(R.id.BTNsignUp)).perform(click());
        onView(withId(R.id.ETsignupEmailAddress)).perform(typeText("test@user.com"));
        onView(withId(R.id.ETsignupPassword)).perform(typeText("password"));
        onView(withId(R.id.ETsignupPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.BTNcompleteSignUp)).perform(click());
        onView(withId(R.id.toolbar_top)).check(matches(isDisplayed()));
        testHelpers.logOutUser();
    }
}
