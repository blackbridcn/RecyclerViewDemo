package blackbird.com.recyclerviewdemo.menueven.iml;

import android.app.Activity;

import org.x5webview.utils.WebViewsUtils;

import blackbird.com.recyclerviewdemo.bean.MenuResourceData;
import blackbird.com.recyclerviewdemo.menueven.MenuClickStrategy;

public class StartToWebAcivity implements MenuClickStrategy {

    @Override
    public void onMenuClick(Activity mContext, MenuResourceData mMenuResourceData) {
        WebViewsUtils.startWebView(mContext, mMenuResourceData.getContentUrl());
    }
}
