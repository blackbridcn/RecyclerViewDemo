package blackbird.com.recyclerviewdemo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yzzhang on 2017/2/9.
 */

public class MenuGroupResourceData implements Serializable {
    private String category;
    private String categoryName;
    private List<MenuResourceData> child;


    public MenuGroupResourceData(String category, String categoryName, List<MenuResourceData> child) {
        this.category=category;
        this.categoryName = categoryName;
        this.child = child;
    }

    public MenuGroupResourceData() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<MenuResourceData> getChild() {
        return child;
    }

    public void setChild(List<MenuResourceData> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "ButtonResourceDataInfo{" +
                "category='" + category + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", child=" + child +
                '}';
    }
}
