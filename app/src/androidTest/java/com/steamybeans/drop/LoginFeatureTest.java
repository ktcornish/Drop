package com.steamybeans.drop;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginFeatureTest {

    private SignUpFeatureTest signUp = new SignUpFeatureTest();

    @Rule
    public ActivityTestRule<LoginPage> mainActivityTestRule = new ActivityTestRule<LoginPage>(LoginPage.class);

    @Test
    public void LoggingIn() throws Exception {
        signUp.fillInEmailAndPassword();
        onView(withId(R.id.ETloginEmailAddress)).perform(typeText("test@user.com"));
        onView(withId(R.id.ETloginPassword)).perform(typeText("password"));
        onView(withId(R.id.BTNlogin)).perform(click());
    }
}
