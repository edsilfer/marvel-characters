package br.com.hole19.marvel.comm.model.marvel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by edgar on 29-Apr-16.
 */
public class Collection implements Serializable {

    private int available;
    private String collectionURI;
    private List<Item> items;

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
