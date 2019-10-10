package bjy.edu.android_learn.http;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 用于okhttpclient.addNetworkInterceptor()
 * 将最终的请求报文打印出来
 */
public class OkRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl httpUrl = request.url();
        StringBuilder sbRequest = new StringBuilder();
        sbRequest.append("------------------ request ------------------ \n");
        sbRequest.append(request.method()).append(" ").append(path(httpUrl.pathSegments())).append("\n\n");
        sbRequest.append("Host: ").append(httpUrl.host()).append("\n");
        sbRequest.append(request.headers().toString()).append("\n");
        if (request.body() != null){
            RequestBody requestBody = request.body();
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("utf-8");
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            sbRequest.append(buffer.readString(charset)).append("\n");
        }
        sbRequest.append("------------------ request ------------------ \n");
        Log.i("okHttp", sbRequest.toString() + "\n");

        return chain.proceed(request);
    }

    private String path(List<String> list){
        if (list == null || list.isEmpty())
            return "/";

        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<list.size(); i++){
            stringBuilder.append("/").append(list.get(i));
        }

        return stringBuilder.toString();
    }
}
