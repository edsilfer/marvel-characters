package br.com.hole19.marvel.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import br.com.hole19.marvel.R;
import br.com.hole19.marvel.comm.model.marvel.Character;
import br.com.hole19.marvel.comm.model.marvel.Collection;
import br.com.hole19.marvel.comm.model.marvel.Item;
import br.com.hole19.marvel.comm.model.marvel.Link;
import br.com.hole19.marvel.ui.bpo.CharacterUIService;
import br.com.hole19.marvel.ui.controller.ActivityCharacter;
import br.com.hole19.marvel.ui.infrastructure.Constants;

/**
 * Created by edgar on 08-May-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestActivityCharacter {

    @Mock
    CharacterUIService characterUIService;

    @InjectMocks
    ActivityCharacter activityCharacter = Mockito.mock(ActivityCharacter.class);


    @Test
    public void testStartResources () {
        Mockito.doCallRealMethod().when(activityCharacter).startResources();
        Character character = Mockito.mock(Character.class);
        Mockito.when(activityCharacter.retrieveElement(Constants.ActivityCommunication.ACTCOMM_CHARACTER)).thenReturn(character);
        Collection collection = Mockito.mock(Collection.class);
        Mockito.when(character.getComics()).thenReturn(collection);
        Mockito.when(character.getSeries()).thenReturn(collection);
        Mockito.when(character.getStories()).thenReturn(collection);
        Mockito.when(character.getEvents()).thenReturn(collection);
        List<Link> links = new ArrayList<>();
        Mockito.when(character.getUrls()).thenReturn(links);
        List<Item> items = new ArrayList<>();
        Mockito.when(collection.getItems()).thenReturn(items);
        activityCharacter.startResources();
        Mockito.verify(characterUIService).loadDescription(character, activityCharacter);
        Mockito.verify(characterUIService).loadRelatedLinks(links, activityCharacter);
        Mockito.verify(characterUIService).loadCollection(R.id.comics, items, activityCharacter);
        Mockito.verify(characterUIService).loadCollection(R.id.series, items, activityCharacter);
        Mockito.verify(characterUIService).loadCollection(R.id.stories, items, activityCharacter);
        Mockito.verify(characterUIService).loadCollection(R.id.events, items, activityCharacter);


    }


}
