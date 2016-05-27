package br.com.hole19.marvel.bpo;


import android.content.res.Resources;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.gc.materialdesign.views.ProgressBarIndeterminate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

import br.com.edsilfer.kiwi.loading.LoaderManager;
import br.com.edsilfer.kiwi.notification.NotificationManager;
import br.com.hole19.marvel.R;
import br.com.hole19.marvel.ui.bpo.CharacterSearchService;
import br.com.hole19.marvel.ui.bpo.CharacterUIService;
import br.com.hole19.marvel.ui.controller.ActivityTemplate;
import br.com.hole19.marvel.ui.commons.util.managers.ToolbarManager;

/**
 * Created by edgar on 05-May-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestCharacterSearchService {

    @Mock
    CharacterUIService characterUIService;
    @Mock
    LoaderManager mLoaderManager;
    @Mock
    NotificationManager mNotificationManager;

    @InjectMocks
    CharacterSearchService service = new CharacterSearchService();

    @Test
    public void testInitSearchInterface () {
        ActivityTemplate activity = Mockito.mock(ActivityTemplate.class);
        ToolbarManager toolbarService = Mockito.mock(ToolbarManager.class);
        Mockito.when(activity.getToolbarService()).thenReturn(toolbarService);
        Toolbar toolbar = Mockito.mock(Toolbar.class);
        EditText editText = Mockito.mock(EditText.class);
        Mockito.when(toolbarService.getToolbar()).thenReturn(toolbar);
        Mockito.when(toolbar.findViewById(R.id.query_container)).thenReturn(editText);
        service.initSearchInterface(activity);
    }

    @Test
    public void testOnTextChanged () {
        ActivityTemplate activity = Mockito.mock(ActivityTemplate.class);
        ToolbarManager toolbarService = Mockito.mock(ToolbarManager.class);
        Mockito.when(activity.getToolbarService()).thenReturn(toolbarService);
        Toolbar toolbar = Mockito.mock(Toolbar.class);
        EditText editText = Mockito.mock(EditText.class);
        Mockito.when(toolbarService.getToolbar()).thenReturn(toolbar);
        Mockito.when(toolbar.findViewById(R.id.query_container)).thenReturn(editText);
        Resources resources = Mockito.mock(Resources.class);
        Mockito.when(resources.getInteger(R.integer.MARVEL_API_PARAM_MAX_REQUESTS)).thenReturn(0);
        Mockito.when(activity.getResources()).thenReturn(resources);
        Mockito.when(activity.findViewById(R.id.progress_bar)).thenReturn(Mockito.mock(ProgressBarIndeterminate.class));
        Mockito.when(activity.getString(Mockito.any(Integer.class))).thenReturn("");
        service.initSearchInterface(activity);
    }
}
