package com.example.studybuddy;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasBackground;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import com.example.studybuddy.ui.auth.RegisterAccount;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestRegisterAccountUI {

    @Rule
    public ActivityScenarioRule<RegisterAccount> activityRule =
            new ActivityScenarioRule<>(RegisterAccount.class);

    @Test
    public void testRegisterButton_Disabled() {
        activityRule.getScenario().onActivity(activity -> {
            Button register = activity.findViewById(R.id.register);
            assertFalse(register.isEnabled());
        });
    }

    @Test
    public void testRegisterButton_Enabled() {
        activityRule.getScenario().onActivity(activity -> {
            EditText editFirstName = new EditText(activity);
            EditText editLastName = new EditText(activity);
            EditText editEmailAddress = new EditText(activity);
            EditText editPassword = new EditText(activity);
            Button register = new Button(activity);

            editFirstName.setText("John");
            editLastName.setText("Doe");
            editEmailAddress.setText("johndoe@gmail.com");
            editPassword.setText("Password1$");

            assertTrue(register.isEnabled());
        });
    }
}