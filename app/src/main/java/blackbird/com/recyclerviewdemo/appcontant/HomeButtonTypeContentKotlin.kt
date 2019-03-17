package com.org.appconstant

/**
 * Created by yzzhang on 2017/12/8.
 */

 object HomeButtonTypeContentKotlin {
    //主页按钮存储标签
    val TYPE_MAIN_BUTTON_DATA = "TYPE_MAIN_BUTTON_DATA"
    //全部按钮存储标签
    val TYPE_ALL_BUTTON_DATA = "TYPE_ALL_BUTTON_DATA"
    //按钮分组 标题
    val TYPE_LOCAL_LIST_NAME_THRID = "跳转第三方APP"
    val TYPE_LOCAL_LIST_NAME_WEB = "webView"
    val TYPE_LOCAL_LIST_NAME_OTHER = "Other"
   val TYPE_LOCAL_LIST_NAME_SET = "Set"
    //按钮分组 类型
    val TYPE_LOCAL_LIST_TYPE_THRID = "thridApp"
    val TYPE_LOCAL_LIST_TYPE_WEB = "webView"
    val TYPE_LOCAL_LIST_TYPE_OTHER = "Other"
    val TYPE_LOCAL_LIST_TYPE_SET = "Set"
    //按钮来源 服务器数据：0；本地数据：1；
    val SERVER_TYPE = 0
    val LOCAL_TYPE = 1
    /**
     * 按钮功能类型
     * 用于JSON解析和按钮点击
     * 事件发生时类型判断
     */
    //根据本地类跳转Activity
    val TYPE_LOCAL_CLASS_J_ACTIVITY = "TYPE_LOCAL_CLASS_J_ACTIVITY"
    //根据本地String跳转Activity
    val TYPE_LOCAL_STRING_J_ACTIVITY = "TYPE_LOCAL_STRING_J_ACTIVITY"
    //根据服务器返回String类型包名和类名跳转Activity
    val TYPE_URL_CLASS_J_ACTIVITY = "TYPE_URL_CLASS_J_ACTIVITY"
    //本地设置APP跳转(图标显示问题)
    val TYPE_LOCAL_SKIP_APP = "TYPE_LOCAL_SKIP_APP"
    //根据服务器返回数据进行APP跳转类型
    val TYPE_URL_SKIP_APP = "TYPE_URL_SKIP_APP"
    //根据服务器返回数据跳转WebView/H5
    val TYPE_URL_J_WEBVIEW_H5 = "TYPE_URL_J_WEBVIEW_H5"
    //本地URL数据跳转WebView/H5
    val TYPE_LOCAL_J_WEBVIEW_H5 = "TYPE_LOCAL_J_WEBVIEW_H5"
    //Android App跳转指定的Activity
    val  TYPE_SKIP_APP_START_ACTIVITY = "AndroidAppStartActivity"
    //AndroidAPP跳转Packagename
    val TYPE_SKIP_APP_PACKAGE_NAME = "AndroidAppPackageName"
    //AndroidAPP下载路径
    val TYPE_SKIP_APP_DOWN_LOAD_PATH = "AndroidAppDownLoadPath"

   var netData = "{\"state\": \"1\", \"type\": \"MainButtons\",\"data\": " +
           "[{\"category\": \"webView\",\"categoryName\": \"webView\", \"child\":" +
           " [{\"title\": \"百度一下\",\"iconUrl\":\"https://www.baidu.com/img/bd_logo1.png\",\"contentUrl\": \"http://news.baidu.com/\",\"description\": \"百度一下\",\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\"}," +
           " {\"title\": \"58同城\", \"iconUrl\": \"http://img.58cdn.com.cn/logo/58/252_84/logo-o.png?v=2\",\"contentUrl\": \"http://sz.58.com\",\"description\": \"58同城\",\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\"}," +
           "{\"title\": \"GitHub\",\"iconUrl\": \"https://github.com/fluidicon.png\",\"contentUrl\": \"https://github.com\", \"description\": \"GitHub\",\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\"}] }, {\"category\": \"thridApp\",\"categoryName\": \"跳转第三方APP\",\"child\": [{\"title\": \"支付宝\",\"iconUrl\": \"https://i.alipayobjects.com/common/favicon/favicon.ico\",\"contentUrl\":\"http://www.ccdi.gov.cn/\",\"description \": \"我要举报 \",\"btnType\": \"TYPE_URL_SKIP_APP\",\"customConten\": [{\"customKey\":\"AndroidAppPackageName\",\"customValue\": \"com.eg.android.AlipayGphone\" },{\"customKey\": \"AndroidAppDownLoadPath\",\"customValue\":\"https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk\" }] }] }, {\"category\": \"thridLined\",\"categoryName\": \"友情链接\",\"child\": [{\"title\": \"举报\",\"iconUrl\": \"http://www.ccdi.gov.cn/images/jjr.png\",\"contentUrl\": \"http://www.ccdi.gov.cn/\", \"description \": \"我要举报 \",\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\" },{\"title \": \"AcFun \",\"iconUrl \": \"http: //imgs.aixifan.com/cms/2017_11_01/1509533273187.gif?imageView2/1/w/100/h/100\", \"contentUrl\": \"http://www.acfun.cn/\",\"description\": \"AcFun\",\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\" }] }, {\"category\": \"tour\",\"categoryName\": \"旅游\",\"child\": [{\"title\": \"携程\",\"iconUrl\": \"http://pic.c-ctrip.com/common/c_logo2013.png\",\"contentUrl\": \"http://www.ctrip.com/\",\"description\": \"携程\",\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\"}, {\"title\": \"途牛\", \"iconUrl\": \"http://img3.tuniucdn.com/u/mainpic/logo/logo_20170124.png\", \"contentUrl\": \"http://www.tuniu.com/\",\"description\": \"途牛\",\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\"}] }, {\"category\": \"express\",\"categoryName\": \"快递 \",\"child\": [{ \"title\": \"顺丰\",\"iconUrl\": \"http://www.sf-express.com/resource/images/index/sf.png\", \"contentUrl\": \"http://www.sf-express.com/cn/sc/\",\"description \": \"顺丰 \",\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\"}, {\"title \": \"EMS\",\"iconUrl \": \"http://www.ems.com.cn/images/405.jpg\",\"contentUrl\": \"http://www.ems.com.cn/\",\"description\": \"EMS\",\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\" }]}]}";


   /* }, {
    "customKey": "AndroidAppStartActivity",
    "customValue": "com.eg.android.AlipayGphone.AlipayLogin"*/

}
