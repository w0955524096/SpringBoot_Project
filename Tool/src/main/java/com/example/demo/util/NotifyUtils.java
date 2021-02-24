package com.example.demo.util;

import ch.qos.logback.classic.Logger;
import com.example.demo.DemoApplication;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class NotifyUtils {
    private static final String MERCHANT_ID = DemoApplication.MERCHANT_ID;
    private static final String MD5KEY = DemoApplication.MD5KEY;
    private static final String FILENAME = DemoApplication.FILENAME;
    private static final String AGENTPAY_URL = DemoApplication.AGENTPAY_URL;
    private static final Logger log= DemoApplication.log;

    /**
     * Verify
     *
     * @throws Exception 錯誤
     */


    //Verify256
    private static boolean verify256(byte[] data, PublicKey publicKey, byte[] sign) throws Exception {
        // 解密公钥

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(sign);

    }

    //NotifyA
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

        PublicKey RSAPubKey = RSAUtils.GetStringToPublicKey(publicKey);
        Boolean result = verify256(paramsSrc.getBytes(StandardCharsets.UTF_8), RSAPubKey, Base64.getDecoder().decode(signature));
        System.out.println(result);

    }

}
