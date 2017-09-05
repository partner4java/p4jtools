package com.partner4java.p4jtools.type;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class CustomJacksonObjectMapper extends ObjectMapper {
	private static final long serialVersionUID = 2364182010827462056L;

	public CustomJacksonObjectMapper() {
		super();
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.setSerializationInclusion(Include.NON_NULL);
		this.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE);
		this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
	}
}