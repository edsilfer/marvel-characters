package br.com.hole19.marvel.ui.bpo;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import br.com.edsilfer.kiwi.loading.LoaderManager;
import br.com.edsilfer.kiwi.notification.NotificationManager;
import br.com.hole19.marvel.R;
import br.com.hole19.marvel.comm.model.EventCatalog;
import br.com.hole19.marvel.comm.model.TaskExecutor;
import br.com.hole19.marvel.comm.model.comm_data.PayloadWrapper;
import br.com.hole19.marvel.comm.model.marvel.Character;
import br.com.hole19.marvel.comm.post_office.NotificationCenter;
import br.com.hole19.marvel.comm.post_office.Postman;
import br.com.hole19.marvel.ui.commons.util.managers.ToolbarManager;
import br.com.hole19.marvel.ui.controller.ActivityTemplate;
import br.com.hole19.marvel.ui.infrastructure.App;

/**
 * Created by edgar on 04-May-16.
 */
public class CharacterSearchService {

    private static final String TAG = "CharacterSearchService";

    private static int ANIMATION_DURATION = 400;
    private static int ANIMATION_DISTANCE = 400;
    private static int THREADS_NUMBER = 0;

    private EditText mQueryContainer;
    private TextView mNoResultsFound;
    private ActivityTemplate mActivity;
    private RecyclerView mSearchResults;
    private String mLastMatchingQuery;
    private Boolean isValidQuery = Boolean.TRUE;

    @Inject
    CharacterUIService characterUIService;
    @Inject
    Postman mPostman;
    @Inject
    LoaderManager mLoaderManager;
    @Inject
    NotificationManager mNotificationManager;

    public CharacterSearchService() {
        try {
            App.getComponent().inject(this);
        } catch (Exception e) {
        }
    }

    public TaskExecutor event0000Handler = new TaskExecutor() {
        @Override
        public void executeOnSuccessTask(Object response) {
            CharacterSearchService.THREADS_NUMBER--;
            mLoaderManager.hideIndeterminateProgressBar(CharacterSearchService.this.mActivity);
            Gson gson = new Gson();
            PayloadWrapper payloadWrapper = gson.fromJson(response.toString(), PayloadWrapper.class);
            List<Character> results = payloadWrapper.getData().getResults();

            if (results.size() > 0) {
                CharacterSearchService.this.mLastMatchingQuery = CharacterSearchService.this.mQueryContainer.getText().toString();
                CharacterSearchService.this.hideNoResultsFoundMessage();
                CharacterSearchService.this.characterUIService.updateResultList(results);
            } else {
                CharacterSearchService.this.showNoResultsFoundMessage();
            }
        }

        @Override
        public void executeOnErrorTask(Object payload) {
            CharacterSearchService.this.THREADS_NUMBER--;
            if (payload != null) {
                mNotificationManager.showErrorPopUp(CharacterSearchService.this.mActivity, payload.toString());
                Log.e(TAG, payload.toString());
            }
        }
    };

    private void hideNoResultsFoundMessage() {
        if (this.mNoResultsFound.getVisibility() == TextView.VISIBLE) {
            this.mSearchResults.animate().translationXBy(-ANIMATION_DISTANCE).alpha(1).setDuration(ANIMATION_DURATION).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    CharacterSearchService.this.mNoResultsFound.setVisibility(TextView.GONE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }

    private void showNoResultsFoundMessage() {
        this.updateUnmachedQueryText();
        if (this.mNoResultsFound.getVisibility() == TextView.GONE) {
            this.mSearchResults.animate().translationXBy(ANIMATION_DISTANCE).alpha(0).setDuration(ANIMATION_DURATION).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    CharacterSearchService.this.mNoResultsFound.setVisibility(TextView.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
    }

    private void updateUnmachedQueryText() {
        String label = CharacterSearchService.this.mActivity.getString(R.string.rsc_search_result_not_found);
        label = label.replace("PLACEHOLDER", Html.fromHtml("<b>".concat(this.mQueryContainer.getText().toString()).concat("</b>")));
        CharacterSearchService.this.mNoResultsFound.setText(label);
    }

    public void initSearchInterface(ActivityTemplate activity) {
        this.mSearchResults = this.characterUIService.loadSearchResults(activity);
        NotificationCenter.RegistrationCenter.registerForEvent(EventCatalog.e0000, event0000Handler);
        this.mActivity = activity;
        this.mQueryContainer = (EditText) this.mActivity.getToolbarService().getToolbar().findViewById(R.id.query_container);
        this.mNoResultsFound = (TextView) this.mActivity.findViewById(R.id.result_not_found);
        this.addQueryListener();
    }

    public void onTextChanged(String query) {
        if (this.mNoResultsFound.getVisibility() == TextView.GONE || query.equals(this.mLastMatchingQuery)) {
            if (null != query && !"".equals(query)) {
                CharacterSearchService.this.mActivity.getToolbarService().prepareNavigationBehavior(R.drawable.ic_clear, CharacterSearchService.this.clearQuery);
                if (CharacterSearchService.THREADS_NUMBER <= CharacterSearchService.this.mActivity.getResources().getInteger(R.integer.MARVEL_API_PARAM_MAX_REQUESTS)) {
                    mLoaderManager.showIndeterminateProgressBar(CharacterSearchService.this.mActivity);
                    CharacterSearchService.THREADS_NUMBER++;
                    this.mPostman.cancelRequests();
                    this.mPostman.listCharacters(CharacterSearchService.this.mActivity, query, 15, 0);
                }
            } else {
                CharacterSearchService.this.mActivity.getToolbarService().prepareNavigationBehavior(R.drawable.ic_arrow_left_white_24dp, CharacterSearchService.this.finishActivity);
            }
        } else if (query.equals("")) {
            this.hideNoResultsFoundMessage();
        } else {
            this.updateUnmachedQueryText();
        }
    }

    private ToolbarManager.OnIconClicked finishActivity = new ToolbarManager.OnIconClicked() {
        @Override
        public void executeTask() {
            CharacterSearchService.this.mActivity.finish();
        }
    };

    private ToolbarManager.OnIconClicked clearQuery = new ToolbarManager.OnIconClicked() {
        @Override
        public void executeTask() {
            CharacterSearchService.this.mQueryContainer.setText("");
        }
    };

    private void addQueryListener() {
        this.mQueryContainer.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                CharacterSearchService.this.onTextChanged(CharacterSearchService.this.mQueryContainer.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
