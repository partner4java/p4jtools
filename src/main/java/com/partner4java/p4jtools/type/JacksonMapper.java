package com.partner4java.p4jtools.type;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * json解析通用类<br/>
 * 默认是不区分大小写，dis结尾的方法是区分大小写的
 * 
 * @author 王昌龙
 * 
 */
public class JacksonMapper extends ObjectMapper {
	private static final long serialVersionUID = 2364182010827462056L;

	private static JacksonMapper mapper = new JacksonMapper();
	private static JacksonMapper mapperDis = new JacksonMapper();

	static {
		// 没有指定bean是否报错
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// this.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		// SerializationConfig.Feature documentation, WRITE_NULL_PROPERTIES
		// feature is deprecated < v2.0 and you should be using
		// SerializationConfig.setSerializationInclusion() anyway. I assume this
		// is why the @SuppressWarnings("deprecation") exists in your code. In
		// Jackson >= v2.0, it has been removed,
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE);

		mapperDis.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapperDis.setSerializationInclusion(Include.NON_EMPTY);
	}

	/**
	 * 获取mapper
	 * 
	 * @return
	 */
	public static JacksonMapper getMapper() {
		return mapper;
	}

	/**
	 * 获取mapper
	 * 
	 * @return
	 */
	public static JacksonMapper getMapperDis() {
		return mapperDis;
	}

	/**
	 * 把字符串转换为对象
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @param valueType
	 *            需要转化的对象类型
	 * 
	 * @return 传入对象类型对象
	 */
	public static <T> T read(String jsonStr, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		return mapper.readValue(jsonStr, valueType);
	}

	/**
	 * 把字符串转换为对象
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @param valueType
	 *            需要转化的对象类型
	 * 
	 * @return 传入对象类型对象
	 */
	public static <T> List<T> readList(String jsonStr, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		return mapper.readValue(jsonStr, mapper.getTypeFactory().constructCollectionType(List.class, valueType));
	}

	/**
	 * 把字符串转换为对象
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @param valueType
	 *            需要转化的对象类型
	 * 
	 * @return 传入对象类型对象
	 */
	public static <T> List<T> readDisList(String jsonStr, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		return mapperDis.readValue(jsonStr, mapperDis.getTypeFactory().constructCollectionType(List.class, valueType));
	}

	/**
	 * 把字符串转换为对象
	 * 
	 * @param jsonStr
	 *            json字符串
	 * 
	 * @return 传入对象类型对象
	 */
	public static <K, V> Map<K, V> readMap(String jsonStr, Class<K> keyType, Class<V> valueType) throws IOException, JsonParseException, JsonMappingException {
		return mapper.readValue(jsonStr, mapper.getTypeFactory().constructMapType(Map.class, keyType, valueType));
	}

	/**
	 * 把字符串转换为对象,区分大小写
	 * 
	 * @param jsonStr
	 *            json字符串
	 * 
	 * @return 传入对象类型对象
	 */
	public static <K, V> Map<K, V> readDisMap(String jsonStr, Class<K> keyType, Class<V> valueType)
			throws IOException, JsonParseException, JsonMappingException {
		return mapperDis.readValue(jsonStr, mapper.getTypeFactory().constructMapType(Map.class, keyType, valueType));
	}

	/**
	 * 把字符串转换为对象
	 * 
	 * @param src
	 *            url地址
	 * @param valueType
	 *            需要转化的对象类型
	 * 
	 * @return 传入对象类型对象
	 */
	public static <T> T read(URL src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		return mapper.readValue(src, valueType);
	}

	/**
	 * 把对象转为json
	 * 
	 * @param obj
	 *            需要转化的对象
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String toStr(Object obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	}

	/**
	 * 把字符串转换为对象
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @param valueType
	 *            需要转化的对象类型
	 * 
	 * @return 传入对象类型对象
	 */
	public static <T> T readDis(String jsonStr, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		return mapperDis.readValue(jsonStr, valueType);
	}

	/**
	 * 把字符串转换为对象
	 * 
	 * @param src
	 *            url地址
	 * @param valueType
	 *            需要转化的对象类型
	 * 
	 * @return 传入对象类型对象
	 */
	public static <T> T readDis(URL src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		return mapperDis.readValue(src, valueType);
	}

	/**
	 * 把字符串转换为对象
	 * 
	 * @param src
	 *            url地址
	 * @param valueType
	 *            需要转化的对象类型
	 * 
	 * @return 传入对象类型对象
	 */
	public static <T> T readDis(InputStream src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		return mapperDis.readValue(src, valueType);
	}

	/**
	 * 把对象转为json
	 * 
	 * @param obj
	 *            需要转化的对象
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String toStrDis(Object obj) throws JsonProcessingException {
		return mapperDis.writeValueAsString(obj);
	}
}
