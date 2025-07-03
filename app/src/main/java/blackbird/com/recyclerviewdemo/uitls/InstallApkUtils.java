package blackbird.com.recyclerviewdemo.uitls;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;

import blackbird.com.recyclerviewdemo.ApkModel;
import blackbird.com.recyclerviewdemo.tv.tab.AppInfo;

public class InstallApkUtils {

    public  static void loadAllApk(Context mContext){

        PackageManager pm = mContext.getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);

        for (ApplicationInfo app : apps) {
            String packageName = app.packageName;
            String appName = pm.getApplicationLabel(app).toString();
            Log.e("InstalledApp", "-------- > Package Name: " + packageName + ", App Name: " + appName+" , ApplicationInfo:"+app.toString());
            ApkModel apkModel =new ApkModel();
            apkModel.setName(app.loadLabel(pm).toString());
            apkModel.setName(app.loadLabel(pm).toString());
            apkModel.setName(app.loadLabel(pm).toString());
            apkModel.setName(app.loadLabel(pm).toString());

        }

    }
}
