package blackbird.com.recyclerviewdemo.tv.tab;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import blackbird.com.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ItemService extends Service {

    public static final int TYPE_ERROR = -1;
    public static final int FLAG_LOGO_BINDER = 0;
    public static final int DATE_UPDATE = 1;
    public static final int NETWORK_UPDATE = 2;
    public static final int WIFI_SIGNAL_UPDATE = 3;

    public static final int FLAG_FAV_BINDER = 4;

    private ServiceCallback serviceCallback;
    private final IBinder mLocalBinder = new LocalBinder();
    private IBinder logoCallbackBinder;
    private PackageListener mPackageListener;
    private LogoFragmentListener mLogoFragmentListener;
    private UnlockListener mUnlockListener;

    private ArrayList<AppInfo> mAppItems;
    private ArrayList<AppInfo> mFavItems;
    //private volatile boolean mAppListValid;
    //private volatile String mPackageName;
   // private AppItemsDbHelper mDbHelper;
    private final Object mDbHelperLock = new Object();
    private Map<ComponentName, Long> mPriorityMap;
    private Map<String, Long> mOobPriority;
    private Map<String, Long> mFavPriority;
    private String filterApp;
    private String defaultApps;
    private String defaultFavorite;
   // private UpdateRunnable mRunnable;
    private String pkgFromReceiver;


    @Override
    public void onCreate() {
        super.onCreate();
    //    mRunnable = new UpdateRunnable();
        updateAppList(null);

        IntentFilter packageIntentFilter = new IntentFilter();
        packageIntentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        packageIntentFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        packageIntentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        packageIntentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        packageIntentFilter.addDataScheme("package");
        if (mPackageListener == null) mPackageListener = new PackageListener();
        registerReceiver(mPackageListener, packageIntentFilter);

        IntentFilter logoFragmentFilter = new IntentFilter();
        //时间更新广播
        logoFragmentFilter.addAction(Intent.ACTION_TIME_TICK);
        logoFragmentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        logoFragmentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        //网络更新广播
        logoFragmentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        logoFragmentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        logoFragmentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        if (mLogoFragmentListener == null) mLogoFragmentListener = new LogoFragmentListener();
        registerReceiver(mLogoFragmentListener, logoFragmentFilter);

        //user unlock broadcast
        IntentFilter userFilter = new IntentFilter();
        userFilter.addAction(Intent.ACTION_USER_UNLOCKED);
        if (mUnlockListener == null) mUnlockListener = new UnlockListener();
        registerReceiver(mUnlockListener, userFilter);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mLocalBinder;
    }

    /**
     * 获取喜好APP排序的优先等级
     *
     * @param pkgName 包名
     * @return 优先等级
     */
    private long getFavoritePriority(String pkgName) {
        if (mFavPriority == null) {
            final String[] favOrder = getDefaultFavorite().split(";");
            mFavPriority = new ArrayMap<>(favOrder.length);
            for (int i = 0; i < favOrder.length; i++) {
                mFavPriority.put(favOrder[i], (long) (favOrder.length - i));
            }
        }
        return mFavPriority.get(pkgName);
    }

    /**
     * 获取APPS的排序等级
     *
     * @return 优先等级
     */
    private long getAppPriority(ResolveInfo info) {
        ensureDatabase();

        final ComponentName cn =
                new ComponentName(info.activityInfo.packageName, info.activityInfo.name);

        Long priority = mPriorityMap.get(cn);
        if (priority == null) {
            if (mOobPriority.containsKey(info.activityInfo.packageName)) {
                priority = mOobPriority.get(info.activityInfo.packageName);
            } else {
                priority = 0L;
            }
            //mDbHelper.writeOrderPriority(cn, priority);
            mPriorityMap.put(cn, priority);

        }
        return priority;
    }

    /**
     * 获取APPS的排序等级
     *
     * @return 优先等级
     */
    private long getAppPriorityFromMap(ResolveInfo info) {
        final ComponentName cn =
                new ComponentName(info.activityInfo.packageName, info.activityInfo.name);
        Long priority = mPriorityMap.get(cn);
        if (priority == null) {
            if (mOobPriority.containsKey(info.activityInfo.packageName)) {
                priority = mOobPriority.get(info.activityInfo.packageName);
            } else {
                priority = 0L;
            }
            mPriorityMap.put(cn, priority);
        }
        return priority;
    }


    //给默认的配置文件APP优先排到前面
    private void ensureDatabase() {
        synchronized (mDbHelperLock) {
           /* if (mDbHelper == null) {
                mDbHelper = new AppItemsDbHelper(getApplicationContext());
            }*/
           // mPriorityMap = mDbHelper.readOrderPriorities();
            final String[] oobOrder = getDefaultApps().split(";");
            //final String[] oobOrder = getResources().getStringArray(R.array.oob_order);
            mOobPriority = new ArrayMap<>(oobOrder.length);
            for (int i = 0; i < oobOrder.length; i++) {
                mOobPriority.put(oobOrder[i], (long) oobOrder.length - i);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnlockListener != null) {
            unregisterReceiver(mUnlockListener);
            mUnlockListener = null;
        }
        if (mPackageListener != null) {
            unregisterReceiver(mPackageListener);
            mPackageListener = null;
        }
        if (mLogoFragmentListener != null) {
            unregisterReceiver(mLogoFragmentListener);
            mLogoFragmentListener = null;
        }
    }

    private void invalidateListsForPackage(String packageName) {
        boolean updateApps = false;

        if (mAppItems == null) {
            return;
        }

        // Check if the package was previously listed in apps
        for (final AppInfo appInfo : mAppItems) {
            if (TextUtils.equals(appInfo.packageName, packageName)) {
                updateApps = true;
                break;
            }
        }

        // Check if the app will be listed in apps
        final List<ResolveInfo> leanbackInfos = getPackageManager().queryIntentActivities(new Intent(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)
                .setPackage(packageName), 0);

        final List<ResolveInfo> mobileInfos = getPackageManager().queryIntentActivities(new Intent(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_LAUNCHER)
                .setPackage(packageName), 0);
        if (!leanbackInfos.isEmpty() || !mobileInfos.isEmpty()) {
            updateApps = true;
        }

        if (updateApps) {
            updateAppList(packageName);
        }

    }

//    public ArrayList<AppInfo> getAppItems(String pkaName) {
//        updateAppList(pkaName);
//        return mAppItems;
//    }

    /**
     * @return 获取新排序的APP列表
     */
    public ArrayList<AppInfo> getSortAppItems() {
       // AppItemsDbHelper dbHelper = new AppItemsDbHelper(this);
        //dbHelper.deletePriorities();
        loadAllAppFirstTime();
        return mAppItems;
    }

    private boolean addOrRemove(String pkaName) {
        int index = TYPE_ERROR;
        for (AppInfo appInfo : mAppItems) {
            if (appInfo.packageName.equals(pkaName)) {
                //在列表内
                index = mAppItems.indexOf(appInfo);
            }
        }

        //index = TYPE_ERROR(-1), 指不在列表内，是ADD
        //index != -1,且app已安装，说明是REPLACE
        //index != -1,且app未安装，说明是REMOVE
        if (index == TYPE_ERROR) {
            Log.d("mopp", "add package : " + pkaName);
            addAppInfoByPackageName(pkaName);
        } else {
           /* if (Utils.checkApkInstalled(this, pkaName)) {
                Log.d("mopp", "replace package : " + pkaName);
                return false;
            } else {*/
                Log.d("mopp", "remove package : " + pkaName);
                mAppItems.remove(index);
                //App列表更新
                FileUtils.saveToApps(this, mAppItems);

                //Favorite更新
                removeAppInFavList(pkaName);
                //Toast.makeText(this, getString(R.string.uninstall_ok), Toast.LENGTH_SHORT).show();
            //}
        }
        return true;
    }

    private void removeAppInFavList(String pgkName) {
        mFavItems = FileUtils.getFromFav(this);
        if (mFavItems.size() <= 1) {
            return;
        }
        int index = TYPE_ERROR;
        for (int i = 0; i < mFavItems.size() - 1; i++) {
            if (mFavItems.get(i).packageName.equals(pgkName)) {
                index = i;
                break;
            }
        }

        if (index != TYPE_ERROR) {
            mFavItems.remove(index);
            FileUtils.saveToFav(this, mFavItems);
            //LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(FavRowPresenter.ACTION_FAV_UPDATE_LIST));
        }
    }

    private List<ResolveInfo> loadMobileInfo() {
        return getPackageManager().queryIntentActivities(
                new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER),
                0);
    }

    private List<ResolveInfo> loadLeanbackInfo() {
        return getPackageManager().queryIntentActivities(
                new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER),
                0);
    }

