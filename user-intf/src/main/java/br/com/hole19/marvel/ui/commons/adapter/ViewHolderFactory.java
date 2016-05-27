package br.com.hole19.marvel.ui.commons.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.hole19.marvel.R;
import br.com.hole19.marvel.ui.commons.model.LayoutType;

/**
 * Created by edgar on 08-May-16.
 */
public class ViewHolderFactory {

    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.getLayoutInflater(parent.getContext());
        View view;
        switch (LayoutType.fromInteger(viewType)) {
            case SMALL_VERTICAL:
                view = inflater.inflate(R.layout.rsc_util_item_small, parent, false);
                return new ViewHolder.ItemViewHolder(view);
            case LARGE_VERTICAL:
                view = inflater.inflate(R.layout.rsc_util_item_large, parent, false);
                return new ViewHolder.ItemViewHolder(view);
        }
        return null;
    }

    public LayoutInflater getLayoutInflater(Context context) {
        return LayoutInflater.from(context);
    }

}
