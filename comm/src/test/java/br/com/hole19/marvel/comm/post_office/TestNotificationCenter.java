package br.com.hole19.marvel.comm.post_office;

import org.junit.Test;
import org.mockito.Mockito;

import br.com.hole19.marvel.comm.model.EventCatalog;
import br.com.hole19.marvel.comm.model.comm_data.ResponseType;
import br.com.hole19.marvel.comm.model.comm_data.ResponseWrapper;

/**
 * Created by r720929 on 05/05/2016.
 */
public class TestNotificationCenter {

    @Test
    public void testNotifyError () {
        ResponseWrapper response = Mockito.mock(ResponseWrapper.class);
        Mockito.when(response.getType()).thenReturn(ResponseType.ERROR);
        NotificationCenter.notify(EventCatalog.e0000, response);
    }

    @Test
    public void testNotifySuccess () {
        ResponseWrapper response = Mockito.mock(ResponseWrapper.class);
        Mockito.when(response.getType()).thenReturn(ResponseType.SUCCESS);
        NotificationCenter.notify(EventCatalog.e0000, response);
    }

}
