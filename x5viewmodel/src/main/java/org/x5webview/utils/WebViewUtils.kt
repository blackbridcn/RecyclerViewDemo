package org.x5webview.utils

import android.app.Application
import com.tencent.smtt.sdk.QbSdk

/**
 * Created by yuzhu on 2018/2/9.
 */
object WebViewUtils {

    fun initTBSX5(mApplication: Application): Boolean {
        var result: Boolean = false;
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        var cb = object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {
            }

            override fun onViewInitFinished(arg0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                //Logger.d("app", " onViewInitFinished is   x5內核初始化完成" + arg0)
                result = arg0
            }
        }
        QbSdk.initX5Environment(mApplication, cb)
        return result
    }

  /*  fun startWebView(pckagCtx: Context, Url: String) {
        //Context packageContext, String Url
        var intent = Intent(pckagCtx, X5BrowserActivity::class.java)
        intent.data = Uri.parse(Url)
        pckagCtx.startActivity(intent)
    }*/
}