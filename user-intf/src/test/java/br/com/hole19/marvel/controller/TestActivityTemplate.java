package br.com.hole19.marvel.controller;

import android.content.Intent;
import android.os.Bundle;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.edsilfer.kiwi.notification.NotificationManager;
import br.com.hole19.marvel.ui.commons.util.managers.ToolbarManager;
import br.com.hole19.marvel.ui.controller.ActivityBasicConfiguration;
import br.com.hole19.marvel.ui.controller.ActivityTemplate;

/**
 * Created by edgar on 08-May-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestActivityTemplate extends TestCase {

    @Mock
    ToolbarManager mToolbarService;
    @Mock
    NotificationManager mNotificationManager;

    @InjectMocks
    ActivityTemplate activityTemplate = Mockito.mock(ActivityTemplate.class);

    @Test
    public void testStartResourcers() {
        Mockito.doCallRealMethod().when(activityTemplate).startResources();
        ActivityBasicConfiguration setup = Mockito.mock(ActivityBasicConfiguration.class);
        Mockito.when(setup.getDefaultReturnBehavior()).thenReturn(Boolean.TRUE);
        Mockito.when(setup.getContentView()).thenReturn(1);
        Mockito.when(activityTemplate.setupActivity()).thenReturn(setup);
        activityTemplate.startResources();
    }

    @Test
    public void testRetrieveElement() {
        String key = "KEY";
        Mockito.doCallRealMethod().when(activityTemplate).retrieveElement(key);
        String value = "VALUE";
        Bundle bundle = Mockito.mock(Bundle.class);
        bundle.putSerializable(key, value);
        Mockito.when(bundle.getSerializable(key)).thenReturn(value);
        Intent intent = Mockito.mock(Intent.class);
        Mockito.when(activityTemplate.getIntent()).thenReturn(intent);
        Mockito.when(intent.getExtras()).thenReturn(bundle);
        String result = (String) activityTemplate.retrieveElement(key);
        assertNotNull(result);
        assertEquals(result, value);
    }

}
