package blackbird.com.recyclerviewdemo;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.org.appconstant.HomeButtonTypeContentKotlin;

import java.util.LinkedList;
import java.util.List;

import blackbird.com.recyclerviewdemo.bean.BaseJson;
import blackbird.com.recyclerviewdemo.bean.MenuResourceData;
import blackbird.com.recyclerviewdemo.bean.MenuGroupResourceData;
import blackbird.com.recyclerviewdemo.uitls.AppMainButtonDataUtils;



/**
 * Created by yuzhu on 2017/6/1.
 * 处理服务器返回的数据与本地数据之间关系 Thread
 */

public class NetDataTask implements Runnable {

    private String netData;
    private Context mContext;

    public NetDataTask(Context mContext, String netData) {
        this.mContext = mContext;
        this.netData = netData;
    }

    @Override
    public void run() {
        BaseJson baseJson = JSON.parseObject(netData, BaseJson.class);
        if (baseJson.getState() > 0) {
            List<MenuGroupResourceData> infos = JSON.parseArray(baseJson.getData().toString(), MenuGroupResourceData.class);
            if (infos != null && infos.size() > 0)
                parseBtnResour(infos, this.mContext);
            else
                AppMainButtonDataUtils.getInstance().clearNetButtonResource();
        }
    }


    /**
     * 处理服务器返回的数据与本地数据之间关系
     *
     * @param infos    List<ButtonResourceDataInfo>
     * @param mContext Context
     */
    private void parseBtnResour(List<MenuGroupResourceData> infos, Context mContext) {
        checkMainFunctionButtonData(infos, mContext);
        List<MenuGroupResourceData> loacalData = AppMainButtonDataUtils.getInstance().getLoacalAllButtonData();
        for (MenuGroupResourceData info : infos) {
            if (info.getCategory() == null) continue;
            if (info.getCategory().equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_LIST_TYPE_THRID()))
                parseNetDataToLocal(info.getChild(), loacalData, 0);
            else if (info.getCategory().equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_LIST_TYPE_WEB()))
                parseNetDataToLocal(info.getChild(), loacalData, 1);
            else if (info.getCategory().equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_LIST_TYPE_OTHER()))
                parseNetDataToLocal(info.getChild(), loacalData, 2);
            else if(info.getCategory().equals(HomeButtonTypeContentKotlin.INSTANCE.getTYPE_LOCAL_LIST_TYPE_SET()))
                parseNetDataToLocal(info.getChild(), loacalData, 3);
            else
                loacalData.add(info);
        }
        AppMainButtonDataUtils.getInstance().saveAllFunctionButtonData(loacalData);
    }

    /**
     * 检查已经添加在主页中的
     * 按钮数据（来自服务器配置的按钮数据）是否还存在，
     * 如果不存则删除按钮数据
     */
    private void checkMainFunctionButtonData(List<MenuGroupResourceData> infos, Context mContext) {
        List<MenuResourceData> all = new LinkedList<>();
        for (MenuGroupResourceData mMenuGroupResourceData : infos)
            all.addAll(mMenuGroupResourceData.getChild());
        int tmpSize = all.size();
        int len = tmpSize - 1;
        List<MenuResourceData> main = AppMainButtonDataUtils.getInstance().getMainButtonRes();
        int size = main.size();
        for (int i = 0; i < size; i++) {
            MenuResourceData data = main.get(i);
            if (data.getType() == HomeButtonTypeContentKotlin.INSTANCE.getSERVER_TYPE())
                for (int j = 0; j < tmpSize; j++) {
                    boolean b = all.get(j).getTitle().equals(data.getTitle());
                    if (b)
                        break;
                    else if (!b && j == len)
                        main.remove(i);
                }
        }
        if (size != main.size()) {
            AppMainButtonDataUtils.getInstance().saveMainFunctionButtonData(main);
            if (AppMainButtonDataUtils.getInstance().listener != null)
                AppMainButtonDataUtils.getInstance().listener.HomePageMianButtomChange(main);
        }
    }


    /**
     * 处理服务器返回的数据与本地数据之间关系child
     * 同一个类型（category）下中
     * 服务器返回的button自动插在本地button之后；
     * 同时,服务器返回的button名字与本地数据button名字相同时：
     * 服务器的button数据 覆盖本地的button数据
     *
     * @param child      List<ButtonResourceData>
     * @param loacalData List<ButtonResourceDataInfo>
     * @param index      int position
     */
    private void parseNetDataToLocal(List<MenuResourceData> child, List<MenuGroupResourceData> loacalData, int index) {
        if (child == null || child.size() < 1) return;
        List<MenuResourceData> localThirdAppList = loacalData.get(index).getChild();
        for (int k = 0; k < child.size(); k++) {
            MenuResourceData childData = child.get(k);
            for (int j = 0; j < localThirdAppList.size(); j++) {
                MenuResourceData data = localThirdAppList.get(j);
                if (data.getTitle().equals(childData.getTitle()))
                    localThirdAppList.remove(j);
            }
        }
        localThirdAppList.addAll(child);
    }
}
