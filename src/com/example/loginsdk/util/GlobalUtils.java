package com.example.loginsdk.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager.LayoutParams;

import com.example.loginsdk.controller.MGCommitDialog;

/**
 * Created by mitnick.cheng on 2016/8/15.
 */

public class GlobalUtils {
    private static MGCommitDialog mgCommitDialog;

    public static void a(String var0, Context var1) {
        if(var1 != null) {
            dismissDialog();
            (mgCommitDialog = new MGCommitDialog(var1)).setMessage(var0);
            mgCommitDialog.show();
        }
    }

    public static void dismissDialog() {
        if(mgCommitDialog != null && mgCommitDialog.isShowing()) {
            mgCommitDialog.dismiss();
        }
    }

    /**
     * 获取当前设备的IMIE，需与上面的isPhone一起使用
     * 需添加权限<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     */
    public static String getDeviceIMEI(Context context) {
        String var1 = "";
        if(TextUtils.isEmpty("")) {
            var1 = ((TelephonyManager)context.getSystemService("phone")).getDeviceId();
        }
        return var1;
    }
    
    /**
     * 获取设备MAC地址
     * 需添加权限<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     */
    public static String getMacAddress(Context var0) {
        return ((WifiManager)var0.getSystemService("wifi")).getConnectionInfo().getMacAddress();
    }

    public static void setScreenOrientation(Activity activity, int var1) {
        if(var1 == 0) {
            if(VERSION.SDK_INT < 9) {
            	activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                return;
            }
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
        	setScreenOrientation(activity);
        }
    }

    public static void setScreenOrientation(Activity var0) {
        if(VERSION.SDK_INT >= 9) {
            var0.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        } else {
            var0.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public static int a(Context var0, int var1) {
        float var2 = var0.getResources().getDisplayMetrics().density;
        return (int)((float)var1 * var2 + 0.5F * (float)(var1 >= 0?1:-1));
    }

    public static void setDisplayPartams(Activity var0) {
        Display var1 = var0.getWindowManager().getDefaultDisplay();
        LayoutParams var2;
        (var2 = var0.getWindow().getAttributes()).width = (int)((double)var1.getWidth() * 0.9D);
        var0.getWindow().setAttributes(var2);
        var0.getWindow().setGravity(16);
    }

    public static LayoutParams setDisplayPartams(Activity activity, double height, double width) {
        Display var5 = activity.getWindowManager().getDefaultDisplay();
        LayoutParams params;
        (params = activity.getWindow().getAttributes()).width = (int)((double)var5.getWidth() * width);
        params.height = (int)((double)var5.getHeight() * height);
        activity.getWindow().setAttributes(params);
        activity.getWindow().setGravity(16);
        return params;
    }
    
    public static String getLocalTime() {
        String var0 = (new SimpleDateFormat("MM-dd HH:mm")).format(new Date());
        System.out.println(var0);
        return var0;
    }
    
    static boolean isPassword(String var0) {
        return !TextUtils.isEmpty(var0) && var0.length() >= 6 && var0.length() <= 18;
    }
    
    public static int getStatusBarHeight(Context var0) {
        Class var1 = null;
        Object var2 = null;
        var1 = null;
        boolean var4 = false;
        var4 = false;

        try {
            var2 = (var1 = Class.forName("com.android.internal.R$dimen")).newInstance();
            int var5 = Integer.parseInt(var1.getField("status_bar_height").get(var2).toString());
            return var0.getResources().getDimensionPixelSize(var5);
        } catch (Exception var3) {
            L.e("get status bar height fail");
            var3.printStackTrace();
            return 0;
        }
    }
}
