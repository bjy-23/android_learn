package bjy.edu.android_learn.webView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.io.File;

import bjy.edu.android_learn.MainActivity;
import bjy.edu.android_learn.R;
import bjy.edu.android_learn.util.BitmapUtil;

public class WebViewActivity extends AppCompatActivity {
    public static final String TAG = WebViewActivity.class.getSimpleName();

    private WebView webView;
    //    private static final String URL = "https://mh5.sogukz.com/question/index.html?type=protocol";
    private static final String URL = "https://www.baidu.com";
//    private static final String URL = "http://www.baidu.com/";

    StringBuilder headBuilder = new StringBuilder()
            .append("<head>")
            .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">")
            .append("<style>img{max-width: 100%; width:auto; height:auto;} .reduce-font p{font-size:\" + 16 + \"px!important;}</style>")
            .append("</head>");

    String style = "<body class='reduce-font' style='background-color:#ffffff;color:#000000;'> ";

    StringBuilder htmlBuilder = new StringBuilder();

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);
        //1.websetting
        final WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互（有<script>标签），则webview必须设置支持Javascript;)
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setDisplayZoomControls(false);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setAllowFileAccess(false);
//        webSettings.setAllowContentAccess(false);
//        webSettings.setAllowFileAccessFromFileURLs(false);
//        webSettings.setAllowUniversalAccessFromFileURLs(false);


//        webSettings.setDomStorageEnabled(true);
//        webSettings.setUseWideViewPort(true);//支持html<meta>标签 viewport
//        webSettings.setLoadWithOverviewMode(true);//内容适应屏幕大小
//        //缩放操作 --------------
//        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
//        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
//        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //缩放操作 --------------
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
////支持插件
//        webSettings.setPluginsEnabled(true);
////其他细节操作
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
//        webSettings.setAllowFileAccess(true); //设置可以访问文件
//        webSettings.setAllowUniversalAccessFromFileURLs(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
//        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
//        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式


//        String data = "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head>\n" +
//                "<meta charset=\"UTF-8\">\n" +
//                "<title>Insert title here</title>\n" +
//                "<style>\n" +
//                "a{\n" +
//                "    font-size: 50px;\n" +
//                "}\n" +
//                "</style>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "    <a href=\"sougukj://hello\"\">App</a>\n" +
//                "</body>\n" +
//                "</html>\n";

        int position = 2;
        switch (position){
            case 1:
                //js 和 android交互
                test_1();
                break;
            case 2:
                //https
                test_2();
                break;
                //
            case 3:
                test_3();
                break;
        }

        //loadData的三种方式，推荐第三种loadDataWithBaseURL，其他的可能会造成乱码
//        webView.loadData(data, "text/html", "UTF-8");
//        webView.loadData("<html>", "text/html; charset=UTF-8", null);
//        webView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);//

//        webView.setWebViewClient(new WebViewClient() {
//
//            //默认值 return false: webView继续加载当前url；return true:webView停止加载当前url
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.i(TAG, "shouldOverrideUrlLoading:  " + url);
//
//                boolean result = true;
//                if (url.contains("https"))
//                    result = false;
//
//                return result;
//            }
//
//            // TODO: 2019-11-04  方法失效？？？
//            //return false : 在当前webView继续加载新网址
//            //return true : 默认不处理，交给用户来处理
////            @Override
////            @TargetApi(21)
////            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
////                Log.i(TAG, "shouldOverrideUrlLoading 2:  " + request.getUrl().toString());
////                return true;
////            }
//
//
//            //https证书验证失败
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
//            }
//        });

//        webView.setWebChromeClient(new WebChromeClient());

        //响应按键回退网页
//        webView.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                //按返回键操作并且能回退网页
//                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
//                    //后退
//                    webView.goBack();
//                    return true;
//                }
//                return false;
//            }
//        });

    }

    //webView js和android交互
    public void test_1(){
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("<p> my name is <input type=\"text\" id=\"input_1\" value=\"bbbjy\"/> </p>")
                //js调用android
                .append("<input type=\"button\" value=\"点我\" onclick=\"android.clickNow2(333)\" />")
                .append("<br><br><br>")
                .append("<a href=http://www.baidu.com>超链接</a>")
                .append("<br><br><br>")
                .append("<a href=https://www.baidu.com>超链接</a>")
                .append("<br><br><br>")
                .append("<a href=spdbbank://wap.spdb.com.cn>超链接2</a>")
                .append("<script type=\"text/javascript\"> " +
                        "function changeText(){ " +
                        "document.getElementById(\"input_1\").value = \"jjjjjj\";" +
                        "return \"修改成功了\"" +
                        "} " +
                        "</script>");
        htmlBuilder.append("<html>")
                .append(headBuilder)
                .append(style)
                .append("<div style='width:100%'>")
                .append(bodyBuilder.toString())
                .append("</div></body></html>");
        webView.loadDataWithBaseURL(null, htmlBuilder.toString(), "text/html", "UTF-8", null);
        //第二个参数name和onclick中的前缀保持一致
        //安全原因，需要api在17以上
        webView.addJavascriptInterface(new MyJs(WebViewActivity.this), "android");
//        webView.addJavascriptInterface(new WebViewActivity(), "tag1"); todo 调用失败，alertdialog为什么弹出失败？？？

        TextView tv_js = findViewById(R.id.tv_js);
        tv_js.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo api 19
                //webview主动调用js
                webView.evaluateJavascript("javascript:changeText()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.i("value", value);
                    }
                });

                // TODO: 2019-10-15
//                webView.loadUrl("javascript:changeText()");
            }
        });
    }

    @JavascriptInterface
    public void clickNow() {
        Log.i("clickNow", Thread.currentThread().getName());
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Context context = WebViewActivity.this;
                if (context == null) {
                    Log.i("111", "context == null");
                    return;
                }
                new AlertDialog.Builder(context)
                        .setTitle("点我")
                        .setMessage("来自html的点击事件")
                        .show();
            }
        });
    }

    //验证https
    public void test_2(){
        webView.loadUrl(URL);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.i(TAG, "onReceivedSslError");
                super.onReceivedSslError(view, handler, error);

            }
        });
    }

    public void test_3(){
        webView.loadUrl("http://121.41.43.94/klrq/phone/test/pay_result");
        webView.goBack();
        webView.setWebViewClient(new WebViewClient(){

            /*
            * 监听url
            * */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.i(TAG, "shouldInterceptRequest: " + request.getUrl());
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
                return super.onRenderProcessGone(view, detail);
            }

            /*
             *  可以监听到css、js等资源的获取
             *  1.可以在这里设置资源文件从缓存中读取，减少时间
             * */
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Log.i(TAG, "shouldInterceptRequest: " + request.getUrl());
                return super.shouldInterceptRequest(view, request);
            }

            /*
            * 只在SSL验证失败时调用
            * handler.proceed()
            * handler.cancel()
            * */
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }

            /*
            * 页面资源加载错误时调用；默认只监听了MainFrame
            * */
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){

        });
    }
}
