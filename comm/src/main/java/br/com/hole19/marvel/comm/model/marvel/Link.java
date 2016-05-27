package br.com.hole19.marvel.comm.model.marvel;

import java.io.Serializable;

/**
 * Created by edgar on 29-Apr-16.
 */
public class Link implements Serializable {

    private String type;
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
