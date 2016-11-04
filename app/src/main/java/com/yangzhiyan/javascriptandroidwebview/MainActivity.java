package com.yangzhiyan.javascriptandroidwebview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new MyJavaObject(this),"obj");


        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("mypro://")){
                    String text = url.substring("mypro://".length());
                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                }else {
                    view.loadUrl(url);
                }

                return true;

            }

//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                //return super.shouldOverrideUrlLoading(view, request);
//                String url = request.getUrl().toString();
//                if (url.startsWith("mypro://")){
//                    String text = url.substring("mypro://".length());
//                    Toast.makeText(MainActivity.this, "text", Toast.LENGTH_SHORT).show();
//                }else {
//                    view.loadUrl(url);
//                }
//                return true;
//            }
        });
        //加载网络页面
        //webView.loadUrl("http://www.baidu.com");

        //加载本地Web页面
        webView.loadUrl("file:///android_asset/Sample.html");
    }

    public void btntoJavaScript(View view){
        webView.loadUrl("javascript:myJavaScriptFunction('Tom')");
    }
    public class MyJavaObject{
        private Context context;

        public MyJavaObject(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void showToast(String name){
            Toast.makeText(context, "Hello "+ name, Toast.LENGTH_SHORT).show();
        }
    }
}
