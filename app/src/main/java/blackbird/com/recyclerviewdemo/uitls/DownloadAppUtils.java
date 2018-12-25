package blackbird.com.recyclerviewdemo.uitls;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

import blackbird.com.recyclerviewdemo.application.AppApplication;

/**
 * Created by yzzhang on 2017/12/9.
 */

public class DownloadAppUtils {
    private static final String TAG = DownloadAppUtils.class.getSimpleName();
    public static long downloadUpdateApkId = -1;//下载更新Apk 下载任务对应的Id
    public static String downloadUpdateApkFilePath;//下载更新Apk 文件路径

    public static void downloadApp(Context context, String uri, String title) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(uri));
        //设置在什么网络情况下进行下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle(title);
        request.setAllowedOverRoaming(false);
        //在通知栏中显示
        request.setVisibleInDownloadsUi(true);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE );
        request.setMimeType("application/vnd.android.package-archive");
        // 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "third.apk");
        DownloadManager downManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadFerence = downManager.enqueue(request);
         AppApplication.getInstance().setDownloadFerence(downloadFerence);
    }

    /**
     * 通过浏览器下载APK包
     * @param context
     * @param url
     */
    public static void downloadForWebView(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * 下载更新apk包
     * 权限:1,<uses-permission android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION" />
     * @param context
     * @param url
     */
    public static void downloadForAutoInstall(Context context, String url, String fileName, String title) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        try {
            Uri uri = Uri.parse(url);
            DownloadManager downloadManager = (DownloadManager) context
                    .getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            //在通知栏中显示
            request.setVisibleInDownloadsUi(true);
            request.setTitle(title);
            String filePath = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//外部存储卡
                filePath = Environment.getExternalStorageDirectory().getAbsolutePath();

            } else {
                Log.i(TAG,"没有SD卡");
                return;
            }
            downloadUpdateApkFilePath = filePath + File.separator + fileName;
            // 若存在，则删除
            deleteFile(downloadUpdateApkFilePath);
            Uri fileUri = Uri.fromFile(new File(downloadUpdateApkFilePath));
            request.setDestinationUri(fileUri);
            downloadUpdateApkId = downloadManager.enqueue(request);
        } catch (Exception e) {
            e.printStackTrace();
            downloadForWebView(context, url);
        }finally {
//            registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }
    }


    private static boolean deleteFile(String fileStr) {
        File file = new File(fileStr);
        return file.delete();
    }
}
