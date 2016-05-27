package br.com.hole19.marvel.ui.controller;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;

import javax.inject.Inject;

import br.com.hole19.marvel.R;
import br.com.hole19.marvel.comm.model.marvel.Item;
import br.com.hole19.marvel.comm.model.EventCatalog;
import br.com.hole19.marvel.comm.post_office.NotificationCenter;
import br.com.hole19.marvel.comm.post_office.Postman;
import br.com.hole19.marvel.comm.util.MarvelRequestParser;
import br.com.hole19.marvel.comm.model.TaskExecutor;
import br.com.hole19.marvel.ui.commons.util.ImageWarehouse;
import br.com.hole19.marvel.ui.commons.util.StaticResourceProvider;
import br.com.hole19.marvel.ui.infrastructure.App;
import br.com.hole19.marvel.ui.infrastructure.Constants;

/**
 * Created by edgar on 06-May-16.
 */
public class FragmentItem extends Fragment {

    private static String TAG = "FragmentItem";

    private ImageView mContainer;
    private ViewGroup mRootView;
    @Inject
    App mApplication;
    @Inject
    StaticResourceProvider resourceManager;
    @Inject
    Postman mPostman;

    public TaskExecutor event0001Handler = new TaskExecutor() {
        @Override
        public void executeOnSuccessTask(Object response) {
            try {
                String thumbnailUrl = MarvelRequestParser.getItemThumbnailUrl((JSONObject) response);
                String name = MarvelRequestParser.getTitle((JSONObject) response);

                FragmentItem.this.mRootView.findViewById(R.id.name).setVisibility(TextView.VISIBLE);
                FragmentItem.this.resourceManager.getPicasso()
                        .load(thumbnailUrl)
                        .fit()
                        .centerInside()
                        .into(FragmentItem.this.mContainer, new ImageWarehouse(name, FragmentItem.this.mContainer, Constants.MEDIA_COLLECTIONS));
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        @Override
        public void executeOnErrorTask(Object payload) {
            if (payload != null) {
                Log.e(TAG, payload.toString());
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        App.getComponent().inject(this);
        NotificationCenter.RegistrationCenter.registerForEvent(EventCatalog.e0001, this.event0001Handler);

        this.mRootView = (ViewGroup) inflater.inflate(R.layout.rsc_util_item_large, container, false);
        this.mContainer = (ImageView) this.mRootView.findViewById(R.id.thumbnail);
        Item item = (Item) getArguments().getSerializable(Constants.ActivityCommunication.ACTCOMM_COLLECTIONS);

        TextView name = (TextView) this.mRootView.findViewById(R.id.name);
        name.setText(item.getName());
        name.setVisibility(TextView.VISIBLE);

        this.loadImage(item.getResourceURI(), item.getName());

        return this.mRootView;
    }

    private void loadImage(String uri, String name) {
        File image = new File(
                Environment.getExternalStorageDirectory()
                        .getAbsolutePath()
                        .concat(Constants.MEDIA_DIRECTORY.concat(Constants.MEDIA_COLLECTIONS).concat(name.concat(Constants.MEDIA_EXTENSION)))
        );
        if (image.exists()) {
            FragmentItem.this.resourceManager.getPicasso()
                    .load(image)
                    .fit()
                    .centerInside()
                    .into((ImageView) this.mRootView.findViewById(R.id.thumbnail));
        } else {
            this.mPostman.listComicDetails(uri, this.mApplication.getContext());
        }
    }

}
