package br.com.hole19.marvel.ui.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Map;

import javax.inject.Inject;

import br.com.edsilfer.kiwi.notification.NotificationManager;
import br.com.hole19.marvel.R;
import br.com.hole19.marvel.comm.model.EventCatalog;
import br.com.hole19.marvel.comm.post_office.NotificationCenter;
import br.com.hole19.marvel.comm.model.TaskExecutor;
import br.com.hole19.marvel.ui.commons.util.managers.ToolbarManager;

/**
 * Created by edgar on 02-May-16.
 */
public abstract class ActivityTemplate extends AppCompatActivity {

    private Boolean isRunning = Boolean.TRUE;
    private ActivityBasicConfiguration mConfig;

    @Inject
    ToolbarManager mToolbarService;

    public abstract ActivityBasicConfiguration setupActivity();

    // CALLBACKS
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.startResources();
    }

    public void startResources() {
        this.mConfig = this.setupActivity();
        if (this.mConfig != null) {
            try {
                this.setContentView(this.mConfig.getContentView());
                this.initToolbar();
                this.loadBackgroundImage((ImageView) this.findViewById(R.id.background), R.drawable.background);
                this.registerForEvents();
            } catch (Exception e) {
                this.startActivity(new Intent(this, ActivityHomepage.class));
            }
        }
    }

    @Override
    public void onDestroy() {
        this.unregisterForEvents();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.isRunning = Boolean.FALSE;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.isRunning = Boolean.TRUE;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (null != this.mConfig.getMenuFile()) {
            this.getMenuInflater().inflate(this.mConfig.getMenuFile(), menu);
            return true;
        }
        return true;
    }

    // INITIALIZATIONS
    private void initToolbar() {
        this.mToolbarService.initToolbar(
                this,
                R.id.toolbar,
                this.mConfig.getTitle(),
                this.mConfig.getToolbartTextColor()
        );

        this.setNavigationBehavior();
    }

    public void setNavigationBehavior() {
        if (this.mConfig.getDefaultReturnBehavior()) {
            this.mToolbarService.prepareNavigationBehavior(R.drawable.ic_back, new ToolbarManager.OnIconClicked() {
                @Override
                public void executeTask() {
                    ActivityTemplate.this.finish();
                }
            });
        } else if (null != this.mConfig.getNavigationIcon()) {
            this.mToolbarService.prepareNavigationBehavior(this.mConfig.getNavigationIcon(), this.mConfig.getOnNavigationItemClicked());
        }
    }

    public void loadBackgroundImage(ImageView wrapper, int imageId) {
        if (null != wrapper) {
            Picasso.with(this)
                    .load(imageId)
                    .fit()
                    .centerCrop()
                    .into(wrapper);
        }
    }

    // HANDLERS LIFECYCLE
    public void registerForEvents() {
        Map<EventCatalog, TaskExecutor> executors = this.setEventHandlers();
        if (null != executors && executors.size() > 0) {
            for (EventCatalog event : executors.keySet()) {
                NotificationCenter.RegistrationCenter.registerForEvent(event, executors.get(event));
            }
        }
    }

    public void unregisterForEvents() {
        Map<EventCatalog, TaskExecutor> executors = this.setEventHandlers();
        if (null != executors && executors.size() > 0) {
            for (EventCatalog key : executors.keySet()) {
                NotificationCenter.RegistrationCenter.unregisterForEvent(key, executors.get(key));
            }
        }
    }

    // UTIL ACTIVITIES METHODS
    public Object retrieveElement(String key) {
        Bundle extras = this.getIntent().getExtras();
        return extras.getSerializable(key);
    }

    // GETTERS AND SETTERS
    public ToolbarManager getToolbarService() {
        return this.mToolbarService;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public Map<EventCatalog, TaskExecutor> setEventHandlers() {
        return null;
    }
}
