package com.xiaocui.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

@SuppressWarnings("rawtypes")
public class EnumUtils {
	/**
	 * 将枚举类型转换为相应的name名称字符串列表
	 * 
	 * @param clz
	 * @return
	 */
	public static List<String> enum2Name(Class<? extends Enum> clz) {
		// 判断是否为枚举类型
		if (!clz.isEnum())
			return null;

		Enum[] enums = clz.getEnumConstants();

		List<String> rels = new ArrayList<String>();

		for (Enum en : enums) {
			rels.add(en.name());
		}

		return rels;
	}

	/**
	 * 将枚举中的属性值转换为字符串列表
	 * 
	 * @param clz
	 * @param propName
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static List<String> enumProp2List(Class<? extends Enum> clz,
			String propName) {
		// 判断是否为枚举类型
		if (!clz.isEnum())
			return null;

		try {
			Enum[] enums = clz.getEnumConstants();

			List<String> rels = new ArrayList<String>();

			for (Enum en : enums) {
				rels.add((String) PropertyUtils.getProperty(en, propName));
			}

			return rels;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将枚举中的属性值转换为序号和字符串列表
	 * 
	 * @param clz
	 * @param propName
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static Map<Integer, String> enumProp2OrdinalMap(
			Class<? extends Enum> clz, String propName) {
		// 判断是否为枚举类型
		if (!clz.isEnum())
			return null;

		try {
			Enum[] enums = clz.getEnumConstants();

			Map<Integer, String> rels = new HashMap<Integer, String>();

			for (Enum en : enums) {
				rels.put(en.ordinal(),
						(String) PropertyUtils.getProperty(en, propName));
			}

			return rels;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将枚举中的两个属性转换为Map类型
	 * 
	 * @param clz
	 * @param keyProp
	 * @param valueProp
	 * @return
	 */
	public static Map<String, String> enumProp2Map(Class<? extends Enum> clz,
			String keyProp, String valueProp) {
		// 判断是否为枚举类型
		if (!clz.isEnum())
			return null;

		try {
			Enum[] enums = clz.getEnumConstants();

			Map<String, String> rels = new HashMap<String, String>();

			for (Enum en : enums) {
				rels.put((String) PropertyUtils.getProperty(en, keyProp),
						(String) PropertyUtils.getProperty(en, valueProp));
			}

			return rels;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将枚举中的两个属性转换为name和字符串Map
	 * 
	 * @param clz
	 * @param keyProp
	 * @param valueProp
	 * @return
	 */
	public static Map<String, String> enumProp2NameMap(
			Class<? extends Enum> clz, String keyProp, String valueProp) {
		// 判断是否为枚举类型
		if (!clz.isEnum())
			return null;

		try {
			Enum[] enums = clz.getEnumConstants();

			Map<String, String> rels = new HashMap<String, String>();

			for (Enum en : enums) {
				rels.put(en.name(),
						(String) PropertyUtils.getProperty(en, valueProp));
			}

			return rels;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}
