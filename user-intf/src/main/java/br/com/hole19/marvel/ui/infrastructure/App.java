package br.com.hole19.marvel.ui.infrastructure;

import android.app.Application;
import android.content.Context;

import br.com.hole19.marvel.ui.infrastructure.modules.ModuleUtil;

/**
 * Helper class that provides a context throught the app to access resources
 */
public class App extends Application {

    private static App mApp = null;
    private static MainComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mApp = this;
        this.component = DaggerMainComponent
                .builder()
                .moduleUtil(new ModuleUtil(this))
                .build();
    }

    public static Application getApplication() {
        return mApp;
    }

    public Context getContext() {
        if (mApp == null) {
            return new Application();
        }
        return mApp.getApplicationContext();
    }

    public static MainComponent getComponent() {
        return component;
    }
}
