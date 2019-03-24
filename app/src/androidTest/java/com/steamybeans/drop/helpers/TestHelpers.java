package com.steamybeans.drop.helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.steamybeans.drop.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class TestHelpers {

    public void deleteCurrentUser() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        firebaseUser.delete();
    }

    public void signUpTestUser() {
        onView(withId(R.id.BTNsignUp)).perform(click());
        onView(withId(R.id.ETsignupEmailAddress)).perform(typeText("test@user.com"));
        onView(withId(R.id.ETsignupPassword)).perform(typeText("password"));
        onView(withId(R.id.ETsignupPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.BTNcompleteSignUp)).perform(click());
    }

    public void logInTestUser() {
        onView(withId(R.id.ETloginEmailAddress)).perform(typeText("test@user.com"));
        onView(withId(R.id.ETloginPassword)).perform(typeText("password"));
        onView(withId(R.id.ETloginPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.BTNlogin)).perform(click());
}

    public void logOutUser() {
        onView(withId(R.id.TBAccount)).perform(click());
        onView(withId(R.id.BTNlogOut)).perform(click());
    }
}
