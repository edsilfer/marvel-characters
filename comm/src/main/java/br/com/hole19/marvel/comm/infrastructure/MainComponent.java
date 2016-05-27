package br.com.hole19.marvel.comm.infrastructure;

import javax.inject.Singleton;

import br.com.hole19.marvel.comm.post_office.Postman;
import dagger.Component;

/**
 * Created by edgar on 09-May-16.
 */
@Singleton
@Component(
        modules = {
                ModuleUtil.class
        })
public interface MainComponent {
    void inject(Postman postman);
}
