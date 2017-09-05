package com.partner4java.p4jtools.http;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 发送网络请求获取相应内容
 * 
 * @author 王昌龙
 * 
 */
public class UrlGetContent {

	/**
	 * get请求获取内容
	 * 
	 * @param urlPath
	 * @param params
	 * @param enc
	 * @return
	 */
	public static String getContent(String urlPath, Map<String, Object> params, String enc) {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		InputStreamReader isr = null;
		try {
			URL url = new URL(getURL(urlPath, params, enc));
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", enc);
			conn.connect();

			isr = new InputStreamReader(conn.getInputStream(), enc);
			reader = new BufferedReader(isr);
			StringBuilder returnStr = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				returnStr.append(line);
			}
			return returnStr.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null)
						conn.disconnect();
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					try {
						if (isr != null)
							isr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	public static String getURL(String urlPath, Map<String, Object> params, String enc) {
		StringBuffer buffer = new StringBuffer(urlPath);
		if (!urlPath.contains("?")) {
			buffer.append("?");
		} else {
			buffer.append("&");
		}
		buffer.append(getParams(params, enc));
		return buffer.toString();
	}

	/**
	 * post请求获取内容
	 * 
	 * @param urlPath
	 * @param params
	 * @param enc
	 * @return
	 */
	public static String postContent(String urlPath, Map<String, Object> params, String enc) {
		HttpURLConnection conn = null;
		OutputStreamWriter out = null;
		BufferedReader reader = null;
		InputStreamReader isr = null;
		try {
			URL url = new URL(urlPath);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", enc);
			conn.connect();
			out = new OutputStreamWriter(conn.getOutputStream(), enc);
			String body = getParams(params, enc);
			out.write(body);
			out.flush();

			isr = new InputStreamReader(conn.getInputStream());
			reader = new BufferedReader(isr);
			StringBuilder readJson = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				readJson.append(line);
			}
			return readJson.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null)
						out.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (conn != null)
						conn.disconnect();
					try {
						if (isr != null)
							isr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	/**
	 * POST请求
	 */
	public static String postContent(String urlPath, String content, String enc) {
		HttpURLConnection conn = null;
		OutputStreamWriter out = null;
		BufferedReader reader = null;
		InputStreamReader isr = null;
		try {
			URL url = new URL(urlPath);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", enc);
			conn.connect();
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			// String body = getParams(params, enc);
			out.write(content);
			out.flush();

			isr = new InputStreamReader(conn.getInputStream());
			reader = new BufferedReader(isr);
			StringBuilder readJson = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				readJson.append(line);
			}
			return readJson.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null)
						out.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (conn != null)
						conn.disconnect();
					try {
						if (isr != null)
							isr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 下载网络资源文件
	 * 
	 * @param urlPath
	 *            url地址
	 * @param saveFile
	 *            保存路径(调用方需保证目录的存在)
	 * @return
	 */
	public static boolean download(String urlPath, String saveFile) {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			url = new URL(urlPath);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);

			inStream = conn.getInputStream();
			fs = new FileOutputStream(saveFile);

			int byteread = 0;
			byte[] buffer = new byte[1204];
			while ((byteread = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fs != null) {
					fs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (inStream != null) {
						inStream.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (conn != null) {
						conn.disconnect();
					}
				}
			}
		}
		return false;
	}

	/**
	 * 将map转换成url
	 * 
	 * @param map
	 * @return
	 */
	private static String getParams(Map<String, Object> map, String enc) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			try {
				if (StringUtils.isNotEmpty(entry.getKey()) && StringUtils.isNotEmpty(entry.getValue().toString())) {
					sb.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue().toString(), enc));
					sb.append("&");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}

}
