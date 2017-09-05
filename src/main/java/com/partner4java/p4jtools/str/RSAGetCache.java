package com.partner4java.p4jtools.str;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 获取rsa密钥<br/>
 * 添加缓存
 * 
 * @author 王昌龙
 *
 */
public class RSAGetCache {
	/** 缓冲池 */
	private static List<Map<Integer, String>> rsaList = new LinkedList<Map<Integer, String>>();

	/**
	 * 获取rsa数据
	 */
	public static Map<Integer, String> getRsa() {
		synchronized (RSAGetCache.class) {
			try {
				// 如果缓存有数据，直接返回
				if (rsaList.size() > 0) {
					Map<Integer, String> rsa = rsaList.get(0);
					rsaList.remove(0);

					// 如果缓存数据不超过2个了
					if (rsaList.size() < 2) {
						cacheRsa();
					}
					return rsa;
				}

				// 没有数据缓存数据
				cacheRsa();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return RSAHelper.getKey();
		}
	}

	/**
	 * 添加rsa数据
	 * 
	 * @param rsa
	 *            有效密钥
	 */
	public static void put(Map<Integer, String> rsa) {
		synchronized (RSAGetCache.class) {
			rsaList.add(rsa);
		}
	}

	/**
	 * 一次性异步缓存100条密钥
	 */
	public static void cacheRsa() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 30; i++) {
					put(RSAHelper.getKey());
				}
			}
		}).start();
	}
}
