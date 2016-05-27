package br.com.hole19.marvel.ui.commons.model;

/**
 * Created by edgar on 02-May-16.
 */
public enum LayoutType {

    SMALL_VERTICAL(0),
    LARGE_VERTICAL(1),
    SMALL_HORIZONTAL(2),
    LARGE_HORIZONTAL(3);

    private int mValue;

    LayoutType(int value) {
        this.mValue = value;
    }

    public static LayoutType fromInteger(int value) {
        for (LayoutType b : LayoutType.values()) {
            if (value == b.mValue) {
                return b;
            }
        }
        return null;
    }

    public int toInteger() {
        return this.mValue;
    }
}
