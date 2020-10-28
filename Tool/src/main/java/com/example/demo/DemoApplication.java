package com.example.demo;


import ch.qos.logback.classic.Logger;
import com.example.demo.util.MD5Utils;
import com.example.demo.util.Util;
import com.github.tomakehurst.wiremock.common.Json;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import wiremock.org.apache.http.HttpEntity;
import wiremock.org.apache.http.HttpResponse;
import wiremock.org.apache.http.NameValuePair;
import wiremock.org.apache.http.client.ClientProtocolException;
import wiremock.org.apache.http.client.HttpClient;
import wiremock.org.apache.http.client.ResponseHandler;
import wiremock.org.apache.http.client.entity.UrlEncodedFormEntity;
import wiremock.org.apache.http.client.methods.HttpGet;
import wiremock.org.apache.http.client.methods.HttpPost;
import wiremock.org.apache.http.entity.StringEntity;
import wiremock.org.apache.http.impl.client.CloseableHttpClient;
import wiremock.org.apache.http.impl.client.HttpClients;
import wiremock.org.apache.http.message.BasicNameValuePair;
import wiremock.org.apache.http.util.EntityUtils;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSONObject;

@Controller
@SpringBootApplication
public class DemoApplication {

    private static final String MERCHANT_ID = "请填入商户号";
    private static final String MD5KEY = "bd4fc1e7986cbb6c";
    private static final String FILENAME = "C:\\Users\\Peter\\Documents\\GithubRepo\\工具\\西瓜代付密钥.cer";
    private static final String AGENTPAY_URL = "https://api.watermelonpaytech.com/watermelon-gateway/back-pay/agent-pay?";
    private static final transient Logger log = (Logger) LoggerFactory.getLogger(DemoApplication.class);

    /**
     * WTM
     *
     * @throws Exception 例外處理
     */

