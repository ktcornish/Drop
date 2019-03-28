package com.steamybeans.drop.helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.steamybeans.drop.R;

import androidx.test.espresso.Espresso;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class TestHelpers {
    public void signUpTestUser() {
        onView(withId(R.id.BTNsignUp)).perform(click());
        onView(withId(R.id.ETsignupEmailAddress)).perform(typeText("test@user.com"));
        onView(withId(R.id.ETsignupUsername)).perform(typeText("testuser"));
        onView(withId(R.id.ETsignupPassword)).perform(typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.CBtermsAndConditions)).perform(click());
        onView(withId(R.id.BTNcompleteSignUp)).perform(click());
    }

    public boolean isUserLoggedIn() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        return firebaseUser != null;
    }
}
