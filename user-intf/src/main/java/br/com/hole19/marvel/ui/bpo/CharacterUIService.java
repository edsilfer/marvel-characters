package br.com.hole19.marvel.ui.bpo;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.edsilfer.kiwi.layout.RecyclerViewUtil;
import br.com.edsilfer.kiwi.loading.LoaderManager;
import br.com.edsilfer.kiwi.notification.NotificationManager;
import br.com.hole19.marvel.R;
import br.com.hole19.marvel.comm.model.EventCatalog;
import br.com.hole19.marvel.comm.model.TaskExecutor;
import br.com.hole19.marvel.comm.model.comm_data.PayloadWrapper;
import br.com.hole19.marvel.comm.model.marvel.Character;
import br.com.hole19.marvel.comm.model.marvel.Item;
import br.com.hole19.marvel.comm.model.marvel.Link;
import br.com.hole19.marvel.comm.post_office.NotificationCenter;
import br.com.hole19.marvel.comm.post_office.Postman;
import br.com.hole19.marvel.ui.commons.adapter.AdapterCharacter;
import br.com.hole19.marvel.ui.commons.adapter.AdapterItem;
import br.com.hole19.marvel.ui.commons.model.LayoutType;
import br.com.hole19.marvel.ui.commons.util.StaticResourceProvider;
import br.com.hole19.marvel.ui.commons.util.patterns.Subscriber;
import br.com.hole19.marvel.ui.controller.ActivityCharacter;
import br.com.hole19.marvel.ui.controller.ActivityHomepage;
import br.com.hole19.marvel.ui.controller.ActivityItem;
import br.com.hole19.marvel.ui.controller.ActivityTemplate;
import br.com.hole19.marvel.ui.infrastructure.App;
import br.com.hole19.marvel.ui.infrastructure.Constants;

/**
 * Created by edgar on 04-May-16.
 */
public class CharacterUIService {

    private static final String TAG = "CharacterUIService";

    private AdapterCharacter mAdapter;
    private AdapterCharacter mSearchAdapter;
    private ActivityTemplate mHomepageActivity;

    @Inject
    NotificationManager mNotificationManager;
    @Inject
    App mApplication;
    @Inject
    StaticResourceProvider mResources;
    @Inject
    Postman mPostman;
    @Inject
    RecyclerViewUtil mRecyclerViewUtil;
    @Inject
    LoaderManager mLoaderManager;

    public CharacterUIService() {
        try {
            App.getComponent().inject(this);
        } catch (Exception e) {
        }
        this.mAdapter = new AdapterCharacter(new ArrayList<Character>(), LayoutType.LARGE_VERTICAL);
    }

    public TaskExecutor event0000Handler = new TaskExecutor() {
        @Override
        public void executeOnSuccessTask(Object response) {
            if (CharacterUIService.this.mHomepageActivity.isRunning()) {
                mLoaderManager.hideCircularProgressBar(CharacterUIService.this.mHomepageActivity);
                ActivityHomepage.LOADING = Boolean.FALSE;
                Gson gson = new Gson();
                PayloadWrapper payloadWrapper = gson.fromJson(response.toString(), PayloadWrapper.class);
                CharacterUIService.this.updateCharacterList(payloadWrapper.getData().getResults());
            }
        }

        @Override
        public void executeOnErrorTask(Object payload) {
            if (payload != null) {
                mNotificationManager.showErrorPopUp(CharacterUIService.this.mHomepageActivity, payload.toString());
                Log.e(TAG, payload.toString());
            }
        }
    };

    private void updateCharacterList(List<Character> characters) {
        if (null != this.mAdapter) {
            if (this.mAdapter.getItemCount() == 0) {
                this.mAdapter.setCharacter(characters);
                this.mAdapter.notifyDataSetChanged();
            } else {
                this.mAdapter.setCharacter(characters);
                this.mAdapter.notifyCharacterRangeInserted(this.mApplication.getContext().getResources().getInteger(R.integer.MARVEL_API_PARAM_DEFAULT_LIMIT));
            }
        }
    }

    public void updateResultList(List<Character> characters) {
        CharacterUIService.this.mSearchAdapter.setDataset(characters);
        CharacterUIService.this.mSearchAdapter.notifyDataSetChanged();
    }

