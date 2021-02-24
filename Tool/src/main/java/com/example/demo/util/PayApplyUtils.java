package com.example.demo.util;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.DemoApplication;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

public class PayApplyUtils {
    private static final String MERCHANT_ID = DemoApplication.MERCHANT_ID;
    private static final String MD5KEY = DemoApplication.MD5KEY;
    private static final String FILENAME = DemoApplication.FILENAME;
    private static final String AGENTPAY_URL = DemoApplication.AGENTPAY_URL;
    private static final Logger log= DemoApplication.log;


    //payApply
    public static void payApply() throws Exception {
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


        PostUtils.xFormPost("https://api2.watermelonpaytech.com/watermelon-gateway/front-pay/ebank-pay.htm", new JSONObject(paramsMap));

        System.out.println("========================================================================================================================================================================================");

    }

    //H5
    public static void H5() throws Exception {
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


        PostUtils.xFormPost("https://api2.watermelonpaytech.com/watermelon-gateway/front-pay/h5-pay.htm", new JSONObject(paramsMap));

        System.out.println("========================================================================================================================================================================================");

    }

    //QR
    public static void QR() throws Exception {
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


        PostUtils.xFormPost("https://api2.watermelonpaytech.com/watermelon-gateway/front-pay/qr-pay.htm", new JSONObject(paramsMap));

        System.out.println("========================================================================================================================================================================================");

    }

    //quick
    public static void quick() throws Exception {
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


        PostUtils.xFormPost("https://api2.watermelonpaytech.com/watermelon-gateway/front-pay/quick-pay.htm", new JSONObject(paramsMap));

        System.out.println("========================================================================================================================================================================================");

    }

    //pay-query
    public static void payQuery() throws Exception {
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


        PostUtils.xFormPost("https://api2.watermelonpaytech.com/watermelon-gateway/back-pay/qr-pay-query", new JSONObject(paramsMap));

        System.out.println("========================================================================================================================================================================================");

    }

    //count-query
    public static void countQuery() throws Exception {
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


        PostUtils.xFormPost("https://api2.watermelonpaytech.com/watermelon-gateway/back-pay/count-query", new JSONObject(paramsMap));

        System.out.println("========================================================================================================================================================================================");

    }

}
