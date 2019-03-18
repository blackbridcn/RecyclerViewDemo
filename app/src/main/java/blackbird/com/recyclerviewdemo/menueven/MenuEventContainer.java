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
        if (menuMap == null) {
            menuMap = new HashMap<>();
            menuMap.put(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_CLASS_J_ACTIVITY(), new LocalClassActivity());
            menuMap.put(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_SKIP_APP(), new SkipThridApp());
            menuMap.put(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_URL_J_WEBVIEW_H5(), new StartToWebAcivity());
        }
    }


    public static MenuClickStrategy getMenuEvent(String key) {
        if (StringUtils.isNotEmpty(key))
            return menuMap.get(key);
        return null;
    }

}
