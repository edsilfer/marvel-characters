package br.com.hole19.marvel.commons.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import br.com.hole19.marvel.R;
import br.com.hole19.marvel.ui.commons.adapter.ViewHolderFactory;
import br.com.hole19.marvel.ui.commons.model.LayoutType;

/**
 * Created by edgar on 08-May-16.
 */
@RunWith(JUnit4.class)
public class TestViewHolderFactor extends TestCase {

    ViewHolderFactory viewHolderFactor;

    @Test
    public void testGetViewHolderSmallVertical () {
        int layoutType = LayoutType.SMALL_VERTICAL.toInteger();

        this.viewHolderFactor = Mockito.mock(ViewHolderFactory.class);
        ViewGroup viewGroup = Mockito.mock(ViewGroup.class);
        Mockito.when(viewHolderFactor.getViewHolder(viewGroup, layoutType)).thenCallRealMethod();
        Context context = Mockito.mock(Context.class);
        Mockito.when(viewGroup.getContext()).thenReturn(context);
        LayoutInflater layoutInflater = Mockito.mock(LayoutInflater.class);
        Mockito.when(viewHolderFactor.getLayoutInflater(context)).thenReturn(layoutInflater);
        Mockito.when(layoutInflater.inflate(R.layout.rsc_util_item_small, viewGroup, false)).thenReturn(Mockito.mock(View.class));
        RecyclerView.ViewHolder result = viewHolderFactor.getViewHolder(viewGroup, layoutType);
        assertNotNull(result);
    }

    @Test
    public void testGetViewHolderLargeVertical () {
        int layoutType = LayoutType.LARGE_VERTICAL.toInteger();

        this.viewHolderFactor = Mockito.mock(ViewHolderFactory.class);
        ViewGroup viewGroup = Mockito.mock(ViewGroup.class);
        Mockito.when(viewHolderFactor.getViewHolder(viewGroup, layoutType)).thenCallRealMethod();
        Context context = Mockito.mock(Context.class);
        Mockito.when(viewGroup.getContext()).thenReturn(context);
        LayoutInflater layoutInflater = Mockito.mock(LayoutInflater.class);
        Mockito.when(viewHolderFactor.getLayoutInflater(context)).thenReturn(layoutInflater);
        Mockito.when(layoutInflater.inflate(R.layout.rsc_util_item_large, viewGroup, false)).thenReturn(Mockito.mock(View.class));
        RecyclerView.ViewHolder result = viewHolderFactor.getViewHolder(viewGroup, layoutType);
        assertNotNull(result);
    }

}
