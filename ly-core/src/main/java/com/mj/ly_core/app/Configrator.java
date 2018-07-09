package com.mj.ly_core.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

public class Configrator {

    private static final HashMap<String, Object> LY_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();

    // 构造函数
    private Configrator() {
        LY_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    public static Configrator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<String, Object> getLyConfigs() {
        return LY_CONFIGS;
    }

    // 静态内部类单例
    private static class Holder {
        private static final Configrator INSTANCE = new Configrator();
    }

    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final void configure() {
        initIcons();
        LY_CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
    }

    public final Configrator withApiHost(String host) {
        LY_CONFIGS.put(ConfigType.API_HOST.name(), host);
        return this;
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) LY_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("Configration is not ready,call configure");
        }
    }

    public final Configrator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Enum<ConfigType> key) {
        checkConfiguration();
        return (T) LY_CONFIGS.get(key.name());
    }
}
