package bjy.edu.android_learn;

import android.net.Uri;
import android.util.Log;
import android.util.Patterns;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509CRLSelector;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.SortedMap;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void test(){
       int a = 10;
       int b = a;

       a = 12;
       System.out.println(b);
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

    public static void main(String[] args) {

        int a = 65;
        char c = (char) a;
        String s = new String(new char[]{c});
        Log.e("65", s);

        Writer writer;
        OutputStream outputStream;
        InputStream inputStream;

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
        CharArrayReader charArrayReader = new CharArrayReader(new char[]{'柏', '建', '宇'});
        try {
            int a1 = charArrayReader.read();
            System.out.println(a1 + "  " + num2Hex16(a1));
            int a2 = charArrayReader.read();
            System.out.println(a2 + "  " + num2Hex16(a2));
            int a3 = charArrayReader.read();
            System.out.println(a3 + "  " + num2Hex16(a3));
            int a4 = charArrayReader.read();
            System.out.println(a4 + "  " + num2Hex16(a4));
        } catch (IOException e) {
            e.printStackTrace();
        }

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