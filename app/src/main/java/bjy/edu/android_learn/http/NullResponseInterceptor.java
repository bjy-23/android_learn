package bjy.edu.android_learn.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class NullResponseInterceptor implements Interceptor {
    /**
     *  需要在intercept中实现一次 chain.proceed
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
