package br.com.hole19.marvel.ui.commons.util.managers;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by edgar on 28-Apr-16.
 */
public class ToolbarManager {

    private Toolbar mToolbar;
    private AppCompatActivity mActivity;

    // PUBLIC INTERFACE
    public void initToolbar(AppCompatActivity activity, int resourceId, String title, int resID) {
        this.mActivity = activity;
        this.mToolbar = (Toolbar) this.mActivity.findViewById(resourceId);
        this.mActivity.setSupportActionBar(this.mToolbar);
        this.mActivity.getSupportActionBar().setTitle(title);
        this.mToolbar.setTitleTextColor(resID);
    }

    public void prepareNavigationBehavior(Integer icon, final OnIconClicked executor) {
        if (icon != null) {
            this.mToolbar.setNavigationIcon(icon);
        }
        this.mActivity.getSupportActionBar().setHomeButtonEnabled(true);
        if (executor != null) {
            this.mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    executor.executeTask();
                }
            });
        }
    }

    // GETTERS AND SETTERS
    public Toolbar getToolbar() {
        return mToolbar;
    }

    // INNER CLASSES
    public interface OnIconClicked {
        public void executeTask();
    }
}
