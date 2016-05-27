package br.com.hole19.marvel.ui.commons.adapter;

import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import br.com.hole19.marvel.R;
import br.com.hole19.marvel.comm.model.EventCatalog;
import br.com.hole19.marvel.comm.model.TaskExecutor;
import br.com.hole19.marvel.comm.model.marvel.Item;
import br.com.hole19.marvel.comm.post_office.NotificationCenter;
import br.com.hole19.marvel.comm.post_office.Postman;
import br.com.hole19.marvel.comm.util.MarvelRequestParser;
import br.com.hole19.marvel.ui.commons.model.LayoutType;
import br.com.hole19.marvel.ui.commons.util.ImageWarehouse;
import br.com.hole19.marvel.ui.commons.util.StaticResourceProvider;
import br.com.hole19.marvel.ui.commons.util.patterns.Publisher;
import br.com.hole19.marvel.ui.commons.util.patterns.Subscriber;
import br.com.hole19.marvel.ui.infrastructure.App;
import br.com.hole19.marvel.ui.infrastructure.Constants;

/**
 * Created by edgar on 29-Apr-16.
 */
public class AdapterItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Publisher {

    private static final String TAG = "AdapterItem";
    private static final int MAX_THREADS = 10;
    private static int THREAD_NUMBER = 0;

    private List<Item> mDataSet;

    private List<ViewHolder.ItemViewHolder> mHolders = new ArrayList<>();
    private Map<String, ViewHolder.ItemViewHolder> mURIViewHolder;
    private LayoutType mViewType;
    @Inject
    ViewHolderFactory mViewHolderFactory;
    @Inject
    StaticResourceProvider resourceManager;
    @Inject
    App mApplication;
    @Inject
    Postman mPostman;

    private List<Subscriber> mSubscribers = new ArrayList<>();

    // HANDLERS
    public TaskExecutor event0001Handler = new TaskExecutor() {
        @Override
        public void executeOnSuccessTask(Object response) {
            AdapterItem.THREAD_NUMBER--;
            try {
                String resourceUri = MarvelRequestParser.getResourceUri((JSONObject) response);
                String thumbnailUrl = MarvelRequestParser.getItemThumbnailUrl((JSONObject) response);
                String name = MarvelRequestParser.getTitle((JSONObject) response);

                for (String key : AdapterItem.this.mURIViewHolder.keySet()) {
                    ViewHolder.ItemViewHolder viewHolder = AdapterItem.this.mURIViewHolder.get(key);
                    if (resourceUri.equals(viewHolder.getResourceURI())) {
                        AdapterItem.this.mDataSet.get(viewHolder.getDatasetPosition()).setThumbnailUrl(thumbnailUrl);
                        AdapterItem.this.resourceManager.getPicasso()
                                .load(thumbnailUrl)
                                .placeholder(R.drawable.rsc_item_placeholder)
                                .fit()
                                .centerCrop()
                                .into(viewHolder.getThumbnail(), new ImageWarehouse(name, viewHolder.getThumbnail(), Constants.MEDIA_COLLECTIONS));
                    }
                }

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }

        @Override
        public void executeOnErrorTask(Object payload) {
            AdapterItem.THREAD_NUMBER--;
            if (payload != null) {
                Log.e(TAG, payload.toString());
            }
        }
    };

    // CONSTRUCTOR
    @Inject
    public AdapterItem(List<Item> dataset, LayoutType viewType) {
        try {
            App.getComponent().inject(this);
        } catch (Exception e) {
        }
        this.mDataSet = dataset;
        this.mViewType = viewType;
        this.mURIViewHolder = new HashMap<>();
        NotificationCenter.RegistrationCenter.registerForEvent(EventCatalog.e0001, this.event0001Handler);
    }

    // CALLBACKS
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return this.mViewHolderFactory.getViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        this.notifyEvent(position);

        Item item = this.mDataSet.get(position);

        ViewHolder.ItemViewHolder viewHolder = (ViewHolder.ItemViewHolder) holder;
        viewHolder.getName().setText(item.getName());
        viewHolder.setResourceURI(item.getResourceURI());
        viewHolder.setDatasetPosition(position);
        this.mURIViewHolder.put(viewHolder.getResourceURI(), viewHolder);


        this.loadImage(
                item.getThumbnailUrl(),
                item.getName(),
                item.getResourceURI(),
                viewHolder.getThumbnail()
        );

        this.setAnimation(viewHolder.getThumbnail());

        if (!this.mHolders.contains(viewHolder)) {
            mHolders.add(viewHolder);
        }
    }

    private void loadImage(String url, String name, String uri, ImageView container) {
        File image;

        try {
            image = new File(
                    Environment.getExternalStorageDirectory()
                            .getAbsolutePath()
                            .concat(Constants.MEDIA_DIRECTORY.concat(Constants.MEDIA_COLLECTIONS).concat(name.concat(Constants.MEDIA_EXTENSION)))
            );
        } catch (Exception e) {
            image = null;
        }

        if (null != image && image.exists()) {
            this.resourceManager.getPicasso()
                    .load(image)
                    .fit()
                    .centerInside()
                    .placeholder(R.drawable.rsc_item_placeholder)
                    .into(container);
        } else {
            if (null == url || "".equals(url) && AdapterItem.THREAD_NUMBER < AdapterItem.MAX_THREADS) {
                this.resourceManager.getPicasso()
                        .load(R.drawable.rsc_item_placeholder)
                        .fit()
                        .centerInside()
                        .into(container);

                AdapterItem.THREAD_NUMBER++;
                this.mPostman.listComicDetails(uri, this.mApplication.getContext());
            }
        }
    }

    private void setAnimation(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(this.mApplication.getContext(), android.R.anim.fade_in);
        animation.setDuration(1000);
        viewToAnimate.startAnimation(animation);
    }

    // GETTERS AND SETTERS
    @Override
    public int getItemViewType(int position) {
        return this.mViewType.toInteger();
    }

    @Override
    public int getItemCount() {
        return this.mDataSet.size();
    }

    public List<Item> getDataset() {
        return mDataSet;
    }

    public View getThumbnailView(int pos) {
        return this.mURIViewHolder.get(this.mDataSet.get(pos).getResourceURI()).getThumbnail();
    }

    // PUBLISHER INTERFACE
    @Override
    public void registerSubscriber(Subscriber subscriber) {
        this.mSubscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        this.mSubscribers.remove(subscriber);
    }

    @Override
    public void notifyEvent(Object... params) {
        for (Subscriber curr : this.mSubscribers) {
            curr.onEventListener(params);
        }
    }
}
