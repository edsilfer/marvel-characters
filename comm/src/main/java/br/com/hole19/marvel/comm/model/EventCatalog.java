package br.com.hole19.marvel.comm.model;

/**
 * Created by edgar on 17/02/2016.
 */
public enum EventCatalog {
    // Event 0000: List Characters
    e0000("e0000"),
    // Event 0001: List Comic Details
    e0001("e0001"),
    // Event 0002: Search character
    e0002("e0002");

    private String mValue;

    EventCatalog(String status) {
        this.mValue = status;
    }

    public static EventCatalog fromString(String value) {
        if (value != null) {
            for (EventCatalog b : EventCatalog.values()) {
                if (value.equalsIgnoreCase(b.mValue)) {
                    return b;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        if (this.mValue == null) {
            return "";
        }
        return this.mValue;
    }
}
