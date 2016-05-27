package br.com.hole19.marvel.ui.commons.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.hole19.marvel.R;

/**
 * Created by edgar on 08-May-16.
 */
public class ViewHolder {
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView thumbnail;
        private String resourceURI;
        private int datasetPosition;

        public ItemViewHolder(View v) {
            super(v);
            this.name = (TextView) v.findViewById(R.id.name);
            this.thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
        }

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public ImageView getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(ImageView thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getResourceURI() {
            return resourceURI;
        }

        public void setResourceURI(String resourceURI) {
            this.resourceURI = resourceURI;
        }

        public int getDatasetPosition() {
            return datasetPosition;
        }

        public void setDatasetPosition(int datasetPosition) {
            this.datasetPosition = datasetPosition;
        }
    }
}
