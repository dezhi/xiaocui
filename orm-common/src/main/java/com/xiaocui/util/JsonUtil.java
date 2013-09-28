package com.xiaocui.util;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	/**
	 * JSONUtil JSON工具
	 */
	private static JsonUtil ju;
	/**
	 * JSONFactory JSON工厂
	 */
	private static JsonFactory jf;
	/**
	 * ObjectMapper 对象映射
	 */
	private static ObjectMapper om;

	private JsonUtil() {
	}

	public static JsonUtil getInstance() {
		if (ju == null)
			ju = new JsonUtil();
		return ju;
	}

	public static ObjectMapper getMapper() {
		if (om == null) {
			om = new ObjectMapper();
		}
		return om;
	}

	public static JsonFactory getFactory() {
		if (jf == null)
			jf = new JsonFactory();
		return jf;
	}

	@SuppressWarnings("deprecation")
	public String obj2json(Object obj) {
		JsonGenerator jg = null;
		try {
			jf = getFactory();
			om = getMapper();
			StringWriter out = new StringWriter();
			jg = jf.createJsonGenerator(out);
			om.writeValue(jg, obj);
			return out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (jg != null)
					jg.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Object json2obj(String json, Class<?> clz) {
		try {
			om = getMapper();
			return om.readValue(json, clz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
