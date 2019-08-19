package bjy.edu.android_learn.http;

import okhttp3.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class HttpApi {
    static Service service;

    public static Service getService(){
        if (service == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.baidu.com/")
                    .build();

            service = retrofit.create(Service.class);
        }

        return service;
    }
    interface Service{
        @GET("ss/bjy")
        Call getName();
    }
}
