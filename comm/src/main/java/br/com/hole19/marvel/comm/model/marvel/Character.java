package br.com.hole19.marvel.comm.model.marvel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by edgar on 29-Apr-16.
 */
public class Character implements Serializable {

    private long id;
    private String name;
    private String description;
    private String modified;
    private Thumbnail thumbnail;
    private String resourceURI;
    private Collection comics;
    private Collection series;
    private Collection stories;
    private Collection events;
    private List<Link> urls;

    public Collection getComics() {
        return comics;
    }

    public void setComics(Collection comics) {
        this.comics = comics;
    }

    public Collection getSeries() {
        return series;
    }

    public void setSeries(Collection series) {
        this.series = series;
    }

    public Collection getStories() {
        return stories;
    }

    public void setStories(Collection stories) {
        this.stories = stories;
    }

    public Collection getEvents() {
        return events;
    }

    public void setEvents(Collection events) {
        this.events = events;
    }

    public List<Link> getUrls() {
        return urls;
    }

    public void setUrls(List<Link> urls) {
        this.urls = urls;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }
}
