package bjy.edu.android_learn.http;

import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Field;

import okhttp3.Address;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.RealInterceptorChain;

public class MyNetWorkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        RealInterceptorChain realInterceptorChain = null;
        if (chain instanceof RealInterceptorChain){
            realInterceptorChain = (RealInterceptorChain) chain;

            Class class_chain = realInterceptorChain.getClass();
            try {
                Field field_streamAllocation = class_chain.getDeclaredField("streamAllocation");
                field_streamAllocation.setAccessible(true);
                // TODO: 2019-09-03 通过streamAllocation分析Socket
                StreamAllocation streamAllocation = (StreamAllocation) field_streamAllocation.get(realInterceptorChain);
                Address address = streamAllocation.address;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }catch (IllegalAccessException e){

            }

        }

        return chain.proceed(chain.request());
    }
}
