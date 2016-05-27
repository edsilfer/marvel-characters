package br.com.hole19.marvel.comm.model.marvel;

import java.io.Serializable;

/**
 * Created by edgar on 29-Apr-16.
 */
public class Item implements Serializable {

    private String resourceURI;
    private String name;
    private String thumbnailUrl;

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
