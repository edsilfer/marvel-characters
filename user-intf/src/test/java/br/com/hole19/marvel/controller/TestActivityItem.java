package br.com.hole19.marvel.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.hole19.marvel.R;
import br.com.hole19.marvel.comm.model.marvel.Item;
import br.com.hole19.marvel.ui.bpo.CharacterSearchService;
import br.com.hole19.marvel.ui.commons.util.managers.ToolbarManager;
import br.com.hole19.marvel.ui.controller.ActivityItem;
import br.com.hole19.marvel.ui.infrastructure.Constants;

/**
 * Created by edgar on 08-May-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestActivityItem extends TestCase {

    @Mock
    CharacterSearchService characterSearchService;

    @InjectMocks
    ActivityItem activityItem = Mockito.mock(ActivityItem.class);

    @Test
    public void testActivityItem() {
        Mockito.doCallRealMethod().when(activityItem).startResources();
        Mockito.doCallRealMethod().when(activityItem).onBackPressed();
        Mockito.doCallRealMethod().when(activityItem).updateItemPosition(0);
        ViewPager pager = Mockito.mock(ViewPager.class);
        Mockito.when(activityItem.findViewById(R.id.items)).thenReturn(pager);
        Intent intent = Mockito.mock(Intent.class);
        Bundle bundle = Mockito.mock(Bundle.class);
        Mockito.when(intent.getExtras()).thenReturn(bundle);
        List<Item> items = new ArrayList<>();
        Mockito.when(bundle.getSerializable(Constants.ActivityCommunication.ACTCOMM_COLLECTIONS)).thenReturn((Serializable) items);
        Mockito.when(activityItem.getIntent()).thenReturn(intent);
        activityItem.startResources();
        Mockito.when(pager.getCurrentItem()).thenReturn(2);
        activityItem.onBackPressed();
        Mockito.when(pager.getCurrentItem()).thenReturn(-1);
        activityItem.onBackPressed();
        ToolbarManager toolbarManager = Mockito.mock(ToolbarManager.class);
        Toolbar toolbar = Mockito.mock(Toolbar.class);
        Mockito.when(toolbarManager.getToolbar()).thenReturn(toolbar);
        Mockito.when(toolbar.findViewById(R.id.title)).thenReturn(Mockito.mock(TextView.class));
        Mockito.when(activityItem.getToolbarService()).thenReturn(toolbarManager);
        String label = activityItem.updateItemPosition(0);
        System.out.println(label);
        assertEquals("1 / 0", label);
    }

}

