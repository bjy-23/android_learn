package bjy.edu.android_learn.socket;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import bjy.edu.android_learn.R;

public class SocketActivity extends AppCompatActivity {
    private static int PORT = 1229;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        //理解一些基本类
//        test_1();

        // tcp 通信
        test_2();
    }

    private void test_1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    Log.i("inetAddress", "本机名：" + inetAddress.getHostName());
                    Log.i("inetAddress", "本机地址：" + inetAddress.getHostAddress());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void test_2() {
        PORT++;
        // 客户端
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);

                    Socket socket = new Socket("", PORT);
                    OutputStream outputStream = socket.getOutputStream();
                    PrintWriter printWriter = new PrintWriter(outputStream);
//                    printWriter.write("我好想你");
                    printWriter.println("我好想你");
                    printWriter.flush();
                    Thread.sleep(2000);
//                    printWriter.write("我想死你了");
                    printWriter.println("我想死你了");
                    printWriter.flush();

                    InputStream inputStream = socket.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String reponse;
                    while ((reponse = bufferedReader.readLine()) != null){
                        System.out.println("服务器响应：" + reponse);
                    }
//                    socket.shutdownOutput();
//                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (InterruptedException e){

                }
            }
        }).start();


        //服务端
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(PORT);
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    String ip = inetAddress.getHostAddress();
                    System.out.println("~~~服务端已就绪，等待客户端接入~，服务端ip地址: " + ip);
                    Socket socket = serverSocket.accept();
                    InputStream is = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();
                    PrintWriter printWriter = new PrintWriter(outputStream);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                    String info;
                    while ((info = bufferedReader.readLine()) != null) {
                        System.out.println("客户端发送过来的信息：" + info);
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        printWriter.println("想你个大头鬼");
                        printWriter.flush();
                    }
                    socket.shutdownInput();
                    socket.close();
                    System.out.println("服务端已关闭");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
