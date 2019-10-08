package bjy.edu.android_learn;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class RSATest {

    static final String CHARSET = "utf-8";

    @Test
    public void test(){

        //RSA
        //RSA加密算法对于加密数据的长度是有要求的。一般来说，明文长度小于等于密钥长度（Bytes）-11。解决这个问题需要对较长的明文进行分段加解密。
        //加解密的编码方式要保持一致

        String message = "2019年8月25日，北京某超市五花肉标价25.8元每斤，售货员表示这已经是“最低价”，正常应该每斤卖30多元。\n" +
                "近期，猪肉价格明显上涨，一些老百姓觉得“菜篮子”沉了。据商务部监测，8月份猪肉平均批发价格为每公斤29.58元，同比上涨47.8%。\n" +
                "猪肉价格既关系到消费者的“小账”，又关系着经济运行的“大账”。百姓餐桌上的肉有保障吗？有关部门采取了哪些举措恢复生猪生产？未来猪肉价格走势如何？";


        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            //初始化，秘钥长度
            keyPairGenerator.initialize(1024);
            //生成秘钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //获得公钥
            Key publicKey = keyPair.getPublic();
            String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            System.out.println("公钥：" + publicKeyStr);
            //获得私钥
            Key privateKey = keyPair.getPrivate();
            String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            System.out.println("私钥：" + privateKeyStr);

            KeyFactory publicKeyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicX509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStr));
            RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKeyFactory.generatePublic(publicX509EncodedKeySpec);

            Cipher publicCipher = Cipher.getInstance("RSA");
            publicCipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
            String encodeMessage = new String(Base64.getEncoder().encode(rsaSplitCodec(publicCipher, Cipher.ENCRYPT_MODE, message.getBytes(CHARSET),
                    rsaPublicKey.getModulus().bitLength())));
            System.out.println("加密秘钥长度：" + (rsaPublicKey.getModulus().bitLength() /  8));
            System.out.println("加密数据：" + encodeMessage);

            KeyFactory privateKeyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr));
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKeyFactory.generatePrivate(pkcs8EncodedKeySpec);

            Cipher privateCipher = Cipher.getInstance("RSA");
            privateCipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
            String decodeMessage = new String(rsaSplitCodec(privateCipher, Cipher.DECRYPT_MODE, Base64.getDecoder().decode(encodeMessage),
                    rsaPrivateKey.getModulus().bitLength()));
            System.out.println("默认字符编码：" + Charset.defaultCharset().toString());
            System.out.println("解密秘钥长度：" + (rsaPrivateKey.getModulus().bitLength() /  8));
            System.out.println("解密数据：" + decodeMessage);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize){
        int maxBlock = 0;
        if(opmode == Cipher.DECRYPT_MODE){
            maxBlock = keySize / 8;
        }else{
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try{
            while(datas.length > offSet){
                if(datas.length-offSet > maxBlock){
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                }else{
                    buff = cipher.doFinal(datas, offSet, datas.length-offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        }catch(Exception e){
            throw new RuntimeException("加解密阀值为["+maxBlock+"]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultDatas;
    }
}
