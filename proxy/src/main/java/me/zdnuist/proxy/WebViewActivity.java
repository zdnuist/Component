package me.zdnuist.proxy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by zd on 2017/12/6.
 */

public class WebViewActivity extends AppCompatActivity {

  private WebView mWebView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_webview);
    init();
    initData();
  }

  private void init() {
    mWebView = findViewById(R.id.id_web_view);
    WebSettings webSettings = mWebView.getSettings();
    //支持缩放，默认为true。
    webSettings.setSupportZoom(true);
    //调整图片至适合webview的大小
    webSettings.setUseWideViewPort(true);
    // 缩放至屏幕的大小
    webSettings.setLoadWithOverviewMode(true);
    //设置默认编码
    webSettings.setDefaultTextEncodingName("utf-8");
    ////设置自动加载图片
    webSettings.setLoadsImagesAutomatically(true);
//多窗口
    webSettings.supportMultipleWindows();
//获取触摸焦点
    mWebView.requestFocusFromTouch();
//允许访问文件
    webSettings.setAllowFileAccess(true);
//开启javascript
    webSettings.setJavaScriptEnabled(true);
    //支持通过JS打开新窗口
    webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//提高渲染的优先级
    webSettings.setRenderPriority(RenderPriority.HIGH);
    //支持内容重新布局
    webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//关闭webview中缓存
    webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    mWebView.setWebViewClient(new WebViewClient(){
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(request.getUrl().toString());
        return true;
      }

      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
      }
    });
  }

  private void initData(){
    String url = getIntent().getStringExtra("_url");
    if(!TextUtils.isEmpty(url)){
      mWebView.loadUrl(url);
    }else{
      String tiltle = getIntent().getStringExtra("_title");
      mWebView.loadUrl("https://www.baidu.com/s?wd=" + tiltle);
    }

  }


}
