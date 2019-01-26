package bjy.edu.android_learn.websocket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import bjy.edu.android_learn.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.ws.WebSocket;
import okhttp3.ws.WebSocketCall;
import okhttp3.ws.WebSocketListener;
import okio.Buffer;

public class WebSocketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket);

        JSONObject jsonObject = new JSONObject();

        Request request = new Request.Builder()
                .url("wss://market01.gmex.io/v1/market")
                .build();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.SECONDS)
                .build();

        WebSocketCall.create(client, request).enqueue(new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                Log.i("WebSocketCall", "onOpen");

                String message = "{\"req\":\"Sub\",\"rid\":\"20\",\"expires\":1537708219903,\"args\":[\"index_GMEX_CI_ETH\"]}";
                try {
                    webSocket.sendMessage(RequestBody.create(WebSocket.TEXT, message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(IOException e, Response response) {
                Log.i("WebSocketCall", "onFailure");
            }

            @Override
            public void onMessage(ResponseBody message) throws IOException {
                Log.i("WebSocketCall", "onMessage");
                String msg = message.string();
                Log.i("", msg);
            }

            @Override
            public void onPong(Buffer payload) {
                Log.i("WebSocketCall", "onPong");
            }

            @Override
            public void onClose(int code, String reason) {
                Log.i("WebSocketCall", "onClose");
            }
        });
    }
}
