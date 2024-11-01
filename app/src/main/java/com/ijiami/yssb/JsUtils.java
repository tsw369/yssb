package com.ijiami.yssb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.test.demo1.R;

import java.util.ArrayList;
import java.util.List;

public class JsUtils
{
    //https://blog.csdn.net/salary/article/details/83097638
    //https://blog.csdn.net/qq_38287890/article/details/91416925
    //dumpsys window w | grep \\/ | grep name=
    private Context mContext;
    private WebView wvTest;

    public JsUtils(Context context) {
        this.mContext = context;
    }

    @JavascriptInterface
    public void showToast(String toast){
        List<AppInfo> appInfoList = AppInfoParser.getAppInfos(mContext);
        if (appInfoList != null)
        {
            for (AppInfo appInfo : appInfoList)
            {
                Toast.makeText(mContext,appInfo.toString(),Toast.LENGTH_SHORT).show();
            }
        }
        //Toast.makeText(mContext,toast,Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void toActivity(){
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
    }

    @JavascriptInterface
    public void showDialog(String data1,String data2){
        show(data1,data2);
    }

    private void show(String title,String data) {
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(data)
                .setPositiveButton("确定", null)
                .create().show();
    }

    @JavascriptInterface
    public void update(String appName, String packageName, String versionName, String signature){
        /*
        if (appName == null || appName.length() <= 0)
        {
            Toast.makeText(mContext, "app名字不能为空", Toast.LENGTH_SHORT).show();
            return;
        }*/

        if (packageName == null || packageName.length() <= 0)
        {
            Toast.makeText(mContext, "包名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        /*
        if (versionName == null || versionName.length() <= 0)
        {
            Toast.makeText(mContext, "版本不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (signature == null || signature.length() <= 0)
        {
            Toast.makeText(mContext, "签名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }*/

        String appNameList = mContext.getString(R.string.app_name_list);
        List<AppInfo> appInfoList = AppInfoParser.getAppInfos(mContext);
        List<AppInfo> appStoreList = new ArrayList<>();
        boolean isContains = false;
        if (appInfoList != null)
        {
            for (AppInfo appInfo : appInfoList)
            {
                if (appNameList.contains(appInfo.getAppName()))
                {
                    appStoreList.add(appInfo);
                }
            }
        }

        if (appStoreList.size() > 0)
        {
            String str = "market://details?id=" + packageName;
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            localIntent.setData(Uri.parse(str));
            mContext.startActivity(localIntent);
        }
        else
        {
            showAppUpdateGuidePicture();
        }
    }


    public String toString() {
        return "index";
    }

    public void showAppUpdateGuidePicture(){
        Intent intent = new Intent();
        intent.setClass(mContext,AppUpdateGuideActivity.class);
        mContext.startActivity(intent);
    }
}
