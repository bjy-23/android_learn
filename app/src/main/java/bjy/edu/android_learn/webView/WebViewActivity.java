package bjy.edu.android_learn.webView;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import bjy.edu.android_learn.R;

public class WebViewActivity extends AppCompatActivity {
//    private static final String URL = "https://mh5.sogukz.com/question/index.html?type=protocol";
    private static final String URL = "https://mh5.sogukz.com/question/index.html?type=video";
//    private static final String URL = "http://www.baidu.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = findViewById(R.id.webView);
        webView.loadUrl(URL);
        //loadData的三种方式，推荐第三种loadDataWithBaseURL，其他的可能会造成乱码
        webView.loadData("<html>", "text/html", "UTF-8");
        webView.loadData("<html>", "text/html; charset=UTF-8", null);
        webView.loadDataWithBaseURL(null, "<html>", "text/html", "UTF-8", null);//

        WebSettings webSettings = webView.getSettings();

        //如果访问的页面中要与Javascript交互（有<script>标签），则webview必须设置支持Javascript;)
        webSettings.setJavaScriptEnabled(true);

        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);//支持html<meta>标签 viewport
        webSettings.setLoadWithOverviewMode(true);//内容适应屏幕大小
        //缩放操作 --------------
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //缩放操作 --------------
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
////支持插件
//        webSettings.setPluginsEnabled(true);

////其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
//        webSettings.setAllowFileAccess(true); //设置可以访问文件
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
//        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
//        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webView.setWebViewClient(new WebViewClient(){
            @Override
            @TargetApi(21)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }
}
