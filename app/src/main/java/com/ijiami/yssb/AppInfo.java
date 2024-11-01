package com.ijiami.yssb;

public class AppInfo {
    //应用图标
    //private Drawable icon;
    //应用的名字
    private String appName;
    //应用程序的大小
    private String apkSize;
    //表示用户程序
    private boolean isUserApp;
    //存储的位置.
    private boolean isSD;
    private String packageName;
    private String signature;
    private String versionName;
    private int versionCode;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getSignature(){
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    /*
    public Drawable getIcon() {
        return icon;
    }
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }*/
    public String getAppName() {
        return appName;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }
    public String getApkSize() {
        return apkSize;
    }
    public void setApkSize(String apkSize) {
        this.apkSize = apkSize;
    }
    public boolean isUserApp() {
        return isUserApp;
    }
    public void setUserApp(boolean isUserApp) {
        this.isUserApp = isUserApp;
    }
    public boolean isSD() {
        return isSD;
    }
    public void setSD(boolean isSD) {
        this.isSD = isSD;
    }
    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName + '\'' +
                "packageName='" + packageName + '\'' +
                "versionName='" + versionName + '\'' +
                "versionCode='" + versionCode + '\'' +
                "signature='" + signature + '\'' +
                ", apkSize='" + apkSize + '\'' +
                ", isUserApp=" + isUserApp +
                ", isSD=" + isSD +
                '}';
    }
}
