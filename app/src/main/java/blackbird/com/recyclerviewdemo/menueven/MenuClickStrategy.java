package blackbird.com.recyclerviewdemo.menueven;

import android.app.Activity;

import blackbird.com.recyclerviewdemo.bean.MenuResourceData;

public interface MenuClickStrategy {

    void onMenuClick(Activity mContext, MenuResourceData mMenuResourceData);
}
