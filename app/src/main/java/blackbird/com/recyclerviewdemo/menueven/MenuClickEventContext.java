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
        MenuEventContainer.getMenuEvent(mMenuResourceData.getBtnType()).onMenuClick(mContext, mMenuResourceData);
        MenuEventContainer.getAllMenuEvent().get(mMenuResourceData.getBtnType()).onMenuClick(mContext, mMenuResourceData);
    }
}