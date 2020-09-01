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
import java.net.Proxy;

import bjy.edu.android_learn.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class HttpActivity extends AppCompatActivity {

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        //对HttpURLConnection的封装
//        test_1();

        //okHttp同步调用
        test_2();

        //
//        test_3();
    }

    private void test_1(){
        new HttpHelper().get("/appConfig");
    }

    private void test_2(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        //addInterceptor-->对发出去的请求做最初的处理，以及在拿到最后Reponse时候做最后的处理
//                        .addInterceptor(new NullResponseInterceptor())
//                        .addInterceptor(new HttpLoggingInterceptor())
                        .addInterceptor(new OkHttpLogInterceptor())
                        //addNetworkInterceptor -->对发出去的请求做最后的处理，以及在拿到结果时候做最初的处理
//                        .addNetworkInterceptor(new MyNetWorkInterceptor())
                        .addNetworkInterceptor(new OkRequestInterceptor())
                        .build();

                Request request = new Request.Builder()
                        .url("http://pdtapi.sogukz.com")
                        .build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    Log.i("response", response.body().string());
                    HttpHelper.get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(runnable).start();
    }

    private void test_3(){
        RetrofitApi.Service service = RetrofitApi.getService();
        retrofit2.Call<ResponseBody> call = service.dapanPredict("");
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.i("1", "1");
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Log.i("0", "0");
            }
        });
    }
}
