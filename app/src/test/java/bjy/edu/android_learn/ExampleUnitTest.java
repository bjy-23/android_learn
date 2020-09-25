package bjy.edu.android_learn;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.regex.Pattern;

import bjy.edu.android_learn.http.HttpResult;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public static void main(String[] args) {

        //泛型测试
        String result = "{\"RspHeader\":{\"ResponseMsg\":\"\",\"ResponseCode\":\"AAAAAAA\"},\"RspBody\":{\"OpenFlag\":\"1\",\"RejectCount\":\"0\"}}";
//        HttpResult<HttpResult.OpenBean> bean = new HttpResult<>();
//        result = null;
//        result = "null";
//        result = "";
//        result = "{}";
        Gson gson = new Gson();
        Type type = new TypeToken<HttpResult<HttpResult.OpenBean>>(){}.getType();
        HttpResult<HttpResult.OpenBean> bean = gson.fromJson(result, type);

        result = "{\"RspHeader\":{\"ResponseMsg\":\"\",\"ResponseCode\":\"AAAAAAA\"},\"RspBody\":{}}";
        HttpResult httpResult = gson.fromJson(result, HttpResult.class);

        System.out.println("OpenFlag: "+bean.getRspBody().getOpenFlag());

//        ArrayList<String> list = new ArrayList<>();
//        T1.getParameterType(list);
//
//        new T1<HashMap>(HashMap.class);
//        new T1<HashMap<String, String>>(HashMap.class);
//        new T1<HashMap<String, String>>(HashMap<String, String>.class);
//
//        try {
//            Class<HashMap<String, String>> clazz = Class.forName("");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

//        String intS = "99";
//        try{
//            int value = Integer.parseInt(intS);
//        }catch (NumberFormatException e){
//            System.out.println("111222 parseInt " + e.getMessage());
//        }
//
//
//        String phoneNum = "1.13501741861E11";
////        String phoneNum = "1.11111E11";
//        String regNum = "[1-9].[0-9]*E[0-9]*";
//        Pattern pattern = Pattern.compile(regNum);
//        Matcher matcher = pattern.matcher(phoneNum);
////        while (matcher.find()){
////            System.out.println("匹配成功！");
////        }
////
//        if (matcher.matches()){
//            System.out.println("匹配成功！！!");
//        }else {
//            System.out.println("匹配失败！！!");
//        }

//        int i1 = 0x00002000;
//        System.out.println("i1: " + i1);
//        System.out.println("~i1: " + ~i1);
//        int i2 = 0xffffdfff;
//        System.out.println("i2: " + i2);
//        int i3 = 0xffffe000;
//        System.out.println("i3: " + i3);

//        String param = "bjy=柏建宇&&&&";
//        if (param != null){
//            String[] keyValues = param.split("&");
//            System.out.println("数组长度 " + keyValues.length);
//            for (String key_value : keyValues){
//                String[] array = key_value.split("=");
//                if (array.length == 2){
//                    System.out.println("[key]" + array[0] + "[value]" + array[1]);
//                }else {
//                    System.out.println(array.toString());
//                }
//            }
//        }


        // 测试之前要将copy过的文件删除 ！！！
//        File file = new File("/Users/bjy1229/StudioProjects/bjy/android_learn/app/src/main/assets/2020.pdf");
//        File fileCopy = new File("/Users/bjy1229/StudioProjects/bjy/android_learn/app/src/main/assets/2023.pdf");

        // io方式
//        long time_1 = System.currentTimeMillis();
//        FileInputStream fileInputStream = null;
//        ByteArrayOutputStream byteArrayOutputStream = null;
//        FileOutputStream fileOutputStream = null;
//        try {
//            fileInputStream = new FileInputStream(file);
//            byteArrayOutputStream = new ByteArrayOutputStream();
//            byte[] bytes = new byte[1024];
//            int count = -1;
//            while ((count = fileInputStream.read(bytes)) != -1){
//                byteArrayOutputStream.write(bytes, 0, count);
//            }
//
//            fileOutputStream = new FileOutputStream(fileCopy);
//            fileOutputStream.write(byteArrayOutputStream.toByteArray());
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if (fileInputStream != null){
//                    fileInputStream.close();
//                }
//                if (byteArrayOutputStream != null){
//                    byteArrayOutputStream.close();
//                }
//                if (fileOutputStream != null){
//                    fileOutputStream.close();
//                }
//            }catch (IOException e){
//
//            }
//        }
//        System.out.println("用时： " + (System.currentTimeMillis() - time_1) + "毫秒");

        // nio 方式
//        long time_1 = System.currentTimeMillis();
//        FileChannel input = null;
//        FileChannel output = null;
//        try{
//            input = new FileInputStream(file).getChannel();
//            output = new FileOutputStream(fileCopy).getChannel();
//            output.transferFrom(input, 0, input.size());
//        }catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if (input != null)
//                    input.close();
//
//                if (output != null)
//                    output.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("用时： " + (System.currentTimeMillis() - time_1) + "毫秒");


//        testCookie();

//        int a = 65;
//        char c = (char) a;
//        String s = new String(new char[]{c});
//        Log.e("65", s);
//
//        Writer writer;
//        OutputStream outputStream;
//        InputStream inputStream;

        //jdk支持的字符集
//        SortedMap<String, Charset> map = Charset.availableCharsets();
//        System.out.println("charset >>>");
//        for (String alias: map.keySet()){
//            System.out.println(alias);
//        }
//        System.out.println("<<< charset");

        // char -> 16进制
//        Charset charset = Charset.forName("utf-16");
//        CharsetEncoder encoder = charset.newEncoder();
//        CharBuffer charBuffer = CharBuffer.allocate(10);
//        charBuffer.put("柏");
//        charBuffer.flip();
//        try {
//            ByteBuffer byteBuffer = encoder.encode(charBuffer);
//            System.out.println("char >>> byte");
//            for (int i=0; i<byteBuffer.limit(); i++){
//                System.out.println(byte2String(byteBuffer.get(i)) + "");
//            }
//            System.out.println("<<< end");
//        } catch (CharacterCodingException e) {
//            e.printStackTrace();
//        }

//        字符流读取
//        CharArrayReader charArrayReader = new CharArrayReader(new char[]{'柏', '杨', '若', '楠'});
//        try {
//            int a1 = charArrayReader.read();
//            System.out.println(a1 + "  " + num2Hex16(a1));
//            int a2 = charArrayReader.read();
//            System.out.println(a2 + "  " + num2Hex16(a2));
//            int a3 = charArrayReader.read();
//            System.out.println(a3 + "  " + num2Hex16(a3));
//            int a4 = charArrayReader.read();
//            System.out.println(a4 + "  " + num2Hex16(a4));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void testCookie(){
        System.out.println("111222: " + getCookieValue(null));
        System.out.println("111222: " + getCookieValue("2222"));
        System.out.println("111222: " + getCookieValue("222;"));
        System.out.println("111222: " + getCookieValue("222;333"));
        System.out.println("111222: " + getCookieValue("mobwapLongTicketId=0F917EBF19C0711EA97709F29D728B119;Path=/;HttpOnly; Secure"));
    }

    //获取请求头中的Cookie的key、 value
    public static String[] getCookieValue(String cookieValue){
        if (cookieValue == null || "".equals(cookieValue.trim()))
            return null;
        String[] data = cookieValue.split(";");
        String[] key_value = data[0].split("=");
        if (key_value.length != 2)
            return null;
        return key_value;
    }

    @Test
    public void test(){
       int a = 10;
       int b = a;

       a = 12;
       System.out.println(b);

       String value = "提交";
        try {
            String e1 = URLEncoder.encode(value, "gbk");
            System.out.println("gbk: " + e1);

            String e2 = URLEncoder.encode(value);
            System.out.println("默认: " + e2);

            String e3 = URLEncoder.encode(value, "utf-8");
            System.out.println("utf-8: " + e3);

            System.out.println("java支持的字符集");
            SortedMap<String,Charset> map = Charset.availableCharsets();
            for (Map.Entry<String, Charset> entry: map.entrySet()){
                System.out.println(entry.getKey() + ": " +entry.getValue().toString());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addition_isCorrect() throws Exception {
        //正则
        String pattern = "spdbbank://wap.spdb.com.cn/pay?Plain=.*&Signature=.*";

        String s1 = "spdbbank://wap.spdb.com.cn/pay?Plain=k&Signature=f";
        String s2 = "spdbbank://wap.spdb.com.cn/pay?Plain2=jfjjkksk%3nsk&Signature=%%%jjdsskkkk&name=jsjnn";
        String s3 = "spdbbank://wap.spdb.com.cn/pay?Signature=%%%jjdsskkkk&name=jsjnn&Plain2=jfjjkksk%3nsk&&";


        System.out.println(Pattern.matches(pattern, s1));
        System.out.println(Pattern.matches(pattern, s2));
        System.out.println(Pattern.matches(pattern, s3));

        Uri uri = Uri.parse(s1);
        System.out.println("scheme: " + uri.getScheme());
        System.out.println("host: " + uri.getHost());
        System.out.println("path: " + uri.getPath());
    }

    public static String byte2String(byte b){

        String temp = Integer.toHexString(b & 0xFF);
        if (temp.length() == 1){
            return 0 + temp;
        }

        return temp;
    }

    public static String num2Hex16(int num){
        return String.format("%04x", num);
    }
}