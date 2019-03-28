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
public class SignUpFeatureTest {

    @Rule
    public ActivityTestRule<LoginPage> mainActivityTestRule = new ActivityTestRule<LoginPage>(LoginPage.class);

    @Test
    public void clickSignUpButton_toOpenSignUpPage() throws Exception {
        onView(withId(R.id.BTNsignUp)).perform(click());
        onView(withId(R.id.BTNcompleteSignUp)).check(matches(isDisplayed()));
    }

    @Test
    public void fillInEmailAndPassword() throws Exception {
        TestHelpers testHelpers = new TestHelpers();
        Thread.sleep(2000);
        onView(withId(R.id.BTNsignUp)).perform(click());
        testHelpers.signUpTestUser();
        Thread.sleep(2000);
        onView(withId(R.id.toolbar_top)).check(matches(isDisplayed()));
        Thread.sleep(2000);
    }
}
