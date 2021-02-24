package com.example.demo.util;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.DemoApplication;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

public class AgentPayUtils {
    private static final String MERCHANT_ID = DemoApplication.MERCHANT_ID;
    private static final String MD5KEY = DemoApplication.MD5KEY;
    private static final String FILENAME = DemoApplication.FILENAME;
    private static final String AGENTPAY_URL = DemoApplication.AGENTPAY_URL;
    private static final Logger log= DemoApplication.log;

    //agentPay
    public static void agentPay() throws Exception {
        TreeMap<String, Object> paramsMap = new TreeMap<>();

        paramsMap.put("MERCHANT_ID", "WTM000859PENSTONE2");

        paramsMap.put("TRAN_CODE", Util.getTran_Code());

        paramsMap.put("TRAN_AMT", "1000");

        //敏感资讯须经过RSA加密
        //paramsMap.put("NAME", Base64.getEncoder().encodeToString(encrypt("demoName".getBytes("UTF-8"), getStringToPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgbYQRjd8wJ6JM2bkbjPpV0vUAoDzGMN2VkN6VV6Tg2yzK6VMe47mJMlZnDgVvF4eI1+OaRaRKPXY3itwRBtT5K82k7e7GDSs+jvGvKbw7XNxDZIl+tq/ioMYrh1SlaAmUkjuVikLKI0+AxlJvoIBlzzaXxjEyV8xX+i9KoPBSPKYx8fDMZvpgiKJKBqJaQgHLO+iVN204I7/1CfoliWCHk4ronA4vd921lskcLZ9Uz4utvQRPyZETNg8+4YLUEkIPGFeSayloJ5YOBiHhxIe3FMHUIcafX36Nu6AE7A7QsuGH3CzdCJv4ib2c503q/rA32oMc0Pp0llMbu7Xd1V8ZQIDAQAB"))));
        paramsMap.put("NAME", RSAUtils.RSAEncryptByKey("test", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgbYQRjd8wJ6JM2bkbjPpV0vUAoDzGMN2VkN6VV6Tg2yzK6VMe47mJMlZnDgVvF4eI1+OaRaRKPXY3itwRBtT5K82k7e7GDSs+jvGvKbw7XNxDZIl+tq/ioMYrh1SlaAmUkjuVikLKI0+AxlJvoIBlzzaXxjEyV8xX+i9KoPBSPKYx8fDMZvpgiKJKBqJaQgHLO+iVN204I7/1CfoliWCHk4ronA4vd921lskcLZ9Uz4utvQRPyZETNg8+4YLUEkIPGFeSayloJ5YOBiHhxIe3FMHUIcafX36Nu6AE7A7QsuGH3CzdCJv4ib2c503q/rA32oMc0Pp0llMbu7Xd1V8ZQIDAQAB"));
        //System.out.println(paramsMap.get("NAME"));

        paramsMap.put("BANK_NO", RSAUtils.RSAEncrypt("123456"));

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


        PostUtils.xFormPost("https://api2.watermelonpaytech.com/watermelon-gateway/back-pay/agent-pay", new JSONObject(paramsMap));


        System.out.println("========================================================================================================================================================================================");

    }

    //agent-query
    public static void agentQuery() throws Exception {
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


        PostUtils.xFormPost("https://api2.watermelonpaytech.com/watermelon-gateway/back-pay/agent-pay-query", new JSONObject(paramsMap));

        System.out.println("========================================================================================================================================================================================");

    }
}
