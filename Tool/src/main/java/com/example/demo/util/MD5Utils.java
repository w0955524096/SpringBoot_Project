package com.example.demo.util;

import ch.qos.logback.classic.Logger;
import com.example.demo.DemoApplication;

import java.security.MessageDigest;


public class MD5Utils {
	private static final String MERCHANT_ID = DemoApplication.MERCHANT_ID;
	private static final String MD5KEY = DemoApplication.MD5KEY;
	private static final String FILENAME = DemoApplication.FILENAME;
	private static final String AGENTPAY_URL = DemoApplication.AGENTPAY_URL;
	private static final Logger log= DemoApplication.log;
	// MD5加密
	public static String md5(String paramSrc, String encodeType) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = paramSrc.getBytes(encodeType);
			// 获得MD5摘要算法的MessageDigest对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字结更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转成十六进制的字串符
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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



}
