package br.com.hole19.marvel.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

import br.com.hole19.marvel.ui.bpo.CharacterSearchService;
import br.com.hole19.marvel.ui.controller.ActivitySearch;

/**
 * Created by edgar on 08-May-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestActivitySearch {

    @Mock
    CharacterSearchService characterSearchService;

    @InjectMocks
    ActivitySearch activitySearch = Mockito.mock(ActivitySearch.class);

    @Test
    public void test () {
        Mockito.doCallRealMethod().when(activitySearch).startResources();
        activitySearch.startResources();
        Mockito.verify(characterSearchService).initSearchInterface(activitySearch);
    }

}
