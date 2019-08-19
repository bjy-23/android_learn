package bjy.edu.android_learn.http;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import bjy.edu.android_learn.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpActivity extends AppCompatActivity {

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);


        //httpUrlConnection
//        test_1();

        //okhttp 同步调用、异步调用
        test_2();

        //retrofit
//        test_3();

//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new HttpLoggingInterceptor())
//                .build();
//        JSONObject jsonObject = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        try {
//            jsonArray.put("pic1");
//            jsonArray.put("pic2");
//            jsonObject.put("publishUserId", "1229");
//            jsonObject.put("title", "bjy");
//            jsonObject.put("allContent", "cool");
//            jsonObject.put("writingType", "1");
//            jsonObject.put("publishStatus", "1");
//            jsonObject.put("wordContent", "cool");
//            jsonObject.put("picContent", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
//
//        Request request = new Request.Builder()
//                .url("http://47.98.188.238:3020/writings")
//                .addHeader("Content-type", "application/json")
//                .post(requestBody)
//                .build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("1", "onFailure");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.e("1", "onResponse");
//            }
//        });
    }

    private void test_1(){
        new HttpHelper().get("/appConfig");
    }

    private void test_2(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                        .addInterceptor(new NullResponseInterceptor())
                        .addInterceptor(new HttpLoggingInterceptor())
                        .build();

                Request request = new Request.Builder()
                        .url("https://www.baidu.com")
                        .build();

                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    if (response != null)
                        Log.i("response", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Call call2 = call.clone();
                call2.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });
            }
        };

        new Thread(runnable).start();
    }

    private void test_3(){
        Call call = HttpApi.getService().getName();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
