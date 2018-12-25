package org.x5webview.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

import org.x5webview.activity.X5BrowserActivity;

public class WebViewsUtils {

    public static void initTBSX5(Application mApplication){

            //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
            QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
                @Override
                public void onViewInitFinished(boolean arg0) {
                    // TODO Auto-generated method stub
                    //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                    Log.d("app", " onViewInitFinished is   x5內核初始化完成" + arg0);
                }

                @Override
                public void onCoreInitFinished() {
                    // TODO Auto-generated method stub
                }
            };
            //x5内核初始化接口
            QbSdk.initX5Environment(mApplication, cb);


    }
    public static void startWebView(Context pckCtx,String uri){
        Intent intent = new Intent(pckCtx, X5BrowserActivity.class);
        intent.setData(Uri.parse(uri));
        pckCtx.startActivity(intent);
    }
}
