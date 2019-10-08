package bjy.edu.android_learn.http;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLStreamHandler;

import javax.net.SocketFactory;

public class HttpHelper {
    //大盘竞猜
    private String base = "http://pdtapi.sogukz.com";

    public void get(final String path){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(base + path);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();
                    int code = httpURLConnection.getResponseCode();
                    Log.i("code", code+"");
                    InputStream inputStream = httpURLConnection.getInputStream();

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int count = -1;
                    while ((count = inputStream.read(bytes)) != -1){
                        bos.write(bytes, 0, count);
                    }
                    String result = new String(bos.toByteArray());

                    Log.i("result", result);

                    inputStream.close();
                    httpURLConnection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e){

                }
            }
        }).start();
    }

    public static void get(){
        Socket socket = null;
        SocketFactory socketFactory = SocketFactory.getDefault();
        try {
            socket = socketFactory.createSocket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress("pdtapi.sogukz.com", 80);
            socket.connect(inetSocketAddress, 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
