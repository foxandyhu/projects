package com.bfly.industry.util;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 加密安全类
 * @author 胡礼波
 * 2012-5-16 下午03:18:01
 */
public class SecurityUtil {

	/**
	 * Base64加密
	 * @author 胡礼波
	 * 2012-5-16 下午03:19:11
	 * @param data
	 * @return
	 * @throws IOException 
	 */
	public static String base64Encoding(byte[] data) throws IOException
	{
		Base64 encoder=new Base64();
		return encoder.encodeToString(data);
	}
	
	/**
	 * * Base64加密
	 * @author 胡礼波
	 * 2014-5-17 下午4:15:58
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static byte[] base64Decoding(String data) throws IOException
	{
		Base64 decoder=new Base64();
		return decoder.decode(data);
	}
	
	/**
	 * AES数据加密
	 * @author 胡礼波
	 * 2014-5-17 下午4:16:45
	 * @param data
	 * @return
	 */
	public static String AESEncoding(String data,String key)
	{
		if(StringUtils.isBlank(data))
		{
			return null;
		}
		try 
		{
			KeyGenerator kgen =KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(key.getBytes()));
			
			SecretKey skey=kgen.generateKey();
            byte[] enCodeFormat = skey.getEncoded();  
            SecretKeySpec skeySpec = new SecretKeySpec(enCodeFormat, "AES");
            
			Cipher cipher = Cipher.getInstance("AES");  
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);// 初始化
            
            byte[] byteContent = data.getBytes("utf-8");
            byte bytes[]=cipher.doFinal(byteContent);
            String result =new String(Hex.encodeHex(bytes));
            return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * AES数据解密
	 * @author 胡礼波
	 * 2014-5-17 下午4:17:23
	 * @param data
	 * @return
	 */
	public static byte[] AESDecoding(String data,String key)
	{
		try 
		{
			KeyGenerator kgen = KeyGenerator.getInstance("AES");  
            kgen.init(128, new SecureRandom(key.getBytes()));  
            
            SecretKey secretKey = kgen.generateKey();  
            byte[] enCodeFormat = secretKey.getEncoded();  
            SecretKeySpec skey = new SecretKeySpec(enCodeFormat, "AES");              
            
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
            cipher.init(Cipher.DECRYPT_MODE, skey);// 初始化  
            
            byte bytes[]=Hex.decodeHex(data.toCharArray());
            byte[] result = cipher.doFinal(bytes);  
            return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		String data="13522345634";
		String key="12345678";
		
		String s1=AESEncoding(data, key);
		System.out.println(s1);
		
		byte bs[]=AESDecoding(s1, key);
		System.out.println(new String(bs));
	}
	
	/**
	 * SHA1加密
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年4月18日 上午10:45:17
	 * @param data
	 * @return
	 */
	public static String SHA1(String data)
	{
		return DigestUtils.sha1Hex(data);
	}
	
	/**
	 * MD5加密后得到小写的字符串
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年8月4日 上午9:31:28
	 * @param data
	 * @return
	 */
	public static String md5(String data)
	{
		return DigestUtils.md5Hex(data);
	}
}
