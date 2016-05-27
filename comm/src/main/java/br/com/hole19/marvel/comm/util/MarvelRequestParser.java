package br.com.hole19.marvel.comm.util;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.hole19.marvel.comm.model.marvel.Thumbnail;

/**
 * Created by edgar on 30-Apr-16.
 */
public class MarvelRequestParser {

    public static String getItemThumbnailUrl(JSONObject object) throws JSONException {
        Gson gson = new Gson();
        return gson.fromJson(((JSONObject) object.getJSONObject("data").getJSONArray("results").get(0)).getJSONObject("thumbnail").toString(), Thumbnail.class).getUrl();
    }

    public static String getResourceUri(JSONObject object) throws JSONException {
        return ((JSONObject) object.getJSONObject("data").getJSONArray("results").get(0)).getString("resourceURI");
    }

    public static String getName(JSONObject object) throws JSONException {
        return ((JSONObject) object.getJSONObject("data").getJSONArray("results").get(0)).getString("name");
    }

    public static String getTitle(JSONObject object) throws JSONException {
        return ((JSONObject) object.getJSONObject("data").getJSONArray("results").get(0)).getString("title");
    }

}
