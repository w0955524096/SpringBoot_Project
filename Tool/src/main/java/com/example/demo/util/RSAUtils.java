package com.example.demo.util;

import ch.qos.logback.classic.Logger;
import com.example.demo.DemoApplication;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtils {
    private static final String MERCHANT_ID = DemoApplication.MERCHANT_ID;
    private static final String MD5KEY = DemoApplication.MD5KEY;
    private static final String FILENAME = DemoApplication.FILENAME;
    private static final String AGENTPAY_URL = DemoApplication.AGENTPAY_URL;
    private static final Logger log= DemoApplication.log;

    /**
     * RSA Encrypt
     *
     * @param sensitiveInformation 加密內容
     * @return 加密結果
     * @throws Exception 例外
     */

    //RSA Encrypt By FILENAME
    protected static String RSAEncrypt(String sensitiveInformation) throws Exception {
        PublicKey publicKey = GetPublicKey(FILENAME);
        byte[] encryptedBytes = Encrypt(sensitiveInformation.getBytes(StandardCharsets.UTF_8), publicKey);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    //RSA Encrypt By PublicKey String
    protected static String RSAEncryptByKey(String data, String Key) throws Exception {

        //公鑰 String 轉 PublicKey 對象
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(Key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        //RSA 加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] sign = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
//        System.out.println(sign);
//        System.out.println(Arrays.toString(sign));
//        System.out.println(Base64.getEncoder().encodeToString(sign));

        //RSA
        return Base64.getEncoder().encodeToString(sign);
    }

    /**
     * RSA Tool
     * @param key
     * @return
     * @throws Exception
     */

    //PublicKey String To PublicKey Object
    protected static PublicKey GetStringToPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    //.cer File To PublicKey Object
    private static PublicKey GetPublicKey(String filename) throws Exception {

        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        FileInputStream is = new FileInputStream(filename);
        X509Certificate cer = (X509Certificate) fact.generateCertificate(is);
        PublicKey key = cer.getPublicKey();

        log.info("public  key:" + new BASE64Encoder().encode(key.getEncoded()).replace("\r\n", ""));
        return key;
    }

    //RSA Encrypt By PublicKey Object
    private static byte[] Encrypt(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    //Decrypt By PrivateKey .cer
    private static byte[] Decrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }


}
