package blackbird.com.recyclerviewdemo.bean;

import java.io.Serializable;

/**
 * Created by yzzhang on 2017/2/8.
 */

public class MenuCustomContent implements Serializable {
    private String customKey;
    private String customValue;

    public MenuCustomContent() {
    }

    public MenuCustomContent(String customKey, String customValue) {
        this.customKey = customKey;
        this.customValue = customValue;
    }

    public String getCustomKey() {
        return customKey;
    }

    public void setCustomKey(String customKey) {
        this.customKey = customKey;
    }

    public String getCustomValue() {
        return customValue;
    }

    public void setCustomValue(String customValue) {
        this.customValue = customValue;
    }

    @Override
    public String toString() {
        return "ButtonCustomContent{" +
                "customKey='" + customKey + '\'' +
                ", customValue='" + customValue + '\'' +
                '}';
    }
}
