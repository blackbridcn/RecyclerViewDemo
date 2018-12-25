package blackbird.com.recyclerviewdemo.uitls;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class SystemInfoUtil {
    /**
     * 获取可用内存
     *
     * @param context
     * @return
     */
    public static long getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem / (1024 * 1024);


    }


    /**
     * 获取总内存
     *
     * @param context
     * @return
     */
    public static long getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return initial_memory / (1024 * 1024);
    }


//    public static final int IMPORTANCE_BACKGROUND = 400//后台
//    public static final int IMPORTANCE_EMPTY = 500//空进程
//    public static final int IMPORTANCE_SERVICE = 300//在服务中
//    public static final int IMPORTANCE_VISIBLE = 200//在屏幕前端、获取不到焦点可理解为
//    public static final int IMPORTANCE_FOREGROUND = 100//在屏幕最前端、可获取到焦点 可理解为Activity生命周期的OnResume();


    /**
     * 清理内存
     *
     * @param context
     */
    @SuppressLint("MissingPermission")
    public static void clearMemory(Context context) {
        ActivityManager activityManger = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = activityManger.getRunningAppProcesses();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                ActivityManager.RunningAppProcessInfo apinfo = list.get(i);
                String[] pkgList = apinfo.pkgList;
                if (apinfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    for (int j = 0; j < pkgList.length; j++) {
                        /**清理不可用的内容空间**/
                        activityManger.killBackgroundProcesses(pkgList[j]);
                    }
                }
            }
        }
    }


    /**
     * 获取每个APP占用的内存
     *
     * @param context
     */
    public static void getEveryAppMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                ActivityManager.RunningAppProcessInfo appinfo = list.get(i);
                int[] myMempid = new int[]{appinfo.pid};
                Debug.MemoryInfo[] appMem = am.getProcessMemoryInfo(myMempid);
                int memSize = appMem[0].dalvikPrivateDirty / 1024;
                Log.e("AppMemory", appinfo.processName + ":" + memSize);
            }
        }
    }


    /**
     * 清理应用缓存
     *
     * @param context
     */
    public static void clearAppCache(Context context) {
        File[] dir = context.getCacheDir().listFiles();
        if (dir != null) {
            for (File f : dir) {
                f.delete();
            }
        }
    }

    public void onCreate(Context context) {
        //To change body of implemented methods use File | Settings | File Templates.
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        //注意到manifest中的一句代码
        //
        //android:largeHeap="true"
        //
        //
        //查询相关文档得知是为了让应用能申请使用更多的内存，我们知道安卓系统对于每个应用都有内存使用的限制，机器的内存限制，在/system/build.prop文件中配置的。
        am.getLargeMemoryClass();
        List<ActivityManager.RunningAppProcessInfo> infoList = am.getRunningAppProcesses();
        List<ActivityManager.RunningServiceInfo> serviceInfos = am.getRunningServices(100);

        long beforeMem = getAvailMemory(context);
        Log.d("TAG", "-----------before memory info : " + beforeMem);
        int count = 0;
        if (infoList != null) {
            for (int i = 0; i < infoList.size(); ++i) {
                ActivityManager.RunningAppProcessInfo appProcessInfo = infoList.get(i);
                Log.d("TAG", "process name : " + appProcessInfo.processName);
                //importance 该进程的重要程度  分为几个级别，数值越低就越重要。
                Log.d("TAG", "importance : " + appProcessInfo.importance);

                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
                if (appProcessInfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    String[] pkgList = appProcessInfo.pkgList;
                    for (int j = 0; j < pkgList.length; ++j) {//pkgList 得到该进程下运行的包名
                        Log.d("TAG", "It will be killed, package name : " + pkgList[j]);
                        am.killBackgroundProcesses(pkgList[j]);
                        count++;
                    }
                }


                long afterMem = getAvailMemory(context);
                Log.d("TAG", "----------- after memory info : " + afterMem);
                Toast.makeText(context, "clear " + count + " process, "
                        + (afterMem - beforeMem) + "M", Toast.LENGTH_LONG).show();
            }
        }
    }
}
