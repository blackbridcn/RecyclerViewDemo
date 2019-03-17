package blackbird.com.recyclerviewdemo.menueven;

import com.org.appconstant.HomeButtonTypeContentKotlin;

import java.util.HashMap;
import java.util.Map;

import blackbird.com.recyclerviewdemo.menueven.iml.LocalClassActivity;
import blackbird.com.recyclerviewdemo.menueven.iml.SkipThridApp;
import blackbird.com.recyclerviewdemo.menueven.iml.StartToWebAcivity;
import blackbird.com.recyclerviewdemo.uitls.StringUtils;

public class MenuEventContainer {

    private static Map<String, MenuClickStrategy> menuMap;

    public static void init() {
        menuMap = new HashMap<>();
        menuMap.put(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_CLASS_J_ACTIVITY(), new LocalClassActivity());
        menuMap.put(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_SKIP_APP(),new SkipThridApp());
        menuMap.put(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_J_WEBVIEW_H5(),new StartToWebAcivity());
        menuMap.put(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_URL_J_WEBVIEW_H5(),new StartToWebAcivity());

    }

    public static Map<String, MenuClickStrategy> getAllMenuEvent() {
        Map<String, MenuClickStrategy> menuMap = new HashMap<>();
        menuMap.put(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_CLASS_J_ACTIVITY(), new LocalClassActivity());
        return menuMap;
    }

    public static MenuClickStrategy getMenuEvent(String key) {
        if (StringUtils.isNotEmpty(key))
            return menuMap.get(key);
        return null;
    }

  /*  val TYPE_LOCAL_CLASS_J_ACTIVITY = "TYPE_LOCAL_CLASS_J_ACTIVITY"
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
    val TYPE_SKIP_APP_DOWN_LOAD_PATH = "AndroidAppDownLoadPath"*/
}
