package com.partner4java.p4jtools.xml;

import java.beans.IntrospectionException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.partner4java.p4jtools.type.BeanToMapUtil;

/**
 * xml工具类
 *
 */
public class XMLParser {

	/**
	 * xml转换为map
	 * 
	 * @param xmlString
	 * @return
	 */
	public static Map<String, Object> getMapFromXML(String xmlString) throws ParserConfigurationException, IOException, SAXException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream is = getStringStream(xmlString);
		Document document = builder.parse(is);

		// 获取到document里面的全部结点
		NodeList allNodes = document.getFirstChild().getChildNodes();
		Node node;
		Map<String, Object> map = new HashMap<String, Object>();
		int i = 0;
		while (i < allNodes.getLength()) {
			node = allNodes.item(i);
			if (node instanceof Element) {
				map.put(node.getNodeName(), node.getTextContent());
			}
			i++;
		}
		return map;

	}

	/**
	 * Map转换为xml字符串
	 * 
	 * @param map
	 *            带转换map
	 * @return
	 */
	public static String mapToXml(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString())) {
				sb.append("<").append(entry.getKey()).append(">").append(entry.getValue()).append("</").append(entry.getKey()).append(">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * xml转为实体bean
	 * 
	 * @param xmlString
	 *            xml字符串
	 * @param clazz
	 *            bean类
	 * @return
	 */
	public static <T> T getBeanFromXML(String xmlString, Class<T> clazz)
			throws ParserConfigurationException, IOException, SAXException, InstantiationException, IllegalAccessException, IntrospectionException {

		Map<String, Object> map = getMapFromXML(xmlString);
		return BeanToMapUtil.convertMap(clazz, map);
	}

	/**
	 * 字符串转为输入流
	 * 
	 * @param sInputString
	 *            字符串
	 * @return
	 */
	public static InputStream getStringStream(String sInputString) {
		ByteArrayInputStream tInputStringStream = null;
		if (sInputString != null && !sInputString.trim().equals("")) {
			tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
		}
		return tInputStringStream;
	}

}
