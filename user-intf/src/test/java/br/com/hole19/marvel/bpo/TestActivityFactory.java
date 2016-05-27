package br.com.hole19.marvel.bpo;

import android.content.res.Resources;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import br.com.hole19.marvel.R;
import br.com.hole19.marvel.ui.bpo.ActivityFactory;
import br.com.hole19.marvel.ui.controller.ActivityBasicConfiguration;
import br.com.hole19.marvel.ui.controller.ActivityTemplate;
import br.com.hole19.marvel.ui.controller.ActivityCharacter;
import br.com.hole19.marvel.ui.controller.ActivityHomepage;
import br.com.hole19.marvel.ui.controller.ActivityItem;
import br.com.hole19.marvel.ui.controller.ActivitySearch;

/**
 * Created by edgar on 05-May-16.
 */
public class TestActivityFactory extends TestCase {

    @Test
    public void testGetActivityConfigurationHomepage () {
        ActivityHomepage activityHomepage = Mockito.mock(ActivityHomepage.class);
        Resources resources = Mockito.mock(Resources.class);
        Mockito.when(activityHomepage.getResources()).thenReturn(resources);
        Mockito.when(resources.getColor(R.color.colorTextSecondary)).thenReturn(1);
        ActivityBasicConfiguration setup = ActivityFactory.getActivityConfiguration(activityHomepage);
        assertTrue(setup.getContentView() == R.layout.act_homepage);
        assertFalse(setup.getDefaultReturnBehavior());
        assertTrue(1== setup.getToolbartTextColor());
    }

    @Test
    public void testGetActivityConfigurationCharacter () {
        ActivityCharacter activityHomepage = Mockito.mock(ActivityCharacter.class);
        ActivityBasicConfiguration setup = ActivityFactory.getActivityConfiguration(activityHomepage);
        assertTrue(setup.getContentView() == R.layout.act_character);
        assertNull(setup.getMenuFile());
    }

    @Test
    public void testGetActivityConfigurationSearch () {
        ActivitySearch activityHomepage = Mockito.mock(ActivitySearch.class);
        ActivityBasicConfiguration setup = ActivityFactory.getActivityConfiguration(activityHomepage);
        assertTrue(setup.getContentView() == R.layout.act_search);
    }


    @Test
    public void testGetActivityConfigurationItem () {
        ActivityItem activityHomepage = Mockito.mock(ActivityItem.class);
        ActivityBasicConfiguration setup = ActivityFactory.getActivityConfiguration(activityHomepage);
        assertTrue(setup.getContentView() == R.layout.act_item);
    }


}
