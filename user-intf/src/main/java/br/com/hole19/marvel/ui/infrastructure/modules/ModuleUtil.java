package br.com.hole19.marvel.ui.infrastructure.modules;

import android.content.Context;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import br.com.edsilfer.kiwi.layout.RecyclerViewUtil;
import br.com.edsilfer.kiwi.loading.LoaderManager;
import br.com.edsilfer.kiwi.notification.NotificationManager;
import br.com.hole19.marvel.comm.post_office.Postman;
import br.com.hole19.marvel.ui.commons.util.PermissionsUtil;
import br.com.hole19.marvel.ui.commons.util.StaticResourceProvider;
import br.com.hole19.marvel.ui.commons.util.managers.ToolbarManager;
import br.com.hole19.marvel.ui.infrastructure.App;
import dagger.Module;
import dagger.Provides;

/**
 * Created by edgar on 03-May-16.
 */
@Module
public class ModuleUtil {

    private final Context context;

    public ModuleUtil(Context context) {
        this.context = context;
    }

    @Provides
    public ToolbarManager provideToolbarManager() {
        return new ToolbarManager();
    }

    @Provides
    @Singleton
    public Postman providePostman() {
        return new Postman();
    }

    @Provides
    @Singleton
    public PermissionsUtil providePermissionUtil() {
        return new PermissionsUtil();
    }

    @Singleton
    @Provides
    public App provideApplication() {
        return new App();
    }

    @Provides
    public Picasso providePicasso(Context context) {
        return Picasso.with(context);
    }

    @Singleton
    @Provides
    public StaticResourceProvider provideResourceManager() {
        return new StaticResourceProvider();
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    public RecyclerViewUtil provideRecyclerViewUtil() {
        return new RecyclerViewUtil();
    }

    @Provides
    @Singleton
    public NotificationManager provideNotificationManager() {
        return new NotificationManager();
    }

    @Provides
    @Singleton
    public LoaderManager provideLoaderManager() {
        return new LoaderManager();
    }

}
