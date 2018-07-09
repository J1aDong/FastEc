package com.mj.ly_core.app;

import android.content.Context;
import android.util.Config;

import java.util.HashMap;

public final class Ly {

    public static Configrator init(Context context) {
        getConfiguration().put(ConfigType.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configrator.getInstance();
    }

    public static HashMap<String, Object> getConfiguration() {
        return Configrator.getInstance().getLyConfigs();
    }

    public static Context getApplication() {
        return (Context) getConfiguration().get(ConfigType.APPLICATION_CONTEXT.name());
    }
}
