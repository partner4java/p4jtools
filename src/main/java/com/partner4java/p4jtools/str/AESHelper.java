package com.partner4java.p4jtools.str;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Base64Utils;

import com.partner4java.p4jtools.Config;

/**
 * Advanced Encryption Standard，AES
 * 
 * @author 王昌龙
 * 
 */
public class AESHelper {
	private static Log logger = LogFactory.getLog(AESHelper.class);

	/** 密钥 */
	public static final String NAME = "name";
	public static final String PHONE = "phone";
	public static final String ADDRESS = "address";
	public static final String REMARK = "remark";
	public static final String REMARKS = "remarks";
	public static final String DES = "des";
	public static final String BACK = "back";
	public static final String PAYMENT = "payment";
	public static final String CARD = "card";
	public static final String HEAD = "head";
	public static final String HOME = "home";

	// 生成Cipher对象,指定其支持的DES算法
	private static Map<String, Cipher> enCis = new HashMap<String, Cipher>();
	private static Map<String, Cipher> deCis = new HashMap<String, Cipher>();
	private static Map<String, SecretKey> enSecretKeys = new HashMap<String, SecretKey>();
	private static Map<String, SecretKey> deSecretKeys = new HashMap<String, SecretKey>();

	/**
	 * 生成密钥
	 * 
	 * @param filePath
	 *            密钥保存的路径和名称
	 */
	@SuppressWarnings("restriction")
	public static void generateKey(String filePath) {
		filePath = getFullPath(filePath);
		ObjectOutputStream outputStream = null;
		try {
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			// 实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			// 生成密钥
			SecretKey deskey = keygen.generateKey();
			outputStream = new ObjectOutputStream(new FileOutputStream(filePath));
			outputStream.writeObject(deskey);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static String getFullPath(String filePath) {
		if (filePath.contains(".aes")) {
			return filePath;
		}
		return Config.value("aes_path") + filePath + ".aes";
	}

	/**
	 * 获取密钥
	 * 
	 * @param filePath
	 *            密钥文件路径
	 * @return
	 */
	public static SecretKey getEnSecretKey(String filePath) {
		filePath = getFullPath(filePath);
		SecretKey secretKey = enSecretKeys.get(filePath);
		if (secretKey != null) {
			return secretKey;
		}
		ObjectInputStream inputStream = null;
		try {
			inputStream = new ObjectInputStream(new FileInputStream(filePath));
			secretKey = (SecretKey) inputStream.readObject();
			enSecretKeys.put(filePath, secretKey);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return secretKey;
	}

	/**
	 * 获取密钥
	 * 
	 * @param filePath
	 *            密钥文件路径
	 * @return
	 */
	public static SecretKey getDeSecretKey(String filePath) {
		filePath = getFullPath(filePath);
		SecretKey secretKey = deSecretKeys.get(filePath);
		if (secretKey != null) {
			return secretKey;
		}
		ObjectInputStream inputStream = null;
		try {
			inputStream = new ObjectInputStream(new FileInputStream(filePath));
			secretKey = (SecretKey) inputStream.readObject();
			deSecretKeys.put(filePath, secretKey);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return secretKey;
	}

	/**
	 * 加密
	 * 
	 * @param key
	 *            密钥
	 * @param str
	 *            要加密的文本
	 * @return
	 */
	public static String encrytor(String key, String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		key = getFullPath(key);
		synchronized (key) {
			try {

				byte[] src = str.getBytes();
				// 加密，结果保存进cipherByte
				byte[] cipherByte = getEnCi(key).doFinal(src);
				return Base64Utils.encodeToString(cipherByte);
			} catch (Exception e) {
				logger.error("加密错误：key-" + key + ";str-" + str);
				e.printStackTrace();
			}
			return null;
		}
	}

	private static Cipher getEnCi(String filePath) {
		Cipher ci = enCis.get(filePath);
		if (ci != null) {
			return ci;
		}
		try {
			ci = Cipher.getInstance("AES");
			ci.init(Cipher.ENCRYPT_MODE, getEnSecretKey(filePath));
			enCis.put(filePath, ci);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ci;
	}

	/**
	 * 解密
	 * 
	 * @param key
	 *            密钥
	 * @param str
	 *            要解密的文本
	 * @return
	 */
	public static String decryptor(String key, String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		key = getFullPath(key);
		synchronized (key) {
			try {
				byte[] src = Base64Utils.decodeFromString(str);
				// 加密，结果保存进cipherByte
				byte[] cipherByte = getDeCi(key).doFinal(src);
				return new String(cipherByte);
			} catch (Exception e) {
				logger.error("解密错误：key-" + key + ";str-" + str);
				e.printStackTrace();
				deCis.remove(key);
			}
			return null;
		}
	}

	private static Cipher getDeCi(String filePath) {
		Cipher ci = deCis.get(filePath);
		if (ci != null) {
			return ci;
		}
		try {
			ci = Cipher.getInstance("AES");
			ci.init(Cipher.DECRYPT_MODE, getDeSecretKey(filePath));
			deCis.put(filePath, ci);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ci;
	}
}
