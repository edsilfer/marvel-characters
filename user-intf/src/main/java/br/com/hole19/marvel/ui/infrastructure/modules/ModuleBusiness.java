package br.com.hole19.marvel.ui.infrastructure.modules;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import br.com.hole19.marvel.ui.bpo.CharacterSearchService;
import br.com.hole19.marvel.ui.bpo.CharacterUIService;
import br.com.hole19.marvel.ui.commons.adapter.ViewHolderFactory;
import dagger.Module;
import dagger.Provides;

/**
 * Created by edgar on 04-May-16.
 */
@Module
public class ModuleBusiness {

    @Provides
    @Singleton
    public CharacterUIService provideCharacterService() {
        return new CharacterUIService();
    }

    @Provides
    @Singleton
    public CharacterSearchService provideCharacterSearchInterface() {
        return new CharacterSearchService();
    }

    @Provides
    @Singleton
    public ViewHolderFactory provideViewHolderFactor() {
        return new ViewHolderFactory();
    }
}
