package blackbird.com.recyclerviewdemo.tv.tab;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppUtils {

    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    packageName, 0);
            return packageInfo.applicationInfo.loadLabel(packageManager) + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isPkgInstalled(Context context, String pkgName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }


    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getVersionName(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    packageName, 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized int getVersionCode(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    packageName, 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context, String packageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取图标 bitmap
     * @param context
     */
    public static synchronized Bitmap getBitmap(Context context, String packageName) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext()
                    .getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        Drawable d = packageManager.getApplicationIcon(applicationInfo);
        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap bm = bd.getBitmap();
        return bm;
    }

    public static List<Map<String, String>> getInstalledPackages(Context context) {
        PackageManager packageManager = getPackageManager(context);
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<Map<String, String>> packageInfoList = new ArrayList<>();
        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo packageInfo = packageInfos.get(i);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                Map<String, String> map = new HashMap<>();
                map.put("app_name", packageInfo.applicationInfo.loadLabel(packageManager) + "");
                map.put("package_name", packageInfo.packageName);
                map.put("version", packageInfo.versionName);
                packageInfoList.add(map);
            }
        }
        return packageInfoList;
    }

    private static PackageManager getPackageManager(Context context) {
        return context.getPackageManager();
    }


    public static boolean isAppRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }


    //获取已安装应用的 uid，-1 表示未安装此应用或程序异常
    public static int getPackageUid(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            if (applicationInfo != null) {
                //Logger.d(applicationInfo.uid);
                return applicationInfo.uid;
            }
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }

    /**
     * 判断某一 uid 的程序是否有正在运行的进程，即是否存活
     * Created by cafeting on 2017/2/4.
     *
     * @param context   上下文
     * @param uid 已安装应用的 uid
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isProcessRunning(Context context, int uid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
        if (runningServiceInfos.size() > 0) {
            for (ActivityManager.RunningServiceInfo appProcess : runningServiceInfos) {
                if (uid == appProcess.uid) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取应用图标
     * @param context 上下文对象
     * @param packageName 目标应用包名
     * @return 应用图标Drawable，失败返回默认图标
     */
    public static Drawable getAppIcon(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            return pm.getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            return ContextCompat.getDrawable(context, android.R.drawable.sym_def_app_icon);
        }
    }

    /**
     * 获取应用横幅（优先获取Application Banner，失败则尝试获取Activity Banner）
     * @param context 上下文对象
     * @param packageName 目标应用包名
     * @return 横幅Drawable，失败返回null
     */
    public static Drawable getAppBanner(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(packageName, 0);

            // 尝试获取Application级别的Banner
            int bannerId = appInfo.banner;
            if (bannerId != 0) {
                return pm.getDrawable(packageName, bannerId, appInfo);
            }

            // 如果应用未声明banner，尝试获取主Activity的banner
            ActivityInfo[] activities = pm.getPackageArchiveInfo(
                    appInfo.sourceDir, PackageManager.GET_ACTIVITIES).activities;
            if (activities != null) {
                for (ActivityInfo activity : activities) {
                    if (activity.banner != 0) {
                        return pm.getDrawable(packageName, activity.banner, appInfo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAppLabelByPackageName(Context context, String packageName) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(packageName, 0);
            return pm.getApplicationLabel(appInfo).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

}
