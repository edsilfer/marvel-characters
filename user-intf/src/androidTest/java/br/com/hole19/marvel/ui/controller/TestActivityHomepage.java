package br.com.hole19.marvel.ui.controller;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import br.com.hole19.marvel.R;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by r720929 on 04/05/2016.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestActivityHomepage extends ActivityInstrumentationTestCase2 <ActivityHomepage> {

    private static final String TAG = "TestActivityHomepage";


    private ActivityHomepage mActivity;
    public ActivityTestRule<ActivityHomepage> rule = new ActivityTestRule(ActivityHomepage.class, true, false);

    public TestActivityHomepage() {
        super(ActivityHomepage.class);
    }

   @Before
    public void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent();
        mActivity = rule.launchActivity(intent);
    }

    @Test
    public void testPreconditions() {
        assertNotNull(mActivity);
    }

    @Test
    public void testLoadMoreCharacters () throws InterruptedException {
        RecyclerView characters = (RecyclerView) this.mActivity.findViewById(R.id.characters);
        int previousSize = characters.getAdapter().getItemCount();
        characters.smoothScrollToPosition(previousSize);
        Thread.sleep(2000);
        int newSize = characters.getAdapter().getItemCount();
        onView(withId(R.id.characters)).perform(RecyclerViewActions.scrollToPosition(previousSize));
        assertNotSame(previousSize, newSize);
    }

    @Test
    public void openActivityCharacter () {
        onView(withId(R.id.characters))
                .perform(
                        RecyclerViewActions
                                .actionOnItemAtPosition(0, click()));

        assertFalse(mActivity.isRunning());
    }
}