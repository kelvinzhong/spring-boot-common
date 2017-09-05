/**
 * 
 */
package com.hippo.common.util.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

/**
 * 
 * 首选推荐此类进行加密。选择其他进行加密，有可能由于平台不一样导致随机数生成有误造成加解密失败
 * 
 */
public class DESUtils {

	private static Key key;

	private static final String KEY_STR = "life8165";

	static {
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(KEY_STR.getBytes());
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			generator.init(secureRandom);
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String encode64BASE(String bstr) {
		return new BASE64Encoder().encode(bstr.getBytes());
	}

	
	public static String decode64BASE(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    } 

	public static String encryptStrinng(String str) {
		try {
			BASE64Encoder base64en = new BASE64Encoder();
			byte[] strBytes = str.getBytes("UTF8");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptStrBytes = cipher.doFinal(strBytes);
			return base64en.encode(encryptStrBytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String decryptString(String str) {
		try {
			BASE64Decoder base64de = new BASE64Decoder();
			byte[] strBytes = base64de.decodeBuffer(str);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decryptStrBytes = cipher.doFinal(strBytes);
			return new String(decryptStrBytes, "UTF8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	public static void main(String[] args) {
		System.err.println(encode64BASE("XXXXXXXXXXXXXXXXX"));
		System.err.println(decode64BASE("WFhYWFhYWFhYWFhYWFhYWFg=").toString());

	}
}