/*    //过滤列表
    private String getFilterApps() {
        if (filterApp == null) {
            if ((filterApp = FileUtils.readFromFile("/system/etc/launcher_app_filter.txt")) == null) {
                filterApp = FileUtils.readFromRes(this, R.raw.launcher_app_filter);
            }
        }
        return filterApp;
    }*/

    //默认Favorite列表
    private String getDefaultFavorite() {
        if (defaultFavorite == null) {
          /*  if ((defaultFavorite = ContentProviderHelper.getLauncherFavList(this)) != null) {
                return defaultFavorite;
            } else*/ if ((defaultFavorite = FileUtils.readFromFile("/system/etc/launcher_default_favorite.txt")) != null) {
                return defaultFavorite;
            } else {
               // defaultFavorite = FileUtils.readFromRes(this, R.raw.launcher_default_favorite);
            }
        }
        return defaultFavorite;
    }

    //默认Apps列表
    private String getDefaultApps() {
        if (defaultApps == null) {
            /*if ((defaultApps = ContentProviderHelper.getLauncherAppsList(this)) != null) {
                return defaultApps;
            } else */if ((defaultApps = FileUtils.readFromFile("/system/etc/launcher_default_apps.txt")) != null) {
                return defaultApps;
            } /*else {
                defaultApps = FileUtils.readFromRes(this, R.raw.launcher_default_apps);
            }*/
        }
        return defaultApps;
    }

    private void validaAppInfoFirstTime(ResolveInfo info) {
        String pkgName = info.activityInfo.packageName;
        if (pkgName == null) {
            return;
        }

        AppInfo appInfo = new AppInfo(info, getAppPriorityFromMap(info));
        //Apps
        mAppItems.add(appInfo);
        if (getDefaultApps().contains(pkgName)) { //Apps默认排序,已排序给标志位
         //   SpUtils.putInt(this, SpUtils.FILE_APPS, appInfo.packageName, 1);
        }
        //Favorite
        if (getDefaultFavorite().contains(pkgName)) {
         //   SpUtils.putInt(this, SpUtils.FILE_FAVORITE, pkgName, 1);
            appInfo = new AppInfo(info, getFavoritePriority(pkgName));
            mFavItems.add(appInfo);
        }
    }

    private void validAppInfoByBroadCast(ResolveInfo info) {
        String pkgName = info.activityInfo.packageName;
        if (pkgName == null) {
            return;
        }



        //广播安装的APPS是否要排序
        AppInfo appInfo = new AppInfo(info, getAppPriority(info));
        mAppItems.add(appInfo);

        FileUtils.saveToApps(this, mAppItems);
    }

    private void loadAllAppFirstTime() {
        UserManager userManager = (UserManager) getSystemService(Context.USER_SERVICE);
        if (!userManager.isUserUnlocked()) {
            Log.d("mopp", "UserUnlocked,init interrupt");
            return;
        }
        mAppItems = new ArrayList<>();
        mFavItems = new ArrayList<>();
        Set<String> toRemoves = new ArraySet<>();
        ensureDatabase();
        //优先TV版APP
        for (ResolveInfo info : loadLeanbackInfo()) {
           /* if (getFilterApps().contains(info.activityInfo.packageName)) {
                continue;
            }*/
            validaAppInfoFirstTime(info);
            toRemoves.add(info.activityInfo.packageName);
        }

        //手机版APP
        for (ResolveInfo info : loadMobileInfo()) {
          /*  if (getFilterApps().contains(info.activityInfo.packageName)) {
                continue;
            }*/
            if (toRemoves.contains(info.activityInfo.packageName)) {
                continue;
            }
            validaAppInfoFirstTime(info);
        }
   //     mDbHelper.writeOrderPriority(mPriorityMap);

        Log.d("mopp", "loadAllAppFirstTime mAppItems size = " + mAppItems.size());
        if (mAppItems.isEmpty()) {
            return;
        }
        //第一次需要排序Apps
        Collections.sort(mAppItems);
        FileUtils.saveToApps(ItemService.this, mAppItems);
        //第一次需要排序Favorite
        Collections.sort(mFavItems);
        mFavItems.add(new AppInfo(AppInfo.ADD_PRIORITY));
        FileUtils.saveToFav(ItemService.this, mFavItems);
        //LocalBroadcastManager.getInstance(ItemService.this).sendBroadcast(new Intent(FavRowPresenter.ACTION_FAV_UPDATE_LIST));
    }

    public void updateAppList(String pkaName) {
        pkgFromReceiver = pkaName;
      //  new Thread(mRunnable).start();
    }



    private void addAppInfoByPackageName(String pkgName) {
        //过滤列表
     /*   if (getFilterApps().contains(pkgName)) {
            return;
        }*/
        //手机版APP
        for (ResolveInfo info : loadMobileInfo()) {
            if (info.activityInfo.packageName.equals(pkgName)) {
                validAppInfoByBroadCast(info);
                return;
            }
        }

        //TV版APP
        for (ResolveInfo info : loadLeanbackInfo()) {
            if (info.activityInfo.packageName.equals(pkgName)) {
                validAppInfoByBroadCast(info);
                return;
            }
        }
    }





    public class LocalBinder extends Binder {
        public ItemService getItemService() {
            return ItemService.this;
        }

        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            if (code == FLAG_LOGO_BINDER) {
                logoCallbackBinder = data.readStrongBinder();
            } else if (code == FLAG_FAV_BINDER) {
                //TODO 暂时无用
                IBinder favCallbackBinder = data.readStrongBinder();
            }

            return true;
        }
    }

    public class UnlockListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("mopp", "UnlockListener onReceive");
            updateAppList(null);
            sendMessageToActivity();
        }
    }

    public class PackageListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final Uri packageUri = Uri.parse(intent.getDataString());
            invalidateListsForPackage(packageUri.getSchemeSpecificPart());
        }
    }

    public class LogoFragmentListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            try {
                if (logoCallbackBinder != null) {
                    switch (action) {
                        case Intent.ACTION_TIME_TICK:
                        case Intent.ACTION_TIME_CHANGED:
                        case Intent.ACTION_TIMEZONE_CHANGED:
                            logoCallbackBinder.transact(DATE_UPDATE, null, null, 0);
                            break;
                        case ConnectivityManager.CONNECTIVITY_ACTION:
                        case WifiManager.WIFI_STATE_CHANGED_ACTION:
                            logoCallbackBinder.transact(NETWORK_UPDATE, null, null, 0);
                            break;
                        case WifiManager.RSSI_CHANGED_ACTION:
                            logoCallbackBinder.transact(WIFI_SIGNAL_UPDATE, null, null, 0);
                            break;
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToActivity() {
        if (serviceCallback != null) {
            serviceCallback.onMessageReceived();
        }
    }

    public void setServiceCallback(ServiceCallback serviceCallback) {
        this.serviceCallback = serviceCallback;
    }


    public interface ServiceCallback {
        void onMessageReceived();
    }

}
