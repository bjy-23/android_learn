package bjy.edu.android_learn.http;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RetrofitApi {

    static Service getService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kzapi.sogukz.com:3020")
                .build();

        return retrofit.create(Service.class);
    }

    interface Service{
        @GET("entireQuotations")
        Call<ResponseBody>  dapanPredict(@Query("name") String name);
    }

    public static void main(String[] args) {
        Class clazz = Service.class;

        try {
            Method method = clazz.getMethod("dapanPredict", String.class);
            Annotation[][] array = method.getParameterAnnotations();
            array[0] = null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
