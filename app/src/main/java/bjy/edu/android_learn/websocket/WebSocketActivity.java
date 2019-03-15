package bjy.edu.android_learn.websocket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import bjy.edu.android_learn.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketActivity extends AppCompatActivity {
    private List<WebSocket> sockets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket);

        JSONObject jsonObject = new JSONObject();

//        test_1();

        test_2();

//        WebSocketCall.create(client, request).enqueue(new WebSocketListener() {
//            @Override
//            public void onOpen(WebSocket webSocket, Response response) {
//                Log.i("WebSocketCall", "onOpen");
//
//                String message = "{\"req\":\"Sub\",\"rid\":\"20\",\"expires\":1537708219903,\"args\":[\"index_GMEX_CI_ETH\"]}";
//                try {
//                    webSocket.sendMessage(RequestBody.create(WebSocket.TEXT, message));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(IOException e, Response response) {
//                Log.i("WebSocketCall", "onFailure");
//            }
//
//            @Override
//            public void onMessage(ResponseBody message) throws IOException {
//                Log.i("WebSocketCall", "onMessage");
//                String msg = message.string();
//                Log.i("msg", msg);
//            }
//
//            @Override
//            public void onPong(Buffer payload) {
//                Log.i("WebSocketCall", "onPong");
//            }
//
//            @Override
//            public void onClose(int code, String reason) {
//                Log.i("WebSocketCall", "onClose");
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        for (WebSocket webSocket: sockets){
            webSocket.cancel();
        }
    }

    private void test_1(){
        Request request = new Request.Builder()
                .url("wss://market01.gmex.io/v1/market")
                .build();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.SECONDS)
                .build();

        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);

                sockets.add(webSocket);
                Log.i("test_1", "onOpen");
                String message = "{\"req\":\"Sub\",\"rid\":\"20\",\"expires\":1537708219903,\"args\":[\"index_GMEX_CI_ETH\"]}";
                webSocket.send(message);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                Log.i("test_1", "onMessage String");
                Log.i("test_1", text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);

                Log.i("test_1", "onMessage ByteString");
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);

                Log.i("test_1", "onClosing");
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);

                Log.i("test_1", "onClosed");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);

                Log.i("test_1", "onFailure");
            }
        });

    }

    private void test_2(){
        Request request = new Request.Builder()
                .url("ws://47.98.188.238:8081")
                .build();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.SECONDS)
                .build();
//        final String message = "/stkdata?obj=SZ300168,&field=ZhongWenJianCheng,ZuiXinJia,ZhangFu,ShiFouTingPai,ZhangDie&count=0&sub=1&qid=123456";
        final String message = "/stkdata?obj=SH000001,SZ399001,SH000009&field=ZhongWenJianCheng,ZuiXinJia,ZhangFu,ShiFouTingPai,ZhangDie,FenZhongZhangFu5,LingZhangGu,DaDanDangRiLiuRuE&count=0&sub=1&qid=123456";

        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);

                sockets.add(webSocket);
                Log.i("test_2", "onOpen");

                webSocket.send(message);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);

                Log.i("test_2", "onMessage String");
                Log.i("test_2", text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);

                Log.i("test_2", "onMessage ByteString");
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                Log.i("test_2", "onClosing");
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                Log.i("test_2", "onClosed");
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t,  Response response) {
                super.onFailure(webSocket, t, response);

                Log.i("test_2", "onFailure");
            }
        });
    }
}
