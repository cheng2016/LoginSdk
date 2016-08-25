package com.example.loginsdk.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by mitnick.cheng on 2016/8/15.
 */

public class MangoUtils {
    public static int getIntMetaData(Context context, String key){
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getInt(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
