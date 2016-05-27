package br.com.hole19.marvel.ui.commons.util.constants;

import android.text.Html;

/**
 * Created by edgar on 02-May-16.
 */
public class NotificationConstants {

    public static final String PLACEHOLDER = "jhasbgsv76AGSF7aI769Z";
    public static final String EXCEPTION_LAYOUT_MISSING = "You need to add: ".concat(PLACEHOLDER).concat(" to your layout");

    public static class LoadingInterface {
        public static final Integer LAYOUT_PROGRESS_BAR_INDETERMINATE_CIRCULAR_CIRCULAR_TOP_MARGIN = 120;
        public static final Integer LAYOUT_PROGRESS_BAR_INDETERMINATE_CIRCULAR_CIRCULAR_DURATION = 600;

        public static final String LAYOUT_PROGRESS_BAR_INDETERMINATE_CIRCULAR = "rsc_util_loading_indeterminate_circular.xml";
        public static final String LAYOUT_PROGRESS_BAR_INDETERMINATE_BAR = "rsc_util_loading_indeterminate_bar.xml";
    }

    public static class NotificationInterface {
        public static final String LAYOUT_SNACK_BAR = "rsc_util_notification_snackbar.xml";

        public static final String INFO_ERROR_TITLE = "ERROR";
        public static final String INFO_WARNING_TITLE = "WARNING";
        public static final String INFO_UNDER_CONSTRUCTION_TITLE = "Under Construction!";
        public static final String INFO_UNDER_CONSTRUCTION_MESSAGE = "Currently this functionality is under construction. Please come back later!";

        public static final String BUTTON_OKAY = "OKAY";
        public static final String BUTTON_CANCEL = "CANCEL";
    }

}
