package com.example.demo;


import ch.qos.logback.classic.Logger;
import com.example.demo.util.AgentPayUtils;
import com.example.demo.util.MD5Utils;
import com.example.demo.util.PayApplyUtils;
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

    public static final String MERCHANT_ID = "请填入商户号";
    public static final String MD5KEY = "bd4fc1e7986cbb6c";
    public static final String FILENAME = "C:\\Users\\Peter\\Documents\\GithubRepo\\工具\\西瓜代付密钥.cer";
    public static final String AGENTPAY_URL = "https://api.watermelonpaytech.com/watermelon-gateway/back-pay/agent-pay?";
    public static final transient Logger log = (Logger) LoggerFactory.getLogger(DemoApplication.class);

    /**
     * WTM
     *
     * @throws Exception 例外處理
     */


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

//        AgentPayUtils.agentPay();
//        PayApplyUtils.payApply();
//        PayApplyUtils.H5();
//        PayApplyUtils.QR();
//        PayApplyUtils.quick();
//        AgentPayUtils.agentQuery();
//        PayApplyUtils.payQuery();
//        PayApplyUtils.countQuery();

        log.info(String.valueOf("您尾号3626卡2月24日11:37工商银行收入(他行汇入)600元，余额29,683.02元，对方户名：曾顺生，对方账户尾号：371".length()));
    }

}




