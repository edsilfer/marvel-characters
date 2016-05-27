package br.com.hole19.marvel.ui.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.hole19.marvel.R;
import br.com.hole19.marvel.comm.model.marvel.Item;
import br.com.hole19.marvel.ui.bpo.ActivityFactory;
import br.com.hole19.marvel.ui.commons.util.DepthPageTransformer;
import br.com.hole19.marvel.ui.infrastructure.App;
import br.com.hole19.marvel.ui.infrastructure.Constants;

/**
 * Created by edgar on 01-May-16.
 */
public class ActivityItem extends ActivityTemplate {

    private static final String TAG = "ActivityItem";

    private PagerAdapter mPagerAdapter;
    private ViewPager mPager;
    private List<Item> mItems;
    private int mStartingPosition;

    public ActivityBasicConfiguration setupActivity() {
        return ActivityFactory.getActivityConfiguration(this);
    }

    @Override
    public void startResources() {
        this.injectMembers();
        super.startResources();
        this.loadItems();
    }

    private void injectMembers () {
        try {
            App.getComponent().inject(this);
        } catch (Exception e) {
            Log.e(TAG, "Could not inject members");
        }
    }

    private void loadItems () {
        this.mPager = (ViewPager) findViewById(R.id.items);
        this.mItems = this.getItems();
        this.mStartingPosition = this.getStartPosition();
        this.mPagerAdapter = new ScreenSlidePagerAdapter(this.getSupportFragmentManager());
        this.mPager.setAdapter(mPagerAdapter);
        this.mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ActivityItem.this.updateItemPosition(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPager.setPageTransformer(true, new DepthPageTransformer());
        this.mPager.setCurrentItem(this.mStartingPosition);
    }

    public String updateItemPosition(int position) {
        TextView title = (TextView) this.getToolbarService().getToolbar().findViewById(R.id.title);
        String label = new Integer(position + 1).toString().concat(" / ").concat(new Integer(this.mItems.size()).toString());
        title.setText(label);
        return label;
    }

    @Override
    public void onBackPressed() {
        if ((this.mPager.getCurrentItem() == this.mStartingPosition)) {
            super.onBackPressed();
        } else if (this.mPager.getCurrentItem() < this.mStartingPosition) {
            this.mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        } else {
            this.mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private int getStartPosition () {
        return this
                .getIntent()
                .getExtras()
                .getInt(Constants.ActivityCommunication.ACTCOMM_COLLECTIONS_STARTPOSITION);
    }

    private List<Item> getItems() {
        Bundle extras = this.getIntent().getExtras();
        Object object = extras.getSerializable(Constants.ActivityCommunication.ACTCOMM_COLLECTIONS);
        return (object instanceof ArrayList) ? (ArrayList<Item>) object : null;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.ActivityCommunication.ACTCOMM_COLLECTIONS, mItems.get(mPager.getCurrentItem()));
            FragmentItem obj = new FragmentItem();
            obj.setArguments(bundle);
            return obj;
        }

        @Override
        public int getCount() {
            return ActivityItem.this.mItems.size();
        }
    }
}
