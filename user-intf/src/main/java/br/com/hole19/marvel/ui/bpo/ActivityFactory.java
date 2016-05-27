package br.com.hole19.marvel.ui.bpo;

import br.com.hole19.marvel.R;
import br.com.hole19.marvel.ui.controller.ActivityBasicConfiguration;
import br.com.hole19.marvel.ui.controller.ActivityCharacter;
import br.com.hole19.marvel.ui.controller.ActivityHomepage;
import br.com.hole19.marvel.ui.controller.ActivityItem;
import br.com.hole19.marvel.ui.controller.ActivitySearch;
import br.com.hole19.marvel.ui.controller.ActivityTemplate;

/**
 * Created by edgar on 04-May-16.
 */
public class ActivityFactory {

    public static ActivityBasicConfiguration getActivityConfiguration(Object activity) {
        if (activity instanceof ActivityTemplate) {
            ActivityBasicConfiguration setup = new ActivityBasicConfiguration();
            addCommonConfig(setup);
            if (activity instanceof ActivityHomepage) {
                setup.setContentView(R.layout.act_homepage);
                setup.setDefaultReturnBehavior(Boolean.FALSE);
                setup.setToolbartTextColor(((ActivityTemplate) activity).getResources().getColor(R.color.colorTextSecondary));
                setup.setTitle(((ActivityTemplate) activity).getString(R.string.app_name));
            } else if (activity instanceof ActivityCharacter) {
                setup.setContentView(R.layout.act_character);
                setup.setMenuFile(null);
            } else if (activity instanceof ActivityItem) {
                setup.setMenuFile(null);
                setup.setContentView(R.layout.act_item);
            } else if (activity instanceof ActivitySearch) {
                setup.setContentView(R.layout.act_search);
                setup.setMenuFile(null);
            }
            return setup;
        }
        throw (new IllegalArgumentException("Activity must be descendant from ActivityTemplate"));
    }

    private static void addCommonConfig(ActivityBasicConfiguration setup) {
        setup.setOnNavigationItemClicked(null);
        setup.setDefaultReturnBehavior(Boolean.TRUE);
        setup.setTitle("");
        setup.setToolbartTextColor(R.color.colorTextSecondary);
        setup.setMenuFile(R.menu.mnu_homepage);
    }

}
