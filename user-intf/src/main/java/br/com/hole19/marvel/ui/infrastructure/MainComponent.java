package br.com.hole19.marvel.ui.infrastructure;

import javax.inject.Singleton;

import br.com.hole19.marvel.ui.bpo.CharacterSearchService;
import br.com.hole19.marvel.ui.bpo.CharacterUIService;
import br.com.hole19.marvel.ui.commons.adapter.AdapterCharacter;
import br.com.hole19.marvel.ui.commons.adapter.AdapterItem;
import br.com.hole19.marvel.ui.commons.util.StaticResourceProvider;
import br.com.hole19.marvel.ui.controller.ActivityCharacter;
import br.com.hole19.marvel.ui.controller.ActivityHomepage;
import br.com.hole19.marvel.ui.controller.ActivityItem;
import br.com.hole19.marvel.ui.controller.ActivitySearch;
import br.com.hole19.marvel.ui.controller.FragmentItem;
import br.com.hole19.marvel.ui.infrastructure.modules.ModuleBusiness;
import br.com.hole19.marvel.ui.infrastructure.modules.ModuleUtil;
import dagger.Component;

/**
 * Created by edgar on 04-May-16.
 */
@Singleton
@Component(
        modules = {
                ModuleBusiness.class,
                ModuleUtil.class
        })
public interface MainComponent {
    void inject(ActivityHomepage target);
    void inject(AdapterCharacter target);
    void inject(StaticResourceProvider target);
    void inject(CharacterUIService target);
    void inject(AdapterItem target);
    void inject(ActivityCharacter target);
    void inject(ActivitySearch target);
    void inject(ActivityItem target);
    void inject(FragmentItem target);
    void inject(CharacterSearchService target);
}
