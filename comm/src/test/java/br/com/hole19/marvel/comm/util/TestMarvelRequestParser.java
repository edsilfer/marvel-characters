package br.com.hole19.marvel.comm.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import br.com.hole19.marvel.comm.model.comm_data.Payload;
import br.com.hole19.marvel.comm.model.comm_data.PayloadWrapper;
import br.com.hole19.marvel.comm.model.marvel.Character;
import br.com.hole19.marvel.comm.model.marvel.Thumbnail;

/**
 * Created by edgar on 10-May-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestMarvelRequestParser extends TestCase {

    private static final String REFERENCE_URL = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg";
    private static final String REFERENCE_PATH = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784";
    private static final String REFERENCE_EXTENSION = "jpg";
    private static final String REFERENCE_RESOURCE_URI = "http://gateway.marvel.com/v1/public/characters/1011334";
    private static final String REFERENCE_NAME = "3-D Man";

    @Test
    public void testGetItemThumbnailUrl() throws JSONException, ParseException {
        String result = MarvelRequestParser.getItemThumbnailUrl(this.prepareTestingObject());
        assertEquals(REFERENCE_URL, result);
    }

    @Test
    public void testGetResourceUri() throws JSONException, ParseException {
        String result = MarvelRequestParser.getResourceUri(this.prepareTestingObject());
        assertEquals(REFERENCE_RESOURCE_URI, result);
    }

    @Test
    public void testGetName() throws JSONException, ParseException {
        String result = MarvelRequestParser.getName(this.prepareTestingObject());
        assertEquals(REFERENCE_NAME, result);
    }

    private JSONObject prepareTestingObject() throws JSONException, ParseException {
        PayloadWrapper wrapper = new PayloadWrapper();
        Payload payload = new Payload();
        List<Character> list = new ArrayList<>();
        Character character = new Character();
        character.setResourceURI(REFERENCE_RESOURCE_URI);
        character.setName(REFERENCE_NAME);
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setPath(REFERENCE_PATH);
        thumbnail.setExtension(REFERENCE_EXTENSION);
        character.setThumbnail(thumbnail);
        list.add(character);
        payload.setResults(list);
        wrapper.setData(payload);
        Gson gson = new GsonBuilder().create();
        return new JSONObject(gson.toJson(wrapper).toString());
    }

}
