package org.sag.wedt.common;

/**
 * Created by Michał Breiter.
 */
public enum ServiceTypes {
    GATHERER,
    CATEGORIZER;

    public String toTypeString() {
        switch(this) {
            case GATHERER:
                return "store";
            case CATEGORIZER:
                return "categorizer";
        }
        return null;
    }
    public static String commonTypeString() {
        return "twitter-catastrophes-miner";
    }


}
