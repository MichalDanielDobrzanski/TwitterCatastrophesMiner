package org.sag.wedt.common;

/**
 * Created by breiker on 5/23/16.
 */
public enum ServiceTypes {
    GATHERER,
    CATEGORIZER;

    public String toTypeString() {
        switch(this) {
            case GATHERER:
                return "gatherer";
            case CATEGORIZER:
                return "categorizer";
        }
        return null;
    }
    public static String commonTypeString() {
        return "twitter-catastrophes-miner";
    }


}
