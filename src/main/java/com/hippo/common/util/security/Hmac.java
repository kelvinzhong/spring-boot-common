package com.hippo.common.util.security;
///**
// * 
// */
//package com.bloomlife.framework.security;
//
//import java.security.Security;
//import javax.crypto.KeyGenerator;
//import javax.crypto.Mac;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.util.encoders.Hex;
///**
// * MAC消息摘要组件
// * @author kongqz
// * */
//public class Hmac {
/////////////////////////////HmacMD5///////////////////////////////
//	/**
//	 * 初始化HmacMD5的密钥
//	 * @return byte[] 密钥
//	 * 
//	 * */
//	public static byte[] initHmacMD5Key() throws Exception{
//		//加入BouncyCastleProvider的支持
//		Security.addProvider(new BouncyCastleProvider());
//		//初始化KeyGenerator
//		KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacMD5");
//		//产生密钥
//		SecretKey secretKey=keyGenerator.generateKey();
//		//获取密钥
//		return secretKey.getEncoded();
//	}
//	/**
//	 * HmacMD5消息摘要
//	 * @param data 待做摘要处理的数据
//	 * @param key 密钥
//	 * @return  byte[] 消息摘要
//	 * */
//	public static byte[] encodeHmacMD5(byte[] data,byte[] key) throws Exception{
//		//加入BouncyCastleProvider的支持
//		Security.addProvider(new BouncyCastleProvider());
//		//还原密钥，因为密钥是以byte形式为消息传递算法所拥有
//		SecretKey secretKey=new SecretKeySpec(key,"HmacMD5");
//		//实例化Mac
//		Mac mac=Mac.getInstance(secretKey.getAlgorithm());
//		//初始化Mac
//		mac.init(secretKey);
//		//执行消息摘要处理
//		return mac.doFinal(data);
//	}
//	/**
//	 * HmacMD2Hex消息摘要
//	 * @param data 待做消息摘要处理的数据
//	 * @param String 密钥
//	 * @return byte[] 消息摘要
//	 * */
//	public static String encodeHmacMD5Hex(byte[] data,byte[] key) throws Exception{
//		//执行消息摘要处理
//		byte[] b=encodeHmacMD5(data,key);
//		//做十六进制转换
//		return new String(Hex.encode(b));
//	}
//	
/////////////////////////////////HmacSHA1//////////////////////////////////
//	/**
//	 * 初始化HmacSHA1的密钥
//	 * @return byte[] 密钥
//	 * 
//	 * */
//	public static byte[] initHmacSHAKey() throws Exception{
//		//初始化KeyGenerator
//		KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA1");
//		//产生密钥
//		SecretKey secretKey=keyGenerator.generateKey();
//		//获取密钥
//		return secretKey.getEncoded();
//	}
//	/**
//	 * HmacSHA1消息摘要
//	 * @param data 待做摘要处理的数据
//	 * @param key 密钥
//	 * @return  byte[] 消息摘要
//	 * */
//	public static byte[] encodeHmacSHA(byte[] data,byte[] key) throws Exception{
//		//还原密钥，因为密钥是以byte形式为消息传递算法所拥有
//		SecretKey secretKey=new SecretKeySpec(key,"HmacSHA1");
//		//实例化Mac
//		Mac mac=Mac.getInstance(secretKey.getAlgorithm());
//		//初始化Mac
//		mac.init(secretKey);
//		//执行消息摘要处理
//		return mac.doFinal(data);
//	}
//	
/////////////////////////////////HmacSHA256//////////////////////////////////
//	/**
//	 * 初始化HmacSHA256的密钥
//	 * @return byte[] 密钥
//	 * 
//	 * */
//	public static byte[] initHmacSHA256Key() throws Exception{
//		Security.addProvider(new BouncyCastleProvider());
//		//初始化KeyGenerator
//		KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA256");
//		//产生密钥
//		SecretKey secretKey=keyGenerator.generateKey();
//		//获取密钥
//		return secretKey.getEncoded();
//	}
//	/**
//	 * HmacSHA256消息摘要
//	 * @param data 待做摘要处理的数据
//	 * @param key 密钥
//	 * @return  byte[] 消息摘要
//	 * */
//	public static byte[] encodeHmacSHA256(byte[] data,byte[] key) throws Exception{
//		//加入BouncyCastleProvider的支持
//		Security.addProvider(new BouncyCastleProvider());
//		//还原密钥，因为密钥是以byte形式为消息传递算法所拥有
//		SecretKey secretKey=new SecretKeySpec(key,"HmacSHA256");
//		//实例化Mac
//		Mac mac=Mac.getInstance(secretKey.getAlgorithm());
//		//初始化Mac
//		mac.init(secretKey);
//		//执行消息摘要处理
//		return mac.doFinal(data);
//	}
//	
/////////////////////////////////HmacSHA384//////////////////////////////////
//	/**
//	 * 初始化HmacSHA384的密钥
//	 * @return byte[] 密钥
//	 * 
//	 * */
//	public static byte[] initHmacSHA384Key() throws Exception{
//		Security.addProvider(new BouncyCastleProvider());
//		//初始化KeyGenerator
//		KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA384");
//		//产生密钥
//		SecretKey secretKey=keyGenerator.generateKey();
//		//获取密钥
//		return secretKey.getEncoded();
//	}
//	/**
//	 * HmacSHA384消息摘要
//	 * @param data 待做摘要处理的数据
//	 * @param key 密钥
//	 * @return  byte[] 消息摘要
//	 * */
//	public static byte[] encodeHmacSHA384(byte[] data,byte[] key) throws Exception{
//		Security.addProvider(new BouncyCastleProvider());
//		//还原密钥，因为密钥是以byte形式为消息传递算法所拥有
//		SecretKey secretKey=new SecretKeySpec(key,"HmacSHA384");
//		//实例化Mac
//		Mac mac=Mac.getInstance(secretKey.getAlgorithm());
//		//初始化Mac
//		mac.init(secretKey);
//		//执行消息摘要处理
//		return mac.doFinal(data);
//	}
//	
/////////////////////////////////HmacSHA512//////////////////////////////////
//	/**
//	 * 初始化HmacSHA512的密钥
//	 * @return byte[] 密钥
//	 * 
//	 * */
//	public static byte[] initHmacSHA512Key() throws Exception{
//		//初始化KeyGenerator
//		KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA512");
//		//产生密钥
//		SecretKey secretKey=keyGenerator.generateKey();
//		//获取密钥
//		return secretKey.getEncoded();
//	}
//	/**
//	 * HmacSHA512消息摘要
//	 * @param data 待做摘要处理的数据
//	 * @param key 密钥
//	 * @return  byte[] 消息摘要
//	 * */
//	public static byte[] encodeHmacSHA512(byte[] data,byte[] key) throws Exception{
//		//还原密钥，因为密钥是以byte形式为消息传递算法所拥有
//		SecretKey secretKey=new SecretKeySpec(key,"HmacSHA512");
//		//实例化Mac
//		Mac mac=Mac.getInstance(secretKey.getAlgorithm());
//		//初始化Mac
//		mac.init(secretKey);
//		//执行消息摘要处理
//		return mac.doFinal(data);
//	}
/////////////////////////////////HmacMD2-BouncyCastle才支持的实现//////////////////////////////////
//	/**
//	 * 初始化HmacMD2的密钥
//	 * @return byte[] 密钥
//	 * */
//	public static byte[] initHmacMD2Key() throws Exception{
//		
//		//加入BouncyCastleProvider的支持
//		Security.addProvider(new BouncyCastleProvider());
//		//初始化KeyGenerator
//		KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacMD2");
//		//产生密钥
//		SecretKey secretKey=keyGenerator.generateKey();
//		//获取密钥
//		return secretKey.getEncoded();
//	}
//	/**
//	 * HmacMD2消息摘要
//	 * @param data 待做摘要处理的数据
//	 * @param key 密钥
//	 * @return  byte[] 消息摘要
//	 * */
//	public static byte[] encodeHmacMD2(byte[] data,byte[] key) throws Exception{
//		//加入BouncyCastleProvider的支持
//		Security.addProvider(new BouncyCastleProvider());
//		//还原密钥，因为密钥是以byte形式为消息传递算法所拥有
//		SecretKey secretKey=new SecretKeySpec(key,"HmacMD2");
//		//实例化Mac
//		Mac mac=Mac.getInstance(secretKey.getAlgorithm());
//		//初始化Mac
//		mac.init(secretKey);
//		//执行消息摘要处理
//		return mac.doFinal(data);
//	}
//	/**
//	 * HmacMD2Hex消息摘要
//	 * @param data 待做消息摘要处理的数据
//	 * @param String 密钥
//	 * @return byte[] 消息摘要
//	 * */
//	public static String encodeHmacMD2Hex(byte[] data,byte[] key) throws Exception{
//		//执行消息摘要处理
//		byte[] b=encodeHmacMD2(data,key);
//		//做十六进制转换
//		return new String(Hex.encode(b));
//	}
//	
/////////////////////////////////HmacMD4-BouncyCastle才支持的实现//////////////////////////////////
//	/**
//	 * 初始化HmacMD2的密钥
//	 * @return byte[] 密钥
//	 * */
//	public static byte[] initHmacMD4Key() throws Exception{
//		
//		//加入BouncyCastleProvider的支持
//		Security.addProvider(new BouncyCastleProvider());
//		//初始化KeyGenerator
//		KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacMD4");
//		//产生密钥
//		SecretKey secretKey=keyGenerator.generateKey();
//		//获取密钥
//		return secretKey.getEncoded();
//	}
//	/**
//	 * HmacMD4消息摘要
//	 * @param data 待做摘要处理的数据
//	 * @param key 密钥
//	 * @return  byte[] 消息摘要
//	 * */
//	public static byte[] encodeHmacMD4(byte[] data,byte[] key) throws Exception{
//		//加入BouncyCastleProvider的支持
//		Security.addProvider(new BouncyCastleProvider());
//		//还原密钥，因为密钥是以byte形式为消息传递算法所拥有
//		SecretKey secretKey=new SecretKeySpec(key,"HmacMD4");
//		//实例化Mac
//		Mac mac=Mac.getInstance(secretKey.getAlgorithm());
//		//初始化Mac
//		mac.init(secretKey);
//		//执行消息摘要处理
//		return mac.doFinal(data);
//	}
//	/**
//	 * HmacMD4Hex消息摘要
//	 * @param data 待做消息摘要处理的数据
//	 * @param String 密钥
//	 * @return byte[] 消息摘要
//	 * */
//	public static String encodeHmacMD4Hex(byte[] data,byte[] key) throws Exception{
//		//执行消息摘要处理
//		byte[] b=encodeHmacMD4(data,key);
//		//做十六进制转换
//		return new String(Hex.encode(b));
//	}
/////////////////////////////////HmacSHA224-BouncyCastle才支持的实现//////////////////////////////////
//	/**
//	 * 初始化HmacSHA224的密钥
//	 * @return byte[] 密钥
//	 * */
//	public static byte[] initHmacSHA224Key() throws Exception{
//		
//		//加入BouncyCastleProvider的支持
//		Security.addProvider(new BouncyCastleProvider());
//		//初始化KeyGenerator
//		KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA224");
//		//产生密钥
//		SecretKey secretKey=keyGenerator.generateKey();
//		//获取密钥
//		return secretKey.getEncoded();
//	}
//	/**
//	 * HmacSHA224消息摘要
//	 * @param data 待做摘要处理的数据
//	 * @param key 密钥
//	 * @return  byte[] 消息摘要
//	 * */
//	public static byte[] encodeHmacSHA224(byte[] data,byte[] key) throws Exception{
//		//加入BouncyCastleProvider的支持
//		Security.addProvider(new BouncyCastleProvider());
//		//还原密钥，因为密钥是以byte形式为消息传递算法所拥有
//		SecretKey secretKey=new SecretKeySpec(key,"HmacSHA224");
//		//实例化Mac
//		Mac mac=Mac.getInstance(secretKey.getAlgorithm());
//		//初始化Mac
//		mac.init(secretKey);
//		//执行消息摘要处理
//		return mac.doFinal(data);
//	}
//	/**
//	 * HmacSHA224Hex消息摘要
//	 * @param data 待做消息摘要处理的数据
//	 * @param String 密钥
//	 * @return byte[] 消息摘要
//	 * */
//	public static String encodeHmacSHA224Hex(byte[] data,byte[] key) throws Exception{
//		//执行消息摘要处理
//		byte[] b=encodeHmacSHA224(data,key);
//		//做十六进制转换
//		return new String(Hex.encode(b));
//	}
//	/**
//	 * 进行相关的摘要算法的处理展示
//	 * @throws Exception 
//	 * **/
//	public static void main(String[] args) throws Exception {
////		String str="123000000000090989999999999999999999999999999999999999999999999999999999999999999999111" +
////				"23000000000090989999999999999999999999999999999999999999999999999999999999999999999" +
////				"23000000000090989999999999999999999999999999999999999999999999999999999999999999999" +
////				"23000000000090989999999999999999999999999999999999999999999999999999999999999999999" +
////				"23000000000090989999999999999999999999999999999999999999999999999999999999999999999" +
////				"23000000000090989999999999999999999999999999999999999999999999999999999999999999999" +
////				"23000000000090989999999999999999999999999999999999999999999999999999999999999999999" +
////				"23000000000090989999999999999999999999999999999999999999999999999999999999999999999" +
////				"23000000000090989999999999999999999999999999999999999999999999999999999999999999999" +
////				"23000000000090989999999999999999999999999999999999999999999999999999999999999999999" +
////				"23000000000090989999999999999999999999999999999999999999999999999999999999999999999" +
////				"23000000000090989999999999999999999999999999999999999999999999999999999999999999999" +
////				"23000000000090989999999999999999999999999999999999999999999999999999999999999999999" +
////				"23000000000090989999999999999999999999999999999999999999999999999999999999999999999" +
////				"23000000000090989999999999999999999999999999999999999999999999999999999999999999999" +
////				"23000000000090989999999999999999999999999999999999999999999999999999999999999999999" +
////				"230000000000909899999999999999999999999999999999999999999999999999999999999999999991";
////		//初始化密钥
////		byte[] key1=Hmac.initHmacMD5Key();
////		//获取摘要信息
////		byte[] data1=Hmac.encodeHmacMD5(str.getBytes(), key1);
////		String datahex1=new String(Hex.encode(data1));
////		
////		System.out.println("原文："+str);
////		System.out.println();
////		
////		System.out.println("Bouncycastle HmacMD5的密钥内容和长度:"+key1.toString()+"--"+key1.length);
////		System.out.println("Bouncycastle HmacMD5算法摘要："+data1.toString());
////		System.out.println("Bouncycastle HmacMD5算法摘要HEX："+datahex1.toString());
////		System.out.println();
////		
////		//初始化密钥
////		byte[] key2=Hmac.initHmacSHA256Key();
////		//获取摘要信息
////		byte[] data2=Hmac.encodeHmacSHA256(str.getBytes(), key2);
////		String datahex2=new String(Hex.encode(data2));
////		System.out.println("Bouncycastle HmacSHA256的密钥:"+key2.toString());
////		System.out.println("Bouncycastle HmacSHA256算法摘要："+data2.toString());
////		System.out.println("Bouncycastle HmacSHA256算法摘要HEX："+datahex2);
////		System.out.println();
////		
////		
////		//初始化密钥
////		byte[] key3=Hmac.initHmacSHAKey();
////		//获取摘要信息
////		byte[] data3=Hmac.encodeHmacSHA(str.getBytes(), key3);
////		String datahex3=new String(Hex.encode(data3));
////		System.out.println("Bouncycastle HmacSHA1的密钥:"+key3.toString());
////		System.out.println("Bouncycastle HmacSHA1算法摘要："+data3.toString());
////		System.out.println("Bouncycastle HmacSHA1算法摘要HEX："+datahex3);
////		System.out.println();
////		
////		
////		//初始化密钥
////		byte[] key4=Hmac.initHmacSHA384Key();
////		
////		//获取摘要信息
////		byte[] data4=Hmac.encodeHmacSHA384(str.getBytes(), key4);
////		String datahex4=new String(Hex.encode(data4));
////		System.out.println("Bouncycastle HmacSHA384的密钥:"+key4.toString());
////		System.out.println("Bouncycastle HmacSHA384算法摘要："+data4.toString());
////		System.out.println("Bouncycastle HmacSHA384算法摘要HEX："+datahex4);
////		System.out.println();
////		
////		
////		//初始化密钥
////		byte[] key5=Hmac.initHmacSHA512Key();
////		//获取摘要信息
////		byte[] data5=Hmac.encodeHmacSHA512(str.getBytes(), key5);
////		System.out.println("HmacSHA512的密钥:"+key5.toString());
////		System.out.println("HmacSHA512算法摘要："+data5.toString());
////		System.out.println();
////		
////		System.out.println("================以下的算法支持是bouncycastle支持的算法，sun java6不支持=======================");
////		//初始化密钥
////		byte[] key6=Hmac.initHmacMD2Key();
////		//获取摘要信息
////		byte[] data6=Hmac.encodeHmacMD2(str.getBytes(), key6);
////		String datahex6=Hmac.encodeHmacMD2Hex(str.getBytes(), key6);
////		System.out.println("Bouncycastle HmacMD2的密钥:"+key6.toString());
////		System.out.println("Bouncycastle HmacMD2算法摘要："+data6.toString());
////		System.out.println("Bouncycastle HmacMD2Hex算法摘要："+datahex6.toString());
////		System.out.println();
////		
////		//初始化密钥
////		byte[] key7=Hmac.initHmacMD4Key();
////		//获取摘要信息
////		byte[] data7=Hmac.encodeHmacMD4(str.getBytes(), key7);
////		String datahex7=Hmac.encodeHmacMD4Hex(str.getBytes(), key7);
////		System.out.println("Bouncycastle HmacMD4的密钥:"+key7.toString());
////		System.out.println("Bouncycastle HmacMD4算法摘要："+data7.toString());
////		System.out.println("Bouncycastle HmacMD4Hex算法摘要："+datahex7.toString());
////		System.out.println();
////		
////		//初始化密钥
////		byte[] key8=Hmac.initHmacSHA224Key();
////		//获取摘要信息
////		byte[] data8=Hmac.encodeHmacSHA224(str.getBytes(), key8);
////		String datahex8=Hmac.encodeHmacSHA224Hex(str.getBytes(), key8);
////		System.out.println("Bouncycastle HmacSHA224的密钥:"+key8.toString());
////		System.out.println("Bouncycastle HmacSHA224算法摘要："+data8.toString());
////		System.out.println("Bouncycastle HmacSHA224算法摘要："+datahex8.toString());
////		System.out.println();
//	}
//}
