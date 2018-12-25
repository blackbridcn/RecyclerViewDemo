package blackbird.com.recyclerviewdemo.bean;

/**
 * Created by yuzhu on 2017/1/1.
 */

public class BaseJson {

    private int state;
    private String type;
    private Object data;

    public BaseJson(int state, String type, String data) {
        this.state = state;
        this.type = type;
        this.data = data;
    }

    public BaseJson() {
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseJson{" +
                "state='" + state + '\'' +
                ", type='" + type + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    private class testInnerClass{

    }
}
