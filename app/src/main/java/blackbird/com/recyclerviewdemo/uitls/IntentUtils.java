package blackbird.com.recyclerviewdemo.uitls;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * 所用的Intent跳
 */
public class IntentUtils {


    public static boolean startActivity(@NonNull Context mContext, @NonNull Class<?> cls) {
        return startActivityResult(mContext, cls, null);
    }

    public static boolean startActivityResult(@NonNull Context mContext, @NonNull Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (extras != null) {
            intent.putExtras(extras);
        }
        try {
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 跳转到第三方Apk中Intent ,当未安装第三方Apk时【ActivityNotFoundException】返回false
     *
     * @param mContext
     * @param packageName 第三方apk包名
     * @param className   第三方apk中指定className
     * @return 是否发生ActivityNotFoundException, 没有为true 否则为false；
     */
    public static boolean skipThridApkActivity(@NonNull Context mContext, @NonNull String packageName, String className) {
        ComponentName mComponent = null;
        Intent intent = null;
        if (StringUtils.isNotEmpty(packageName) && StringUtils.isNotEmpty(className)) {
            mComponent = new ComponentName(packageName, className);
        } else if (StringUtils.isNotEmpty(packageName))
            intent = mContext.getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageName);
        if (mComponent != null)
            //
            intent = new Intent().setComponent(mComponent);
        try {
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * Activity 间通过隐式 Intent 的跳转，在发出 Intent 之前必须通过 resolveActivity
     * 检查，避免找不到合适的调用组件，造成 ActivityNotFoundException 的异常
     *
     * @param mContext Context
     * @param intent Intent
     * @param flags PackageManager.MATCH_DEFAULT_ONLY 默认
     * @return
     */
    public static boolean resolveActivity(@NonNull Context mContext, @NonNull Intent intent, int flags) {
        ResolveInfo resolveInfo = mContext.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo == null;
    }

}
