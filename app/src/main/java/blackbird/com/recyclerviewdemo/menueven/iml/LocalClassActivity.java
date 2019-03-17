package blackbird.com.recyclerviewdemo.menueven.iml;

import android.app.Activity;
import android.content.Intent;

import blackbird.com.recyclerviewdemo.MainActivity;
import blackbird.com.recyclerviewdemo.bean.MenuResourceData;
import blackbird.com.recyclerviewdemo.menueven.MenuClickStrategy;

/**
 * 点击按钮 进行本地Activity 显示Intent跳转
 */
public class LocalClassActivity implements MenuClickStrategy {

    @Override
    public void onMenuClick(Activity mContext, MenuResourceData mMenuResourceData) {
        Intent intent = new Intent();
        intent.setClass(mContext, mMenuResourceData.getClazz());
        mContext.startActivityForResult(intent, MainActivity.BTN_MENU_REQUEST_CODE);
    }
}
