package bjy.edu.android_learn;

import android.util.Log;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

public class HttpTest {

    @Test
    public void testGet() {
        Socket socket = null;
        try {
            //host:既可以使用域名，也可使用ip地址
//            String host = "pdtapi.sogukz.com";
            String host = "114.55.185.207";
            socket = new Socket(host, 80);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //GET必须大写
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GET /appConfig HTTP/1.1").append(" \n")
                .append("Host: pdtapi.sogukz.com").append(" \n")
                .append("User-Agent: PostmanRuntime/7.15.2").append(" \n")
                .append("Accept: */*").append(" \n")
                .append("Accept-encoding: gzip, deflate").append(" \n")
                .append("Connection: keep-alive").append(" \n")
                // 注意这里要换行结束请求头
                .append("\n");

        System.out.println("request: " + stringBuilder.toString());
        System.out.println("\n");
        try {
            OutputStream outputStream = socket.getOutputStream();
            //String.getBytes()默认使用utf-8编码
            outputStream.write(stringBuilder.toString().getBytes());
            InputStream inputStream = socket.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int count;
            while ((count = inputStream.read(bytes)) != -1) {
                byteArrayOutputStream.write(bytes, 0, count);
            }
            socket.close();
            byteArrayOutputStream.close();

            System.out.println("response");
            System.out.println(new String(byteArrayOutputStream.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPost(){

    }

    final String ServiceIp = "127.0.0.1";
    final int ServicePort = 10010;
    final char EndChar = '#';
    final String charName = "utf-8";

    /**
     * tcp 服务端
     */
    @Test
    public void testTcp() {
        try {
            InetAddress inetAddress = InetAddress.getByName(ServiceIp);
            ServerSocket serverSocket = new ServerSocket(ServicePort, 50, inetAddress);

            //1秒后开启客户端请求
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    testTcp2();
                }
            }).start();

//            while (true){
            System.out.println("socket server ---start accept---");
            Socket socket = serverSocket.accept();
            System.out.println("socket server ---accept success---");
            InputStream inputStream = socket.getInputStream();
//            System.out.println("socket server ---getInputStream---");

            StringBuilder stringBuilder = new StringBuilder();
            int c = -1;
            System.out.println("socket server input ready");
            while ((c = inputStream.read()) != -1) {
//                System.out.println("socket server inputRead" + c);
                if (c == EndChar)
                    break;

                //todo inputSteam.read()的返回值在0-255之间；对于使用utf-8编码的bytes来说，汉字(2个字节)不适合使用 (char)强制转换
                stringBuilder.append((char) c);
            }
            System.out.println("socket server input over :" + stringBuilder.toString());

//            int count = -1;
//            byte[] bytes = new byte[1024];
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            while ((count = inputStream.read(bytes)) != -1){
//                byteArrayOutputStream.write(bytes, 0, count);
//            }
//            System.out.println("socket server input over :" + new String(byteArrayOutputStream.toByteArray()));
//            inputStream.close();

//            String response = "接到了信息：" + stringBuilder.toString() + EndChar;
//            OutputStream outputStream = socket.getOutputStream();
//            outputStream.write(response.getBytes());
//            System.out.println("socket server output over");
//            // todo 不写close, 客户端的inputsteam.read()会一直阻塞，不会返回-1
//            outputStream.close();
//            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * tcp客户端
     */
    @Test
    public void testTcp2() {
        String msg = "bbbjy = 不不不交易" + EndChar;

        try {
            System.out.println("socket client ---start---");
            Socket socket = new Socket(ServiceIp, ServicePort);
            System.out.println("socket client ---new Socket() success---");
            OutputStream outputStream = socket.getOutputStream();
//            System.out.println("socket client ---getOutput---");
            // todo String.getBytes() 默认是utf-8编码
            outputStream.write(msg.getBytes());
            System.out.println("socket client write over");
            outputStream.close();
            // TODO: 2019-09-26 out流的关闭会导致socket close; getInput流就会失败
//            InputStream inputStream = socket.getInputStream();
//            System.out.println("socket client ---getInput---");
//            byte[] bytes = new byte[1024];
//            int count = -1;
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            System.out.println("socket client input ready");
//            while ((count = inputStream.read(bytes)) != -1) {
//                System.out.println("socket client input count :" + count);
//                byteArrayOutputStream.write(bytes, 0, count);
//            }
//            inputStream.close();
//
//            System.out.println("socket client input over");
//            System.out.println("client 接收：" + new String(byteArrayOutputStream.toByteArray(), "utf-16"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
