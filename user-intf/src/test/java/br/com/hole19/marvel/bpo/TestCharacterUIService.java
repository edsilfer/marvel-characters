package br.com.hole19.marvel.bpo;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import br.com.edsilfer.kiwi.layout.RecyclerViewUtil;
import br.com.edsilfer.kiwi.loading.LoaderManager;
import br.com.edsilfer.kiwi.notification.NotificationManager;
import br.com.hole19.marvel.R;
import br.com.hole19.marvel.comm.model.marvel.Character;
import br.com.hole19.marvel.comm.model.marvel.Item;
import br.com.hole19.marvel.comm.model.marvel.Link;
import br.com.hole19.marvel.comm.model.marvel.Thumbnail;
import br.com.hole19.marvel.ui.bpo.CharacterUIService;
import br.com.hole19.marvel.ui.commons.util.StaticResourceProvider;
import br.com.hole19.marvel.ui.controller.ActivityTemplate;
import br.com.hole19.marvel.ui.infrastructure.App;

/**
 * Created by edgar on 04-May-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestCharacterUIService {

    @Mock
    App mApplication;
    @Mock
    StaticResourceProvider mResources;
    @Mock
    LoaderManager mLoaderManager;
    @Mock
    NotificationManager mNotificationManager;
    @Mock
    RecyclerViewUtil mRecyclerViewUtil;
    @InjectMocks
    CharacterUIService service;

    @Test
    public void loadDescription() {
        String path = "http://";
        AppCompatActivity activity = Mockito.mock(AppCompatActivity.class);
        Character character = Mockito.mock(Character.class);
        Thumbnail thumbnail = Mockito.mock(Thumbnail.class);
        TextView textView = Mockito.mock(TextView.class);
        Picasso picasso = Mockito.mock(Picasso.class);
        Mockito.when(mResources.getPicasso()).thenReturn(picasso);
        RequestCreator requestCreator = Mockito.mock(RequestCreator.class);
        Mockito.when(picasso.load(Mockito.any(Integer.class))).thenReturn(requestCreator);
        Mockito.when(picasso.load(path)).thenReturn(requestCreator);
        Mockito.when(requestCreator.fit()).thenReturn(requestCreator);
        Mockito.when(requestCreator.centerCrop()).thenReturn(requestCreator);
        Mockito.when(character.getThumbnail()).thenReturn(thumbnail);
        Mockito.when(thumbnail.getUrl()).thenReturn(path);
        Mockito.when(activity.findViewById(R.id.cover)).thenReturn(Mockito.mock(ImageView.class));
        Mockito.when(activity.findViewById(R.id.name)).thenReturn(textView);
        Mockito.when(activity.findViewById(R.id.character_description)).thenReturn(textView);
        service.loadDescription(character, activity);
        Mockito.verify(picasso).load(path);
        Mockito.verify(activity).findViewById(R.id.cover);
        Mockito.verify(activity).findViewById(R.id.name);
        Mockito.verify(activity).findViewById(R.id.character_description);
    }

    @Test
    public void testLoadRelatedLinks() {
        List<Link> links = new ArrayList<>();
        AppCompatActivity activity = Mockito.mock(AppCompatActivity.class);
        ListView listView = Mockito.mock(ListView.class);
        Mockito.when(listView.getAdapter()).thenReturn(Mockito.mock(ListAdapter.class));
        Mockito.when(listView.getLayoutParams()).thenReturn(Mockito.mock(ViewGroup.LayoutParams.class));
        Mockito.when(activity.findViewById(R.id.links)).thenReturn(listView);
        Mockito.when(activity.getResources()).thenReturn(Mockito.mock(Resources.class));
        service.loadRelatedLinks(links, activity);
        Mockito.verify(activity).findViewById(R.id.links);
        Mockito.verify(listView).setOnItemClickListener(Mockito.any(AdapterView.OnItemClickListener.class));
    }

    @Test
    public void testLoadCollection () {
        int resourceID = 1;
        List<Item> dataSet = new ArrayList<>();
        AppCompatActivity activity = Mockito.mock(AppCompatActivity.class);
        RecyclerView recyclerView = Mockito.mock(RecyclerView.class);
        Mockito.when(activity.findViewById(Mockito.eq(resourceID))).thenReturn(recyclerView);
        service.loadCollection(resourceID, dataSet, activity);
    }

    @Test
    public void testLoadCharacter () {
        ActivityTemplate activity = Mockito.mock(ActivityTemplate.class);
        RecyclerView recyclerView = Mockito.mock(RecyclerView.class);
        Mockito.when(activity.findViewById(R.id.characters)).thenReturn(recyclerView);
        service.loadCharacters(activity);
    }

    @Test
    public void testLoadSearchResults () {
        ActivityTemplate activity = Mockito.mock(ActivityTemplate.class);
        RecyclerView recyclerView = Mockito.mock(RecyclerView.class);
        Mockito.when(activity.findViewById(R.id.characters)).thenReturn(recyclerView);
        service.loadSearchResults(activity);
    }

}
