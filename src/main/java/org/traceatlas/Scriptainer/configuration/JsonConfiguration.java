package org.traceatlas.Scriptainer.configuration;

import com.google.gson.Gson;

public class JsonConfiguration {

    public static Gson gsonParser;

    public static void initGsonProcessor() {
        gsonParser = new Gson();
    }
}
