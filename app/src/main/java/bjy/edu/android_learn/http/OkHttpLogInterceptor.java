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
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by sogubaby on 2018/7/3.
 *
 * okHttp日志拦截器
 */

public class OkHttpLogInterceptor implements Interceptor {

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

        Response response = chain.proceed(request);
        MediaType mediaType = response.body().contentType();
        String body = response.body().string();
        StringBuilder sbResponse = new StringBuilder();
        sbResponse.append("------------------ response ------------------ \n");
        sbResponse.append("url: ").append(response.request().url().toString()).append("\n");
        sbResponse.append("body: ").append(body).append("\n");
        sbResponse.append("------------------ response ------------------ \n");
        Log.i("okHttp", sbResponse.toString());

        return response.newBuilder().body(ResponseBody.create(mediaType, body)).build();
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
