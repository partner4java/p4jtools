package com.partner4java.p4jtools.spring.cache;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.interceptor.DefaultKeyGenerator;

import com.partner4java.p4jtools.generic.GenericsGetHelper;

/**
 * 针对Spring的cache缓存策略制定自定义的key生成策略<br/>
 * 会对所有非空值参数进行遍历，然后进行字符累加（如果为自定义对象，会累加对象的hashCode），最后对累加的字符串取hashCode<br/>
 * <b>注意：</b>如果为自定一定formbean等对象，需要根绝业务重载hashCode。
 * 
 * @author partner4java
 * 
 * @see DefaultKeyGenerator
 */
public class CustomKeyGenerator extends DefaultKeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
		StringBuffer buffer = new StringBuffer();
		@SuppressWarnings("rawtypes")
		Class entityClass = GenericsGetHelper.getSuperGenericsClass(target
				.getClass());
		buffer.append(entityClass.getName());
		if (params != null && params.length > 1) {
			for (Object obj : params) {
				if (obj != null) {
					if (obj instanceof AtomicInteger
							|| obj instanceof AtomicLong
							|| obj instanceof BigDecimal
							|| obj instanceof BigInteger || obj instanceof Byte
							|| obj instanceof Double || obj instanceof Float
							|| obj instanceof Integer || obj instanceof Long
							|| obj instanceof Short) {
						buffer.append(obj);
					} else if (obj instanceof List || obj instanceof Set
							|| obj instanceof Map) {
						buffer.append(obj);
					} else {
						buffer.append(obj.hashCode());
					}
				}
			}
		}

		int keyGenerator = buffer.toString().hashCode();

		Log logger = LogFactory.getLog(CustomKeyGenerator.class);
		if (logger.isDebugEnabled()) {
			logger.debug("key:" + buffer.toString() + " -- " + keyGenerator);
		}

		return keyGenerator;
	}

}
