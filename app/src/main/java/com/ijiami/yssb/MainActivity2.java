package com.ijiami.yssb;

import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.test.demo1.R;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener
{
    private WebView wvTest;
    private Button btGreen;
    private Button btColor;
    private Button btReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {
        wvTest = findViewById(R.id.wvTest);
        btGreen = findViewById(R.id.btGreen);
        btColor = findViewById(R.id.btColor);
        btReturn = findViewById(R.id.btReturn);

        btGreen.setOnClickListener(this);
        btColor.setOnClickListener(this);
        btReturn.setOnClickListener(this);

        WebSettings webSettings = wvTest.getSettings();
        webSettings.setJavaScriptEnabled(true);
        JsUtils jsUtils = new JsUtils(MainActivity2.this);
        wvTest.addJavascriptInterface(jsUtils, jsUtils.toString());

        wvTest.loadUrl("file:///android_asset/Index2.html");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btGreen:
                wvTest.loadUrl("javascript:setColor()");
                break;
            case R.id.btColor:
                wvTest.loadUrl("javascript:setColorAndText('#FFFFE0','text3:这是修改后的文字！')");
                break;
            case R.id.btReturn:
                wvTest.evaluateJavascript("sum(100,2)", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Toast.makeText(MainActivity2.this, "sum返回的结果为："+s, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            default:
                break;
        }
    }
}
