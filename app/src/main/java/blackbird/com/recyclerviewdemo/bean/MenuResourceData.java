package blackbird.com.recyclerviewdemo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yzzhang on 2017/2/8.
 */

public class MenuResourceData implements Serializable {
    /**
     * Childs按钮标题
     */
    private String title;
    /**
     * Childs按钮图标(网络图片资源)
     */
    private String iconUrl;
    /**
     * Childs H5页面web地址
     */
    private String contentUrl;
    /**
     * Childs按钮内容描述(后台配置)
     */
    private String description;
    /**
     * Childs按钮功能类型
     */
    private String btnType;
    /**
     * Childs功能自定义部分(例如APP跳转)
     */
    private List<MenuCustomContent> customContent;
    /**
     * 按钮图标(本地图片资源)
     */
    private int iconRes;
    /**
     * 按钮跳转目标Activity
     */
    private Class<?> clazz;
    /**
     * 跳转目标Activity
     */
    private String actyName;
    /**
     * 按钮是否添加在主页中显示
     */
    private boolean select;
    /**
     * 按钮消息数字
     */
    private String tips;
    /**
     * 按钮权限值
     */
    private int authority;
    /**
     * Source Type 本地按钮 服务器数据按钮
     */
    private int type;

    public MenuResourceData() {
    }

    public MenuResourceData(String title, String btnType, int iconRes, Class<?> clazz, int type) {
        this.title = title;
        this.btnType = btnType;
        this.iconRes = iconRes;
        this.clazz = clazz;
        this.type = type;
    }

    public MenuResourceData(String title, String btnType, int iconRes, String description, int type) {
        this.title = title;
        this.btnType = btnType;
        this.iconRes = iconRes;
        this.description = description;
        this.type = type;
    }

    public MenuResourceData(String title, String btnType, List<MenuCustomContent> customContent, int iconRes, String tips, int type) {
        this.title = title;
        this.btnType = btnType;
        this.customContent = customContent;
        this.iconRes = iconRes;
        this.tips = tips;
        this.type = type;
    }

    public MenuResourceData(String title, String btnType, List<MenuCustomContent> customContent, int iconRes, int type) {
        this.title = title;
        this.btnType = btnType;
        this.customContent = customContent;
        this.iconRes = iconRes;
        this.type = type;
    }

    public MenuResourceData(String title, String btnType, String contentUrl, int iconRes, int type) {
        this.title = title;
        this.contentUrl = contentUrl;
        this.btnType = btnType;
        this.iconRes = iconRes;
        this.type = type;
    }

    public MenuResourceData(String title, String btnType, String contentUrl, int iconRes, String tips, int type) {
        this.title = title;
        this.contentUrl = contentUrl;
        this.btnType = btnType;
        this.iconRes = iconRes;
        this.tips = tips;
        this.type = type;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBtnType() {
        return btnType;
    }

    public void setBtnType(String btnType) {
        this.btnType = btnType;
    }

    public List<MenuCustomContent> getCustomContent() {
        return customContent;
    }

    public void setCustomContent(List<MenuCustomContent> customContent) {
        this.customContent = customContent;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getActyName() {
        return actyName;
    }

    public void setActyName(String actyName) {
        this.actyName = actyName;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ButtonResourceData{" +
                "title='" + title + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", contentUrl='" + contentUrl + '\'' +
                ", description='" + description + '\'' +
                ", btnType='" + btnType + '\'' +
                ", customContent=" + customContent +
                ", iconRes=" + iconRes +
                ", clazz=" + clazz +
                ", actyName='" + actyName + '\'' +
                ", select=" + select +
                ", tips=" + tips +
                ", authority=" + authority +
                '}';
    }

}


