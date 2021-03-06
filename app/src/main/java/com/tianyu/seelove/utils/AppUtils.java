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

    /**
     * 保存用户的ID
     * @param userId
     */
    public void setUserId(Long userId) {
        sharedPreferences.edit().putLong("userId", userId).commit();
    }

    /**
     * 获取用户手机号
     * @return
     */
    public Long getUserId() {
        return sharedPreferences.getLong("userId", 0L);
    }

    /**
     * 保存用户的token
     * @param token
     */
    public void setUserToken(String token) {
        sharedPreferences.edit().putString("userToken", token).commit();
    }

    /**
     * 获取用户token
     * @return
     */
    public String getUserToken() {
        return sharedPreferences.getString("userToken", "");
    }

    /**
     * 设置用户筛选年龄起始范围
     * @param startAge
     */
    public void setStartAge(int startAge) {
        sharedPreferences.edit().putInt("startAge", startAge);
    }

    /**
     * 获取用户筛选年龄起始范围
     * @return
     */
    public int getStartAge() {
        return sharedPreferences.getInt("startAge", 18);
    }

    /**
     * 设置用户筛选年龄截止范围
     * @param endAge
     */
    public void setEndAge(int endAge) {
        sharedPreferences.edit().putInt("endAge", endAge);
    }

    /**
     * 获取用户筛选年龄截止范围
     * @return
     */
    public int getEndAge() {
        return sharedPreferences.getInt("endAge", 50);
    }

    /**
     * 设置用户性别code
     * @param sexCode
     */
    public void setSexCode(String sexCode) {
        sharedPreferences.edit().putString("sexCode", sexCode);
    }

    /**
     * 获取用户性别code
     * @return
     */
    public String getSexCode() {
        return sharedPreferences.getString("sexCode", "0");
    }

    /**
     * 设置用户所在城市code
     * @param cityCode
     */
    public void setCityCode(String cityCode) {
        sharedPreferences.edit().putString("cityCode", cityCode);
    }

    /**
     * 获取用户所在城市code
     * @return
     */
    public String getCityCode() {
        return sharedPreferences.getString("cityCode", "00");
    }
}