    //agentPay
    private static void agentPay() throws Exception {
        TreeMap<String, Object> paramsMap = new TreeMap<>();

        paramsMap.put("MERCHANT_ID", "WTM000859PENSTONE2");

        paramsMap.put("TRAN_CODE", Util.getTran_Code());

        paramsMap.put("TRAN_AMT", "1000");

        //敏感资讯须经过RSA加密
        //paramsMap.put("NAME", Base64.getEncoder().encodeToString(encrypt("demoName".getBytes("UTF-8"), getStringToPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgbYQRjd8wJ6JM2bkbjPpV0vUAoDzGMN2VkN6VV6Tg2yzK6VMe47mJMlZnDgVvF4eI1+OaRaRKPXY3itwRBtT5K82k7e7GDSs+jvGvKbw7XNxDZIl+tq/ioMYrh1SlaAmUkjuVikLKI0+AxlJvoIBlzzaXxjEyV8xX+i9KoPBSPKYx8fDMZvpgiKJKBqJaQgHLO+iVN204I7/1CfoliWCHk4ronA4vd921lskcLZ9Uz4utvQRPyZETNg8+4YLUEkIPGFeSayloJ5YOBiHhxIe3FMHUIcafX36Nu6AE7A7QsuGH3CzdCJv4ib2c503q/rA32oMc0Pp0llMbu7Xd1V8ZQIDAQAB"))));
        paramsMap.put("NAME", rsaEncryptByKey("test", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgbYQRjd8wJ6JM2bkbjPpV0vUAoDzGMN2VkN6VV6Tg2yzK6VMe47mJMlZnDgVvF4eI1+OaRaRKPXY3itwRBtT5K82k7e7GDSs+jvGvKbw7XNxDZIl+tq/ioMYrh1SlaAmUkjuVikLKI0+AxlJvoIBlzzaXxjEyV8xX+i9KoPBSPKYx8fDMZvpgiKJKBqJaQgHLO+iVN204I7/1CfoliWCHk4ronA4vd921lskcLZ9Uz4utvQRPyZETNg8+4YLUEkIPGFeSayloJ5YOBiHhxIe3FMHUIcafX36Nu6AE7A7QsuGH3CzdCJv4ib2c503q/rA32oMc0Pp0llMbu7Xd1V8ZQIDAQAB"));
        //System.out.println(paramsMap.get("NAME"));

        paramsMap.put("BANK_NO", rsaEncrypt("123456"));

        paramsMap.put("BANK_CODE", "1001");

        paramsMap.put("SUBMIT_TIME", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        paramsMap.put("VERSION", "1");

        // 将Map物件转换成字串
        String paramSrc = Util.mapToString(paramsMap);
        // 路径+上MD5金钥制作签名
        String sign = MD5Utils.md5(paramSrc + MD5KEY, "UTF-8");
        // URLEncoder.encode(utf-8)参数的值
        paramSrc = Util.mapToURLEncodeString(paramsMap);
        // 结尾加上签名
        paramSrc = paramSrc + "&SIGNED_MSG=" + sign;
        paramsMap.put("SIGNED_MSG", "");

        log.info("方法名稱[{}]", Thread.currentThread().getStackTrace()[1].getMethodName());


        xFormPost("https://api2.watermelonpaytech.com/watermelon-gateway/back-pay/agent-pay", new JSONObject(paramsMap));


        System.out.println("========================================================================================================================================================================================");

    }

    //payApply
    private static void payApply() throws Exception {
        TreeMap<String, Object> paramsMap = new TreeMap<>();

        paramsMap.put("MERCHANT_ID", "WTM000859PENSTONE2");

        paramsMap.put("TRAN_CODE", Util.getTran_Code());

        paramsMap.put("TRAN_AMT", "1000");

        paramsMap.put("TYPE", "27");

        paramsMap.put("BANK_CODE", "848347383939");

        paramsMap.put("REMARK", "REMARK");

        paramsMap.put("NO_URL", "NO_URL");

        paramsMap.put("RET_URL", "RET_URL");

        paramsMap.put("BANK_ID", "1001");

        paramsMap.put("USER_NAME", "TEST");

        paramsMap.put("SUBMIT_TIME", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        paramsMap.put("VERSION", "1");

        // 将Map物件转换成字串
        String paramSrc = Util.mapToString(paramsMap);
        // 路径+上MD5金钥制作签名
        String sign = MD5Utils.md5(paramSrc + MD5KEY, "UTF-8");
        // URLEncoder.encode(utf-8)参数的值
        paramSrc = Util.mapToURLEncodeString(paramsMap);
        // 结尾加上签名
        paramSrc = paramSrc + "&SIGNED_MSG=" + sign;
        paramsMap.put("SIGNED_MSG", sign);
        log.info("方法名稱[{}]", Thread.currentThread().getStackTrace()[1].getMethodName());


        xFormPost("https://api2.watermelonpaytech.com/watermelon-gateway/front-pay/ebank-pay.htm", new JSONObject(paramsMap));

        System.out.println("========================================================================================================================================================================================");

    }

    //H5
    private static void H5() throws Exception {
        TreeMap<String, Object> paramsMap = new TreeMap<>();

        paramsMap.put("MERCHANT_ID", "WTM000859PENSTONE2");

        paramsMap.put("TRAN_CODE", Util.getTran_Code());

        paramsMap.put("TRAN_AMT", "1000");

        paramsMap.put("REMARK", "REMARK");

        paramsMap.put("TYPE", "8");

        paramsMap.put("BANK_ACCOUNT_NO", "848347383939");

        paramsMap.put("NO_URL", "NO_URL");

        paramsMap.put("RET_URL", "RET_URL");

        paramsMap.put("SUBMIT_TIME", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        paramsMap.put("BANK_ID", "1001");

        paramsMap.put("VERSION", "1");


        // 将Map物件转换成字串
        String paramSrc = Util.mapToString(paramsMap);
        // 路径+上MD5金钥制作签名
        String sign = MD5Utils.md5(paramSrc + MD5KEY, "UTF-8");
        // URLEncoder.encode(utf-8)参数的值
        paramSrc = Util.mapToURLEncodeString(paramsMap);
        // 结尾加上签名
        paramSrc = paramSrc + "&SIGNED_MSG=" + sign;
        paramsMap.put("SIGNED_MSG", sign);
        log.info("方法名稱[{}]", Thread.currentThread().getStackTrace()[1].getMethodName());


        xFormPost("https://api2.watermelonpaytech.com/watermelon-gateway/front-pay/h5-pay.htm", new JSONObject(paramsMap));

        System.out.println("========================================================================================================================================================================================");

    }

    //QR
    private static void QR() throws Exception {
        TreeMap<String, Object> paramsMap = new TreeMap<>();

        paramsMap.put("MERCHANT_ID", "WTM000859PENSTONE2");

        paramsMap.put("TRAN_CODE", Util.getTran_Code());

        paramsMap.put("TRAN_AMT", "1000");

        paramsMap.put("TYPE", "1");

        paramsMap.put("REMARK", "REMARK");

        paramsMap.put("AUTHCODE", "AUTHCODE");

        paramsMap.put("NO_URL", "NO_URL");

        paramsMap.put("SUBMIT_TIME", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        paramsMap.put("VERSION", "1");


        // 将Map物件转换成字串
        String paramSrc = Util.mapToString(paramsMap);
        // 路径+上MD5金钥制作签名
        String sign = MD5Utils.md5(paramSrc + MD5KEY, "UTF-8");
        // URLEncoder.encode(utf-8)参数的值
        paramSrc = Util.mapToURLEncodeString(paramsMap);
        // 结尾加上签名
        paramSrc = paramSrc + "&SIGNED_MSG=" + sign;
        paramsMap.put("SIGNED_MSG", sign);
        log.info("方法名稱[{}]", Thread.currentThread().getStackTrace()[1].getMethodName());


        xFormPost("https://api2.watermelonpaytech.com/watermelon-gateway/front-pay/qr-pay.htm", new JSONObject(paramsMap));

        System.out.println("========================================================================================================================================================================================");

    }

    //quick
    private static void quick() throws Exception {
        TreeMap<String, Object> paramsMap = new TreeMap<>();

        paramsMap.put("MERCHANT_ID", "WTM000859PENSTONE2");

        paramsMap.put("TRAN_CODE", Util.getTran_Code());

        paramsMap.put("TRAN_AMT", "1000");

        paramsMap.put("TYPE", "15");

        paramsMap.put("REMARK", "REMARK");

        paramsMap.put("NO_URL", "NO_URL");

        paramsMap.put("RET_URL", "RET_URL");

        paramsMap.put("SUBMIT_TIME", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        paramsMap.put("VERSION", "1");


        // 将Map物件转换成字串
        String paramSrc = Util.mapToString(paramsMap);
        // 路径+上MD5金钥制作签名
        String sign = MD5Utils.md5(paramSrc + MD5KEY, "UTF-8");
        // URLEncoder.encode(utf-8)参数的值
        paramSrc = Util.mapToURLEncodeString(paramsMap);
        // 结尾加上签名
        paramSrc = paramSrc + "&SIGNED_MSG=" + sign;
        paramsMap.put("SIGNED_MSG", sign);
        log.info("方法名稱[{}]", Thread.currentThread().getStackTrace()[1].getMethodName());


        xFormPost("https://api2.watermelonpaytech.com/watermelon-gateway/front-pay/quick-pay.htm", new JSONObject(paramsMap));

        System.out.println("========================================================================================================================================================================================");

    }

    //agent-query
    private static void agentQuery() throws Exception {
        TreeMap<String, Object> paramsMap = new TreeMap<>();

        paramsMap.put("MERCHANT_ID", "WTM000859PENSTONE2");

        paramsMap.put("TRAN_CODE", Util.getTran_Code());

        paramsMap.put("VERSION", "1");


        // 将Map物件转换成字串
        String paramSrc = Util.mapToString(paramsMap);
        // 路径+上MD5金钥制作签名
        String sign = MD5Utils.md5(paramSrc + MD5KEY, "UTF-8");
        // URLEncoder.encode(utf-8)参数的值
        paramSrc = Util.mapToURLEncodeString(paramsMap);
        // 结尾加上签名
        paramSrc = paramSrc + "&SIGNED_MSG=" + sign;
        paramsMap.put("SIGNED_MSG", sign);
        log.info("方法名稱[{}]", Thread.currentThread().getStackTrace()[1].getMethodName());


        xFormPost("https://api2.watermelonpaytech.com/watermelon-gateway/back-pay/agent-pay-query", new JSONObject(paramsMap));

        System.out.println("========================================================================================================================================================================================");

    }

    //pay-query
    private static void payQuery() throws Exception {
        TreeMap<String, Object> paramsMap = new TreeMap<>();

        paramsMap.put("MERCHANT_ID", "WTM000859PENSTONE2");

        paramsMap.put("TRAN_CODE", Util.getTran_Code());

        paramsMap.put("VERSION", "1");


        // 将Map物件转换成字串
        String paramSrc = Util.mapToString(paramsMap);
        // 路径+上MD5金钥制作签名
        String sign = MD5Utils.md5(paramSrc + MD5KEY, "UTF-8");
        // URLEncoder.encode(utf-8)参数的值
        paramSrc = Util.mapToURLEncodeString(paramsMap);
        // 结尾加上签名
        paramSrc = paramSrc + "&SIGNED_MSG=" + sign;
        paramsMap.put("SIGNED_MSG", sign);
        log.info("方法名稱[{}]", Thread.currentThread().getStackTrace()[1].getMethodName());


        xFormPost("https://api2.watermelonpaytech.com/watermelon-gateway/back-pay/qr-pay-query", new JSONObject(paramsMap));

        System.out.println("========================================================================================================================================================================================");

    }

    //count-query
    private static void countQuery() throws Exception {
        TreeMap<String, Object> paramsMap = new TreeMap<>();

        paramsMap.put("MERCHANT_ID", "WTM000859PENSTONE2");

        paramsMap.put("TRAN_CODE", Util.getTran_Code());

        paramsMap.put("VERSION", "1");


        // 将Map物件转换成字串
        String paramSrc = Util.mapToString(paramsMap);
        // 路径+上MD5金钥制作签名
        String sign = MD5Utils.md5(paramSrc + MD5KEY, "UTF-8");
        // URLEncoder.encode(utf-8)参数的值
        paramSrc = Util.mapToURLEncodeString(paramsMap);
        // 结尾加上签名
        paramSrc = paramSrc + "&SIGNED_MSG=" + sign;
        paramsMap.put("SIGNED_MSG", sign);
        log.info("方法名稱[{}]", Thread.currentThread().getStackTrace()[1].getMethodName());


        xFormPost("https://api2.watermelonpaytech.com/watermelon-gateway/back-pay/count-query", new JSONObject(paramsMap));

        System.out.println("========================================================================================================================================================================================");

    }

    /**
     * RSA Encrypt
     *
     * @param sensitiveInformation 加密內容
     * @return 加密結果
     * @throws Exception 例外
     */

    //RSA Encrypt
    private static String rsaEncrypt(String sensitiveInformation) throws Exception {
        PublicKey publicKey = getPublicKey(FILENAME);
        byte[] encryptedBytes = encrypt(sensitiveInformation.getBytes(StandardCharsets.UTF_8), publicKey);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    //Encrypt By PublicKey String
    private static String rsaEncryptByKey(String data, String Key) throws Exception {

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

    //PublicKey String To PublicKey Object
    private static PublicKey getStringToPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    //.cer File To PublicKey Object
    private static PublicKey getPublicKey(String filename) throws Exception {

        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        FileInputStream is = new FileInputStream(filename);
        X509Certificate cer = (X509Certificate) fact.generateCertificate(is);
        PublicKey key = cer.getPublicKey();

        log.info("public  key:" + new BASE64Encoder().encode(key.getEncoded()).replace("\r\n", ""));
        return key;
    }

    /**
     * RSA Tool
     *
     * @param content   加密內容
     * @param publicKey 公鑰
     * @return 回傳加解密結果
     * @throws Exception 例外
     */

    //Encrypt By PublicKey .cer
    private static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    //Decrypt By PrivateKey .cer
    private static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }

    /**
     * Md5Key Encrypt
     *
     * @return 回傳加密結果
     */

    //Sign By Md5Key
    private static String getSignByMd5key(String data, String reTurnType) {
        //md5 key
        //String md5Key = (String) JsonParser.parserJsonStringToJavaObject(req.getKey(), java.util.Map.class).get("md5");

        String dataStr;
        dataStr = "amount=100000000&bankCode=201&cid=RCEF8971&notifyUrl=https://pay.cxct.org/api/payBackProcess/498.do&payType=18&requestTime=20201012160804&returnType=" + reTurnType + "&returnUrl=http://mobile.cxct.org/static/html/rech_succes.html&tradeNo=" + data + "&version=1.6";
        String md5Key = "djf838t001v13m6z";

        //md5 sign
        String SignStr = (Util.md5Hex(dataStr + "&key=" + md5Key));

        log.info("[{}]:[{}]", "加密結果", SignStr);

        return SignStr;
    }

    /**
     * Verify
     *
     * @throws Exception 錯誤
     */

    //notifyA
    private static void notifyA() throws Exception {
        String signature = "QtkiNmMs29XPlzAu5/Ph6uQ/F1gXvaLrYN4hyERzffAlwmj+uABVWV6f12U1hp8xoppC9z4e6ybYwjN6KknbrY7TpKUPRRM1x5HftdSVqpZYhMQTYRosxQcM6kU+1KBURUiFQaBjOSsjRX1afR5dgK83IQrOzcOXtvne3Z9ya2LAsOsbR/6+8M+OruGgH5fjyM/Xs8Ep7cDT/E4rGid24v7mmu8Ef3BdXO3nPM5B5EjoS4BRkfbg1bnPIMj8kM+Up/+fmF1qyE+rmx4IUSOKlpC/O+S28EdJAQJH6uSK1OO05U7SLzyEx8W5ew7Dr1+LR1/R2TnpCYT5BrXx1oeZYw==";

        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwmOWEw+b0e6mLX+b3fs1\n" +
                "DB4TphJkZutyKcHXSkKSyhfLs9cJopVjKUCuC5A8YzWG9OV7yCNbkAhQdtL0WaTY\n" +
                "c+FlXuXHGtTGXYkyZ4DFxmomV6s7rdwrnkrBR4Cs7GY3sXNc+To/KHj/NW1HxKdh\n" +
                "NtgBYqLBNcsWv0u6fZsuVyUwUMBh1fEdMLTfbrTMtQjh+URGxvy04wQqZFmxy4pF\n" +
                "fddScCyLMhmDEp6F7IJvRIQn+cU0yA6Cp8pxkVZEazRv5ZH3QAnMjhBF7WnFoclN\n" +
                "RpKA97FXkBp1eqlvZDPsobJRO4cZ7RJdbxfCi1dFZIKzTd/1BObmrqPIh2A/6wxs\n" +
                "KwIDAQAB";
        String paramsSrc = "{\"order\":{\"id\":\"3ece4000-b8e4-4181-935d-30b4e27fda4c\",\"subject\":\"代付\",\"total_amount\":\"501.0\",\"notify_url\":\"http://114.32.170.183:8085/RockPay/NotifyluckypayAgentPay.do\",\"merchant_order_id\":\"20201008154903P001x0f6b\",\"status\":\"failed\",\"merchant_fee\":\"3.0\",\"bank_name\":\"中国工商银行\",\"bank_province_name\":\"北京\",\"bank_city_name\":\"北京\",\"bank_account_no\":\"214121\",\"bank_account_type\":\"personal\",\"bank_account_name\":\"测试用\",\"remark\":null,\"completed_at\":null,\"created_at\":\"2020-10-08T15:49:06+08:00\"},\"notify_type\":\"payment_transfer_failed\"}";

        PublicKey RSAPubKey = getStringToPublicKey(publicKey);
        Boolean result = verify256(paramsSrc.getBytes(StandardCharsets.UTF_8), RSAPubKey, Base64.getDecoder().decode(signature));
        System.out.println(result);

    }

    //verify256
    private static boolean verify256(byte[] data, PublicKey publicKey, byte[] sign) throws Exception {
        // 解密公钥

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(sign);

    }

    /**
     * HttpRequest
     */

    //Post x-www-form-urlencoded
    private static String xFormPost(String url, JSONObject obj) {

        //obj放進params
        List<NameValuePair> params = new ArrayList<>();
        for (String name : obj.keySet()) {
            params.add(new BasicNameValuePair(name, obj.get(name).toString()));
        }

        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String s;
        String Location;
        try {

            log.info("RequestParams[{}]", obj);
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            HttpResponse response = client.execute(httpPost);



            //get response.getEntity
            HttpEntity entity = response.getEntity();
            s = EntityUtils.toString(entity);
            log.info("ContentType:[{}]", String.valueOf(entity.getContentType()));
            log.info("Response[{}]", s);

            //get StatusCode
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("statusCode:[{}]", String.valueOf(statusCode));
//            log.info(String.valueOf(statusCode));

            //get Url
//            Location = response.getFirstHeader("Location").toString().replace("Location: ", "");
//            log.info("Location:[{}]", Location);

//            return (null != entity.getContentType()) ? s : Location;
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "False";
    }

    //Post JSON
    private static String jsonPost(String url, JSONObject obj) {
        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();

            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");

            // 解决中文乱码问题
            StringEntity stringEntity = new StringEntity(obj.toString(), "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(stringEntity);

            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {//
                int status = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                if (status < 200 || status >= 300) {
                    log.info("status:[{}]", status + "");
                }
                return entity != null ? EntityUtils.toString(entity) : null;
            };
            return httpclient.execute(httpPost, responseHandler);

        } catch (Exception e) {
            log.warn(e.toString());
        }
        return null;
    }

    //Get
    private static String httpGet(String url, JSONObject obj) {
        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet(url);

            // 解决中文乱码问题
            StringEntity stringEntity = new StringEntity(obj.toString(), "UTF-8");
            stringEntity.setContentEncoding("UTF-8");

            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                //200~300
                if (status >= 200 && status < 300) {

                    HttpEntity entity = response.getEntity();

                    return entity != null ? EntityUtils.toString(entity) : null;
                }
                //400
                else {
                    throw new ClientProtocolException(
                            "Unexpected response status: " + status);
                }
            };

            return httpclient.execute(httpGet, responseHandler);

        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return null;
    }

    /**
     * mapToStr
     *
     * @param params              Map資料
     * @param isIgnoreBlankOrNull 是否忽略空白Key
     * @return 回傳拼接字串，e.x. key1=value1&key2=value2
     */

    //jsonObjectToStr
    private static String jsonObjectKeySortToLinkString(Map<String, Object> params, boolean isIgnoreBlankOrNull) throws UnsupportedEncodingException {
        ArrayList keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();

        for (String key : params.keySet()) {
            String value = (String) params.get(key);
            if ((isIgnoreBlankOrNull || !"".equals(value) && value.trim().length() != 0)) {
                sb.append(key).append("=").append(URLEncoder.encode(value, "utf-8")).append("&");
            }
        }

        return String.valueOf(sb);
    }

    /**
     * Main
     *
     * @param args 進入參數
     */
    //Main
    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApplication.class, args);

//        agentPay();
//        notifyA();
//        getSignByMd5key(tradeNo);
//        String tradeNo, reTurnType, response, JStr;
//        JSONObject jsonObject;
//
//        reTurnType = "1";
//        tradeNo = "Test" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//        JStr = "{\"requestTime\":\"20201012160804\",\"bankCode\":\"201\",\"amount\":\"100000000\",\"payType\":\"18\",\"tradeNo\":\"" + tradeNo + "\",\"sign\":\"" + getSignByMd5key(tradeNo, reTurnType) + "\",\"notifyUrl\":\"https://pay.cxct.org/api/payBackProcess/498.do\",\"returnUrl\":\"http://mobile.cxct.org/static/html/rech_succes.html\",\"version\":\"1.6\",\"returnType\":\"" + reTurnType + "\",\"cid\":\"RCEF8971\"}";
//        jsonObject = JSONObject.parseObject(JStr);
//        response = String.valueOf(xFormPost("https://test-cashworkwp.epay365.co.uk/vietnamtest/gateway.do", jsonObject));
//        log.info("response:[{}]",response);
//
//        TreeMap<String, Object> paramsMap = new TreeMap<>();
//
//        paramsMap.put("MERCHANT_ID", "SDSADSA");
//
//        paramsMap.put("TRAN_CODE", Util.getTran_Code());
//
//        paramsMap.put("TRAN_AMT", "1000");
//
//        paramsMap.put("NULL_TEST", "");
//
//        paramsMap.forEach((k, v) -> log.info(k + ":" + v));

//        log.info(Util.mapToURLEncodeString(paramsMap));
//
//        JSONObject jsonObject1 = new JSONObject(paramsMap);
//
//        log.info("[{}]:[{}]", "字串拼接", jsonObjectKeySortToLinkString(jsonObject1, false));
//
//        getPublicKey(FILENAME);

//WTM API
        agentPay();
        payApply();
        H5();
        QR();
        quick();
        agentQuery();
        payQuery();
        countQuery();
    }

}




