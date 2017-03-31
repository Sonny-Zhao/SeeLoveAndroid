package com.tianyu.seelove.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 设置一些系统偏好参数sp
 * @author shisheng.zhao
 * @date 2016-11-14 17:57:12
 */
public class AppUtils {
    //系统偏好参数写入文件
    public static final String PREFS_NAME = "dp_prefs";
    private static SharedPreferences sharedPreferences;
    private static PackageInfo packageInfo;
    private static AppUtils instance;
    private static Context context;

    /**
     * 获取一个单例
     * @return
     */
    public static synchronized AppUtils getInstance() {
        if (instance == null) {
            instance = new AppUtils(context);
        }
        return instance;
    }

    /**
     * 构造方法
     * @param context
     */
    private AppUtils(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化
     * @param context
     * @return
     */
    public static AppUtils init(Context context) {
        AppUtils.context = context;
        if (instance == null) {
            instance = new AppUtils(context);
        }
        return instance;
    }

    /**
     * 重置appUtils
     */
    public static void reset() {
        instance = null;
    }

    /**
     * 获取当前应用版本
     * @return
     */
    public int getCurrentVersion() {
        return packageInfo.versionCode;
    }
}