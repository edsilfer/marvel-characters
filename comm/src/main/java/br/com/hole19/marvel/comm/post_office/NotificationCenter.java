package br.com.hole19.marvel.comm.post_office;

import java.util.HashSet;
import java.util.Set;

import br.com.hole19.marvel.comm.model.EventCatalog;
import br.com.hole19.marvel.comm.model.comm_data.ResponseWrapper;
import br.com.hole19.marvel.comm.model.TaskExecutor;


/**
 * Created by edgar on 17/02/2016.
 */
public class NotificationCenter {

    public static final String TAG = "NotificationCenter";

    // Event 0000: Get GCM Token
    private static Set<TaskExecutor> mSubEvent0000 = new HashSet<>();
    // Event 0001: List Comic Details
    private static Set<TaskExecutor> mSubEvent0001 = new HashSet<>();
    // Event 0002: Search character
    private static Set<TaskExecutor> mSubEvent0002 = new HashSet<>();

    public static void notify(EventCatalog event, ResponseWrapper response) {
        for (TaskExecutor tasker : NotificationCenter.getSubscriberList(event)) {
            switch (response.getType()) {
                case SUCCESS:
                    tasker.executeOnSuccessTask(response.getPayload());
                    break;
                case ERROR:
                    tasker.executeOnErrorTask(response.getPayload());
                    break;
            }
        }
    }

    public static class RegistrationCenter {
        public static void registerForEvent(EventCatalog event, TaskExecutor subscriber) {
            NotificationCenter.getSubscriberList(event).add(subscriber);
        }

        public static void unregisterForEvent(EventCatalog event, TaskExecutor subscriber) {
            NotificationCenter.getSubscriberList(event).remove(subscriber);
        }
    }

    private static Set<TaskExecutor> getSubscriberList(EventCatalog event) {
        switch (event) {
            case e0000:
                return NotificationCenter.mSubEvent0000;
            case e0001:
                return NotificationCenter.mSubEvent0001;
            case e0002:
                return NotificationCenter.mSubEvent0002;
        }
        return null;
    }
}
