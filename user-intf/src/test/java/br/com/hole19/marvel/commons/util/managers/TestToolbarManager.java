package br.com.hole19.marvel.commons.util.managers;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.hole19.marvel.ui.commons.util.managers.ToolbarManager;

/**
 * Created by edgar on 03-May-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestToolbarManager extends TestCase {

    private static final String TITLE = "TITLE";
    private static final Integer RESOURCE_ID = 1;
    private static final Integer RESOURCE_ICON = 1;
    private static final Integer RESOURCE_COLOR = 1;

    @Mock
    private AppCompatActivity mActivityMock;
    @Mock
    private android.support.v7.widget.Toolbar mToolbarMock;
    @Mock
    private android.support.v7.app.ActionBar mActionBarMock;
    @Mock
    ToolbarManager.OnIconClicked mOnClickMock;

    private ToolbarManager mToolbarService;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.mToolbarService = new ToolbarManager();

        Mockito.when(this.mActivityMock.findViewById(Mockito.eq(RESOURCE_ID))).thenReturn(mToolbarMock);
        Mockito.when(this.mActivityMock.getSupportActionBar()).thenReturn(mActionBarMock);
        this.mToolbarService.initToolbar(this.mActivityMock, RESOURCE_ID, TITLE, RESOURCE_COLOR);
    }

    @Test
    public void testInitToolbar() {
        Mockito.verify(this.mActivityMock).setSupportActionBar(mToolbarMock);
        Mockito.verify(this.mActionBarMock).setTitle(TITLE);
        Mockito.verify(this.mToolbarMock).setTitleTextColor(RESOURCE_COLOR);
    }

    @Test
    public void testPrepareNavigationBehaviorIconSetPlusExecutor() {
        this.mToolbarService.prepareNavigationBehavior(RESOURCE_ICON, this.mOnClickMock);
        Mockito.verify(this.mToolbarMock).setNavigationIcon(Mockito.eq(RESOURCE_ICON));
        Mockito.verify(this.mActionBarMock).setHomeButtonEnabled(Mockito.eq(Boolean.TRUE));
        Mockito.verify(this.mToolbarMock).setNavigationOnClickListener(Mockito.any(View.OnClickListener.class));
    }

}
