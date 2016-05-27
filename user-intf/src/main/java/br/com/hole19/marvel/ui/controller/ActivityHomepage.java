package br.com.hole19.marvel.ui.controller;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.gc.materialdesign.views.ButtonRectangle;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import javax.inject.Inject;

import br.com.edsilfer.kiwi.loading.LoaderManager;
import br.com.edsilfer.kiwi.notification.NotificationManager;
import br.com.hole19.marvel.R;
import br.com.hole19.marvel.comm.post_office.Postman;
import br.com.hole19.marvel.ui.bpo.ActivityFactory;
import br.com.hole19.marvel.ui.bpo.CharacterUIService;
import br.com.hole19.marvel.ui.commons.util.PermissionsUtil;
import br.com.hole19.marvel.ui.commons.util.StaticResourceProvider;
import br.com.hole19.marvel.ui.infrastructure.App;

/**
 * Created by edgar on 28-Apr-16.
 */
public class ActivityHomepage extends ActivityTemplate {

    private static final String TAG = "ActivityHomepage";
    private static final int REQUEST_WRITE_EXTERNAL_PERMISSION = 1;

    public static Boolean LOADING = Boolean.FALSE;

    @Inject
    CharacterUIService characterUIService;
    @Inject
    PermissionsUtil mPermissionsManager;
    @Inject
    StaticResourceProvider mStaticResourceProvider;
    @Inject
    Postman mPostman;
    @Inject
    NotificationManager mNotificationManager;
    @Inject
    LoaderManager mLoaderManager;

    @Override
    public ActivityBasicConfiguration setupActivity() {
        return ActivityFactory.getActivityConfiguration(this);
    }

    @Override
    public void startResources() {
        this.injectMembers();
        super.startResources();
        mLoaderManager.showCircularProgressBar(this);
        if (this.mPermissionsManager.verifyStoragePermissions(this, REQUEST_WRITE_EXTERNAL_PERMISSION)) {
            this.mPostman.listCharacters(
                    this,
                    "",
                    this.getResources().getInteger(R.integer.MARVEL_API_PARAM_DEFAULT_LIMIT),
                    this.getResources().getInteger(R.integer.MARVEL_API_PARAM_DEFAULT_OFFSET)
            );
        }
        this.initDrawerLayout();
        ActivityHomepage.this.characterUIService.loadCharacters(this);
    }

    private void injectMembers() {
        try {
            App.getComponent().inject(this);
        } catch (Exception e) {
            Log.e(TAG, "Could not inject members");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_EXTERNAL_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.mPostman.listCharacters(
                            this,
                            "",
                            this.getResources().getInteger(R.integer.MARVEL_API_PARAM_DEFAULT_LIMIT),
                            this.getResources().getInteger(R.integer.MARVEL_API_PARAM_DEFAULT_OFFSET)
                    );
                } else {
                    mNotificationManager.showPopUp(this,
                            "We're sorry...",
                            "In order to use Marvel Challenge you need to allow the app to write in the external storage of your phone",
                            R.color.colorPrimaryDark
                    );
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(ActivityHomepage.this, ActivitySearch.class);
                this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initDrawerLayout() {
        final DrawerLayout draweLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                draweLayout,
                ActivityHomepage.this.getToolbarService().getToolbar(),
                R.string.drawer_open,
                R.string.drawer_close
        )

        {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                ActivityHomepage.this.invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ActivityHomepage.this.invalidateOptionsMenu();
                syncState();
            }
        };

        draweLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        this.addAboutLibrariesActivity();
    }

    private void addAboutLibrariesActivity() {
        ButtonRectangle button = (ButtonRectangle) this.findViewById(R.id.about_libraries);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LibsBuilder()
                        .withActivityTitle(ActivityHomepage.this.getString(R.string.app_name))
                        .withAboutIconShown(true)
                        .withAboutVersionShown(true)
                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                        .start(ActivityHomepage.this);
            }
        });
    }
}
