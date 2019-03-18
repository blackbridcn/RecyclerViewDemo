package blackbird.com.recyclerviewdemo.menueven;

import android.app.Activity;

import blackbird.com.recyclerviewdemo.bean.MenuResourceData;

public class MenuClickEventContext {

    private static volatile MenuClickEventContext instance;

    private MenuClickEventContext() {
        MenuEventContainer.init();
    }

    public static MenuClickEventContext getInstance() {
        if (instance == null) {
            synchronized (MenuClickEventContext.class) {
                if (instance == null)
                    instance = new MenuClickEventContext();
            }
        }
        return instance;
    }

    public void onClick(Activity mContext, MenuResourceData mMenuResourceData) {
        MenuClickStrategy clickStrategy = MenuEventContainer.getMenuEvent(mMenuResourceData.getBtnType());
        if (clickStrategy != null) clickStrategy.onMenuClick(mContext, mMenuResourceData);
    }
}