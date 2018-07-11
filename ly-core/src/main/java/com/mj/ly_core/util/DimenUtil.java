package com.mj.ly_core.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.mj.ly_core.app.Ly;

public class DimenUtil {
    public static int getScreenWidth(){
        final Resources resources = Ly.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(){
        final Resources resources = Ly.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
