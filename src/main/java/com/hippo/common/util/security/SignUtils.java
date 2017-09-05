/**
 * 
 */
package com.hippo.common.util.security;

import com.hippo.common.util.general.Constants;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Map.Entry;

public class SignUtils {

	private static final Logger log = LoggerFactory.getLogger(SignUtils.class);

	private static final String HMAC_ALGORITHM = "HmacSHA256";
	private static final String CHARSET_UTF8 = "UTF-8";

	/**
	 * 请求签名。
	 * 
	 * @param requestHolder
	 *            所有字符型请求参数
	 * @param secret
	 *            签名密钥
	 * @param isHmac
	 *            是否为HMAC方式加密
	 * @return 签名
	 * @throws IOException
	 */
	// public static String sign(Map<String, String> sortedParams, String
	// secret) throws IOException {
	//
	// // 第二步：把所有参数名和参数值串在一起
	// StringBuilder query = new StringBuilder();
	// for (Entry<String, String> param : sortedParams.entrySet()) {
	// query.append(param.getKey()).append(param.getValue().replace(" ", ""));
	// }
	//
	// // log.debug("sign string = {}",query.toString());
	//
	// // 第三步：使用MD5/HMAC加密
	// byte[] bytes = encryptMD5(secret + query.toString() + secret);
	//
	// // 第四步：把二进制转化为大写的十六进制
	// return byte2hex(bytes);
	// }

	public static String sign(Map<String, String> signParams, String secret) throws Exception {

		Mac mac = Mac.getInstance(HMAC_ALGORITHM);
		SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(CHARSET_UTF8), mac.getAlgorithm());

		StringBuilder query = new StringBuilder();
		for (Entry<String, String> param : signParams.entrySet()) {
			query.append(param.getKey()).append(param.getValue());
		}
		log.debug("sign string {}", query.toString());
		mac.init(secretKey);
		byte[] hash = mac.doFinal(query.toString().getBytes(CHARSET_UTF8));

		return Base64.encodeBase64String(hash);
	}

	public static String sign(String data, String secret) throws Exception {
		Mac mac = Mac.getInstance(HMAC_ALGORITHM);
		SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(CHARSET_UTF8), mac.getAlgorithm());

		mac.init(secretKey);
		byte[] hash = mac.doFinal(data.getBytes(CHARSET_UTF8));

		return Base64.encodeBase64String(hash);
	}

	private static byte[] encryptHMAC(String data, String secret) throws IOException {
		byte[] bytes = null;
		try {
			SecretKey secretKey = new SecretKeySpec(secret.getBytes(Constants.CHARSET_UTF8), "HmacMD5");
			Mac mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
			bytes = mac.doFinal(data.getBytes(Constants.CHARSET_UTF8));
		} catch (GeneralSecurityException gse) {
			String msg = getStringFromException(gse);
			throw new IOException(msg);
		}
		return bytes;
	}

	private static String getStringFromException(Throwable e) {
		String result = "";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bos);
		e.printStackTrace(ps);
		try {
			result = bos.toString(Constants.CHARSET_UTF8);
		} catch (IOException ioe) {
		}
		return result;
	}

	private static byte[] encryptMD5(String data) throws IOException {
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			bytes = md.digest(data.getBytes(Constants.CHARSET_UTF8));
		} catch (GeneralSecurityException gse) {
			String msg = getStringFromException(gse);
			throw new IOException(msg);
		}
		return bytes;
	}

	private static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toUpperCase());
		}
		return sign.toString();
	}
}
