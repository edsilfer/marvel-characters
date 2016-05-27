package br.com.hole19.marvel.ui.controller;


import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import br.com.hole19.marvel.R;
import br.com.hole19.marvel.ui.commons.util.ActivityTemplate;

/**
 * Created by r720929 on 04/05/2016.
 */
public class TestActivityCharacter extends ActivityUnitTestCase<ActivityCharacter> {

    private ActivityCharacter mActivity;

    public TestActivityCharacter() {
        super(ActivityCharacter.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        startActivity(
                new Intent(getInstrumentation().getTargetContext(), ActivityCharacter.class),
                null,
                null
        );

        this.mActivity = getActivity();
    }

    @SmallTest
    public void testSetupActivity () {
        ActivityTemplate.BasicConfiguration setup = this.mActivity.setupActivity();
        assertTrue(setup.getContentView() == R.layout.act_character);
    }
}
