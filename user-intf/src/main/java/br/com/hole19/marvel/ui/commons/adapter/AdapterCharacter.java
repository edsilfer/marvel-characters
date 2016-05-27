package br.com.hole19.marvel.ui.commons.adapter;

import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.hole19.marvel.R;
import br.com.hole19.marvel.comm.model.marvel.Character;
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
public class AdapterCharacter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Publisher {

    private static final String TAG = "AdapterCharacter";

    private List<Character> mDataset;
    private List<Subscriber> mSubscribers = new ArrayList<>();
    private LayoutType mViewType;

    @Inject
    App mApplication;
    @Inject
    StaticResourceProvider resourceManager;

    // CONSTRUCTOR
    public AdapterCharacter(List<Character> dataset, LayoutType viewType) {
        try {
            App.getComponent().inject(this);
        }catch (Exception e) {}
        this.mDataset = dataset;
        this.mViewType = viewType;
    }

    // CALLBACKS
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view;

        switch (LayoutType.fromInteger(viewType)) {
            case SMALL_HORIZONTAL:
                view = inflater.inflate(R.layout.rsc_util_character_small, parent, false);
                break;
            case LARGE_VERTICAL:
                view = inflater.inflate(R.layout.rsc_util_character_large, parent, false);
                break;
            default:
                view = inflater.inflate(R.layout.rsc_util_character_small, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        this.notifyEvent(position);

        String name = this.mDataset.get(position).getName();

        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.name.setText(name);

        final File image = new File(
                Environment.getExternalStorageDirectory()
                        .getAbsolutePath()
                        .concat(Constants.MEDIA_DIRECTORY.concat(Constants.MEDIA_CHARACTER).concat(name.concat(Constants.MEDIA_EXTENSION)))
        );

        if (image.exists()) {
            this.resourceManager.getPicasso()
                    .load(image)
                    .fit()
                    .centerCrop()
                    .into(viewHolder.cover);
        } else {
            this.resourceManager.getPicasso()
                    .load(this.mDataset.get(position).getThumbnail().getUrl())
                    .fit()
                    .centerCrop()
                    .into(viewHolder.cover, new ImageWarehouse(name, viewHolder.cover, Constants.MEDIA_CHARACTER));
        }

        this.setAnimation(viewHolder.cover);
    }


    public void notifyCharacterRangeInserted(int range) {
        try {
            int start = this.getItemCount() - range;
            this.notifyItemRangeInserted(start, range);
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, "Failed animate character insertion");
        }
    }

    private void setAnimation(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(this.mApplication.getContext(), android.R.anim.slide_in_left);
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
        return this.mDataset.size();
    }

    public Character getCharacter(int pos) {
        return this.mDataset.get(pos);
    }

    public void setDataset(List<Character> dataSet) {
        this.mDataset = dataSet;
    }

    public void setCharacter(List<Character> dataSet) {
        this.mDataset.addAll(dataSet);
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

    // INNER CLASSES
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView cover;

        public ViewHolder(View v) {
            super(v);
            this.name = (TextView) v.findViewById(R.id.name);
            this.cover = (ImageView) v.findViewById(R.id.cover);
        }
    }
}
