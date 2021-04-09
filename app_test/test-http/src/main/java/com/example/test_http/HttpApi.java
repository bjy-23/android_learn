package com.example.test_http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HttpApi {

    @GET("v1/search/managerTop")
    Call<HttpResult<ManagerReturnData>> getManager(@Query("top") int count);
}
