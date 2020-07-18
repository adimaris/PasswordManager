package com.alexanderdimaris.passwordmanager.view;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import com.alexanderdimaris.passwordmanager.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;


public class PassListActivityLaunchAddPassActivityTest {

    @Rule
    public ActivityTestRule<PassListActivity> passListActivityActivityTestRule = new ActivityTestRule<>(PassListActivity.class);

    private PassListActivity passListActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(AddPassActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        passListActivity = passListActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchOfSecondActivityOnButtonClick() {
        assertNotNull(passListActivity.findViewById(R.id.activity_pass_list_fab));

        onView(withId(R.id.activity_pass_list_fab)).perform(click());

        Activity addActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);

        assertNotNull(addActivity);

        addActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        passListActivity = null;
    }
}