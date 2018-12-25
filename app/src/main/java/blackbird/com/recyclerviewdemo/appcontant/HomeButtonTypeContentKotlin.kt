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
    //AndroidAPP跳转Activity
    val  TYPE_SKIP_APP_START_ACTIVITY = "AndroidAppStartActivity"
    //AndroidAPP跳转Packagename
    val TYPE_SKIP_APP_PACKAGE_NAME = "AndroidAppPackageName"
    //AndroidAPP下载路径
    val TYPE_SKIP_APP_DOWN_LOAD_PATH = "AndroidAppDownLoadPath"

   var netData = "{\n" +
           "\t\"state\": \"1\",\n" +
           "\t\"type\": \"MainButtons\",\n" +
           "\t\"data\": [{\n" +
           "\t\t\"category\": \"webView\",\n" +
           "\t\t\"categoryName\": \"webView\",\n" +
           "\t\t\"child\": [{\n" +
           "\t\t\t\"title\": \"百度一下\",\n" +
           "\t\t\t\"iconUrl\": \"https://www.baidu.com/img/bd_logo1.png\",\n" +
           "\t\t\t\"contentUrl\": \"http://news.baidu.com/\",\n" +
           "\t\t\t\"description\": \"百度一下\",\n" +
           "\t\t\t\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\"\n" +
           "\t\t}, {\n" +
           "\t\t\t\"title\": \"58同城\",\n" +
           "\t\t\t\"iconUrl\": \"http://img.58cdn.com.cn/logo/58/252_84/logo-o.png?v=2\",\n" +
           "\t\t\t\"contentUrl\": \"http://sz.58.com\",\n" +
           "\t\t\t\"description\": \"58同城\",\n" +
           "\t\t\t\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\"\n" +
           "\t\t}, {\n" +
           "\t\t\t\"title\": \"GitHub\",\n" +
           "\t\t\t\"iconUrl\": \"https://github.com/fluidicon.png\",\n" +
           "\t\t\t\"contentUrl\": \"https://github.com/\",\n" +
           "\t\t\t\"description\": \"GitHub\",\n" +
           "\t\t\t\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\"\n" +
           "\t\t}]\n" +
           "\t}, {\n" +
           "\t\t\"category\": \"thridApp\",\n" +
           "\t\t\"categoryName\": \"跳转第三方APP\",\n" +
           "\t\t\"child\": [{\n" +
           "\t\t\t\"title\": \"支付宝\",\n" +
           "\t\t\t\"iconUrl\": \"https://i.alipayobjects.com/common/favicon/favicon.ico\",\n" +
           "\t\t\t\"contentUrl\": \"http://www.ccdi.gov.cn/\",\n" +
           "\t\t\t\"description \": \"我要举报 \",\n" +
           "\t\t\t\"btnType\": \"TYPE_URL_SKIP_APP\",\n" +
           "\t\t\t\"customContent\": [{\n" +
           "\t\t\t\t\t\"customKey\": \"AndroidAppPackageName\",\n" +
           "\t\t\t\t\t\"customValue\": \"com.eg.android.AlipayGphone\"\n" +

           "\t\t\t\t},\n" +
           "\t\t\t\t{\n" +
           "\t\t\t\t\t\"customKey\": \"AndroidAppDownLoadPath\",\n" +
           "\t\t\t\t\t\"customValue\": \"https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk\"\n" +
           "\t\t\t\t}\n" +
           "\t\t\t]\n" +
           "\n" +
           "\t\t}]\n" +
           "\t}, {\n" +
           "\t\t\"category\": \"thridLined\",\n" +
           "\t\t\"categoryName\": \"友情链接\",\n" +
           "\t\t\"child\": [{\n" +
           "\t\t\t\t\"title\": \"举报\",\n" +
           "\t\t\t\t\"iconUrl\": \"http://www.ccdi.gov.cn/images/jjr.png\",\n" +
           "\t\t\t\t\"contentUrl\": \"http://www.ccdi.gov.cn/\",\n" +
           "\t\t\t\t\"description \": \"我要举报 \",\n" +
           "\t\t\t\t\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\"\n" +
           "\t\t\t},\n" +
           "\t\t\t{\n" +
           "\t\t\t\t\"title \": \"AcFun \",\n" +
           "\t\t\t\t\"iconUrl \": \"http: //imgs.aixifan.com/cms/2017_11_01/1509533273187.gif?imageView2/1/w/100/h/100\",\n" +
           "\t\t\t\t\"contentUrl\": \"http://www.acfun.cn/\",\n" +
           "\t\t\t\t\"description\": \"AcFun\",\n" +
           "\t\t\t\t\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\"\n" +
           "\t\t\t}\n" +
           "\t\t]\n" +
           "\t}, {\n" +
           "\t\t\"category\": \"tour\",\n" +
           "\t\t\"categoryName\": \"旅游\",\n" +
           "\t\t\"child\": [{\n" +
           "\t\t\t\"title\": \"携程\",\n" +
           "\t\t\t\"iconUrl\": \"http://pic.c-ctrip.com/common/c_logo2013.png\",\n" +
           "\t\t\t\"contentUrl\": \"http://www.ctrip.com/\",\n" +
           "\t\t\t\"description\": \"携程\",\n" +
           "\t\t\t\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\"\n" +
           "\t\t}, {\n" +
           "\t\t\t\"title\": \"途牛\",\n" +
           "\t\t\t\"iconUrl\": \"http://img3.tuniucdn.com/u/mainpic/logo/logo_20170124.png\",\n" +
           "\t\t\t\"contentUrl\": \"http://www.tuniu.com/\",\n" +
           "\t\t\t\"description\": \"途牛\",\n" +
           "\t\t\t\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\"\n" +
           "\t\t}]\n" +
           "\t}, {\n" +
           "\t\t\"category\": \"express\",\n" +
           "\t\t\"categoryName\": \"快递 \",\n" +
           "\t\t\"child\": [{\n" +
           "\t\t\t\t\"title\": \"顺丰\",\n" +
           "\t\t\t\t\"iconUrl\": \"http://www.sf-express.com/resource/images/index/sf.png\",\n" +
           "\t\t\t\t\"contentUrl\": \"http://www.sf-express.com/cn/sc/\",\n" +
           "\t\t\t\t\"description \": \"顺丰 \",\n" +
           "\t\t\t\t\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\"\n" +
           "\t\t\t},\n" +
           "\t\t\t{\n" +
           "\t\t\t\t\"title \": \"EMS\",\n" +
           "\t\t\t\t\"iconUrl \": \"http://www.ems.com.cn/images/405.jpg\",\n" +
           "\t\t\t\t\"contentUrl\": \"http://www.ems.com.cn/\",\n" +
           "\t\t\t\t\"description\": \"EMS\",\n" +
           "\t\t\t\t\"btnType\": \"TYPE_URL_J_WEBVIEW_H5\"\n" +
           "\t\t\t}\n" +
           "\t\t]\n" +
           "\t}]\n" +
           "}"


   /* "\t\t\t\t}, {\n" +
    "\t\t\t\t\t\"customKey\": \"AndroidAppStartActivity\",\n" +
    "\t\t\t\t\t\"customValue\": \"com.eg.android.AlipayGphone.AlipayLogin\"\n" +*/

}
