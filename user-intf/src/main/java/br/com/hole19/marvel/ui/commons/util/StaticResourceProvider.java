package br.com.hole19.marvel.ui.commons.util;

import android.content.Context;
import android.support.design.widget.Snackbar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import br.com.hole19.marvel.ui.infrastructure.App;

/**
 * Created by edgar on 04-May-16.
 */
public class StaticResourceProvider {

    @Inject
    App mApplication;

    public StaticResourceProvider() {
        App.getComponent().inject(this);
    }

    public Picasso getPicasso() {
        return Picasso.with(this.mApplication.getContext());
    }

    public MaterialDialog.Builder getDialogBuilder(Context context) {
        return new MaterialDialog.Builder(context);
    }
}
