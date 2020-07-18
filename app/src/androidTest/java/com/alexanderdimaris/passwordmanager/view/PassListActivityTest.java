package com.alexanderdimaris.passwordmanager.view;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.alexanderdimaris.passwordmanager.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class PassListActivityTest {

    @Rule
    public ActivityTestRule<PassListActivity> passListActivityActivityTestRule = new ActivityTestRule<>(PassListActivity.class);

    private PassListActivity passListActivity = null;

    @Before
    public void setUp() throws Exception {
        passListActivity = passListActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = passListActivity.findViewById(R.id.activity_pass_list_tv_title);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        passListActivity = null;
    }
}