package com.example.test_http;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.106.27:8082/") //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .build();

        HttpApi httpApi = retrofit.create(HttpApi.class);
        httpApi.getManager(2).enqueue(new Callback<HttpResult<ManagerReturnData>>() {
            @Override
            public void onResponse(Call<HttpResult<ManagerReturnData>> call, Response<HttpResult<ManagerReturnData>> response) {
                List<FundManagerBean> list = response.body().getResult().getSearchFundManagers();
                for (FundManagerBean managerBean : list){
                    Log.i("111222", managerBean.toString());
                }
            }

            @Override
            public void onFailure(Call<HttpResult<ManagerReturnData>> call, Throwable t) {

            }
        });
    }
}