package com.ijiami.yssb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class JsUtils2
{
    private Context mContext;

    public JsUtils2(Context context) {
        this.mContext = context;
    }

    @JavascriptInterface
    public void showToast(String toast){
        Toast.makeText(mContext,toast,Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void toActivity(){
        Intent intent = new Intent(mContext, MainActivity2.class);
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

    public String toString() {
        return "index2";
    }
}
