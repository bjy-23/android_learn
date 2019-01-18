package bjy.edu.android_learn.webView;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
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

        final WebView webView = findViewById(R.id.webView);
//        webView.loadUrl(URL);

        String data = "<p> my name is bbbjy </p>";
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
        htmlBuilder.append("<html>")
                .append(headBuilder)
                .append(style)
                .append("<div style='width:100%'>")
                .append(data)
                .append("</div></body></html>");
        webView.loadDataWithBaseURL(null, htmlBuilder.toString(), "text/html", "UTF-8", null);

        //loadData的三种方式，推荐第三种loadDataWithBaseURL，其他的可能会造成乱码
//        webView.loadData(data, "text/html", "UTF-8");
//        webView.loadData("<html>", "text/html; charset=UTF-8", null);
//        webView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);//


        WebSettings webSettings = webView.getSettings();

        //如果访问的页面中要与Javascript交互（有<script>标签），则webview必须设置支持Javascript;)
        webSettings.setJavaScriptEnabled(true);

        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);//支持html<meta>标签 viewport
        webSettings.setLoadWithOverviewMode(true);//内容适应屏幕大小
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

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            //            /**
//             * @param view  和 webView是一个对象
//             * @param request
//             * @return
//             */
//            @Override
//            @TargetApi(21)
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//
//                //return false : 在当前webView继续加载新网址
//
//                //return true : 默认不处理，交给用户来处理
//
//                view.loadUrl(request.getUrl().toString());
//                return super.shouldOverrideUrlLoading(view, request);
//            }
        });

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

//        final String url = "http://view.officeapps.live.com/op/view.aspx?src=http://sogu-kskd.oss-cn-hangzhou.aliyuncs.com/backend/home_file/lijiafei/07854a2d121096d42a9c6524e7618a3b.docx";
//        webView.loadUrl(url);
    }
}
