package com.example.demo.util;

import wiremock.org.apache.commons.codec.binary.Hex;

import java.net.URLEncoder;
import java.util.Map;

import static wiremock.org.apache.commons.codec.digest.DigestUtils.md5;

public class Util {

    public static String mapToString(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            String value = (String) map.get(key);
            if (checkObject(value)) {
                sb.append(key + "=" + value + "&");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    public static String mapToURLEncodeString(Map<String, Object> map) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            String value = (String) map.get(key);
            if (checkObject(value)) {
                sb.append(key).append("=").append(URLEncoder.encode(value, "utf-8")).append("&");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    public static boolean checkObject(String object) {
        boolean isNotNull = true;
        if (object == null || object.trim().length() == 0) {
            isNotNull = false;
        }
        return isNotNull;
    }

    public static String getTran_Code() {
        StringBuffer sb = new StringBuffer();
        int[] A = new int[12];
        for (int i = 0; i < 12; i++) {
            if (i < 1) { // 前 1 放数字
                A[i] = (int) ((Math.random() * 10) + 48);
            } else if (i < 3) { // 中间 2 位放大写英文
                A[i] = (int) (((Math.random() * 26) + 65));
            } else { // 后 1 位放小写英文
                A[i] = ((int) ((Math.random() * 26) + 97));
            }
            sb.append((char) A[i]);
        }
        return sb.toString();
    }

    public static String md5Hex(String data) {
        return Hex.encodeHexString(md5(data));
    }
}
