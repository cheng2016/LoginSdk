package com.example.loginsdk;

import android.app.Application;

import com.example.loginsdk.util.AppUtils;
import com.example.loginsdk.util.L;

/**
 * Created by Michael Smith on 2016/7/21.
 */

public class RxApplication extends Application {

    private static RxApplication sInstance;

    private static final String DEBUG_MODE = "debugMode";

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        L.isDebug = AppUtils.getBooleanMetaData(this , DEBUG_MODE);
    }

    public  synchronized static  RxApplication getInstance(){
        return sInstance;
    }
}
