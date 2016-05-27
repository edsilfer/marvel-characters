package br.com.hole19.marvel.comm.infrastructure;

/**
 * Created by edgar on 09-May-16.
 */
public class DaggerWrapper {

    private static MainComponent mComponent;

    public static MainComponent getComponent() {
        if (mComponent == null) {
            initComponent();
        }
        return mComponent;
    }

    private static void initComponent () {
       mComponent = DaggerMainComponent
                .builder()
                .moduleUtil(new ModuleUtil())
                .build();
    }
}
