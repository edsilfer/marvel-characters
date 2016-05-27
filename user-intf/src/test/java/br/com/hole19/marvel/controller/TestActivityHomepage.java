package br.com.hole19.marvel.controller;

import android.support.v7.widget.Toolbar;
import android.view.ViewPropertyAnimator;
import android.widget.LinearLayout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

import br.com.edsilfer.kiwi.loading.LoaderManager;
import br.com.edsilfer.kiwi.notification.NotificationManager;
import br.com.hole19.marvel.R;
import br.com.hole19.marvel.ui.bpo.CharacterUIService;
import br.com.hole19.marvel.ui.commons.util.PermissionsUtil;
import br.com.hole19.marvel.ui.commons.util.StaticResourceProvider;
import br.com.hole19.marvel.ui.commons.util.constants.NotificationConstants;
import br.com.hole19.marvel.ui.commons.util.managers.ToolbarManager;
import br.com.hole19.marvel.ui.controller.ActivityHomepage;

/**
 * Created by edgar on 09-May-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestActivityHomepage {

    @Mock
    CharacterUIService characterUIService;
    @Mock
    PermissionsUtil mPermissionsManager;
    @Mock
    NotificationManager mNotificationUtil;
    @Mock
    LoaderManager mLoadingUtil;
    @Mock
    StaticResourceProvider mStaticResourceProvider;

    @InjectMocks
    ActivityHomepage activityHomepage = Mockito.mock(ActivityHomepage.class);

    @Test
    public void testStartResources () {
        Mockito.doCallRealMethod().when(activityHomepage).startResources();
        LinearLayout linearLayout = Mockito.mock(LinearLayout.class);
        Mockito.when(activityHomepage.findViewById(R.id.circularProgressBar)).thenReturn(linearLayout);
        ViewPropertyAnimator viewPropertyAnimator = Mockito.mock(ViewPropertyAnimator.class);
        Mockito.when(viewPropertyAnimator.translationY(NotificationConstants.LoadingInterface.LAYOUT_PROGRESS_BAR_INDETERMINATE_CIRCULAR_CIRCULAR_TOP_MARGIN)).thenReturn(viewPropertyAnimator);
        Mockito.when(viewPropertyAnimator.alphaBy(1)).thenReturn(viewPropertyAnimator);
        Mockito.when(linearLayout.animate()).thenReturn(viewPropertyAnimator);
        Toolbar toolbar = Mockito.mock(Toolbar.class);
        ToolbarManager toolbarManager = Mockito.mock(ToolbarManager.class);
        Mockito.when(toolbarManager.getToolbar()).thenReturn(toolbar);
        Mockito.when(activityHomepage.getToolbarService()).thenReturn(toolbarManager);
        activityHomepage.startResources();
    }

}
