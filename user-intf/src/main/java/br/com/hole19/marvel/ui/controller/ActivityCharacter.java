package br.com.hole19.marvel.ui.controller;

import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import br.com.hole19.marvel.R;
import br.com.hole19.marvel.comm.model.marvel.Character;
import br.com.hole19.marvel.ui.bpo.ActivityFactory;
import br.com.hole19.marvel.ui.bpo.CharacterUIService;
import br.com.hole19.marvel.ui.infrastructure.App;
import br.com.hole19.marvel.ui.infrastructure.Constants;

/**
 * Created by edgar on 30-Apr-16.
 */
public class ActivityCharacter extends ActivityTemplate {

    private static final String TAG = "ActivityCharacter";

    @Inject
    CharacterUIService characterUIService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_out_right);
    }

    @Override
    public ActivityBasicConfiguration setupActivity() {
        return ActivityFactory.getActivityConfiguration(this);
    }

    @Override
    public void startResources() {
        this.injectMembers();
        super.startResources();

        Character character = (
                this.retrieveElement(Constants.ActivityCommunication.ACTCOMM_CHARACTER) instanceof Character)
                ? (Character) this.retrieveElement(Constants.ActivityCommunication.ACTCOMM_CHARACTER)
                : null;

        this.characterUIService.loadDescription(character, this);
        this.characterUIService.loadRelatedLinks(character.getUrls(), this);
        this.characterUIService.loadCollection(R.id.comics, character.getComics().getItems(), this);
        this.characterUIService.loadCollection(R.id.series, character.getSeries().getItems(), this);
        this.characterUIService.loadCollection(R.id.stories, character.getStories().getItems(), this);
        this.characterUIService.loadCollection(R.id.events, character.getEvents().getItems(), this);
    }

    private void injectMembers () {
        try {
            App.getComponent().inject(this);
        } catch (Exception e) {
            Log.e(TAG, "Could not inject members");
        }
    }
}
