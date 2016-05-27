package br.com.hole19.marvel.ui.commons.util.patterns;

/**
 * Created by edgar on 01-May-16.
 */
public interface Publisher {

    public void registerSubscriber(Subscriber subscriber);
    public void removeSubscriber(Subscriber subscriber);
    public void notifyEvent(Object... params);

}
