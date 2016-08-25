package com.example.loginsdk.util;

import android.app.Activity;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by mitnick.cheng on 2016/8/15.
 */

public class GlobalUtils {
    public static void doLandscape(Activity var0) {
        Display var1 = var0.getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams var2;
        (var2 = var0.getWindow().getAttributes()).width = (int)((double)var1.getWidth() * 0.9D);
        var0.getWindow().setAttributes(var2);
        var0.getWindow().setGravity(16);
    }

    public static WindowManager.LayoutParams notLandscape(Activity var0, double var1, double var3) {
        Display var5 = var0.getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams var6;
        (var6 = var0.getWindow().getAttributes()).width = (int)((double)var5.getWidth() * var3);
        var6.height = (int)((double)var5.getHeight() * var1);
        var0.getWindow().setAttributes(var6);
        var0.getWindow().setGravity(16);
        return var6;
    }
}
