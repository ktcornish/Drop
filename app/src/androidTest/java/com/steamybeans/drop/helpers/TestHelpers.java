package com.steamybeans.drop.helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.steamybeans.drop.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

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

    public void logOutUser() {
        onView(withId(R.id.TBAccount)).perform(click());
        onView(withId(R.id.BTNlogOut)).perform(click());
    }
}
