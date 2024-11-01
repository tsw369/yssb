package com.ijiami.yssb;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.test.demo1.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private WebView wvTest;
    private Button btGreen;
    private Button btnGetApplicationList;
    private Button btReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        wvTest = findViewById(R.id.wvTest);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        btGreen = findViewById(R.id.btGreen);
        btnGetApplicationList = findViewById(R.id.btnGetApplicationList);
        btReturn = findViewById(R.id.btReturn);

        btGreen.setOnClickListener(this);
        btnGetApplicationList.setOnClickListener(this);
        btReturn.setOnClickListener(this);

        WebSettings webSettings = wvTest.getSettings();
        webSettings.setJavaScriptEnabled(true);
        JsUtils jsUtils = new JsUtils(MainActivity.this);
        wvTest.addJavascriptInterface(jsUtils, jsUtils.toString());
        wvTest.loadUrl("file:///android_asset/Index.html");
        //wvTest.loadUrl("file:///android_asset/test.html");

    }

    @Override
    public void onClick(View view)
    {
        long start = System.currentTimeMillis();

        switch (view.getId())
        {
            case R.id.btGreen:
                wvTest.loadUrl("javascript:setColor()");
                break;
            case R.id.btnGetApplicationList:
                /*
                List<AppInfo> appInfoList = AppInfoParser.getAppInfos(MainActivity.this);
                if (appInfoList != null)
                {
                    Gson gson = new Gson();
                    String listToJsonString = gson.toJson(appInfoList);
                    //wvTest.loadUrl("javascript:setColorAndText('#FFFFE0', "+ listToJsonString +")");
                    wvTest.loadUrl("javascript:setAdd('123')");
                }*/

                List<AppInfo> appInfoList = AppInfoParser.getAppInfos(MainActivity.this);
                if (appInfoList != null)
                {
                    String json = new Gson().toJson(appInfoList);
                    wvTest.loadUrl("javascript:getAppinfoList('"+json+"')");
                }
                break;
            case R.id.btReturn:
                wvTest.evaluateJavascript("sum(100,2)", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Toast.makeText(MainActivity.this, "sum返回的结果为："+s, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            default:
                break;
        }

        long end = System.currentTimeMillis();
        double resultTime = (end - start) / 1000.0;
        Toast.makeText(this, "耗时：" + String.valueOf(resultTime) + "秒", Toast.LENGTH_LONG).show();
    }

    public void showPicture() {
        Intent intent = new Intent(this, AppUpdateGuideActivity.class);
        startActivity(intent);
    }
}