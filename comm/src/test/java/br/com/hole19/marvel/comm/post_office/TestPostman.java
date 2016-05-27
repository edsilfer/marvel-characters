package br.com.hole19.marvel.comm.post_office;

import android.content.Context;

import com.android.volley.Request;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.hole19.marvel.comm.R;
import br.com.hole19.marvel.comm.model.EventCatalog;
import br.com.hole19.marvel.comm.util.RequestUtil;
import br.com.hole19.marvel.comm.util.RestTemplate;

/**
 * Created by edgar on 09-May-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestPostman {

    @Mock
    RestTemplate mRequestService;
    @Mock
    RequestUtil mRequestUtil;
    @InjectMocks
    Postman postman = Mockito.mock(Postman.class);

    @Test
    public void testListCharacters() {
        String query = "query";
        String MARVEL_SERVICE_LIST_CHARACTERS = "service";
        String URL = "";
        int limit = 0;
        int offset = 0;

        Context context = Mockito.mock(Context.class);
        Mockito.doCallRealMethod().when(postman).listCharacters(context, query, limit, offset);
        Mockito.when(context.getString(R.string.MARVEL_SERVICE_LIST_CHARACTERS)).thenReturn(MARVEL_SERVICE_LIST_CHARACTERS);
        Mockito.when(mRequestUtil.buildBasicUrl(context, MARVEL_SERVICE_LIST_CHARACTERS, query, limit, offset)).thenReturn(URL);
        postman.listCharacters(context, query, limit, offset);
        Mockito.verify(mRequestService).setBody(EventCatalog.e0000, Request.Method.GET);
        Mockito.verify(mRequestService).execute(context);
    }

    @Test
    public void testListComicDetails() {
        String resourceUri = "resourceUri";

        Context context = Mockito.mock(Context.class);
        Mockito.doCallRealMethod().when(postman).listComicDetails(resourceUri, context);
        postman.listComicDetails(resourceUri, context);
        Mockito.verify(mRequestService).setBody(EventCatalog.e0001, Request.Method.GET);
        Mockito.verify(mRequestService).execute(context);
    }

}
