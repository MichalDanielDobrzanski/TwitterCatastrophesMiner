package org.sag.wedt.common;

/**
 * Created by Michał Breiter.
 */
public enum ServiceTypes {
    STORE,
    CATEGORIZER;

    public String toTypeString() {
        switch(this) {
            case STORE:
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