    public RecyclerView loadSearchResults(final AppCompatActivity activity) {
        this.mSearchAdapter = new AdapterCharacter(new ArrayList<Character>(), LayoutType.SMALL_HORIZONTAL);

        return mRecyclerViewUtil.initListItems(
                activity,
                R.id.characters,
                LinearLayoutManager.VERTICAL,
                new RecyclerViewUtil.RecyclerViewOnItemClick(activity,
                        new RecyclerViewUtil.RecyclerViewOnItemClick.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(activity, ActivityCharacter.class);
                                intent.putExtra(Constants.ActivityCommunication.ACTCOMM_CHARACTER, CharacterUIService.this.mSearchAdapter.getCharacter(position));
                                activity.startActivity(intent);
                            }
                        }
                ),
                this.mSearchAdapter
        );
    }

    public void loadCharacters(final ActivityTemplate activity) {
        this.mHomepageActivity = activity;

        this.mAdapter.registerSubscriber(new Subscriber() {
            @Override
            public void onEventListener(Object... params) {
                if (params.length == 1) {
                    try {
                        if ((int) params[0] == (CharacterUIService.this.mAdapter.getItemCount() - 1) && !ActivityHomepage.LOADING) {
                            ActivityHomepage.LOADING = Boolean.TRUE;

                            mNotificationManager.showSnackAlert(
                                    activity,
                                    activity.findViewById(R.id.characters_coordinator_wrapper),
                                    activity.getString(R.string.rsc_homepage_loading_characters)
                            );

                            mPostman.listCharacters(
                                    activity,
                                    "",
                                    activity.getResources().getInteger(R.integer.MARVEL_API_PARAM_DEFAULT_LIMIT),
                                    CharacterUIService.this.mAdapter.getItemCount()
                            );
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        });

        mRecyclerViewUtil.initListItems(
                activity,
                R.id.characters,
                LinearLayoutManager.VERTICAL,
                new RecyclerViewUtil.RecyclerViewOnItemClick(
                        activity,
                        new RecyclerViewUtil.RecyclerViewOnItemClick.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(activity, ActivityCharacter.class);
                                intent.putExtra(Constants.ActivityCommunication.ACTCOMM_CHARACTER, CharacterUIService.this.mAdapter.getCharacter(position));
                                activity.startActivity(intent);
                            }
                        }
                ),
                this.mAdapter
        );

        NotificationCenter.RegistrationCenter.registerForEvent(EventCatalog.e0000, event0000Handler);
    }

    public void loadDescription(final Character character, AppCompatActivity activity) {
        this.mResources.getPicasso()
                .load(character.getThumbnail().getUrl())
                .fit()
                .centerCrop()
                .into(((ImageView) activity.findViewById(R.id.cover)));

        TextView name = (TextView) activity.findViewById(R.id.name);
        name.setText((character.getName() != null || !"".equals(character.getName()))
                ? character.getName()
                : activity.getString(R.string.rsc_util_unknown_value));

        TextView description = (TextView) activity.findViewById(R.id.character_description);
        description.setText((character.getDescription() != null && !"".equals(character.getDescription()))
                ? character.getDescription()
                : activity.getString(R.string.rsc_collections_description_not_found));
    }

    public void loadRelatedLinks(final List<Link> urls, final AppCompatActivity activity) {
        ListView links = (ListView) activity.findViewById(R.id.links);

        String[] values = {
                "DETAILS",
                "WIKI",
                "COMICLINK"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                activity,
                R.layout.rsc_character_related_links_item,
                R.id.name,
                values
        );

        links.setAdapter(adapter);
        mRecyclerViewUtil.fixListViewSize((int) activity.getResources().getDimension(R.dimen.rsc_collections_related_links_height), links);

        links.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls.get(position).getUrl()));
                    activity.startActivity(browserIntent);
                } catch (ArrayIndexOutOfBoundsException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }

    public void loadCollection(int resourceID, final List<Item> dataset, final AppCompatActivity activity) {
        if (dataset.size() == 0) {
            this.showNotFoundMessage(resourceID, activity);
        } else {
            final AdapterItem adapter = new AdapterItem(dataset, LayoutType.SMALL_VERTICAL);
            mRecyclerViewUtil.initListItems(
                    activity,
                    resourceID,
                    LinearLayoutManager.HORIZONTAL,
                    new RecyclerViewUtil.RecyclerViewOnItemClick(
                            activity,
                            new RecyclerViewUtil.RecyclerViewOnItemClick.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent = new Intent(activity, ActivityItem.class);
                                    intent.putExtra(Constants.ActivityCommunication.ACTCOMM_COLLECTIONS, (ArrayList<Item>) adapter.getDataset());
                                    intent.putExtra(Constants.ActivityCommunication.ACTCOMM_COLLECTIONS_STARTPOSITION, position);
                                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                            activity,
                                            adapter.getThumbnailView(position),
                                            "picture");
                                    activity.startActivity(intent, options.toBundle());
                                }
                            }
                    ),
                    adapter
            );
        }
    }

    private void showNotFoundMessage(int resourceID, AppCompatActivity activity) {
        switch (resourceID) {
            case R.id.comics:
                activity.findViewById(R.id.comic_not_found).setVisibility(TextView.VISIBLE);
                activity.findViewById(R.id.comics).setVisibility(TextView.GONE);
                break;
            case R.id.series:
                activity.findViewById(R.id.series_not_found).setVisibility(TextView.VISIBLE);
                activity.findViewById(R.id.series).setVisibility(TextView.GONE);
                break;
            case R.id.stories:
                activity.findViewById(R.id.stories_not_found).setVisibility(TextView.VISIBLE);
                activity.findViewById(R.id.stories).setVisibility(TextView.GONE);
                break;
            case R.id.events:
                activity.findViewById(R.id.events_not_found).setVisibility(TextView.VISIBLE);
                activity.findViewById(R.id.events).setVisibility(TextView.GONE);
                break;
        }
    }
}
