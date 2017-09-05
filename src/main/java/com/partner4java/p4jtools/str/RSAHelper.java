package com.partner4java.p4jtools.str;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * RSA公开密钥密码体制。所谓的公开密钥密码体制就是使用不同的加密密钥与解密密钥，是一种“由已知加密密钥推导出解密密钥在计算上是不可行的”密码体制。
 * <br/>
 * 在公开密钥密码体制中，加密密钥（即公开密钥）PK是公开信息，而解密密钥（即秘密密钥）SK是需要保密的。加密算法E和解密算法D也都是公开的。
 * 虽然解密密钥SK是由公开密钥PK决定的，但却不能根据PK计算出SK。
 * 
 * @author 王昌龙
 *
 */
public class RSAHelper {
	/** 从map中获取公钥的key */
	public static final int PUBLIC_KEY = 1;

	/** 从map中获取私钥的key */
	public static final int PRIVATE_KEY = 2;

	/** 加密算法RSA */
	public static final String KEY_ALGORITHM = "RSA";

	/** 签名算法 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/** RSA最大加密明文大小 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/** RSA最大解密密文大小 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * 获取对称密钥<br/>
	 * 注意：本方法性能较低
	 * 
	 * @return
	 */
	public static Map<Integer, String> getKey() {
		Map<Integer, String> keyMap = new HashMap<Integer, String>();

		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();

			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

			keyMap.put(PUBLIC_KEY, byte64ToStr(publicKey.getEncoded()));
			keyMap.put(PRIVATE_KEY, byte64ToStr(privateKey.getEncoded()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keyMap;
	}

	/**
	 * 公钥加密
	 * 
	 * @param source
	 *            需要加密的字符串
	 * @param publicKey
	 *            公钥
	 * @return
	 */
	public static String encryptByPublic(String source, String publicKey) {
		ByteArrayOutputStream out = null;
		try {
			if (source != null && !"".equals(source)) {
				byte[] data = source.getBytes("UTF-8");
				KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
				byte[] keyBytes = strToByte64(publicKey);
				X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
				Key publicK = keyFactory.generatePublic(x509KeySpec);

				// 对数据加密
				Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
				cipher.init(Cipher.ENCRYPT_MODE, publicK);
				int inputLen = data.length;
				out = new ByteArrayOutputStream();
				int offSet = 0;
				byte[] cache;
				int i = 0;
				// 对数据分段加密
				while (inputLen - offSet > 0) {
					if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
						cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
					} else {
						cache = cipher.doFinal(data, offSet, inputLen - offSet);
					}
					out.write(cache, 0, cache.length);
					i++;
					offSet = i * MAX_ENCRYPT_BLOCK;
				}
				return byte64ToStr(out.toByteArray());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 私钥加密
	 * 
	 * @param source
	 *            需加密字符串
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static String encryptByPrivate(String source, String privateKey) {
		ByteArrayOutputStream out = null;
		try {
			if (source != null && !"".equals(source)) {
				byte[] data = source.getBytes("UTF-8");
				KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

				byte[] keyBytes = strToByte64(privateKey);
				PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
				Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
				Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
				cipher.init(Cipher.ENCRYPT_MODE, privateK);
				int inputLen = data.length;
				out = new ByteArrayOutputStream();
				int offSet = 0;
				byte[] cache;
				int i = 0;
				// 对数据分段加密
				while (inputLen - offSet > 0) {
					if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
						cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
					} else {
						cache = cipher.doFinal(data, offSet, inputLen - offSet);
					}
					out.write(cache, 0, cache.length);
					i++;
					offSet = i * MAX_ENCRYPT_BLOCK;
				}
				return byte64ToStr(out.toByteArray());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 私钥解密
	 * 
	 * @param source
	 *            需解密字符串
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static String decryptByPrivate(String source, String privateKey) {
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			byte[] encryptedData = strToByte64(source);
			byte[] keyBytes = strToByte64(privateKey);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateK);
			int inputLen = encryptedData.length;
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			return out.toString("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 公钥解密
	 * 
	 * @param source
	 *            需要解密字符串
	 * @param publicKey
	 *            公钥
	 * @return
	 */
	public static String decryptByPublic(String source, String publicKey) {
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			byte[] encryptedData = strToByte64(source);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

			byte[] keyBytes = strToByte64(publicKey);
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			Key publicK = keyFactory.generatePublic(x509KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicK);
			int inputLen = encryptedData.length;
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			return out.toString("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static String byte64ToStr(byte[] bytes) throws Exception {
		return new String(Base64.encode(bytes));
	}

	private static byte[] strToByte64(String str) throws Exception {
		return Base64.decode(str);
	}

}
