package blackbird.com.recyclerviewdemo;

public class ApkModel {

    String name;
    String packageName;
    String mainClazzName;
    String icon;
    int versionCode;
    String versionName;


    public ApkModel() {
    }

    public ApkModel(String name, String packageName, String mainClazzName, String icon, int versionCode, String versionName) {
        this.name = name;
        this.packageName = packageName;
        this.mainClazzName = mainClazzName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMainClazzName() {
        return mainClazzName;
    }

    public void setMainClazzName(String mainClazzName) {
        this.mainClazzName = mainClazzName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
