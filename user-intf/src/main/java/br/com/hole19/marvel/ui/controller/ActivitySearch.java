package br.com.hole19.marvel.ui.controller;

import android.util.Log;

import javax.inject.Inject;

import br.com.hole19.marvel.ui.bpo.ActivityFactory;
import br.com.hole19.marvel.ui.bpo.CharacterSearchService;
import br.com.hole19.marvel.ui.bpo.CharacterUIService;
import br.com.hole19.marvel.ui.infrastructure.App;

/**
 * Created by edgar on 01-May-16.
 */
public class ActivitySearch extends ActivityTemplate {

    private static final String TAG = "ActivitySearch";

    @Inject
    CharacterSearchService characterSearchService;

    @Override
    public ActivityBasicConfiguration setupActivity() {
        return ActivityFactory.getActivityConfiguration(this);
    }

    @Override
    public void startResources() {
        this.injectMembers();
        super.startResources();
        this.characterSearchService.initSearchInterface(this);
    }

    private void injectMembers () {
        try {
            App.getComponent().inject(this);
        } catch (Exception e) {
            Log.e(TAG, "Could not inject members");
        }
    }
}
